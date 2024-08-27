package mk.ukim.finki.ecimer.rdbms;

import mk.ukim.finki.ecimer.core.VisitedPost;
import mk.ukim.finki.ecimer.core.VisitedPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaVisitedPostRepository extends JpaRepository<VisitedPost, String>, VisitedPostRepository {


    @Query(value = "SELECT vp FROM VisitedPost vp WHERE vp.userUuid = :userUuid")
    Page<VisitedPost> findAllByUserUuid(@Param(value = "userUuid") String userUuid, Pageable pageable);
}
