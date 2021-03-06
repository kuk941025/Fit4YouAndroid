package skku.fit4you_android.retrofit.response;

public class ResponseRegister {
    public final int id, gender, height, shoulder, topsize, down_length, waist, weight, head_width, head_height;
    public final String userid, uname, nickname, photo, intro, email;

    public ResponseRegister(int id, int gender, int height, int shoulder, int topsize, int down_length, int waist, int weight, int head_width,
                            int head_height, String userid, String uname, String nickname, String photo, String intro, String email) {
        this.id = id;
        this.gender = gender;
        this.height = height;
        this.shoulder = shoulder;
        this.topsize = topsize;
        this.down_length = down_length;
        this.waist = waist;
        this.weight = weight;
        this.head_width = head_width;
        this.head_height = head_height;
        this.userid = userid;
        this.uname = uname;
        this.nickname = nickname;
        this.photo = photo;
        this.intro = intro;
        this.email = email;
    }
}
