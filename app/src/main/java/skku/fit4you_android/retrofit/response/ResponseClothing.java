package skku.fit4you_android.retrofit.response;

public class ResponseClothing {
    public final int id, views, cost, season, gender, uid, oid, like;
    public final String cname, hashtag, link, mallname, photo1, photo2, photo3, Islike;

    public ResponseClothing(int id, int views, int cost, int season, int gender, int uid, int oid, int like, String cname,
                            String hashtag, String link, String mallname, String photo1, String photo2, String photo3, String islike) {
        this.id = id;
        this.views = views;
        this.cost = cost;
        this.season = season;
        this.gender = gender;
        this.uid = uid;
        this.oid = oid;
        this.like = like;
        this.cname = cname;
        this.hashtag = hashtag;
        this.link = link;
        this.mallname = mallname;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        Islike = islike;
    }
}
