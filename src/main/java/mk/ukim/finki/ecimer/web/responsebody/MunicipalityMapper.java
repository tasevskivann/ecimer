package mk.ukim.finki.ecimer.web.responsebody;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class MunicipalityMapper {

    public static MunicipalityMapper INSTANCE = Mappers.getMapper(MunicipalityMapper.class);

    public abstract Municipality map(mk.ukim.finki.ecimer.core.Municipality municipality);

    public abstract List<Municipality> map(List<mk.ukim.finki.ecimer.core.Municipality> municipalities);
}
