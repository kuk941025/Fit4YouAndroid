package skku.fit4you_android.retrofit.response;

public class ResponseCommentInfo {
    public final int id, uid, pid;
    public final String contents, createdAt, photo;//nickname;
    public final CommentUser User;

    public ResponseCommentInfo(int id, int uid, int pid, String contents, String createdAt, CommentUser User, String photo) {
        this.id = id;
        this.uid = uid;
        this.pid = pid;
        this.contents = contents;
        this.createdAt = createdAt;
        this.User = User;
        this.photo = photo;
    }
}
