package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.Reviews.*;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.services.ReviewsService;

import java.util.List;
/*@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")*/
@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    @Autowired
    ReviewsService reviewsService;

// PUBLIC
//////////////////////////////////////////////////////////////////////////////////////

    // GET all reviews for specific reviewed user
    @GetMapping("/find/all-user/{userId}")
    public ResponseEntity<?> allReviewsForSpecificReviewedUser(@PathVariable("userId") String userId) {
        List<Reviews> foundReviews = reviewsService.allReviewsForSpecificReviewedUser(userId);

        if (foundReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not reviewed...");
        } else {
            return ResponseEntity.ok(foundReviews);
        }
    }

    // GET all reviews for specific reviewed user WITH paging
    @GetMapping("/find/all-user/{reviewedUserId}/{pageSize}/page/{pagenumber}")
    public ResponseEntity<?> allReviewsForSpecificReviewedUserPage(
            @PathVariable("reviewedUserId") String reviewedUserId,
            @PathVariable("pageSize") int pageSize,
            @PathVariable("pagenumber") int pageNr) {
        List<Reviews> foundReviews = reviewsService.allReviewsForSpecificReviewedUserPage(reviewedUserId, pageSize, pageNr);

        if (foundReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not reviewed or empty page...");
        } else {
            return ResponseEntity.ok(foundReviews);
        }
    }

    // GET List all reviews for a specified user AND sort reviews by Low or High grades
    @GetMapping("/find/all-user-sort/{reviewedUserId}/{HIGH-or-LOW}")
    public ResponseEntity<?> reviewedUserSortReviews(
            @PathVariable("reviewedUserId") String reviewedUserId,
            @PathVariable("HIGH-or-LOW") String highLow ) {
        List<Reviews> foundReviews = reviewsService.reviewedUserSortReviews(reviewedUserId, highLow);
        if (foundReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found for user...");
        } else {
            return ResponseEntity.ok(foundReviews);
        }
    }

    // GET List all reviews for a specified user AND sort reviews by Low or High grades WITH paging
    @GetMapping("/find/all-user-sort/{reviewedUserId}/{pageSize}/{HIGH-or-LOW}/page/{pagenumber}")
    public ResponseEntity<?> reviewedUserSortReviewsPage(
            @PathVariable("reviewedUserId") String reviewedUserId,
            @PathVariable("HIGH-or-LOW") String highLow,
            @PathVariable("pageSize") int pageSize,
            @PathVariable("pagenumber") int pageNr) {
        List<Reviews> foundReviews = reviewsService.reviewedUserSortReviewsPage(reviewedUserId, highLow, pageSize, pageNr);
        if (foundReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found for user...");
        } else {
            return ResponseEntity.ok(foundReviews);
        }
    }

    // GET all reviews for specific reviewed user with specific grade
    @GetMapping("/find/all-user-grade/{reviewedUserId}/{grade}")
    public ResponseEntity<?> reviewedUserSortByGrade(@PathVariable("reviewedUserId") String reviewedUserId,
                                                     @PathVariable("grade") int grade) {
        return reviewsService.reviewedUserSortByGrade(reviewedUserId, grade);
    }

    // GET all reviews for specific reviewed user with specific grade WITH paging
    @GetMapping("/find/all-user-grade/{reviewedUserId}/{grade}/{pageSize}/page/{pagenumber}")
    public ResponseEntity<?> reviewedUserSortByGradeAndPage(
            @PathVariable("reviewedUserId") String reviewedUserId,
            @PathVariable("grade") int grade,
            @PathVariable("pageSize") int pageSize,
            @PathVariable("pagenumber") int pageNr ) {
        return reviewsService.reviewedUserSortByGradeAndPage(reviewedUserId, grade, pageSize, pageNr);
    }

// USER
//////////////////////////////////////////////////////////////////////////////////////

    // POST add a review dto
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewsDTO reviewsDTO) {
        Reviews newReview = reviewsService.addReview(reviewsDTO);
        return ResponseEntity.ok(newReview);
    }

    // PUT Update a review dto
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestBody ReviewsPutDTO updateReviewsDTO) {

        return ResponseEntity.ok(reviewsService.updateReview(updateReviewsDTO));
    }

// ADMIN
//////////////////////////////////////////////////////////////////////////////////////

    // GET all reviews
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/all")
    public ResponseEntity<?> listAllReviews() {
        try {
            return ResponseEntity.ok(reviewsService.listAllReviews());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET all reviews WITH paging
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/all/{pageSize}/page/{pagenumber}")
    public ResponseEntity<?> listAllReviewsPage(@PathVariable("pagenumber") int pageNr,
                                                @PathVariable("pageSize") int pageSize) {
        try {
            return ResponseEntity.ok(reviewsService.listAllReviewsPage(pageSize, pageNr));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET one specific review
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-one/{reviewId}")
    public ResponseEntity<?> listOneSpecificReview(@PathVariable("reviewId") String reviewId) {
        try {
            return reviewsService.listOneSpecificReview(reviewId);
        }  catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE Delete a review dto
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReview(@Valid @RequestBody ReviewsDeleteDTO reviewsDeleteDTO) {
        try {
            return reviewsService.deleteReview(reviewsDeleteDTO);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/all")
    public void deleteAllReviews(){
        reviewsService.deleteAllReviews();
    }
}
