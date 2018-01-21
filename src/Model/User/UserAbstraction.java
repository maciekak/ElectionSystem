package Model.User;

public abstract class UserAbstraction
{
    private String login;
    private String password;

    public UserAbstraction(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }
}
