package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Order;
import sidkbk.celemo.models.User;
import sidkbk.celemo.models.Auction;
import sidkbk.celemo.models.Reports;
import sidkbk.celemo.repositories.UserRepository;
import sidkbk.celemo.repositories.AuctionRepository;
import sidkbk.celemo.repositories.ReportsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReportsServices {

    @Autowired
    ReportsRepository reportsRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;


    public Reports createReport(String reportingUserId,String reportedUserId,String auction, Reports reports){
        if (!auction.isEmpty()) {
            Auction foundAuction = auctionRepository.findById(auction)
                    .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
            reports.setAuction(foundAuction);
            reportedUserId = foundAuction.getSellerId();
        }
            User foundreportedUser = userRepository.findById(reportedUserId)
                    .orElseThrow(() -> new RuntimeException("User does not exist!"));
            reports.setReportedUserId(foundreportedUser);
        User foundreportinguser = userRepository.findById(reportingUserId)
                .orElseThrow(()-> new RuntimeException("User does not exist!"));

        reports.setReportingUserId(foundreportinguser);
        return reportsRepository.save(reports);
    }

    public List<Reports> findAllReports(){
        return reportsRepository.findAll();
    }

    public Reports findOne(String id) {
        Reports foundReport = reportsRepository.findById(id).orElseThrow(() -> new RuntimeException("Order was not found"));
        return foundReport;
    }

    public Reports updateReport(String orderId, Reports updatedReport) {
        return reportsRepository.findById(orderId)
                .map(existingOrder -> {
                    if (updatedReport.getReportingUserId() != null) {
                        existingOrder.setReportingUserId(updatedReport.getReportingUserId());
                    }
                    if (updatedReport.getReportedUserId() != null) {
                        existingOrder.setReportedUserId(updatedReport.getReportedUserId());
                    }
                    if (updatedReport.getAuction() != null) {
                        existingOrder.setAuction(updatedReport.getAuction());
                    }
                    return reportsRepository.save(existingOrder);
                }).orElseThrow(() -> new RuntimeException("Order was not found"));
    }

    public String deleteReport(String id){
        reportsRepository.deleteById(id);
        return "Deleted successfully!";
    }

}
