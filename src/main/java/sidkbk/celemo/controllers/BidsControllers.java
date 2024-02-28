package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.BidsDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.services.BidsServices;

@RestController
@RequestMapping(value = "/api/bids")
public class BidsControllers {

    @Autowired
    BidsServices bidsServices;

    // Post a new book
    @PostMapping("/post")
    public ResponseEntity<?> createBids(@RequestBody BidsDTO bidsDTO){
        try {
            return ResponseEntity.ok(bidsServices.createBids(bidsDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Find by book
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id){
        try {
            return ResponseEntity.ok(bidsServices.findOne(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // find all bids
    @GetMapping("/find")
    public ResponseEntity<?> findAllBids() {
        try {
            return ResponseEntity.ok(bidsServices.findAllBids());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBids(@PathVariable String id){
        try {
            return ResponseEntity.ok(bidsServices.deleteBids(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
