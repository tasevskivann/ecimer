package mk.ukim.finki.ecimer.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ec_municipality")
@Data
@EqualsAndHashCode(callSuper = true)
public class Municipality extends mk.ukim.finki.ecimer.domain.Entity {

    private String name;
    private String cityUuid;

    public Municipality(){

    }

    public Municipality(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCityUuid() {
        return cityUuid;
    }
}
