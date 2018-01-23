import Controllers.AdminPanelController;
import Controllers.IMainController;
import Controllers.LoginController;
import Controllers.UserPanelController;
import Models.User.Admin;
import Models.User.User;
import Models.User.UserAbstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {

        LoginController login = new LoginController();

        UserAbstraction user = login.login();

        if(user == null)
            return;

        IMainController mainController;
        if(user instanceof Admin)
            mainController = new AdminPanelController((Admin) user);
        else
            mainController = new UserPanelController((User) user);

        mainController.home();
    }


}