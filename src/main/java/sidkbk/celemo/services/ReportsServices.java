package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.ReportsDTO;
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


    public ResponseEntity<Reports> createReportUser(ReportsDTO reportsDTO) {
        User foundReportedUser = userRepository.findById(reportsDTO.getId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        User foundReportingUser = userRepository.findById(reportsDTO.getId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        reportsDTO.setReportedUserId(reportsDTO.getReportingUserId());
        reportsDTO.setReportingUserId(reportsDTO.getReportingUserId());
        Auction foundReportedAuction = auctionRepository.findById(reportsDTO.getId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        Reports newReport = new Reports();
        newReport.setReportedUserId(foundReportedUser);
        newReport.setReportingUserId(foundReportingUser);
        newReport.setAuction(foundReportedAuction);
        newReport.setContent(reportsDTO.getContent());

        return ResponseEntity.ok(reportsRepository.save(newReport));
    }

    public Reports createReportAuction(Reports report, String reportingUser, String reportedAuction) {
        Auction foundAuction = auctionRepository.findById(reportedAuction)
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        User founduser = userRepository.findById(reportingUser).get();
        User reportedUser = userRepository.findById(foundAuction.getSellerId()).get();
        report.setAuction(foundAuction);
        report.setReportingUserId(founduser);
        report.setReportedUserId(reportedUser);
        return reportsRepository.save(report);
    }

    public List<Reports> findAllReports() {
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
                    if (updatedReport.getContent() != null) {
                        existingOrder.setContent(updatedReport.getContent());
                    }
                    return reportsRepository.save(existingOrder);
                }).orElseThrow(() -> new RuntimeException("Order was not found"));
    }

    public String deleteReport(String id) {
        reportsRepository.deleteById(id);
        return "Deleted successfully!";
    }
}