package DataAccessLayer;

import Models.User.Admin;
import Models.User.User;
import Models.User.UserAbstraction;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersManager
{
    public UserAbstraction tryLogIn(String login, String password) throws IOException, NotCorrectFileStructureException
    {
        try(BufferedReader br = new BufferedReader(new FileReader("Data/Users.txt")))
        {
            for(String line = br.readLine(); line != null; line = br.readLine())
            {
                String[] words = line.split(":");

                if(words.length != 3)
                    throw new NotCorrectFileStructureException();

                if(words[0].equals(login) && words[1].equals(password))
                {
                    if(words[2].equals("1"))
                        return new Admin(login, password);
                    else
                        return new User(login, password);
                }
            }
        }
        return null;
    }

    public Pair<List<Pair<String,String>>, List<String>> getUserElections(User user) throws IOException, NotCorrectFileStructureException
    {
        try(BufferedReader br = new BufferedReader(new FileReader("Data/UserElections.txt")))
        {
            for(String line = br.readLine(); line != null; )
            {
                int indexOfUserName = line.indexOf(':');
                if(indexOfUserName == -1)
                    throw new NotCorrectFileStructureException();

                if(line.substring(0, indexOfUserName).equals(user.getLogin()))
                {
                    String[] elections = line.split(":");
                    Pair<List<Pair<String,String>>, List<String>> electionIdPersonId = new Pair<>(new ArrayList<Pair<String, String>>(), new ArrayList<String>());

                    for(int i = 1; i < elections.length; i++)
                    {
                        String[] electionIdAndChoice = elections[i].split(",");

                        if(!electionIdAndChoice[0].matches("\\d\\d\\d\\d-\\d\\d-\\d\\d_[a-zA-Z]+\\z"))
                            throw new NotCorrectFileStructureException();

                        if(electionIdAndChoice.length == 1)
                            electionIdPersonId.getValue().add(electionIdAndChoice[0]);
                        else if(electionIdAndChoice.length == 2)
                        {
                            if(!electionIdAndChoice[1].matches("[a-zA-Z]+_[a-zA-Z]+\\z"))
                                throw new NotCorrectFileStructureException();

                            electionIdPersonId.getKey().add(new Pair<>(electionIdAndChoice[0], electionIdAndChoice[1]));
                        }
                        else
                            throw new NotCorrectFileStructureException();
                    }

                    return electionIdPersonId;
                }
                line = br.readLine();
                        int a = 3;
            }
        }
        return null;
    }

    public List<String> getAdminElections(String login) throws IOException, NotCorrectFileStructureException
    {
        try(BufferedReader br = new BufferedReader(new FileReader("Data/UserElections.txt")))
        {
            for(String line = br.readLine(); line != null; line = br.readLine())
            {
                int indexOfUserName = line.indexOf(':');
                if(indexOfUserName == -1)
                    throw new NotCorrectFileStructureException();

                if(line.substring(0, indexOfUserName).equals(login))
                {
                    String[] elections = line.split(":");
                    return new ArrayList<String>(Arrays.asList(elections)).subList(1, elections.length);
                }
            }
        }
        return null;
    }


    public void updateUsersElection(String username, String electionId, String candidateId) throws IOException, NotCorrectFileStructureException
    {
        FileWriter writer= null;
        try(BufferedReader br = new BufferedReader(new FileReader("Data/UserElections.txt")))
        {
            StringBuilder newContent = new StringBuilder();
            for(String line = br.readLine(); line != null; line = br.readLine())
            {
                int indexOfUserName = line.indexOf(':');
                if(indexOfUserName == -1)
                    throw new NotCorrectFileStructureException();

                if(!line.substring(0, indexOfUserName).equals(username))
                {
                    newContent.append(line).append("\n");
                    continue;
                }

                String[] elections = line.split(":");

                newContent.append(line.substring(0, indexOfUserName)).append(":");

                for(int i = 1; i < elections.length; i++)
                {
                    String[] words = elections[i].split(",");
                    if(words[0].equals(electionId))
                    {
                        newContent.append(electionId).append(",").append(candidateId);

                        if(elections.length -1 != i)
                            newContent.append(":");

                        continue;
                    }

                    newContent.append(words[0]);
                    if(words.length > 1)
                    {
                        newContent.append(",").append(words[1]);
                    }
                    if(elections.length -1 != i)
                        newContent.append(":");

                }
                newContent.append("\n");

            }

            writer = new FileWriter("Data/UserElections.txt");
            writer.write(newContent.toString());
        }
        finally
        {
            if(writer != null)
                writer.close();
        }
    }

    public boolean checkIfUserExists(String user) throws NotCorrectFileStructureException, IOException
    {
        try(BufferedReader br = new BufferedReader(new FileReader("Data/Users.txt")))
        {
            for(String line = br.readLine(); line != null; line = br.readLine())
            {
                int indexOfUserName = line.indexOf(':');
                if(indexOfUserName == -1)
                    throw new NotCorrectFileStructureException();

                if(line.substring(0, indexOfUserName).equals(user))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tryAddUserToElection(String user, String electionId) throws IOException, NotCorrectFileStructureException
    {
        FileWriter writer= null;
        try(BufferedReader br = new BufferedReader(new FileReader("Data/UserElections.txt")))
        {
            boolean foundUser = false;
            StringBuilder newContent = new StringBuilder();
            for(String line = br.readLine(); line != null && !line.equals(""); line = br.readLine())
            {
                int indexOfUserName = line.indexOf(':');
                if(indexOfUserName == -1)
                    throw new NotCorrectFileStructureException();

                if(!line.substring(0, indexOfUserName).equals(user))
                {
                    newContent.append(line).append("\n");
                    continue;
                }

                foundUser = true;

                String[] elections = line.split(":");

                newContent.append(line.substring(0, indexOfUserName)).append(":");

                for(int i = 1; i < elections.length; i++)
                {
                    String[] words = elections[i].split(",");

                    if(words[0].equals(electionId))
                        return false;

                    newContent.append(words[0]);
                    if(words.length > 1)
                    {
                        newContent.append(",").append(words[1]);
                    }

                    newContent.append(":");
                }
                newContent.append(electionId);
                newContent.append("\n");
            }
            if(!foundUser)
            {
                newContent.append(user).append(":").append(electionId).append("\n");
            }

            writer = new FileWriter("Data/UserElections.txt");
            writer.write(newContent.toString());
        }
        finally
        {
            if(writer != null)
                writer.close();
        }
        return true;
    }
}
