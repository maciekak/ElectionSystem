package Models.Election;

import java.util.Comparator;

public class Candidate implements Comparable<Candidate>
{
    private Person person;
    private int voicesCount;
    private Election election;

    public Candidate(Person person, Election election)
    {
        this.person = person;
        this.election = election;
        this.voicesCount = 0;
    }

    public Candidate(Person person, Election election, int voicesCount)
    {
        this.person = person;
        this.election = election;
        this.voicesCount = voicesCount;
    }

    public int getVotesCount()
    {
        return voicesCount;
    }

    public void addVote()
    {
        this.voicesCount++;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof Candidate && (obj == this || ((Candidate) obj).getPerson().equals(this.getPerson()));
    }

    public Person getPerson()
    {
        return person;
    }

    @Override
    public int compareTo(Candidate o)
    {
        return Integer.compare(voicesCount, o.voicesCount);
    }

    public class CandidateComparator implements Comparator<Candidate>
    {
        @Override
        public int compare(Candidate o1, Candidate o2)
        {
            return Integer.compare(o1.getVotesCount(), o2.getVotesCount());
        }
    }
}
