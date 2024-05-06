package sidkbk.celemo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sidkbk.celemo.dto.Reviews.*;
import sidkbk.celemo.dto.user.FindUserIdDTO;
import sidkbk.celemo.exceptions.EntityNotFoundException;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.services.ReviewsService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    @Autowired
    ReviewsService reviewsService;

// PUBLIC
//////////////////////////////////////////////////////////////////////////////////////

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

    // GET all reviews for specific reviewed user WITH paging
    @GetMapping("/find/all-user/page/{pagenumber}")
    public ResponseEntity<?> allReviewsForSpecificReviewedUserPage(
            @PathVariable("pagenumber") int pageNr,
            @Valid @RequestBody ReviewsAllPagingUserDTO reviewsAllPagingUserDTO) {
        List<Reviews> foundReviews = reviewsService.allReviewsForSpecificReviewedUserPage(pageNr, reviewsAllPagingUserDTO);

        if (foundReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not reviewed or empty page...");
        } else {
            return ResponseEntity.ok(foundReviews);
        }
    }

    // GET List all reviews for a specified user AND sort reviews by Low or High grades
    @GetMapping("/find/all-user-sort")
    public ResponseEntity<?> reviewedUserSortReviews(@Valid @RequestBody ReviewsSortLowHighDTO reviewsSortLowHighDTO) {
        List<Reviews> foundReviews = reviewsService.reviewedUserSortReviews(reviewsSortLowHighDTO);
        if (foundReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found for user...");
        } else {
            return ResponseEntity.ok(foundReviews);
        }
    }

    // GET List all reviews for a specified user AND sort reviews by Low or High grades WITH paging
    @GetMapping("/find/all-user-sort/page/{pagenumber}")
    public ResponseEntity<?> reviewedUserSortReviewsPage(
            @PathVariable("pagenumber") int pageNr,
            @Valid @RequestBody ReviewsSortLowHighDTO reviewsSortLowHighDTO) {
        List<Reviews> foundReviews = reviewsService.reviewedUserSortReviewsPage(pageNr, reviewsSortLowHighDTO);
        if (foundReviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found for user...");
        } else {
            return ResponseEntity.ok(foundReviews);
        }
    }

    // GET all reviews for specific reviewed user with specific grade
    @GetMapping("/find/all-user-grade")
    public ResponseEntity<?> reviewedUserSortByGrade(@Valid @RequestBody ReviewsGetByGradeDTO reviewsGetByGradesDTO) {
        return reviewsService.reviewedUserSortByGrade(reviewsGetByGradesDTO);
    }

    // GET all reviews for specific reviewed user with specific grade WITH paging
    @GetMapping("/find/all-user-grade/page/{pagenumber}")
    public ResponseEntity<?> reviewedUserSortByGradeAndPage(
            @PathVariable("pagenumber") int pageNr,
            @Valid @RequestBody ReviewsGetByGradeDTO reviewsGetByGradesDTO) {
        return reviewsService.reviewedUserSortByGradeAndPage(pageNr, reviewsGetByGradesDTO);
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
    @GetMapping("/find/all/page/{pagenumber}")
    public ResponseEntity<?> listAllReviewsPage(@PathVariable("pagenumber") int pageNr,
                                                @RequestBody ReviewsPageSizeDTO reviewsPageSizeDTO) {
        try {
            return ResponseEntity.ok(reviewsService.listAllReviewsPage(pageNr, reviewsPageSizeDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET one specific review
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find-one")
    public ResponseEntity<?> listOneSpecificReview(@Valid @RequestBody ReviewsFindDTO reviewsFindDTO) {
        try {
            return reviewsService.listOneSpecificReview(reviewsFindDTO);
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
