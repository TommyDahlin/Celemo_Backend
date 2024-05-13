package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Auction> search(String search) {
        List<Auction> allAuctions = auctionRepository.findAll(); // Temp add all auctions to a list.
        List<Auction> foundAuctions = new ArrayList<>(); // List to return when something is found.
        if (search.equals("getall")) {
           foundAuctions = allAuctions;
        }
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
            return foundAuctions;
        }
        else { // If "search" is not all uppercase
            for (Auction auction : allAuctions) { // Loop all auctions
                // If auction title contains any word of the "search"
                if (auction.getTitle().toLowerCase().contains(search.toLowerCase())) {
                    foundAuctions.add(auction);
                }
            }
        }
        return foundAuctions;
    }

    public List<Auction> searchPage(String search, int pageSize,  int pageNr) {
        Pageable paging = PageRequest.of(pageNr, pageSize);
        List<Auction> foundAuctions = new ArrayList<>();
        if (search.equals("getall")) {
            Page<Auction> page = auctionRepository.findAll(PageRequest.of(pageNr, pageSize));
            foundAuctions = page.toList();

        } else if (search.toUpperCase().equals(search)) {
            foundAuctions = auctionRepository.findByCategoryListContains(search,paging);
        } else {
            foundAuctions = auctionRepository.findByTitleContainsIgnoreCase(search, paging);
        }

        return foundAuctions;
    }
}
