package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.Bids.BidsDTO;
import sidkbk.celemo.dto.Bids.FindBidIdDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BidsServices {

    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BidsServiceHelper bidsServiceHelper;

// Find all bids
    public List<Bids>findAllBids(){
        return bidsRepository.findAll();
    }


    // Create a bids using price, userId and listingId
    public ResponseEntity<?> createBids(BidsDTO bidsDTO) {
        // gets DTO, checks auction id from auction-repo
        Auction foundAuction = auctionRepository.findById(bidsDTO.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        bidsServiceHelper.checkFinished(foundAuction);
        // gets DTO, checks user from user-repo
        User foundUser = userRepository.findById(bidsDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        // auction owner check
        bidsServiceHelper.checkAuctionOwner(foundAuction, foundUser);
        // makes new bid object
        Bids newBid = new Bids();
        newBid.setUser(foundUser.getId());
        newBid.setStartPrice(bidsDTO.getStartBid());
        newBid.setAuctionId(bidsDTO.getAuctionId());
        // Checks if price is higher or lower than previous bid
        newBid = BidsServiceHelper.bidMaxPriceCheck(bidsDTO, newBid);
        // Checks 3 things unfortunately,
        // 1. Check if startBid and maxBid is higher than auction startPrice,
        // 2. Checks if users balance is valid,
        // 3. Checks if users balance is less than starting bid
        bidsServiceHelper.bidOkCheck(bidsDTO, foundAuction, foundUser);
        // checks if auction has a bid
        if (foundAuction.isHasBids() && foundAuction.getBid() != null) {
            // checks if user has the same id as the owner of the auction. auction owner is not supposed to be able to bid on their own auction.
            if (foundAuction.getSeller().equals(newBid.getUser())) {
                return ResponseEntity.ok("You can't bid on your own auction!");
            } else {
                // Gets the current bid from auction.
                Bids auctionCurrentBid = bidsRepository.findById(foundAuction.getBid()).get();
                // Gets the user from the current bid from auction.
                Optional<User> currentBidUser = userRepository.findById(auctionCurrentBid.getUser());
                // Checks if userID is the same as previous bidder.
                if (currentBidUser.get().getId().equals(newBid.getUser()) || newBid.getUser().equals(currentBidUser.get().getId())) {
                    return ResponseEntity.ok("You can't bid twice in a row.");
                } else {

                    // Switch check method compares Maxbid from both auctions, depending on who wins moves to the correct case.
                    switch (bidsServiceHelper.bidWinCheck(auctionCurrentBid, newBid)) {
                        // User Loses max bid.
                        case 1:
                            // User loses or matches.
                            bidsServiceHelper.userLoses(newBid, auctionCurrentBid, foundAuction);
                            break;
                        // User Wins.
                        case 2:
                            bidsServiceHelper.userWins(newBid, auctionCurrentBid, currentBidUser, foundAuction, foundUser);
                            break;
                        }
                }
            }
        }
            // There are no previous bidders and the user has put a valid bid, wins automatically.
            bidsServiceHelper.noPreviousBidsWin(foundUser, newBid, foundAuction);
            return ResponseEntity.ok("finished");
    }


//Find a bids by id
    public Bids findOne(String bidId){
        return bidsRepository.findById(bidId).get();
    }

// delete a bids
    public String deleteBids(FindBidIdDTO findBidIdDTO){
        bidsRepository.deleteById(findBidIdDTO.getbidId());
        return "Deleted successfully!";
    }

// update a bid
    public Bids updateBids(BidsDTO bidsDTO) {
        User foundUser = userRepository.findById(bidsDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        Auction foundAuction = auctionRepository.findById(bidsDTO.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        Bids newUpdate = new Bids();
        newUpdate.setAuctionId(foundAuction.getId());
        newUpdate.setUser(foundUser.getId());
        return bidsRepository.save(newUpdate);
    }

    public List<Bids> findAllBidsForUser(String userId){
        // Find user
        userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        // Skapa en tom lista för hittade bids
        List<Bids> foundBids = new ArrayList<>();
        // Spara alla bids i en lista
        List<Bids> allBids = bidsRepository.findAll();
        // For loop igenom alla bids och kolla efter bids som matchar med userid
        for (Bids bids : allBids){
            if (bids.getUser() != null && bids.getUser().equals(userId)) {
                foundBids.add(bids);
            }
        }
        return foundBids;
    }

    public List<Bids> findByAuction(String auctionId) {
        return bidsRepository.findBidsByAuctionId(auctionId);
    }
}
