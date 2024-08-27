package mk.ukim.finki.ecimer.web.responsebody;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class SavedPostMapper {

    public static final SavedPostMapper INSTANCE = Mappers.getMapper(SavedPostMapper.class);

    public abstract SavedPost map(mk.ukim.finki.ecimer.core.SavedPost savedPost);

}
