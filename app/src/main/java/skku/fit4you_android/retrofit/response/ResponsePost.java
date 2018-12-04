package skku.fit4you_android.retrofit.response;

public class ResponsePost {
    public final int id, totalcost, views, top_outer, top_1, top_2, down, top_outer_size, top_1_size, top_2_size, down_size,
            uid, like, numpost, commentnum, height, weight;
    public final String hashtag, clothingimage, avatarimage, title, createdAt, islike;

    public ResponsePost(int id, int totalcost, int views, int top_outer, int top_1, int top_2, int down, int top_outer_size,
                        int top_1_size, int top_2_size, int down_size, int uid, int like, int numpost, int commentnum, int height, int weight,
                        String hashtag, String clothingimage, String avatarimage, String title, String createdAt, String islike) {
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
        this.commentnum = commentnum;
        this.height = height;
        this.weight = weight;
        this.hashtag = hashtag;
        this.clothingimage = clothingimage;
        this.avatarimage = avatarimage;
        this.title = title;
        this.createdAt = createdAt;
        this.islike = islike;
    }
}
