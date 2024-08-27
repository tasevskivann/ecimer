package mk.ukim.finki.ecimer.rdbms;

import mk.ukim.finki.ecimer.common.SpecificationUtil;
import mk.ukim.finki.ecimer.core.User;
import mk.ukim.finki.ecimer.core.UserRepository;
import mk.ukim.finki.ecimer.core.query.filter.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import static mk.ukim.finki.ecimer.common.SpecificationUtil.acceptNotNullValue;
import static mk.ukim.finki.ecimer.common.SpecificationUtil.filterNull;

@Repository
public interface JpaUserRepository extends JpaRepository<User, String>, UserRepository, JpaSpecificationExecutor<User> {

    default Page<User> search(UserFilter userFilter) {
        final Specification<User> specification = filterNull(
                acceptNotNullValue("email", userFilter.getEmail(), SpecificationUtil::like),
                acceptNotNullValue("firstName", userFilter.getFirstName(), SpecificationUtil::like),
                acceptNotNullValue("lastName", userFilter.getLastName(), SpecificationUtil::like),
                acceptNotNullValue("phoneNumber", userFilter.getPhoneNumber(), SpecificationUtil::like)
        );

        return findAll(specification, userFilter.toPageable());
    }
}
