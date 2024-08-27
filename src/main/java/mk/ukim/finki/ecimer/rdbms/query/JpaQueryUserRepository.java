package mk.ukim.finki.ecimer.rdbms.query;

import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.core.query.QueryUserRepository;
import mk.ukim.finki.ecimer.core.query.filter.UserFilter;
import mk.ukim.finki.ecimer.rdbms.JpaUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class JpaQueryUserRepository implements QueryUserRepository {

    private final JpaUserRepository jpaUserRepository;

    public JpaQueryUserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Page<User> search(UserFilter userFilter) {
        return jpaUserRepository.search(userFilter);
    }
}
