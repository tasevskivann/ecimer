package mk.ukim.finki.ecimer.core;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ec_saved_post")
public class SavedPost extends mk.ukim.finki.ecimer.domain.Entity {

    // userUuid is a field that means for which user is saved post.
    private String userUuid;
    private String postUuid;
    private LocalDateTime createdAt;

    public SavedPost() {

    }

    public SavedPost(String userUuid, String postUuid) {
        this.userUuid = userUuid;
        this.postUuid = postUuid;
    }

    public SavedPost(String userUuid, String postUuid, LocalDateTime createdAt) {
        this.userUuid = userUuid;
        this.postUuid = postUuid;
        this.createdAt = createdAt;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(String postUuid) {
        this.postUuid = postUuid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    protected void assignUuid() {
        super.assignUuid();
        this.createdAt = LocalDateTime.now();
    }
}
