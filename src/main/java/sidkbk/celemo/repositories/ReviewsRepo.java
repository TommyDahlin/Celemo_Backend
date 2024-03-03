package sidkbk.celemo.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sidkbk.celemo.models.Reviews;

import java.util.List;

@Repository
public interface ReviewsRepo extends MongoRepository<Reviews, String> {


    List<Reviews> findByReviewedUser_Id(String reviewedUserId);
    List<Reviews> findByReviewedUser_Id(String reviewedUserId, Pageable pageable);

    List<Reviews> findByReviewedUser_IdAndGrade(String reviewedUserId, double grade);
    List<Reviews> findByReviewedUser_IdAndGrade(String reviewedUserId, double grade, Pageable pageable);

}
