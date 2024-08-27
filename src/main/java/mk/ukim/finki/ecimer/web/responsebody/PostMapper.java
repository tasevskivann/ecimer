package mk.ukim.finki.ecimer.web.responsebody;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {CommentMapper.class})
public abstract class PostMapper {

    public static final PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    public abstract Post map(mk.ukim.finki.ecimer.core.Post post);

    public abstract List<Post> map(List<mk.ukim.finki.ecimer.core.Post> posts);

    public abstract PostCreate mapCreate(mk.ukim.finki.ecimer.core.Post post);
}
