package skku.fit4you_android.model;

import java.io.Serializable;

import skku.fit4you_android.util.Converter;

public class Wishlist implements Serializable {
    public static final int CLOTHING_TOP = Converter.CLOTHING_TOP;
    public static final int CLOTHING_PANTS = Converter.CLOTHING_BOTTOM;
    public static final int CLOTHING_OUTER = Converter.CLOTHING_OUTER;
    private static int WISH_COUNT = 0;
    private int cid, uid, sid, wid, oid;
    private String name, dscrp; //dscrp == price
    private String imgURL, basicURL;
    private int type;
    private boolean isUserSelected;
    public Wishlist() {
        this.wid = WISH_COUNT++;
        isUserSelected = false;
    }

    public Wishlist(int uid) {
        this.uid = uid;this.wid = WISH_COUNT++;
        isUserSelected = false;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public Wishlist(int cid, int uid, String name, String dscrp) {
        this.cid = cid;
        this.uid = uid;
        this.name = name;
        this.dscrp = dscrp;
        this.wid = WISH_COUNT++;
        isUserSelected = false;
    }

    public String getBasicURL() {
        return basicURL;
    }

    public void setBasicURL(String basicURL) {
        this.basicURL = basicURL;
    }

    public boolean isUserSelected() {
        return isUserSelected;
    }

    public void setUserSelected(boolean userSelected) {
        isUserSelected = userSelected;
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

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
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
