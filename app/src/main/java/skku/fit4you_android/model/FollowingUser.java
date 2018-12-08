package skku.fit4you_android.model;

public class FollowingUser {
    private String userName;
    private int id_one, id_two;

    public FollowingUser(){

    }
    public FollowingUser(String userName) {
        this.userName = userName;
    }

    public FollowingUser(String userName, int id_one, int id_two) {
        this.userName = userName;
        this.id_one = id_one;
        this.id_two = id_two;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId_one() {
        return id_one;
    }

    public void setId_one(int id_one) {
        this.id_one = id_one;
    }

    public int getId_two() {
        return id_two;
    }

    public void setId_two(int id_two) {
        this.id_two = id_two;
    }
}
