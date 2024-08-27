package mk.ukim.finki.ecimer.web.responsebody;

import java.util.List;

public class City {

    private String uuid;
    private String name;
    private List<Municipality> municipalities;

    public City(){

    }

    public City(String uuid, String name, List<Municipality> municipalities) {
        this.uuid = uuid;
        this.name = name;
        this.municipalities = municipalities;
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

    public List<Municipality> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<Municipality> municipalities) {
        this.municipalities = municipalities;
    }
}
