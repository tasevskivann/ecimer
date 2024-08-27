package mk.ukim.finki.ecimer.core.query;

import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.core.query.filter.UserFilter;
import org.springframework.data.domain.Page;

public interface QueryUserRepository {

    Page<User> search(UserFilter userFilter);
}
