package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.services.AuctionService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/auction")
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    // POST create new order
    @PostMapping("/add")
    public Auction createAuction(@RequestBody Auction auction) {
        return auctionService.createAuction(auction);
    }

    // Get all orders
    @GetMapping("/find")
    public List<Auction> getAllAuctions() {
        return auctionService.getAllAuctions();
    }

    // GET one order
    @GetMapping("/find/{id}")
    public Auction getAuction(@PathVariable String id) {
        return auctionService.getOneAuction(id);
    }

    // PUT update order by ID
    @PutMapping("/update/{id}")
    public Auction updateAuction(@RequestBody Auction auction, @PathVariable("id") String _id) {
        return auctionService.updateAuction(auction);
    }

    // DELETE order by ID
    @DeleteMapping("/delete/{id}")
    public String deleteAuction(@PathVariable String id) {
        return auctionService.deleteAuction(id);
    }
}
