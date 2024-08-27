package mk.ukim.finki.ecimer.core.query;

import mk.ukim.finki.ecimer.core.Post;
import mk.ukim.finki.ecimer.core.query.filter.PostFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueryPostService {

    private final QueryPostRepository queryPostRepository;


    public QueryPostService(QueryPostRepository queryPostRepository) {
        this.queryPostRepository = queryPostRepository;
    }

    @Transactional(readOnly = true)
    public Page<Post> search(PostFilter postFilter) {
        return queryPostRepository.search(postFilter);
    }
}
