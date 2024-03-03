package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.Reviews.*;
import sidkbk.celemo.dto.user.FindUserIdDTO;
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
        newReview.setCreatedBy(createdBy);
        newReview.setReviewedUser(reviewedUser);
        reviewsRepo.save(newReview);
        updateAverageGrade(reviewedUser.getId());
        return newReview;
    }


    public void updateAverageGrade(String userId){
        List<Reviews> allReviews = reviewsRepo.findAll(); // list all reviews
        User user = userRepository.findById(userId).get(); //get reviews with reviewedUser
        List<Double> reviewGrade = new ArrayList<>(); //create a new list to fill with
        for (Reviews reviews : allReviews) { //checks each review if the reviewedUserId matches with id
            if(reviews.getReviewedUser().getId().equals(userId)){
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

    // Delete a review dto
    public ResponseEntity<?> deleteReview(ReviewsDeleteDTO reviewsDeleteDTO) {
        reviewsRepo.findById(reviewsDeleteDTO.getReviewId())
                .orElseThrow(() -> new RuntimeException("Review does not exist!"));
        reviewsRepo.deleteById(reviewsDeleteDTO.getReviewId());
        return ResponseEntity.ok("Review deleted!");

    }

    // Update
    public Reviews updateReview(ReviewsPutDTO updateReviewsDTO) {

        return reviewsRepo.findById(updateReviewsDTO.getReviewId())
                .map(existingReview -> {
            if (updateReviewsDTO.getGrade() != null) {
                existingReview.setGrade(updateReviewsDTO.getGrade());
            }
            if (updateReviewsDTO.getReviewText() != null) {
                existingReview.setReviewText(updateReviewsDTO.getReviewText());
            }
            reviewsRepo.save(existingReview);
            updateAverageGrade(existingReview.getReviewedUser().getId());
            return existingReview;
        }).orElseThrow(() -> new RuntimeException("Review not found!"));

    }

    // DELETE ALL
    public void deleteAllReviews(){
        reviewsRepo.deleteAll();
    }

    // List all reviews for a specified user
    public List<Reviews> allReviewsForSpecificReviewedUser(FindUserIdDTO findUserIdDTO) {
        return reviewsRepo.findByReviewedUser_Id(findUserIdDTO.getUserId());
    }

    // List all reviews for a specified user with paging - replaces method above
    public List<Reviews> allReviewsForSpecificReviewedUserPage(int pageNr, ReviewsAllPagingUserDTO reviewsAllPagingUserDTO) {
        Pageable paging = PageRequest.of(pageNr, reviewsAllPagingUserDTO.getPageSize());
        return reviewsRepo.findByReviewedUser_Id(reviewsAllPagingUserDTO.getUserId(), paging);
    }

    // List all reviews for a specified user AND sort reviews by Low or High grades.
    public ResponseEntity<?> reviewedUserSortReviews(ReviewsSortLowHighDTO reviewsSortLowHighDTO) {
        FindUserIdDTO findUserIdDTO = new FindUserIdDTO();
        findUserIdDTO.setUserId(reviewsSortLowHighDTO.getUserId());
        // Run method above to get all reviews for specified user
        List<Reviews> foundReviews = allReviewsForSpecificReviewedUser(findUserIdDTO);
        List<Reviews> sortedReviews = new ArrayList<>();
        // If sorting from "low" to "high" grade
        if (reviewsSortLowHighDTO.getLowOrHigh().equals("LOW")) { // Check in DTO
            for (double i = 1; i <= 5; i++) { // Loop through 1-5
                for (Reviews review : foundReviews) { // Loop through found reviews
                    if (review.getGrade().equals(i)) { // If grade matches
                        sortedReviews.add(review); // Save review to sorted list
                    }
                }
            }
        }
        // If sorting from "high" to "low" grade
        if (reviewsSortLowHighDTO.getLowOrHigh().equals("HIGH")) { // Check in DTO
            for (double i = 5; i >= 1; i--) { // Loop through 5-1
                for (Reviews review : foundReviews) { // Loop through found reviews
                    if (review.getGrade().equals(i)) { // If grade matches
                        sortedReviews.add(review); // Save review to sorted list
                    }
                }
            }
        }
        return ResponseEntity.ok(sortedReviews); // Return sorted list
    }

    // List all reviews for specific reviewed user with specific grade
    public ResponseEntity<?> reviewedUserSortByGrade(ReviewsGetByGradeDTO reviewsGetByGradeDTO) {
        FindUserIdDTO findUserIdDTO = new FindUserIdDTO();
        findUserIdDTO.setUserId(reviewsGetByGradeDTO.getUserId());
        // Run method above to get all reviews for specified user
        List<Reviews> foundReviews = allReviewsForSpecificReviewedUser(findUserIdDTO);
        List<Reviews> reviewsByGrade = new ArrayList<>();
        // Loop through reviews found for specified user and find only reviews with matching grade you want to see
        for (Reviews review : foundReviews) {
            if (review.getGrade().equals(reviewsGetByGradeDTO.getGrade())) {
                reviewsByGrade.add(review);
            }
        }
        if (reviewsByGrade.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews with that grade found...");
        }
        return ResponseEntity.ok(reviewsByGrade);
    }


}


