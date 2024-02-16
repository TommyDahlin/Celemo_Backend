package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.repositories.ReviewsRepo;

import java.util.List;

@Service
public class ReviewsService {

    @Autowired
    ReviewsRepo reviewsRepo;

    // Find all reviews and return a list
    public List<Reviews> listAllReviews() {
        return reviewsRepo.findAll();
    }

    // Find and return one specific review
    public Reviews listOneSpecificReview(String id) {
        Reviews foundReview = reviewsRepo.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        return foundReview;
    }
}
