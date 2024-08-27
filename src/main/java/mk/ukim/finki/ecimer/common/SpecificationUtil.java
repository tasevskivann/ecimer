package mk.ukim.finki.ecimer.common;

import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Nullable;
import javax.persistence.criteria.JoinType;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

public class SpecificationUtil {

    public static <T, RFieldValue> Specification<T> equals(String field, RFieldValue fieldValue) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(field), fieldValue);
    }

    public static <T, RFieldValue> Specification<T> like(String field, RFieldValue fieldValue) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(field), "%" + fieldValue + "%");
    }

    public static <T, RFieldValue> Specification<T> isNull(String field, RFieldValue fieldValue) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(field));
    }

    public static <T, RFieldValue> Specification<T> matchingId(String joinTable, String field,
                                                               RFieldValue fieldValue) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(joinTable).get(field), fieldValue);
    }

    public static <T> Specification<T> fetchWithLeftJoin(String field) {
        return (root, query, criteriaBuilder) -> {
            if (query.getResultType().equals(Long.class)) {
                // join fetch should not be used on a count query.
                return null;
            }
            root.fetch(field, JoinType.LEFT);
            return null;
        };
    }

    public static <T, UComparable extends Comparable<UComparable>> Specification<T> between(String field,
                                                                                            UComparable from, UComparable to) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(field), from, to);
    }

    public static <T, UComparable extends Comparable<UComparable>> Specification<T> lessThanOrEqualTo(String field,
                                                                                                      UComparable fieldValue) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.lessThanOrEqualTo(root.get(field), fieldValue);
    }

    public static <T, UComparable extends Comparable<UComparable>> Specification<T> greaterThanOrEqualTo(String field,
                                                                                                         UComparable fieldValue) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), fieldValue);
    }

    @Nullable
    public static <T, RFieldValue> Specification<T> acceptNotNullValue(String fieldName, RFieldValue fieldValue,
                                                                       BiFunction<String, RFieldValue, Specification<T>> fn) {
        if (fieldValue == null) {
            return null;
        }
        return fn.apply(fieldName, fieldValue);
    }

    @Nullable
    public static <T, RFieldValue> Specification<T> acceptNotNullValue(String fieldName, RFieldValue fieldValue1,
                                                                       RFieldValue fieldValue2, TriFunction<String, RFieldValue, RFieldValue, Specification<T>> fn) {
        if (fieldValue1 == null || fieldValue2 == null) {
            return null;
        }
        return fn.apply(fieldName, fieldValue1, fieldValue2);
    }

    @SafeVarargs
    public static <T> Specification<T> filterNull(Specification<T>... specifications) {
        return Arrays.stream(specifications)
                .filter(Objects::nonNull)
                .reduce(whereTrue(), Specification::and);
    }

    private static <T> Specification<T> whereTrue() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and();
    }

    public interface TriFunction<First, Second, Third, Return> {
        Return apply(First first, Second second, Third third);
    }
}
