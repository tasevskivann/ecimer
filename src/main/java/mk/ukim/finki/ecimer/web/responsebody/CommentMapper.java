package mk.ukim.finki.ecimer.web.responsebody;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CommentMapper {

    public static final CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    public abstract Comment map(mk.ukim.finki.ecimer.core.Comment comment);
}
