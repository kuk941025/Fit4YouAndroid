package skku.fit4you_android.retrofit.response;

public class ResponseSize {
    public final int id, topsize, top_length, shoulder_width, bust, sleeve, down_length, thigh, rise, hem, waist, cid;
    public final String size_name;

    public ResponseSize(int id, int topsize, int top_length, int shoulder_width, int bust,
                        int sleeve, int down_length, int thigh, int rise, int hem, int waist, int cid, String size_name) {
        this.id = id;
        this.topsize = topsize;
        this.top_length = top_length;
        this.shoulder_width = shoulder_width;
        this.bust = bust;
        this.sleeve = sleeve;
        this.down_length = down_length;
        this.thigh = thigh;
        this.rise = rise;
        this.hem = hem;
        this.waist = waist;
        this.cid = cid;
        this.size_name = size_name;
    }
}
