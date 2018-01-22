package Controllers;

import DataAccessLayer.ElectionsManager;
import DataAccessLayer.NotCorrectFileStructureException;
import DataAccessLayer.UsersManager;
import Models.Election.Candidate;
import Models.Election.Election;
import Models.User.User;
import Views.ElectionSelectingView;
import Views.ErrorView;
import Views.UserDecision;
import Views.UserPanelView;
import javafx.util.Pair;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserPanelController implements IMainController
{
    private User user;

    private List<String> notVotedElectionsIds;
    private List<Pair<String, String>> votedElectionIdsCandidateIds;

    private HashMap<Election, Candidate> votedElections;
    private List<Election> availableElections;
    private HashMap<Election, Candidate> doneElections;

    private boolean loadedResources;

    public UserPanelController(User user)
    {
        loadedResources = false;
        this.user = user;
    }

    @Override
    public void home()
    {
        while(true)
        {
            UserPanelView userPanel = new UserPanelView();
            UserDecision decision = userPanel.act();

            if(decision == UserDecision.GO_END)
                return;

            if(!loadedResources && !loadResources())
                return;

            switch(decision)
            {
                case GO_ELECT:
                    elect();
                    break;

                case SHOW_STATISTICS:
                    showStatistics();
                    break;
            }
        }
    }

    private boolean loadResources()
    {
        loadedResources = true;

        UsersManager userManager = new UsersManager();
        Pair<List<Pair<String,String>>, List<String>> pair;
        try
        {
            pair = userManager.getUserElections(user);
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

        this.votedElectionIdsCandidateIds = pair.getKey();
        this.notVotedElectionsIds = pair.getValue();

        List<String> onlyElectionsIds = new ArrayList<String>();
        for(Pair<String, String> id : pair.getKey())
            onlyElectionsIds.add(id.getKey());

        onlyElectionsIds.addAll(notVotedElectionsIds);

        ElectionsManager electionsManager = new ElectionsManager();
        List<Election> elections;
        try
        {
            elections = electionsManager.getElections(onlyElectionsIds);
        }
        catch (IOException e)
        {
            new ErrorView(e).act();
            return false;
        }
        catch (NotCorrectFileStructureException | ParseException e)
        {
            new ErrorView("Internal problem. Files have not correct structure.").act();
            return false;
        }

        distributeElections(elections);
        return true;
    }

    private void distributeElections(List<Election> elections)
    {
        votedElections = new HashMap<>();
        availableElections = new ArrayList<>();
        doneElections = new HashMap<>();

        for(Election election : elections)
        {
            String candidateId = getCandidateIdFromElectionId(election.getId());
            if(candidateId == null)
            {
                if(election.isEnded())
                    doneElections.put(election, null);
                else
                    availableElections.add(election);
            }
            else
            {
                Candidate candidate = getCandidateByIdFromElection(election, candidateId);
                if(election.isEnded())
                    doneElections.put(election, candidate);
                else
                    votedElections.put(election, candidate);
            }
        }
    }

    private String getCandidateIdFromElectionId(String electionId)
    {
        for(Pair<String,String> id : votedElectionIdsCandidateIds)
        {
            if(id.getKey().equals(electionId))
                return id.getValue();
        }
        return null;
    }

    private Candidate getCandidateByIdFromElection(Election election, String candidateId)
    {
        for(Candidate candidate : election.getCandidates())
        {
            if(candidate.getPerson().getId().equals(candidateId))
                return candidate;
        }
        return null;
    }

    protected void elect()
    {
        ElectionSelectingView selectionView = new ElectionSelectingView(availableElections);
        Election election = selectionView.act();


    }


    protected void showStatistics()
    {

    }
}
