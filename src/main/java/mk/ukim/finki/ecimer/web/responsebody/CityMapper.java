package mk.ukim.finki.ecimer.web.responsebody;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {MunicipalityMapper.class})
public abstract class CityMapper {

    public static CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    public abstract City map(mk.ukim.finki.ecimer.core.City city);

    public abstract List<City> map(List<mk.ukim.finki.ecimer.core.City> city);

    @Mapping(source = "uuid", target = "cityUuid")
    public abstract CityRegister mapRegister(mk.ukim.finki.ecimer.core.City city);
}
