package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.services.ReviewsService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    @Autowired
    ReviewsService reviewsService;

    // GET all reviews
    @GetMapping("/find")
    public ResponseEntity<?> listAllReviews() {
        try {
            return ResponseEntity.ok(reviewsService.listAllReviews());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // filtered search
    @GetMapping("/find/{filter}")
    public ResponseEntity<?> getAllReviewsFilter(@PathVariable("filter")String filter){
        try {
            return ResponseEntity.ok(reviewsService.getAllReviewsFilter(filter));
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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

    // DELETE Delete a review
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(reviewsService.deleteReview(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PUT Update a review
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateReview(@PathVariable("id") String reviewId,
                                          @Valid @RequestBody Reviews updatedReview) {
        try {
            return ResponseEntity.ok(reviewsService.updateReview(reviewId, updatedReview));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
