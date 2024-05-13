package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.reports.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.services.ReportsServices;

@RestController
@RequestMapping(value = "/api/reports")
public class ReportsControllers {
    @Autowired
    ReportsServices reportsServices;

// USER
//////////////////////////////////////////////////////////////////////////////////////

    //Post a new report for a user
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/report/user")
    public ResponseEntity<?> createReportUser(@Valid @RequestBody ReportsUserDTO rUDTO){
        try {
            return reportsServices.createReportUser(rUDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Post a new report for an auction
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/report/auction")
    public ResponseEntity<?> createReportAuction(@Valid @RequestBody ReportsAuctionDTO rADTO){
        try {
            return reportsServices.createReportAuction(rADTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

// ADMIN
//////////////////////////////////////////////////////////////////////////////////////

    //Find a report by id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-one/{reportsId}")
    public ResponseEntity<?> findOne(@PathVariable("reportsId") String reportsId) {
        try {
            return reportsServices.findOne(reportsId);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Find all reports
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/all")
    public ResponseEntity<?> findAllReports(){
        try {
            return ResponseEntity.ok(reportsServices.findAllReports());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //update report
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateReport(@RequestBody ReportsPutDTO reportsPutDTO) {
        try {
            return reportsServices.updateReport(reportsPutDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReport(@RequestBody ReportsDeleteDTO reportsDeleteDTO) {
        return reportsServices.deleteReport(reportsDeleteDTO);
    }
}
