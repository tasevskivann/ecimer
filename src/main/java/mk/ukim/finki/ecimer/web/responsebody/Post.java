package mk.ukim.finki.ecimer.web.responsebody;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private String uuid;
    private String title;
    private String description;
    private String userUuid;
    private String cityUuid;
    private String municipalityUuid;
    private LocalDateTime createdAt;
    private List<Comment> comments;

    public Post(){

    }

    public Post(String uuid, String title, String description, String userUuid, String cityUuid, String municipalityUuid,
                List<Comment> comments) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.userUuid = userUuid;
        this.cityUuid = cityUuid;
        this.municipalityUuid = municipalityUuid;
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getCityUuid() {
        return cityUuid;
    }

    public void setCityUuid(String cityUuid) {
        this.cityUuid = cityUuid;
    }

    public String getMunicipalityUuid() {
        return municipalityUuid;
    }

    public void setMunicipalityUuid(String municipalityUuid) {
        this.municipalityUuid = municipalityUuid;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
