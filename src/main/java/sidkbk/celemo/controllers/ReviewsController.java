package sidkbk.celemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sidkbk.celemo.exception.EntityNotFoundException;
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

    // GET one specific review
    @GetMapping("/find/{id}")
    public ResponseEntity<?> listOneSpecificReview(@PathVariable("id") String id) {
        try {
            Reviews review = reviewsService.listOneSpecificReview(id);
            return ResponseEntity.ok(review);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
