package skku.fit4you_android.retrofit.response;

public class ResponseLike {
    public final String success, text;
    public final int likes;

    public ResponseLike(String success, String text, int likes) {
        this.success = success;
        this.text = text;
        this.likes = likes;
    }
}
