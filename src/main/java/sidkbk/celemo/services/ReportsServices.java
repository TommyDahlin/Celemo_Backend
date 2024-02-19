package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Reports;
import sidkbk.celemo.repositories.ReportsRepository;

import java.util.List;

@Service
public class ReportsServices {

    @Autowired
    ReportsRepository reportsRepository;


    public Reports createReport(Reports reports){
        return reportsRepository.save(reports);
    }

    public List<Reports> findAllReports(){
        return reportsRepository.findAll();
    }


    public Reports findOne(String id){
        return reportsRepository.findById(id).get();
    }

    public Reports updateReport(Reports reports){
        return reportsRepository.save(reports);
    }


    public String deleteReport(String id){
        reportsRepository.deleteById(id);
        return "Deleted successfully!";
    }

}
