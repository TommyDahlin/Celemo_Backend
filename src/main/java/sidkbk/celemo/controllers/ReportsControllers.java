package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Reports;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.services.ReportsServices;

import java.util.List;

@RestController
@RequestMapping(value = "/reports")
public class ReportsControllers {
    @Autowired
    ReportsServices reportsServices;

    //Post a new report

    @PostMapping("/post")
    public ResponseEntity<?> createReport(
                                       @Valid @RequestBody Reports reports) {
        try {
            return ResponseEntity.ok(reportsServices.createReport(reports));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //Find a report by id
    @GetMapping("/find/{id}")
    public Reports findOne(@PathVariable String id){
        return reportsServices.findOne(id);
    }


    // Find all reports
    @GetMapping("/find")
    public List<Reports> findAllReports(){
        return reportsServices.findAllReports();
    }


    // Update by id
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateReport(@PathVariable("id") String reportId,
                                          @Valid @RequestBody Reports updatedReport) {
        try {
            return ResponseEntity.ok(reportsServices.updateReport(updatedReport, reportId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(reportsServices.deleteReport(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

