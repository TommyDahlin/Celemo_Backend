package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.search.SearchDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.services.SearchService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    SearchService searchService;

// PUBLIC
//////////////////////////////////////////////////////////////////////////////////////

    // Search function
    @GetMapping("/")
    public ResponseEntity<?> search(@Valid @RequestBody SearchDTO searchDTO) {
        List<Auction> foundAuctions = searchService.search(searchDTO);
        if (foundAuctions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nothing found!");
        } else {
            return ResponseEntity.ok(foundAuctions);
        }
    }

    // Search with pagination
    @GetMapping("/page/{pagenumber}")
    public ResponseEntity<?> searchPage(@PathVariable("pagenumber") int pageNr,
                                        @Valid @RequestBody SearchDTO searchDTO) {
        List<Auction> foundAuctions = searchService.searchPage(pageNr, searchDTO);
        if (foundAuctions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nothing found!");
        }

        else {
            return ResponseEntity.ok(foundAuctions);
        }
    }

}
