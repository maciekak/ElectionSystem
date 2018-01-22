package DataAccessLayer;

import Models.User.Admin;
import Models.User.User;
import Models.User.UserAbstraction;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersManager
{
    public UserAbstraction tryLogIn(String login, String password) throws IOException, NotCorrectFileStructure
    {
        try(BufferedReader br = new BufferedReader(new FileReader("Data/Users.txt")))
        {
            for(String line = br.readLine(); line != null; line = br.readLine())
            {
                String[] words = line.split(":");

                if(words.length != 3)
                    throw new NotCorrectFileStructure();

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

    public List<Pair<String, String>> getUserElections(User user) throws IOException, NotCorrectFileStructure
    {
        try(BufferedReader br = new BufferedReader(new FileReader("Data/UserElections.txt")))
        {
            for(String line = br.readLine(); line != null; line = br.readLine())
            {
                int indexOfUserName = line.indexOf(':');
                if(indexOfUserName == -1)
                    throw new NotCorrectFileStructure();

                if(line.substring(0, indexOfUserName).equals(user.getLogin()))
                {
                    String[] elections = line.split(":");
                    List<Pair<String, String>> electionIdPersonId = new ArrayList<>(elections.length - 1);
                    for(int i = 1; i < elections.length; i++)
                    {
                        String[] electionIdAndChoice = elections[i].split(",");

                        if(!electionIdAndChoice[0].matches("\\d\\d\\d\\d-\\d\\d-\\d\\d_[a-zA-Z]+\\z"))
                            throw new NotCorrectFileStructure();

                        if(electionIdAndChoice.length == 1)
                            electionIdPersonId.add(new Pair<>(electionIdAndChoice[0], null));
                        else if(electionIdAndChoice.length == 2)
                        {
                            if(!electionIdAndChoice[1].matches("[a-zA-Z]+_[a-zA-Z]+\\z"))
                                throw new NotCorrectFileStructure();

                            electionIdPersonId.add(new Pair<>(electionIdAndChoice[0], electionIdAndChoice[1]));
                        }
                        else
                            throw new NotCorrectFileStructure();
                    }

                    return electionIdPersonId;
                }
            }
        }
        return null;
    }

    public class NotCorrectFileStructure extends Exception
    {
    }
}
