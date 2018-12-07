package skku.fit4you_android.retrofit.response;

public class ResponseClothing {
    public final int id, views, cost, season, gender, uid, oid, like, numClothing;
    public final String cname, hashtag, link, mallname, basicimage, photo1, photo2, photo3, islike, createdAt, nickname;

    public ResponseClothing(int id, int views, int cost, int season, int gender, int uid, int oid, int like, int numClothing, String cname,
                            String hashtag, String link, String mallname, String basicimage, String photo1, String photo2, String photo3,
                            String islike, String createdAt, String nickname) {
        this.id = id;
        this.views = views;
        this.cost = cost;
        this.season = season;
        this.gender = gender;
        this.uid = uid;
        this.oid = oid;
        this.like = like;
        this.numClothing = numClothing;
        this.cname = cname;
        this.hashtag = hashtag;
        this.link = link;
        this.mallname = mallname;
        this.basicimage = basicimage;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.islike = islike;
        this.createdAt = createdAt;
        this.nickname = nickname;
    }
}
