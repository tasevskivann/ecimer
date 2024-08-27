package mk.ukim.finki.ecimer.web.responsebody;

public class Comment {
    private String uuid;
    private String userUuid;
    private String postUuid;
    private String content;

    public Comment() {

    }

    public Comment(String uuid, String userUuid, String postUuid, String content) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.postUuid = postUuid;
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
