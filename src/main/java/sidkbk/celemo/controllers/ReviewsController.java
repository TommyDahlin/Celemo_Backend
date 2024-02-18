package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            return ResponseEntity.ok(reviewsService.listOneSpecificReview(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // POST add a review
    @PostMapping("/post/{created-by-id}/{reviewed-user-id}")
    public ResponseEntity<?> addReview(@PathVariable("created-by-id") String createdBy,
                                       @PathVariable("reviewed-user-id") String reviewedUser,
                                       @Valid @RequestBody Reviews review) {
        try {
            return ResponseEntity.ok(reviewsService.addReview(createdBy, reviewedUser, review));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
