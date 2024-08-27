package mk.ukim.finki.ecimer.core;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(String postUuid);

    void deleteById(String postUuid);

    List<Post> findAllByUserUuid(String userUuid);
}
