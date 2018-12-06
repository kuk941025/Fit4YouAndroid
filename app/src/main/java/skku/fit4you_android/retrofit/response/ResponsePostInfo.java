package skku.fit4you_android.retrofit.response;

public class ResponsePostInfo {
    public final int id, totalcost, views, top_outer, top_1, top_2, down, top_outer_size, top_1_size, top_2_size, down_size,
            uid, like, numpost, numcomment, height, weight;
    public final String hashtag, clothingimage, avatarimage, title, createdAt, islike, top_outer_name, top_1_name, top_2_name,down_name,top_outer_mall
            ,top_1_mall,top_2_mall,down_mall;

    public ResponsePostInfo(int id, int totalcost, int views, int top_outer, int top_1, int top_2, int down, int top_outer_size, int top_1_size, int top_2_size, int down_size, int uid, int like, int numpost, int height, int weight, String hashtag, String clothingimage,
                            String avatarimage, String title, String createdAt, String islike, String top_outer_name, String top_1_name,
                            String top_2_name, String down_name, String top_outer_mall, String top_1_mall, String top_2_mall, String down_mall, int numcomment) {
        this.id = id;
        this.totalcost = totalcost;
        this.views = views;
        this.top_outer = top_outer;
        this.top_1 = top_1;
        this.top_2 = top_2;
        this.down = down;
        this.top_outer_size = top_outer_size;
        this.top_1_size = top_1_size;
        this.top_2_size = top_2_size;
        this.down_size = down_size;
        this.uid = uid;
        this.like = like;
        this.numpost = numpost;
        this.numcomment = numcomment;
        this.height = height;
        this.weight = weight;
        this.hashtag = hashtag;
        this.clothingimage = clothingimage;
        this.avatarimage = avatarimage;
        this.title = title;
        this.createdAt = createdAt;
        this.islike = islike;
        this.top_outer_name = top_outer_name;
        this.top_1_name = top_1_name;
        this.top_2_name = top_2_name;
        this.down_name = down_name;
        this.top_outer_mall = top_outer_mall;
        this.top_1_mall = top_1_mall;
        this.top_2_mall = top_2_mall;
        this.down_mall = down_mall;
    }
}
