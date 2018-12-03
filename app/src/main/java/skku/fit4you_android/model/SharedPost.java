package skku.fit4you_android.model;

import java.util.Date;

public class SharedPost {
    public static int POST_CLOTHING = 1;
    public static int POST_STYLE_SHARE = 2;
    private String clothing_name, user_name, hash_tags;
    private Date date;
    private int price, type_of_post, num_likes, num_comments, cost;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public static int getPostClothing() {
        return POST_CLOTHING;
    }

    public static void setPostClothing(int postClothing) {
        POST_CLOTHING = postClothing;
    }

    public static int getPostStyleShare() {
        return POST_STYLE_SHARE;
    }

    public static void setPostStyleShare(int postStyleShare) {
        POST_STYLE_SHARE = postStyleShare;
    }

    public String getClothing_name() {
        return clothing_name;
    }

    public void setClothing_name(String clothing_name) {
        this.clothing_name = clothing_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getHash_tags() {
        return hash_tags;
    }

    public void setHash_tags(String hash_tags) {
        this.hash_tags = hash_tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getType_of_post() {
        return type_of_post;
    }

    public void setType_of_post(int type_of_post) {
        this.type_of_post = type_of_post;
    }

    public int getNum_likes() {
        return num_likes;
    }

    public void setNum_likes(int num_likes) {
        this.num_likes = num_likes;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }
}
