package DataAccessLayer;

import Models.Election.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElectionsManager
{
    public List<Election> getElections(List<String> electionsIds) throws IOException, NotCorrectFileStructureException, ParseException
    {
        List<Election> result = new ArrayList<>();

        for(String electionId : electionsIds)
        {
            try(BufferedReader br = new BufferedReader(new FileReader("Data/Elections/" + electionId + ".txt")))
            {
                String line = br.readLine();
                boolean endedElection = line.charAt(0) == '1';

                String[] titleWords = electionId.split("_");

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(titleWords[0]);

                ElectionType type = ElectionType.valueOf(titleWords[1]);

                Election election = new Election(date, new ArrayList<>(), type, endedElection);

                for( ; line != null; line = br.readLine())
                {
                    String[] words = line.split(":");
                    if(words.length != 4)
                        throw new NotCorrectFileStructureException();

                    Candidate candidate = new Candidate(new Person(words[0], words[1], new Party(words[2])), election, Integer.parseInt(words[3]));

                    election.getCandidates().add(candidate);
                }

                result.add(election);
            }
        }

        return result;
    }
}
