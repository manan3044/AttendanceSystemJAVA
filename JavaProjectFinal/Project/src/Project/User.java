package Project;

public abstract class User implements Login{
    protected String username;
    protected String password;
    protected String category;

    public User(String username, String password, String category) {
        this.username = username;
        this.password = password;
        this.category = category;
    }
}

