package mk.ukim.finki.ecimer.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ec_post")
@Data
@EqualsAndHashCode(callSuper = true)
public class Post extends mk.ukim.finki.ecimer.domain.Entity {

    private String title;
    private String description;
    private String userUuid;
    private String cityUuid;
    private String municipalityUuid;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "postUuid", referencedColumnName = "uuid")
    private List<Comment> comments;

    public Post() {

    }

    public Post(String title, String description, String userUuid) {
        this.title = title;
        this.description = description;
        this.userUuid = userUuid;
    }

    public void updateTitleAndDescription(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void updateCityAndMunicipality(String cityUuid, String municipalityUuid) {
        this.cityUuid = cityUuid;
        this.municipalityUuid = municipalityUuid;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void deleteComment(String commentUuid) {
        this.comments.removeIf(comment -> comment.getUuid().equals(commentUuid));
    }

    @Override
    protected void assignUuid() {
        super.assignUuid();
        this.createdAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getCityUuid() {
        return cityUuid;
    }

    public String getMunicipalityUuid() {
        return municipalityUuid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Comment getCommentByUuid(String commentUuid){
        return this.comments.stream().filter(comment -> comment.getUuid().equals(commentUuid)).findFirst().orElse(null);
    }
}
