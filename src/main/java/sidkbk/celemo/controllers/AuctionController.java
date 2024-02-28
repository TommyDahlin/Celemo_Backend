package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.auctions.AuctionCreationDTO;
import sidkbk.celemo.dto.auctions.AuctionIdDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.services.AuctionService;

@RestController
@RequestMapping(value = "/api/auction")
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    // POST create new auction
    @PostMapping("/post")
    public ResponseEntity<?> createAuction(@Valid @RequestBody AuctionCreationDTO auctionCreationDTO) {
        try{
            return ResponseEntity.ok(auctionService.createAuction(auctionCreationDTO));
        }catch (EntityNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get all auctions
    @GetMapping("/find/all")
    public ResponseEntity<?> getAllAuctions() {
        try {
            return ResponseEntity.ok(auctionService.getAllAuctions());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET one auction
    @GetMapping("/find")
    public ResponseEntity<?> getAuction(@Valid @RequestBody AuctionIdDTO auctionIdDTO) {
        try {
            return ResponseEntity.ok(auctionService.getOneAuction(auctionIdDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PUT update an auction
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateAuction(@PathVariable("id") String auctionId,
                                          @Valid @RequestBody Auction auction) {
        try {
            return ResponseEntity.ok(auctionService.updateAuction(auction));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // DELETE an auction
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAuction(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(auctionService.deleteAuction(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //REMOVE BEFORE PRODUCTION
    @DeleteMapping("/deleteAll")
    public void deleteAllAuctions(){
        auctionService.deleteAllAuctions();
    }
}
