package Models.Election;

import java.util.Date;
import java.util.List;

public class Election
{
    private Date date;
    private List<Candidate> candidates;
    private ElectionType electionType;
    private boolean ended;


    public Election(Date date, List<Candidate> candidates, ElectionType electionType, boolean ended)
    {
        this.date = date;
        this.candidates = candidates;
        this.electionType = electionType;
        this.ended = ended;
    }

    public List<Candidate> getCandidates()
    {
        return candidates;
    }

    public ElectionType getElectionType()
    {
        return electionType;
    }

    public boolean isEnded()
    {
        return ended;
    }

    public void setEnded(boolean ended)
    {
        this.ended = ended;
    }

    public String getName()
    {
        return "Election on " + date.toString() + " to " + electionType.toString();
    }
}
