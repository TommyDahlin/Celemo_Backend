package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.services.SearchService;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    SearchService searchService;

// PUBLIC
//////////////////////////////////////////////////////////////////////////////////////

    // Search function
    @GetMapping("/{search}")
    public ResponseEntity<?> search(@PathVariable("search") String search) {
        List<Auction> foundAuctions = searchService.search(search);
        if (foundAuctions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nothing found!");
        } else {
            return ResponseEntity.ok(foundAuctions);
        }
    }

    // Search with pagination
    @GetMapping("/{search}/{pageSize}/page/{pagenumber}")
    public ResponseEntity<?> searchPage(@PathVariable("search") String search,
                                        @PathVariable("pageSize") int pageSize,
                                        @PathVariable("pagenumber") int pageNr) {
        List<Auction> foundAuctions = searchService.searchPage(search, pageSize, pageNr);
        if (foundAuctions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nothing found!");
        }

        else {
            return ResponseEntity.ok(foundAuctions);
        }
    }

}
