package mk.ukim.finki.ecimer.core.query;

import mk.ukim.finki.ecimer.core.Post;
import mk.ukim.finki.ecimer.core.query.filter.PostFilter;
import org.springframework.data.domain.Page;

public interface QueryPostRepository {

    Page<Post> search(PostFilter postFilter);
}
