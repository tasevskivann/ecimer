package mk.ukim.finki.ecimer.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ec_visited_post")
public class VisitedPost extends mk.ukim.finki.ecimer.domain.Entity {

    private String postUuid;
    private String userUuid;
    private LocalDateTime visitedAt;

    public VisitedPost(){

    }

    public VisitedPost(String postUuid, String userUuid) {
        this.postUuid = postUuid;
        this.userUuid = userUuid;
    }

    @Override
    protected void assignUuid() {
        super.assignUuid();
        this.visitedAt = LocalDateTime.now();
    }

    public String getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(String postUuid) {
        this.postUuid = postUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public LocalDateTime getVisitedAt() {
        return visitedAt;
    }

    public void setVisitedAt(LocalDateTime visitedAt) {
        this.visitedAt = visitedAt;
    }
}
