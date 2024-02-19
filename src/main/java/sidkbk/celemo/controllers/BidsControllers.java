package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.services.BidsServices;

import java.util.List;

@RestController
@RequestMapping(value = "/api/bids")
public class BidsControllers {

    @Autowired
    BidsServices bidsServices;

    // Post a new book
    @PostMapping("/post")
    public Bids createBids(@RequestBody Bids bids){
        return bidsServices.createBids(bids);
    }

    //Find by book
    @GetMapping("/find/{id}")
    public Bids findOne(@PathVariable String id){
        return bidsServices.findOne(id);
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
    public String deleteBids(@PathVariable String id){
        return bidsServices.deleteBids(id);
    }



}
