package skku.fit4you_android.retrofit.response;

public class ResponseFollowInfo {
    public final int id, id_one, id_two;
    public final String nickname;

    public ResponseFollowInfo(int id, int id_one, int id_two, String nickname) {
        this.id = id;
        this.id_one = id_one;
        this.id_two = id_two;
        this.nickname = nickname;
    }
}
