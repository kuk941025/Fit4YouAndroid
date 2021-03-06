package skku.fit4you_android.model;

import android.support.annotation.NonNull;

import java.util.Date;

public class SharedPost implements Comparable<SharedPost>{
    public static int POST_CLOTHING = 1;
    public static int POST_STYLE_SHARE = 2;
    private String clothing_name, user_name, hash_tags, mallURL, mall_name, basic_image;
    private String photo1, photo2, photo3;
    private Date date;
    private int price, type_of_post, num_likes, num_comments, cost, views, gender, uid, oid, id;
    private int top_outer, top_1, top_2, top_outer_size, top_1_size, top_2_size, down, down_size, height, weight, isFollowing;
    private boolean isLike;
    @Override
    public int compareTo(@NonNull SharedPost o) {
        return o.getDate().compareTo(getDate());
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getId() {
        return id;
    }

    public int getTop_outer() {
        return top_outer;
    }

    public int getDown_size() {
        return down_size;
    }

    public void setDown_size(int down_size) {
        this.down_size = down_size;
    }

    public int getDown() {

        return down;
    }

    public int getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(int isFollowing) {
        this.isFollowing = isFollowing;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setTop_outer(int top_outer) {
        this.top_outer = top_outer;
    }

    public int getTop_1() {
        return top_1;
    }

    public void setTop_1(int top_1) {
        this.top_1 = top_1;
    }

    public int getTop_2() {
        return top_2;
    }

    public void setTop_2(int top_2) {
        this.top_2 = top_2;
    }

    public int getTop_outer_size() {
        return top_outer_size;
    }

    public void setTop_outer_size(int top_outer_size) {
        this.top_outer_size = top_outer_size;
    }

    public int getTop_1_size() {
        return top_1_size;
    }

    public void setTop_1_size(int top_1_size) {
        this.top_1_size = top_1_size;
    }

    public int getTop_2_size() {
        return top_2_size;
    }

    public void setTop_2_size(int top_2_size) {
        this.top_2_size = top_2_size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMall_name() {
        return mall_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setMall_name(String mall_name) {
        this.mall_name = mall_name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getMallURL() {
        return mallURL;
    }

    public void setMallURL(String mallURL) {
        this.mallURL = mallURL;
    }

    public String getBasic_image() {
        return basic_image;
    }

    public void setBasic_image(String basic_image) {
        this.basic_image = basic_image;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
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
