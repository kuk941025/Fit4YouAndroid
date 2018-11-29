package skku.fit4you_android.retrofit.response;

public class ResponseLogin {
    public final String success;
    public final String uid;

    public ResponseLogin(String success, String uid) {
        this.success = success;
        this.uid = uid;
    }
}
