package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.auctions.AuctionCreationDTO;
import sidkbk.celemo.dto.auctions.AuctionIdDTO;
import sidkbk.celemo.dto.auctions.AuctionUpdateDTO;
import sidkbk.celemo.dto.user.FindUserIdDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.services.AuctionService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/auction")
public class AuctionController {

    @Autowired
    AuctionService auctionService;

// PUBLIC
//////////////////////////////////////////////////////////////////////////////////////

    // Get all auctions
    @GetMapping("/find/all")
    public ResponseEntity<?> getAllAuctions() {
        try {
            return ResponseEntity.ok(auctionService.getAllAuctions());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


// USER
//////////////////////////////////////////////////////////////////////////////////////

    // POST create new auction
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createAuction(@Valid @RequestBody AuctionCreationDTO auctionCreationDTO) {
        try {
            return ResponseEntity.ok(auctionService.createAuction(auctionCreationDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get all auctions from user
    @GetMapping("/find/all/user")
    public ResponseEntity<?> getAllAuctionsFromUser(@Valid @RequestBody FindUserIdDTO findUserIdDTO) {

        List<Auction> foundAuctions = auctionService.getAllAuctionsFromUser(findUserIdDTO);
        if (foundAuctions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not have any auctions...");
        } else {
            return ResponseEntity.ok(foundAuctions);
        }

    }



    // PUT update an auction
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateAuction(@Valid @RequestBody AuctionUpdateDTO auctionUpdateDTO) {
        try {
            return ResponseEntity.ok(auctionService.updateAuction(auctionUpdateDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE an auction
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAuction(@Valid @RequestBody AuctionIdDTO auctionIdDTO) {
        return auctionService.deleteAuction(auctionIdDTO);
    }

// ADMIN
//////////////////////////////////////////////////////////////////////////////////////

    // GET one auction
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-one")
    public ResponseEntity<?> getAuction(@Valid @RequestBody AuctionIdDTO auctionIdDTO) {
        try {
            return ResponseEntity.ok(auctionService.getOneAuction(auctionIdDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //REMOVE BEFORE PRODUCTION
    @DeleteMapping("/dev/deleteAll")
    public void deleteAllAuctions() {
        auctionService.deleteAllAuctions();
    }
}
