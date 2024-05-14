package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.Bids.BidsDTO;
import sidkbk.celemo.dto.Bids.FindBidIdDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.services.BidsServices;

import java.util.List;
/*@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")*/
@RestController
@RequestMapping(value = "/api/bids")
public class BidsControllers {

    @Autowired
    BidsServices bidsServices;

// USER
//////////////////////////////////////////////////////////////////////////////////////

    // Post a new Bid
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createBids(@RequestBody BidsDTO bidsDTO){
        try {
            return bidsServices.createBids(bidsDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    // find all bid for one user
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/find/all-user/{userId}")
    public ResponseEntity<?> findAllBidsForUser(@Valid @PathVariable("userId") String userId){
        List<Bids> foundBids = bidsServices.findAllBidsForUser(userId);
        if (foundBids.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no bids found");
        }else{
            return ResponseEntity.ok().body(foundBids);
        }
    }

// ADMIN
//////////////////////////////////////////////////////////////////////////////////////

    //Update by id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBids(@RequestBody BidsDTO bidsDTO, @PathVariable("id") String _id) {
        try {
            return  ResponseEntity.ok(bidsServices.updateBids(bidsDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Find by BidId
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-one/{bidId}")
    public ResponseEntity<?> findOne(@Valid @PathVariable("bidId") String bidId){
        try {
            return ResponseEntity.ok(bidsServices.findOne(bidId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // find all bids
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/all")
    public ResponseEntity<?> findAllBids() {
        try {
            return ResponseEntity.ok(bidsServices.findAllBids());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // GET all bids on an auction
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/find/byauction/{auctionId}")
    public ResponseEntity<?> findByAuction(@PathVariable("auctionId") String auctionId) {
        List<Bids> foundByAuction = bidsServices.findByAuction(auctionId);
        if (foundByAuction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find any bids");
        } else {
            return ResponseEntity.ok(foundByAuction);
        }
    }
    @GetMapping("/find/bidsamount/{auctionId}")
    public ResponseEntity<?> findAmountBids(@PathVariable("auctionId") String auctionId) {
        List<Bids> foundByAuction = bidsServices.findByAuction(auctionId);
        if (foundByAuction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find any bids");
        } else {
            return ResponseEntity.ok(foundByAuction.size());
        }
    }

    //Delete by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBids(@Valid @RequestBody FindBidIdDTO findBidIdDTO){
        try {
            return ResponseEntity.ok(bidsServices.deleteBids(findBidIdDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
