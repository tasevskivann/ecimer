package mk.ukim.finki.ecimer.web.responsebody;

public class Municipality {
    private String uuid;
    private String name;
    private String cityUuid;

    public Municipality(){

    }

    public Municipality(String uuid, String name, String cityUuid) {
        this.uuid = uuid;
        this.name = name;
        this.cityUuid = cityUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityUuid() {
        return cityUuid;
    }

    public void setCityUuid(String cityUuid) {
        this.cityUuid = cityUuid;
    }
}
