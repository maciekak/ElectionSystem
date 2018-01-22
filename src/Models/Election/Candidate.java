package Models.Election;

public class Candidate
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

    public int getVoicesCount()
    {
        return voicesCount;
    }

    public void addVoice()
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
}
