package mk.ukim.finki.ecimer.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@MappedSuperclass
public abstract class Entity {

    @Id
    @Column(name = "uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    @PrePersist
    protected void assignUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
