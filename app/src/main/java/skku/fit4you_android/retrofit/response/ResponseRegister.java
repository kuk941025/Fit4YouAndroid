package skku.fit4you_android.retrofit.response;

public class ResponseRegister {
    public final String userid, pw, uname, nickname, gender, height, topsize, waist, intro, email, image;

    public ResponseRegister(String userid, String pw, String uname, String nickname, String gender,
                            String height, String topsize, String waist, String intro, String email, String image) {
        this.userid = userid;
        this.pw = pw;
        this.uname = uname;
        this.nickname = nickname;
        this.gender = gender;
        this.height = height;
        this.topsize = topsize;
        this.waist = waist;
        this.intro = intro;
        this.email = email;
        this.image = image;
    }
}
