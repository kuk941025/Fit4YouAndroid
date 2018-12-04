package skku.fit4you_android.retrofit.response;

public class ResponseLike {
    public final String success;
    public final int likes;

    public ResponseLike(String success, int likes) {
        this.success = success;
        this.likes = likes;
    }
}
