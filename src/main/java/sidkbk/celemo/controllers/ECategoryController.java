package sidkbk.celemo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sidkbk.celemo.models.ECategory;
import sidkbk.celemo.services.ECategoryService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/category")
public class ECategoryController {

    @Autowired
    ECategoryService ECategoryService;


    @GetMapping("/find")
    public List<ECategory> getCategory() {

        return ECategoryService.getCategory();
    }



}
