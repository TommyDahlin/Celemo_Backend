package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Bids;
import sidkbk.celemo.repository.BidsRepository;

import java.util.List;

@Service
public class BidsServices {

    @Autowired
    BidsRepository bidsRepository;


    public Bids createBids(Bids bids){
        return bidsRepository.save(bids);
    }

    public List<Bids>findAllBids(){
        return bidsRepository.findAll();
    }

    public Bids findOne(String id){
        return bidsRepository.findById(id).get();
    }

    public Bids updateBids(Bids bids){
        return bidsRepository.save(bids);
    }

    public String deleteBids(String id){
        bidsRepository.deleteById(id);
        return "Deleted successfully!";
    }

}
