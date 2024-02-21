package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.User;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.repositories.UserRepository;
import sidkbk.celemo.repositories.ReviewsRepo;

import java.util.List;

@Service
public class ReviewsService {

    @Autowired
    ReviewsRepo reviewsRepo;
    @Autowired
    UserRepository userRepository;

    // Find all reviews and return a list
    public List<Reviews> listAllReviews() {
        return reviewsRepo.findAll();
    }

    // Find and return one specific review
    public Reviews listOneSpecificReview(String id) {
        Reviews foundReview = reviewsRepo.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        return foundReview;
    }

    // Add a review
    public Reviews addReview(String createdBy, String reviewedUser, Reviews review) {
        User createdByIdFound = userRepository.findById(createdBy).orElseThrow(() -> new RuntimeException("User not found!"));
        User reviewedUserIdFound = userRepository.findById(reviewedUser).orElseThrow(() -> new RuntimeException("Reviewed user not found!"));
        review.setCreatedBy(createdByIdFound);
        review.setReviwedUser(reviewedUserIdFound);
        return reviewsRepo.save(review);
    }

    // Delete a review
    public String deleteReview(String id) {
        reviewsRepo.findById(id).orElseThrow(() -> new RuntimeException("Review does not exists!"));
        reviewsRepo.deleteById(id);
        return "Review deleted!";
    }

    // Update
    public Reviews updateReview(String reviewId, Reviews updatedReview) {
        return reviewsRepo.findById(reviewId)
                .map(existingReview -> {
                    if (updatedReview.getGrade() != null) {
                        existingReview.setGrade(updatedReview.getGrade());
                    }
                    if (updatedReview.getReviewText() != null) {
                        existingReview.setReviewText(updatedReview.getReviewText());
                    }
                    return reviewsRepo.save(existingReview);
                }).orElseThrow(() -> new RuntimeException("Review not found!"));
    }
}
