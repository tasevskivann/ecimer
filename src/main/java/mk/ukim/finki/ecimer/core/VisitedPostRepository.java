package mk.ukim.finki.ecimer.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VisitedPostRepository {

    Optional<VisitedPost> findById(String visitedPostUuid);

    Page<VisitedPost> findAllByUserUuid(String userUuid, Pageable pageable);

    List<VisitedPost> findAllByUserUuid(String userUuid);

    void deleteById(String visitedPostUuid);

    void deleteAllByUserUuid(String userUuid);

    void deleteAllByPostUuid(String postUuid);

    VisitedPost save(VisitedPost visitedPost);
}
