package skku.fit4you_android.retrofit.response;

public class ResponseClothing {
    public final String cname, hashtag, link, mallname, basicimage, photo1, photo2, photo3;
    public final int views, cost, season, gender;

    public ResponseClothing(String cname, String hashtag, String link, String mallname, String basicimage, String photo1, String photo2,
                            String photo3, int views, int cost, int season, int gender) {
        this.cname = cname;
        this.hashtag = hashtag;
        this.link = link;
        this.mallname = mallname;
        this.basicimage = basicimage;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.views = views;
        this.cost = cost;
        this.season = season;
        this.gender = gender;
    }
}
