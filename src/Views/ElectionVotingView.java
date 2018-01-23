package Views;

import Models.Election.Candidate;
import Models.Election.Election;

import java.util.List;
import java.util.Scanner;

public class ElectionVotingView extends SelectionInstructionView
{
    private List<Candidate> candidates;

    public ElectionVotingView(List<Candidate> candidates)
    {
        this.candidates = candidates;
    }

    public Candidate act()
    {
        showInstruction();
        showCandidates();

        int index = getChoice();
        if(index == -1)
            return null;

        if(!askConfirmation())
            return null;

        return candidates.get(index);
    }

    private void showCandidates()
    {
        char choiceSign = 'a';
        for(Candidate candidate : candidates)
        {
            if(choiceSign == 'q')
                choiceSign++;

            System.out.println(choiceSign + " - " + candidate.getPerson().getFirstName() + " " + candidate.getPerson().getLastName() + " - " + candidate.getPerson().getParty());

            if(choiceSign == 'z')
            {
                choiceSign = 'A';
                continue;
            }

            choiceSign++;
        }
    }

    private boolean askConfirmation()
    {
        System.out.println("Jesteś pewny, że chcesz wybrać tego kandydata. Tego wyboru nie da się cofnąć");
        System.out.println("y - Tak, n - Nie");

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        return line.length() == 1 && (line.charAt(0) == 'y' || line.charAt(0) == 'Y');
    }

    private int getChoice()
    {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        if(line.length() != 1 || line.charAt(0) == 'q')
            return -1;

        int index = -1;
        if(line.charAt(0) < 'a')
        {
            index = 'z' - 'a'; //No +1 cause of q
            index += line.charAt(0) - 'A';
        }
        else
        {
            if(line.charAt(0) < 'q')
                index = line.charAt(0) - 'a';
            else
                index = line.charAt(0) - 'a' - 1;
        }
        return index;
    }
}
