package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.Bids.BidsDTO;
import sidkbk.celemo.dto.Bids.FindBidIdDTO;
import sidkbk.celemo.dto.auctions.AuctionIdDTO;
import sidkbk.celemo.dto.user.FindUserIdDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.services.BidsServices;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bids")
public class BidsControllers {

    @Autowired
    BidsServices bidsServices;

    // Post a new Bid
    @PostMapping("/post")
    public ResponseEntity<?> createBids(@RequestBody BidsDTO bidsDTO){
        try {
            return bidsServices.createBids(bidsDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Find by BidId
    @GetMapping("/find")
    public ResponseEntity<?> findOne(@Valid @RequestBody FindBidIdDTO findBidIdDTO){
        try {
            return ResponseEntity.ok(bidsServices.findOne(findBidIdDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // find all bids
    @GetMapping("/find/all")
    public ResponseEntity<?> findAllBids() {
        try {
            return ResponseEntity.ok(bidsServices.findAllBids());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/find/byauction")
    public ResponseEntity<?> findByAuction(@RequestBody AuctionIdDTO auctionIdDTO) {
        List<Bids> foundByAuction = bidsServices.findByAuction(auctionIdDTO);
        if (foundByAuction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find any bids");
        } else {
            return ResponseEntity.ok(foundByAuction);
        }
    }

    //Update by id
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateBids(@RequestBody BidsDTO bidsDTO, @PathVariable("id") String _id) {
        try {
            return  ResponseEntity.ok(bidsServices.updateBids(bidsDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    //Delete by id
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBids(@Valid @RequestBody FindBidIdDTO findBidIdDTO){
        try {
            return ResponseEntity.ok(bidsServices.deleteBids(findBidIdDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // find all bid for one user

    @GetMapping("/find/all-user")
    public ResponseEntity<?> findAllBidsForUser(@Valid @RequestBody FindUserIdDTO findUserIdDTO){
        List<Bids> foundBids = bidsServices.findAllBidsForUser(findUserIdDTO);
        if (foundBids.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no bids found");
        }else{
            return ResponseEntity.ok().body(foundBids);
        }
    }
}
