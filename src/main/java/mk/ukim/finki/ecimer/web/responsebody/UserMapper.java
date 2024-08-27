package mk.ukim.finki.ecimer.web.responsebody;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {PostMapper.class})
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User map(mk.ukim.finki.ecimer.core.User user);

    public abstract UserSearch mapSearch(mk.ukim.finki.ecimer.core.User user);

    public abstract List<UserSearch> map(List<mk.ukim.finki.ecimer.core.User> users);

    public abstract UserRegister mapRegister(mk.ukim.finki.ecimer.core.User user);
}
