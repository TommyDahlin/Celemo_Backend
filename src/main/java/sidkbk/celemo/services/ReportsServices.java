package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.User;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Reports;
import sidkbk.celemo.repositories.UserRepository;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.ReportsRepository;

import java.util.List;

@Service
public class ReportsServices {

    @Autowired
    ReportsRepository reportsRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;


    public Reports createReport(String reportingUserId,String reportedUserId,String auction, Reports reports){
        Auction foundAuction = auctionRepository.findById(auction)
                .orElseThrow(()-> new RuntimeException("Auction does not exist!"));
        User foundreportinguser = userRepository.findById(reportingUserId)
                .orElseThrow(()-> new RuntimeException("User does not exist!"));
        User foundreportedUser = userRepository.findById(reportedUserId)
                .orElseThrow(()-> new RuntimeException("User does not exist!"));

        reports.setAuction(foundAuction);
        reports.setReportedUserId(foundreportedUser);
        reports.setReportingUserId(foundreportinguser);
        return reportsRepository.save(reports);
    }

    public List<Reports> findAllReports(){
        return reportsRepository.findAll();
    }

    public Reports findOne(String id){
        return reportsRepository.findById(id).get();
    }

    public Reports updateReport( Reports reportst){
       return reportsRepository.save(reportst);
    }

    public String deleteReport(String id){
        reportsRepository.deleteById(id);
        return "Deleted successfully!";
    }

}
