package sidkbk.celemo.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sidkbk.celemo.models.Reviews;

import java.util.List;

@Repository
public interface ReviewsRepo extends MongoRepository<Reviews, String> {

    // Find all reviews WITH paging
    //List<Reviews> findAllReviews(Pageable pageable);

    // Find all reviews for specified reviewed user.
    List<Reviews> findByReviewedUser_Id(String reviewedUserId);
    List<Reviews> findByReviewedUser_Id(String reviewedUserId, Pageable pageable);

    // Find all reviews for specified reviewed user AND specified grade.
    List<Reviews> findByReviewedUser_IdAndGrade(String reviewedUserId, double grade);
    List<Reviews> findByReviewedUser_IdAndGrade(String reviewedUserId, double grade, Pageable pageable);

    // Find all reviews for specified reviewed user AND order by grade ASCENDING or DESCENDING.
    List<Reviews> findByReviewedUser_IdOrderByGradeAsc(String reviewedUserId);
    List<Reviews> findByReviewedUser_IdOrderByGradeAsc(String reviewedUserId, Pageable pageable);
    List<Reviews> findByReviewedUser_IdOrderByGradeDesc(String reviewedUserId);
    List<Reviews> findByReviewedUser_IdOrderByGradeDesc(String reviewedUserId, Pageable pageable);

}
