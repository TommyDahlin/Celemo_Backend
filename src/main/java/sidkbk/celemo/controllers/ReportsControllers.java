package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Reports;
import sidkbk.celemo.services.ReportsServices;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reports")
public class ReportsControllers {
    @Autowired
    ReportsServices reportsServices;


    //Post a new report
    @PostMapping("/post/{reporting-User-Id}/{reported-User-Id}/{auction}")
    public ResponseEntity<?> createReport(@PathVariable("reporting-User-Id") String reportingUserId,
                                       @PathVariable("reported-User-Id")String reportedUserId,
                                       @PathVariable("auction") String auction,
                                       @RequestBody Reports reports){
        try {
            return ResponseEntity.ok(reportsServices.createReport(reportingUserId,reportedUserId,auction,reports));
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

    @PutMapping("/put/{id}")
    public Reports updateReport(@RequestBody Reports reports, @PathVariable("id") String _id){
        return reportsServices.updateReport(reports);
    }

    // Delete by id
    @DeleteMapping("/delete/{id}")
    public String deleteReport(@PathVariable String id){
        return reportsServices.deleteReport(id);
    }

}
