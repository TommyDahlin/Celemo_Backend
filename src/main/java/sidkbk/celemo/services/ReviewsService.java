package sidkbk.celemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sidkbk.celemo.dto.Reviews.*;
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

    // Find all reviews
    public List<Reviews> listAllReviews() {
        return reviewsRepo.findAll();
    }

    // Find all reviews WITH paging
    public List<Reviews> listAllReviewsPage(int pageSize, int pageNr) {
        Page<Reviews> allReviews = reviewsRepo.findAll(PageRequest.of(pageNr, pageSize));
        List<Reviews> reviews = allReviews.getContent();
        return reviews;
    }

    // Find and return one specific review dto
    public ResponseEntity<?> listOneSpecificReview(String reviewId) {
        Reviews foundReview = reviewsRepo.findById(reviewId)
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
    public List<Reviews> allReviewsForSpecificReviewedUser(String userId) {
        return reviewsRepo.findByReviewedUser_Id(userId);
    }

    // List all reviews for a specified user WITH paging
    public List<Reviews> allReviewsForSpecificReviewedUserPage(String reviewedUserId, int pageSize, int pageNr) {
        Pageable paging = PageRequest.of(pageNr, pageSize);
        return reviewsRepo.findByReviewedUser_Id(reviewedUserId, paging);
    }

    // List all reviews for a specified user AND sort reviews by Low or High grades.
    public List<Reviews> reviewedUserSortReviews(String reviewedUserId, String highLow) {
        if (highLow.equals("LOW")) {
            return reviewsRepo.findByReviewedUser_IdOrderByGradeAsc(reviewedUserId);
        } else {
            return reviewsRepo.findByReviewedUser_IdOrderByGradeDesc(reviewedUserId);
        }
    }

    // List all reviews for a specified user AND sort reviews by Low or High grades WITH paging.
    public List<Reviews> reviewedUserSortReviewsPage(String reviewedUserId, String highLow, int pageSize, int pageNr) {
        Pageable pageing = PageRequest.of(pageNr, pageSize);
        if (highLow.equals("LOW")) {
            return reviewsRepo.findByReviewedUser_IdOrderByGradeAsc(
                    reviewedUserId, pageing);
        } else {
            return reviewsRepo.findByReviewedUser_IdOrderByGradeDesc(
                    reviewedUserId, pageing);
        }
    }

    // List all reviews for specific reviewed user with specific grade
    public ResponseEntity<?> reviewedUserSortByGrade(String reviewedUserId, int grade) {
        List<Reviews> reviewsByGrade = reviewsRepo.findByReviewedUser_IdAndGrade(
                reviewedUserId, grade);
        if (reviewsByGrade.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews with that grade found...");
        }
        return ResponseEntity.ok(reviewsByGrade);
    }

    // List all reviews for specific reviewed user with specific grade WITH paging
    public ResponseEntity<?> reviewedUserSortByGradeAndPage(String reviewedUserId, int grade, int pageSize, int pageNr) {
        Pageable paging = PageRequest.of(pageNr, pageSize);
        List<Reviews> reviewsByGrade = reviewsRepo.findByReviewedUser_IdAndGrade(
                reviewedUserId, grade, paging);
        if (reviewsByGrade.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews with that grade found...");
        }
        return ResponseEntity.ok(reviewsByGrade);
    }

}


