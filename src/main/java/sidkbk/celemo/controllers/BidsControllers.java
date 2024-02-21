package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.services.BidsServices;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bids")
public class BidsControllers {

    @Autowired
    BidsServices bidsServices;

    // Post a new bid
    @PostMapping("/post")
    public ResponseEntity<?> createBids(
            @Valid @RequestBody Bids bids) {
        try {
            return ResponseEntity.ok(bidsServices.createBids(bids));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Find bid by id
    @GetMapping("/find/{id}")
    public ResponseEntity<?> listOneSpecificReview(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(bidsServices.findOne(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // find all bids
    @GetMapping("/find")
    public List<Bids>findAllBids(){
        return bidsServices.findAllBids();
    }

    //Update by id
    @PutMapping("/put/{id}")
    public Bids updateBids(@RequestBody Bids bids, @PathVariable("id") String _id){
        return  bidsServices.updateBids(bids);
    }

    //Delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBids(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(bidsServices.deleteBids(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
