package sidkbk.celemo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.ECategory;
import sidkbk.celemo.services.ECategoryService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api/category")
public class ECategoryController {


    @Autowired
    ECategoryService eCategoryService;

    // should be an auth on this?
    // or is it prevented by auction auth?
    @GetMapping("/find")
    public ResponseEntity<List<ECategory>> getAllCategory() {
        try {
            return ResponseEntity.ok(eCategoryService.getAllCategory());
        } catch (EntityNotFoundException e) {
            return (ResponseEntity.notFound().build());
        }
    }
}
