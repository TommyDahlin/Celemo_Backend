package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.services.AuctionService;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AuctionController {
    @Autowired
    AuctionService auctionService;
}
