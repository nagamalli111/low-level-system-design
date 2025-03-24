package google_drive;

public class User {
    private String userName;
    private String password;
    private Role role;

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public enum Role {
        ADMIN, USER
    }

    public boolean authenticate(String passWord) {
        return true;
    }
}
