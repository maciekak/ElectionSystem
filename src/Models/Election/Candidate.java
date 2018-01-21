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

}
