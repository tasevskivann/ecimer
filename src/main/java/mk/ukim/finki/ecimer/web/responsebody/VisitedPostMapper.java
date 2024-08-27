package mk.ukim.finki.ecimer.web.responsebody;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class VisitedPostMapper {

    public static final VisitedPostMapper INSTANCE = Mappers.getMapper(VisitedPostMapper.class);

    public abstract VisitedPost map(mk.ukim.finki.ecimer.core.VisitedPost visitedPost);
}
