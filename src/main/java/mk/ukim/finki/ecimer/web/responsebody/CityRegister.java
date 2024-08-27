package mk.ukim.finki.ecimer.web.responsebody;

import lombok.Data;

@Data
public class CityRegister {
    private String cityUuid;

    public String getCityUuid() {
        return cityUuid;
    }

    public void setCityUuid(String cityUuid) {
        this.cityUuid = cityUuid;
    }
}
