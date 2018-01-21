package Models.Election;

import java.util.ArrayList;
import java.util.List;

public class Party
{
    private String name;
    private List<Person> candidates;


    public Party(String name)
    {
        this.name = name;
        this.candidates = new ArrayList<>();
        //TODO: Check if that party already doesn't exist
    }
}
