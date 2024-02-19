package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.models.Reports;
import sidkbk.celemo.services.ReportsServices;

import java.util.List;

@RestController
@RequestMapping(value = "/reports")
public class ReportsControllers {
    @Autowired
    ReportsServices reportsServices;

    //Post a new report
    @PostMapping("/post")
    public Reports createReport(@RequestBody Reports reports){
        return reportsServices.createReport(reports);
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
    public Reports updateReport(@RequestBody Reports reports, @PathVariable("id") String _id){
        return reportsServices.updateReport(reports);
    }

    // Delete by id
    @DeleteMapping("/delete/{id}")
    public String deleteReport(@PathVariable String id){
        return reportsServices.deleteReport(id);
    }

}
