package skku.fit4you_android.model;

public class Wishlist {
    public static final int CLOTHING_TOP = 0;
    public static final int CLOTHING_PANTS = 1;
    public static final int CLOTHING_OUTER = 2;
    private int cid, uid, sid;
    private String name, dscrp; //dscrp == price
    private String imgURL;
    private int type;
    public Wishlist() {
    }

    public Wishlist(int uid) {
        this.uid = uid;
    }

    public Wishlist(int cid, int uid, String name, String dscrp) {
        this.cid = cid;
        this.uid = uid;
        this.name = name;
        this.dscrp = dscrp;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDscrp() {
        return dscrp;
    }

    public void setDscrp(String dscrp) {
        this.dscrp = dscrp;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
