package mk.ukim.finki.ecimer.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ec_comment")
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends mk.ukim.finki.ecimer.domain.Entity {
    private String userUuid;
    private String postUuid;
    private String content;
    private LocalDateTime createdAt;

    public Comment() {

    }

    public Comment(String userUuid, String postUuid, String content) {
        this.userUuid = userUuid;
        this.postUuid = postUuid;
        this.content = content;
    }

    @Override
    protected void assignUuid() {
        super.assignUuid();
        this.createdAt = LocalDateTime.now();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getPostUuid() {
        return postUuid;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
