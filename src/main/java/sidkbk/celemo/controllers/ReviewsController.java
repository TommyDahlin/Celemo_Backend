package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.Reviews.*;
import sidkbk.celemo.dto.user.FindUserIdDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.services.ReviewsService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    @Autowired
    ReviewsService reviewsService;

    // GET all reviews
    @GetMapping("/find/all")
    public ResponseEntity<?> listAllReviews() {
        try {
            return ResponseEntity.ok(reviewsService.listAllReviews());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET one specific review
    @GetMapping("/find")
    public ResponseEntity<?> listOneSpecificReview(@Valid @RequestBody ReviewsFindDTO reviewsFindDTO) {
        try {
            return reviewsService.listOneSpecificReview(reviewsFindDTO);
        }  catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET all reviews for specific reviewed user
    @GetMapping("/find/all-user")
    public ResponseEntity<?> allReviewsForSpecificReviewedUser(@Valid @RequestBody FindUserIdDTO findUserIdDTO) {
        List<Reviews> foundReviews = reviewsService.allReviewsForSpecificReviewedUser(findUserIdDTO);

        if (foundReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not reviewed...");
        } else {
            return ResponseEntity.ok(foundReviews);
        }
    }

    // GET all reviews for specific reviewed user and sort grade "Low to High" or "High to Low"
    @GetMapping("/find/all-user-sort")
    public ResponseEntity<?> reviewedUserSortReviews(@Valid @RequestBody ReviewsSortLowHighDTO reviewsSortLowHighDTO) {
        return reviewsService.reviewedUserSortReviews(reviewsSortLowHighDTO);
    }

    // POST add a review dto
    @PostMapping("/post")
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewsDTO reviewsDTO) {
            Reviews newReview = reviewsService.addReview(reviewsDTO);
            return ResponseEntity.ok(newReview);
    }

    // DELETE Delete a review dto
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReview(@Valid @RequestBody ReviewsDeleteDTO reviewsDeleteDTO) {

        try {
            return reviewsService.deleteReview(reviewsDeleteDTO);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    // PUT Update a review dto
    @PutMapping("/put")
    public ResponseEntity<?> updateReview(@RequestBody ReviewsPutDTO updateReviewsDTO) {

        return ResponseEntity.ok(reviewsService.updateReview(updateReviewsDTO));
    }

    @DeleteMapping("/delete/all")
    public void deleteAllReviews(){
        reviewsService.deleteAllReviews();
    }
}
