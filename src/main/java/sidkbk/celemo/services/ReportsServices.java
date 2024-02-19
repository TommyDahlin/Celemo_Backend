package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Account;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Reports;
import sidkbk.celemo.repositories.AccountRepository;
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
    AccountRepository accountRepository;


    public Reports createReport(Reports reports){
        Auction foundAuction = auctionRepository.findById(reports.getAuctionId())
                .orElseThrow(()-> new RuntimeException("Auction does not exist!"));
        Account foundreportinguser = accountRepository.findById(reports.getReoprtingUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist!"));
        Account foundreportedUser = accountRepository.findById(reports.getReportedUserId())
                .orElseThrow(()-> new RuntimeException("User does not exist!"));

        reports.setAuction(foundAuction);
        reports.setAccount(foundreportedUser);
        reports.setAccount(foundreportinguser);
        return reportsRepository.save(reports);
    }

    public List<Reports> findAllReports(){
        return reportsRepository.findAll();
    }


    public Reports findOne(String id){
        return reportsRepository.findById(id).get();
    }



    public Reports updateReport(Reports reports) {
        Auction foundAuction = auctionRepository.findById(reports.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        Account foundreportinguser = accountRepository.findById(reports.getReoprtingUserId())
                .orElseThrow(() -> new RuntimeException("Reportinguser does not exist!"));
        Account foundreportedUser = accountRepository.findById(reports.getReportedUserId())
                .orElseThrow(() -> new RuntimeException("ReportedUser does not exist!"));

        reports.setAuction(foundAuction);
        reports.setAccount(foundreportedUser);
        reports.setAccount(foundreportinguser);
        return reportsRepository.save(reports);
    }


    public String deleteReport(String id){
        reportsRepository.deleteById(id);
        return "Deleted successfully!";
    }

}
