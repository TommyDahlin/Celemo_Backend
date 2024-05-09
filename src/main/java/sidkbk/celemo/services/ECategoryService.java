package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.ECategory;
import sidkbk.celemo.repositories.AuctionRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ECategoryService {

    @Autowired
    AuctionRepository auctionRepository;

    public List<ECategory> getAllCategory() {
        return Arrays.asList(ECategory.values());
    }



}
