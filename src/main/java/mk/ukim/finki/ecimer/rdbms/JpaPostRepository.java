package mk.ukim.finki.ecimer.rdbms;

import mk.ukim.finki.ecimer.common.SpecificationUtil;
import mk.ukim.finki.ecimer.core.Post;
import mk.ukim.finki.ecimer.core.PostRepository;
import mk.ukim.finki.ecimer.core.query.filter.PostFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import static mk.ukim.finki.ecimer.common.SpecificationUtil.acceptNotNullValue;
import static mk.ukim.finki.ecimer.common.SpecificationUtil.filterNull;

@Repository
public interface JpaPostRepository extends JpaRepository<Post, String>, PostRepository, JpaSpecificationExecutor<Post> {


    default Page<Post> search(PostFilter postFilter) {

        final Specification<Post> specification = filterNull(
                acceptNotNullValue("userUuid", postFilter.getUserUuid(), SpecificationUtil::equals),
                acceptNotNullValue("cityUuid", postFilter.getCityUuid(), SpecificationUtil::equals),
                acceptNotNullValue("municipalityUuid", postFilter.getMunicipalityUuid(), SpecificationUtil::equals),
                acceptNotNullValue("title", postFilter.getTitle(), SpecificationUtil::like));

        return findAll(specification, postFilter.toPageable());
    }
}
