package Models.Election;

public class Person
{
    private String firstName;
    private String lastName;
    private String party;

    public Person(String firstName, String lastName, String party)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.party = party;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getId() { return firstName + "_" + lastName; }

    public String getParty()
    {
        return party;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof Person && (obj == this || ((Person) obj).getId().equals(this.getId()));
    }
}
