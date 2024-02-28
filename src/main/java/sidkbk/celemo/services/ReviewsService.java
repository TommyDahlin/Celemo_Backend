package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.ReviewsDTO;
import sidkbk.celemo.dto.ReviewsDeleteDTO;
import sidkbk.celemo.dto.ReviewsFindDTO;
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

    // Find and return one specific review dto
    public ResponseEntity<?> listOneSpecificReview(ReviewsFindDTO reviewsFindDTO) {
        Reviews foundReview = reviewsRepo.findById(reviewsFindDTO.getReviewId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return ResponseEntity.ok(foundReview);
    }

    // Add a review dto
    public Reviews addReview(ReviewsDTO reviewsDTO) {
        User createdBy = userRepository.findById(reviewsDTO.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User createdBy not found"));
        User reviewedUser = userRepository.findById(reviewsDTO.getReviewedUserId())
                .orElseThrow(() -> new RuntimeException("User reviewedUser not found"));
        Reviews newReview = new Reviews();
        newReview.setGrade(reviewsDTO.getGrade());
        newReview.setReviewText(reviewsDTO.getReviewText());
        //newReview.getCreatedAt(reviewsDTO.getCreatedAt());
        newReview.setCreatedBy(createdBy);
        newReview.setReviwedUser(reviewedUser);

        reviewsRepo.save(newReview);
        //grade
        //reviewText
        //createdAt
        updateAverageGrade(reviewsDTO);
        return newReview;
    }


    public void updateAverageGrade(ReviewsDTO reviewsDTO){
        List<Reviews> allReviews = reviewsRepo.findAll(); // list all reviews
        User user = userRepository.findById(reviewsDTO.getReviewId()).get(); //get reviews with reviewedUser
        List<Double> reviewGrade = new ArrayList<>(); //create a new list to fill with
        for (Reviews reviews : allReviews) { //checks each review if the reviewedUserId matches with id
            if(reviews.getReviwedUser().getId().equals(reviewsDTO.getReviewId())){

                reviewGrade.add(reviewsDTO.getGrade()); //if its a match, add grade to users grade

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

    // Delete a review dto
    public ResponseEntity<?> deleteReview(ReviewsDeleteDTO reviewsDeleteDTO) {
        reviewsRepo.findById(reviewsDeleteDTO.getReviewId())
                .orElseThrow(() -> new RuntimeException("Review does not exist!"));
        reviewsRepo.deleteById(reviewsDeleteDTO.getReviewId());
        return ResponseEntity.ok("Review deleted!");

    }

    // Update
    public Reviews updateReview(ReviewsDTO updateReviewsDTO) {
        return reviewsRepo.findById(updateReviewsDTO.getReviewId())
                .map(existingReview -> {
            if (updateReviewsDTO.getGrade() != null) {
                existingReview.setGrade(updateReviewsDTO.getGrade());
            }
            if (updateReviewsDTO.getReviewText() != null) {
                existingReview.setReviewText(updateReviewsDTO.getReviewText());
            }
            return reviewsRepo.save(existingReview);
        }).orElseThrow(() -> new RuntimeException("Review not found!"));
    }
}
