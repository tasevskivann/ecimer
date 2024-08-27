package mk.ukim.finki.ecimer.core.query;

import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.core.query.filter.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueryUserService {

    private final QueryUserRepository queryUserRepository;

    public QueryUserService(QueryUserRepository queryUserRepository) {
        this.queryUserRepository = queryUserRepository;
    }

    @Transactional(readOnly = true)
    public Page<User> search(UserFilter userFilter) {
        return queryUserRepository.search(userFilter);
    }
}
