package skku.fit4you_android.retrofit.response;

public class ResponseWishList {
    public final int id, uid, top_outer, top_outer_size, top_1, top_1_size, top_2, top_2_size, down, down_size;

    public ResponseWishList(int id, int uid, int top_outer, int top_outer_size, int top_1, int top_1_size, int top_2, int top_2_size, int down, int down_size) {
        this.id = id;
        this.uid = uid;
        this.top_outer = top_outer;
        this.top_outer_size = top_outer_size;
        this.top_1 = top_1;
        this.top_1_size = top_1_size;
        this.top_2 = top_2;
        this.top_2_size = top_2_size;
        this.down = down;
        this.down_size = down_size;
    }
}
