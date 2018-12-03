package skku.fit4you_android.retrofit.response;

public class ResponsePost {
    public final int id, totalcost, views, uid, like, numpost, commentnum;
    public final String hashtag, clothingimage, avatarimage, title, createdAt, islike;

    public ResponsePost(int id, int totalcost, int views, int uid, int like, int numpost,
                        int commentnum, String hashtag, String clothingimage, String avatarimage,
                        String title, String createdAt, String islike) {
        this.id = id;
        this.totalcost = totalcost;
        this.views = views;
        this.uid = uid;
        this.like = like;
        this.numpost = numpost;
        this.commentnum = commentnum;
        this.hashtag = hashtag;
        this.clothingimage = clothingimage;
        this.avatarimage = avatarimage;
        this.title = title;
        this.createdAt = createdAt;
        this.islike = islike;
    }
}
