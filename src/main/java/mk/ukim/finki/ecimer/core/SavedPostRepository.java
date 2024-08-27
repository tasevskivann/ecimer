package mk.ukim.finki.ecimer.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SavedPostRepository {


    Page<SavedPost> findAllByUserUuid(String userUuid, Pageable pageable);

    SavedPost save(SavedPost savedPost);

    Optional<SavedPost> findByUuid(String savedPostUuid);

    void deleteById(String savedPostUuid);

    void deleteAllByUserUuid(String userUuid);

    void deleteAllByPostUuid(String postUuid);

    List<SavedPost> findByUserUuid(String userUuid);
}
