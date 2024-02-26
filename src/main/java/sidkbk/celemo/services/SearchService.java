package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.ECategory;
import sidkbk.celemo.repositories.AuctionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    AuctionRepository auctionRepository;

    // Search
    public ResponseEntity<?> search(String search) {
        String err = "Nothing found!";

        List<Auction> allAuctions = auctionRepository.findAll(); // Temp add all auctions to a list.
        List<Auction> foundAuctions = new ArrayList<>(); // List to return when something is found.
        // Check first if search term is a ENUM Category, therefore check if search is uppercase.
        if (search.toUpperCase().equals(search)) {
            for (Auction auction : allAuctions) { // Loop all auctions
                // Temp add categories to current auction loop
                List<ECategory> foundCategories = auction.categoryList;
                // Loop through all categories in the current auction loop and if category match search
                // add that auction to foundAuctions
                for (ECategory cat : foundCategories) {
                    if (cat.name().equals(search)) {
                        foundAuctions.add(auction);
                    }
                }
            }
            return ResponseEntity.ok(foundAuctions);
        }
        if (!search.toUpperCase().equals(search)) { // If "search" is not all uppercase
            for (Auction auction : allAuctions) { // Loop all auctions
                // If auction title contains any word of the "search"
                if (auction.getTitle().toLowerCase().contains(search)) {
                    foundAuctions.add(auction);
                }
            }
            // If something was found in an auctions title, return found auctions.
            if (!foundAuctions.isEmpty()) {
                return ResponseEntity.ok(foundAuctions);
            }

        }
        // If nothing is found, return error.
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
