package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.ReportsAuctionDTO;
import sidkbk.celemo.dto.ReportsFindDTO;
import sidkbk.celemo.dto.ReportsUserDTO;
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


    public ResponseEntity<Reports> createReportUser(ReportsUserDTO reportsDTO) {
        User foundReportedUser = userRepository.findById(reportsDTO.getId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        User foundReportingUser = userRepository.findById(reportsDTO.getId())
                .orElseThrow(() -> new RuntimeException("User does not exist!"));
        reportsDTO.setReportedUserId(reportsDTO.getReportingUserId());
        reportsDTO.setReportingUserId(reportsDTO.getReportingUserId());
        Reports newReport = new Reports();
        newReport.setReportedUserId(foundReportedUser);
        newReport.setReportingUserId(foundReportingUser);
        newReport.setContent(reportsDTO.getContent());

        return ResponseEntity.ok(reportsRepository.save(newReport));
    }

    public ResponseEntity<?> createReportAuction(ReportsAuctionDTO reportsAuctionDTO) {
        User foundReportingAuction = userRepository.findById(reportsAuctionDTO.getId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        Auction foundAuction = auctionRepository.findById(reportsAuctionDTO.getId())
                .orElseThrow(() -> new RuntimeException("Auction does not exist!"));
        reportsAuctionDTO.setReportingUserId(reportsAuctionDTO.getReportingUserId());
        reportsAuctionDTO.setAuction(reportsAuctionDTO.getAuction());

        Reports newReport = new Reports();
        newReport.setReportingUserId(foundReportingAuction);
        newReport.setAuction(foundAuction);
        newReport.setContent(reportsAuctionDTO.getContent());

        return ResponseEntity.ok(reportsRepository.save(newReport));
    }

    public List<Reports> findAllReports() {
        return reportsRepository.findAll();
    }

    public ResponseEntity<?> findOne(ReportsFindDTO reportsFindDTO) {
        Reports foundReport = reportsRepository.findById(reportsFindDTO.getReportsId())
                .orElseThrow(() -> new RuntimeException("Order was not found"));
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