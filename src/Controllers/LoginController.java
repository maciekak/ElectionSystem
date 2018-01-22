package Controllers;

import DataAccessLayer.UsersManager;
import Models.User.UserAbstraction;
import Views.ErrorView;
import Views.LoginView;
import javafx.util.Pair;

import java.io.IOException;

public class LoginController
{
    public UserAbstraction login()
    {
        UsersManager usersManager = new UsersManager();
        LoginView view = new LoginView();

        Pair<String, String> loginPassword = view.act();

        UserAbstraction user;
        try
        {
            user = usersManager.tryLogIn(loginPassword.getKey(), loginPassword.getValue());
        }
        catch (Exception e)
        {
            new ErrorView(e).act();
            return null;
        }

        if (user == null)
            new ErrorView("Nie ma takiego użytkownika.").act();

        return user;
    }
}
