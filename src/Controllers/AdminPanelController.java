package Controllers;

import DataAccessLayer.ElectionsManager;
import DataAccessLayer.NotCorrectFileStructureException;
import DataAccessLayer.UsersManager;
import Models.Election.Election;
import Models.Statistics.Statistics;
import Models.User.Admin;
import Views.*;
import javafx.util.Pair;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AdminPanelController implements IMainController
{
    private Admin admin;
    private boolean loadedResources;
    private List<Election> elections;
    public AdminPanelController(Admin admin)
    {
        this.admin = admin;
        this.loadedResources = false;
    }

    @Override
    public void home()
    {
        while(true)
        {
            AdminPanelView adminPanel = new AdminPanelView();
            AdminDecision decision = adminPanel.act();

            if(decision == AdminDecision.GO_END)
            {
                GoodbyeView goodbyeView = new GoodbyeView();
                goodbyeView.act();
                return;
            }

            if(!loadedResources && !loadResources())
                return;

            switch(decision)
            {
                case END_ELECTION:
                    endElection();
                    break;

                case SHOW_STATISTICS:
                    showStatistics();
                    break;
            }
        }
    }

    private boolean loadResources()
    {
        UsersManager userManager = new UsersManager();
        List<String> electionsIds;
        try
        {
            electionsIds = userManager.getAdminElections(admin.getLogin());
        }
        catch (IOException e)
        {
            new ErrorView(e).act();
            return false;
        }
        catch (NotCorrectFileStructureException notCorrectFileStructure)
        {
            new ErrorView("Internal problem. Files have not correct structure.").act();
            return false;
        }

        ElectionsManager electionsManager = new ElectionsManager();
        try
        {
            elections = electionsManager.getElections(electionsIds);
        }
        catch (NotCorrectFileStructureException | ParseException e)
        {
            new ErrorView("Internal problem. Files have not correct structure.").act();
            return false;
        }
        catch (IOException e)
        {
            new ErrorView(e).act();
            return false;
        }

        loadedResources = true;

        return true;
    }

    protected void endElection()
    {
        List<Election> notEndedElections = new ArrayList<>();
        for(Election election : elections)
        {
            if(election.isEnded())
                continue;

            notEndedElections.add(election);
        }
        ElectionSelectingView selectionView = new ElectionSelectingView(notEndedElections);
        Election election = selectionView.act();

        if(election == null)
            return;

        election.end();

        saveElection(election);
    }

    protected void showStatistics()
    {
        ElectionSelectingView selectionView = new ElectionSelectingView(elections);
        Election election = selectionView.act();

        if(election == null)
            return;

        Statistics statistics = new Statistics(election);
        statistics.getMandats(20);
        StatisticsView statisticsView = new StatisticsView(statistics);
        statisticsView.act();
    }

    private void saveElection(Election election)
    {
        ElectionsManager electionsManager = new ElectionsManager();
        try
        {
            electionsManager.updateElectionStatus(election);
        }
        catch (IOException e)
        {
            new ErrorView(e).act();
        }
    }
}
