package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidkbk.celemo.models.Reviews;
import sidkbk.celemo.models.User;
import sidkbk.celemo.repositories.ReviewsRepo;
import sidkbk.celemo.repositories.UserRepository;

import java.util.ArrayList;
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
        reviewsRepo.save(review);
        updateAverageGrade(reviewedUser); //have to be before return; // update users average grade
        return review;
    }


    public void updateAverageGrade(String id){
        List<Reviews> allReviews = reviewsRepo.findAll(); // list all reviews
        User user = userRepository.findById(id).get(); //get reviews with reviewedUser
        List<Double> reviewGrade = new ArrayList<>(); //create a new list to fill with
        for (Reviews reviews : allReviews) { //checks each review if the reviewedUserId matches with id
            if(reviews.getReviwedUser().getId().equals(id)){

                reviewGrade.add(reviews.getGrade()); //if its a match, add grade to users grade

            }
        }
        double averageGrade = 0.0; //local grade to fill using for-loop below
        for(int i=0; i < reviewGrade.size(); i++){
            averageGrade += reviewGrade.get(i); //loop through reviewGrade and add to averageGrade
        }
        averageGrade = averageGrade / reviewGrade.size(); //to calculate average number
        user.setGrade(averageGrade); //set new average grade
        userRepository.save(user); //save to user

    }

    // Delete a review
    public String deleteReview(String id) {
        Reviews tempSaveReviewToDelete = reviewsRepo.findById(id).orElseThrow(() -> new RuntimeException("Review does not exists!"));
        reviewsRepo.deleteById(id);
        updateAverageGrade(tempSaveReviewToDelete.getReviwedUser().getId()); //get userId (reviewedUserId) // update new grade after previous grade from deleted review is deleted
        return "Review deleted and user: " + tempSaveReviewToDelete.getReviwedUser().getId() + " average grade was updated!";
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
