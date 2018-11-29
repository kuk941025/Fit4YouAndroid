package skku.fit4you_android.retrofit.response;

public class ResponseSuccess extends Response {

    public final String success;
    public final String text;

    public ResponseSuccess(String success, String text) {
        this.success = success;
        this.text = text;
    }
}
