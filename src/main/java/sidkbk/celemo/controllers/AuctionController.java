package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.services.AuctionService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/auction")
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    // POST create new order
    @PostMapping("/post")
    public ResponseEntity<?> createAuction(@Valid @RequestBody Auction auction) {
        try{
            return ResponseEntity.ok(auctionService.createAuction(auction));
        }catch (EntityNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get all orders
    @GetMapping("/find")
    public List<Auction> getAllAuctions() {
        return auctionService.getAllAuctions();
    }

    // GET one order
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getAuction(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(auctionService.getOneAuction(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PUT update order by ID
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateAuction(@PathVariable("id") String auctionId,
                                          @Valid @RequestBody Auction auction) {
        try {
            return ResponseEntity.ok(auctionService.updateAuction(auction));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // DELETE order by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAuction(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(auctionService.deleteAuction(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/deleteAll")
    public void deleteAllAuctions(){
        auctionService.deleteAllAuctions();
    }
}
