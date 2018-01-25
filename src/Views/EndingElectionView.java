package Views;

import Models.Election.Election;

import java.util.List;
import java.util.Scanner;

public class EndingElectionView extends SelectionInstructionView
{
    private List<Election> availableElections;

    public EndingElectionView(List<Election> availableElections)
    {
        this.availableElections = availableElections;
    }

    public Election act()
    {
        showInstruction();
        showElections();

        int index = getChoice();
        if(index == -1)
            return null;

        if(!askConfirmation())
            return null;

        return availableElections.get(index);
    }

    private boolean askConfirmation()
    {
        System.out.println("Jesteś pewny, że chcesz zakończyć te wybory. Tego wyboru nie da się cofnąć.");
        System.out.println("y - Tak, n - Nie");

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        return line.length() == 1 && (line.charAt(0) == 'y' || line.charAt(0) == 'Y');
    }

    private void showElections()
    {
        char choiceSign = 'a';
        for(Election election : availableElections)
        {
            if(choiceSign == 'q')
                choiceSign++;

            System.out.println(choiceSign + " - " + election.getName());

            if(choiceSign == 'z')
            {
                choiceSign = 'A';
                continue;
            }
            choiceSign++;
        }
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
