package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.search.SearchDTO;
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
    public List<Auction> search(SearchDTO searchDTO) {
        List<Auction> allAuctions = auctionRepository.findAll(); // Temp add all auctions to a list.
        List<Auction> foundAuctions = new ArrayList<>(); // List to return when something is found.
        // Check first if search term is a ENUM Category, therefore check if search is uppercase.
        if (searchDTO.getSearch().toUpperCase().equals(searchDTO.getSearch())) {
            for (Auction auction : allAuctions) { // Loop all auctions
                // Temp add categories to current auction loop
                List<ECategory> foundCategories = auction.categoryList;
                // Loop through all categories in the current auction loop and if category match search
                // add that auction to foundAuctions
                for (ECategory cat : foundCategories) {
                    if (cat.name().equals(searchDTO.getSearch())) {
                        foundAuctions.add(auction);
                    }
                }
            }
            return foundAuctions;
        }
        if (!searchDTO.getSearch().toUpperCase().equals(searchDTO.getSearch())) { // If "search" is not all uppercase
            for (Auction auction : allAuctions) { // Loop all auctions
                // If auction title contains any word of the "search"
                if (auction.getTitle().toLowerCase().contains(searchDTO.getSearch().toLowerCase())) {
                    foundAuctions.add(auction);
                }
            }
        }
        return foundAuctions;
    }

    public List<Auction> searchPage(int pageNr, SearchDTO searchDTO) {
        Pageable paging = PageRequest.of(pageNr, searchDTO.getPageSize());
        List<Auction> foundAuctions = new ArrayList<>();
        if (searchDTO.getSearch().isEmpty()) {
            Page<Auction> page = auctionRepository.findAll(PageRequest.of(pageNr, searchDTO.getPageSize()));
            foundAuctions = page.toList();

        } else if (searchDTO.getSearch().toUpperCase().equals(searchDTO.getSearch())) {
            foundAuctions = auctionRepository.findByCategoryListContains(searchDTO.getSearch(),paging);
        } else {
            foundAuctions = auctionRepository.findByTitleContainsIgnoreCase(searchDTO.getSearch(), paging);
        }

        return foundAuctions;
    }
}
