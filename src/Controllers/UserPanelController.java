package Controllers;

import DataAccessLayer.NotCorrectFileStructureException;
import DataAccessLayer.UsersManager;
import Models.Election.Candidate;
import Models.Election.Election;
import Models.User.User;
import Views.ErrorView;
import Views.UserDecision;
import Views.UserPanelView;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public class UserPanelController implements IMainController
{
    private User user;

    private List<String> availableElectionsIds;
    private List<Pair<String, String>> votedElectionsIds;

    private List<Pair<Election, Candidate>>  votedElections;
    private List<Election> availableElections;
    private List<Pair<Election, Candidate>> doneElections;

    public UserPanelController(User user)
    {
        this.user = user;
    }

    @Override
    public void home()
    {
        while(true)
        {
            UserPanelView userPanel = new UserPanelView();
            UserDecision decision = userPanel.act();

            switch(decision)
            {
                case GO_END:
                    return;

                case GO_ELECT:
                    elect();
                    break;

                case SHOW_STATISTICS:
                    showStatistics();
                    break;
            }
        }
    }

    private void elect()
    {
        UsersManager userManager = new UsersManager();
        Pair<List<Pair<String,String>>, List<String>> pair;
        try
        {
            pair = userManager.getUserElections(user);
        }
        catch (IOException e)
        {
            new ErrorView(e).act();
            return;
        }
        catch (NotCorrectFileStructureException notCorrectFileStructure)
        {
            new ErrorView("Internal problem. Files have not correct structure.").act();
            return;
        }

    }


    private void showStatistics()
    {

    }
}
