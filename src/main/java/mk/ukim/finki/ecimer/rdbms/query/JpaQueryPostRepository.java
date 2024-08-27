package mk.ukim.finki.ecimer.rdbms.query;

import mk.ukim.finki.ecimer.core.Post;
import mk.ukim.finki.ecimer.core.query.filter.PostFilter;
import mk.ukim.finki.ecimer.core.query.QueryPostRepository;
import mk.ukim.finki.ecimer.rdbms.JpaPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class JpaQueryPostRepository implements QueryPostRepository {
    private final JpaPostRepository jpaPostRepository;

    public JpaQueryPostRepository(JpaPostRepository jpaPostRepository) {
        this.jpaPostRepository = jpaPostRepository;
    }


    @Override
    public Page<Post> search(PostFilter postFilter) {
        return jpaPostRepository.search(postFilter);
    }
}
