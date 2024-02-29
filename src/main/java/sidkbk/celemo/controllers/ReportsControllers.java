package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.services.ReportsServices;

@RestController
@RequestMapping(value = "/api/reports")
public class ReportsControllers {
    @Autowired
    ReportsServices reportsServices;


    //Post a new report for a user
    @PostMapping("/post/report/user")
    public ResponseEntity<?> createReportUser(@Valid @RequestBody ReportsUserDTO rUDTO){
        try {
            return reportsServices.createReportUser(rUDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Post a new report for an auction
    @PostMapping("/post/report/auction")
    public ResponseEntity<?> createReportAuction(@Valid @RequestBody ReportsAuctionDTO rADTO){
        try {
            return reportsServices.createReportAuction(rADTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Find a report by id
    @GetMapping("/find")
    public ResponseEntity<?> findOne(@Valid @RequestBody ReportsFindDTO reportsFindDTO) {
        try {
            return reportsServices.findOne(reportsFindDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Find all reports
    @GetMapping("/find/all")
    public ResponseEntity<?> findAllReports(){
        try {
            return ResponseEntity.ok(reportsServices.findAllReports());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //update report
    @PutMapping("/put")
    public ResponseEntity<?> updateReport(@RequestBody ReportsPutDTO reportsPutDTO) {
        try {
            return reportsServices.updateReport(reportsPutDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // Delete by id
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReport(@RequestBody ReportsDeleteDTO reportsDeleteDTO) {

            return reportsServices.deleteReport(reportsDeleteDTO);

    }
}
