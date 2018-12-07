package skku.fit4you_android.model;

public class FollowingUser {
    private String userName;

    public FollowingUser(){

    }
    public FollowingUser(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
