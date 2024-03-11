package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.auctions.AuctionCreationDTO;
import sidkbk.celemo.dto.auctions.AuctionIdDTO;
import sidkbk.celemo.dto.auctions.AuctionUpdateDTO;
import sidkbk.celemo.dto.user.FindUserIdDTO;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.BidsRepository;
import sidkbk.celemo.repositories.OrderRepository;
import sidkbk.celemo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Service
public class AuctionService {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BidsRepository bidsRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserService userService;

    public Auction createAuction(AuctionCreationDTO auctionCreationDTO) {
        User findUser = userRepository.findById(auctionCreationDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("Couldn't find user."));
        Auction newAuction = new Auction();
        newAuction.setSellerId(userRepository.findById(auctionCreationDTO.getSellerId()).get());
        newAuction.setSellerId(findUser);
        newAuction.setTitle(auctionCreationDTO.getTitle());
        newAuction.setProductDescription(auctionCreationDTO.getProductDescription());
        newAuction.setProductPhoto(auctionCreationDTO.getProductPhoto());
        newAuction.setCelebrityName(auctionCreationDTO.getCelebrityName());
        newAuction.setStartPrice(auctionCreationDTO.getStartPrice());
        newAuction.setCategoryList(auctionCreationDTO.getCategoryList());
        return auctionRepository.save(newAuction);
    }
    // READ ALL
    public List<Auction> getAllAuctions(){
        return auctionRepository.findAll();
    }

    // get All auctions from User
    public List<Auction> getAllAuctionsFromUser(FindUserIdDTO findUserIdDTO) {
        return auctionRepository.findAuctionBySeller(findUserIdDTO.getUserId());
    }

    // READ 1 from user
    public Auction getOneAuction(AuctionIdDTO auctionIdDTO){
       return auctionRepository.findById(auctionIdDTO.getAuctionId()).get();
    }
    // PUT
    public Auction updateAuction(AuctionUpdateDTO auctionUpdateDTO){
         return auctionRepository.findById(auctionUpdateDTO.getAuctionId())
                .map(updatedAuction -> {
                    if (auctionUpdateDTO.getProductDescription() != null) {
                        updatedAuction.setProductDescription(auctionUpdateDTO.getProductDescription());
                    }
                    if (auctionUpdateDTO.getProductPhoto() != null) {
                        updatedAuction.setProductPhoto(auctionUpdateDTO.getProductPhoto());
                    }
                    if (auctionUpdateDTO.getCelebrityName() != null) {
                        updatedAuction.setCelebrityName(auctionUpdateDTO.getCelebrityName());
                    }
                    return auctionRepository.save(updatedAuction);
                }).orElseThrow(() -> new RuntimeException("Auction not found"));
    }
    // Takes all auctions in repository, checks for "isFinished" flag, if true skips, if false adds.
    public List<Auction> getActiveAuction(FindUserIdDTO findUserIdDTO){
        List<Auction> auctionList = auctionRepository.findAll();
        List<Auction> activeAuctionList = new ArrayList<>();
        for (int i = 0; i < auctionList.size(); i++) {
            if (!auctionList.get(i).isFinished && Objects.equals(auctionList.get(i).getSeller(), findUserIdDTO.getUserId())){
                activeAuctionList.add(auctionList.get(i));
            }
        }
        return activeAuctionList;
    }
    public List<Auction> getFinishedAuctions(FindUserIdDTO findUserIdDTO){
        List<Auction> auctionList = auctionRepository.findAll();
        List<Auction> finishedAuctionList = new ArrayList<>();
        for (int i = 0; i < auctionList.size(); i++) {
            if (auctionList.get(i).isFinished && Objects.equals(auctionList.get(i).getSeller(), findUserIdDTO.getUserId())){
                finishedAuctionList.add(auctionList.get(i));
            }
        }
        return finishedAuctionList;
    }
    // DELETE 1 by id
    public ResponseEntity<?> deleteAuction(AuctionIdDTO auctionIdDTO) {
        auctionRepository.findById(auctionIdDTO.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction not found!"));
        //auto remove from users favourite list
        userService.removeFavouriteAuctionFromUsers(auctionIdDTO.getAuctionId());
        auctionRepository.deleteById(auctionIdDTO.getAuctionId());
        return ResponseEntity.status(HttpStatus.OK).body("Auction deleted!");
    }

    // Delete all to drop clean collection remotely (only for testing don't keep to production)
    public void deleteAllAuctions(){
        auctionRepository.deleteAll();
    }
}
