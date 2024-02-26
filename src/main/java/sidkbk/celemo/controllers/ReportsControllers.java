package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Reports;
import sidkbk.celemo.services.ReportsServices;

@RestController
@RequestMapping(value = "/api/reports")
public class ReportsControllers {
    @Autowired
    ReportsServices reportsServices;


    //Post a new report for a user
    @PostMapping("/post/reportuser/{reportinguser}/{reporteduser}")
    public ResponseEntity<?> createReportUser(
            Reports report, @PathVariable("reportinguser") String reportingUser, @PathVariable ("reporteduser") String reportedUser){
        try {
            return ResponseEntity.ok(reportsServices.createReportUser(report, reportingUser, reportedUser));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //Post a new report for an auction
    @PostMapping("/post/reportauction/{reportinguser}/{reportedauction}")
    public ResponseEntity<?> createReportAuction(
            Reports report, @PathVariable("reportinguser") String reportingUser, @PathVariable ("reportedauction") String reportedAuction
    ){
        try {
            return ResponseEntity.ok(reportsServices.createReportAuction(report, reportingUser, reportedAuction));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //Find a report by id
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") String id ) {
        try {
            return ResponseEntity.ok(reportsServices.findOne(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // Find all reports
    @GetMapping("/find")
    public ResponseEntity<?> findAllReports(){
        try {
            return ResponseEntity.ok(reportsServices.findAllReports());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateReport(@PathVariable("id") String reportId,
                                         @Valid @RequestBody Reports updatedReport) {
        try {
            return ResponseEntity.ok(reportsServices.updateReport(reportId, updatedReport));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // Delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable String id) {
        try {
            return ResponseEntity.ok(reportsServices.deleteReport(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
