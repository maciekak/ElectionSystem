package Models.Election;

import sun.java2d.pipe.SpanShapeRenderer;

import java.text.SimpleDateFormat;
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
        return "Wybory dnia " + date.toString() + " do " + electionType.toString();
    }

    public String getId()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date) + "_" + electionType.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof Election && (obj == this || ((Election) obj).getId().equals(this.getId()));
    }
}
