package skku.fit4you_android.Model;

public class Wishlist {
    private int cid;
    private String name, dscrp;

    public Wishlist() {
    }

    public Wishlist(int cid) {
        this.cid = cid;
    }

    public Wishlist(int cid, String name, String dscrp) {
        this.cid = cid;
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
}
