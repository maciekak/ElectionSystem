package DataAccessLayer;

import Models.Election.*;

import java.io.*;
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

                for(line = br.readLine(); line != null && !line.equals(""); line = br.readLine())
                {
                    String[] words = line.split(":");
                    if(words.length != 4)
                        throw new NotCorrectFileStructureException();

                    Candidate candidate = new Candidate(new Person(words[0], words[1], words[2]), election, Integer.parseInt(words[3]));

                    election.getCandidates().add(candidate);
                }

                result.add(election);
            }
        }

        return result;
    }

    public void updateElections(String electionId, Candidate candidate) throws IOException
    {
        FileWriter writer= null;

        try(BufferedReader br = new BufferedReader(new FileReader("Data/Elections/" + electionId + ".txt")))
        {
            StringBuilder newContent = new StringBuilder();
            String line = br.readLine();
            newContent.append(line).append("\n");
            for(line = br.readLine(); line != null; line = br.readLine())
            {
                String[] words = line.split(":");

                if(words[0].equals(candidate.getPerson().getFirstName()) && words[1].equals(candidate.getPerson().getLastName()))
                {
                    newContent.append(words[0]).append(":")
                            .append(words[1]).append(":")
                            .append(words[2]).append(":")
                            .append(candidate.getVotesCount()).append("\n");
                    continue;
                }

                newContent.append(line).append("\n");
            }

            writer = new FileWriter("Data/Elections/" + electionId + ".txt");
            writer.write(newContent.toString());
        }
        finally
        {
            if(writer != null)
                writer.close();
        }
    }

    public void updateElectionStatus(Election election) throws IOException
    {
        FileWriter writer= null;
        String electionId = election.getId();
        try(BufferedReader br = new BufferedReader(new FileReader("Data/Elections/" + electionId + ".txt")))
        {
            StringBuilder newContent = new StringBuilder();
            String line = br.readLine();
            if(election.isEnded())
                newContent.append("1");
            else
                newContent.append("0");
            newContent.append("\n");

            for(line = br.readLine(); line != null; line = br.readLine())
                newContent.append(line).append("\n");

            writer = new FileWriter("Data/Elections/" + electionId + ".txt");
            writer.write(newContent.toString());
        }
        finally
        {
            if(writer != null)
                writer.close();
        }
    }

    public boolean checkIfElectionExists(String electionId) throws IOException
    {
        File file = new File("Data/Elections/" + electionId + ".txt");
        if(!file.exists() || file.isDirectory())
            return false;

        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line = br.readLine();

            if(!line.equals("0") && !line.equals("1"))
                return false;

            for(line = br.readLine(); line != null && !line.equals(""); line = br.readLine())
            {
                if(line.split(":").length != 4)
                    return false;
            }
        }

        return true;
    }
}
