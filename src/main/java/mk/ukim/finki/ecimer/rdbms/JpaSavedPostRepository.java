package mk.ukim.finki.ecimer.rdbms;

import mk.ukim.finki.ecimer.core.SavedPost;
import mk.ukim.finki.ecimer.core.SavedPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaSavedPostRepository extends JpaRepository<SavedPost, String>, SavedPostRepository {

    @Query(value = "SELECT sp FROM SavedPost sp WHERE sp.userUuid = :userUuid")
    Page<SavedPost> findAllByUserUuid(@Param(value = "userUuid") String userUuid, Pageable pageable);

}
