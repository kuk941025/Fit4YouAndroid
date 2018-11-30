package skku.fit4you_android.retrofit.response;

public class ResponseRegister {
    public final String userid, pw, uname, nickname, intro, email, photo;
    public final int gender, height, topsize, waist, weight;

    public ResponseRegister(String userid, String pw, String uname, String nickname, String intro, String email, String photo, int gender, int height, int topsize, int waist, int weight) {
        this.userid = userid;
        this.pw = pw;
        this.uname = uname;
        this.nickname = nickname;
        this.intro = intro;
        this.email = email;
        this.photo = photo;
        this.gender = gender;
        this.height = height;
        this.topsize = topsize;
        this.waist = waist;
        this.weight = weight;
    }
}
