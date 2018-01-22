package Controllers;

import Models.User.Admin;

public class AdminPanelController implements IMainController
{
    private Admin admin;

    public AdminPanelController(Admin admin)
    {
        this.admin = admin;
    }

    @Override
    public void home()
    {

    }
}
