package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.services.ReviewsService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    @Autowired
    ReviewsService reviewsService;

    // GET all reviews
    @GetMapping("/find")
    public List<Reviews> listAllReviews() {
        return reviewsService.listAllReviews();
    }
}
