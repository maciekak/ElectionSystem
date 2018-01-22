package Views;

import Models.Election.Election;

import java.util.List;
import java.util.Scanner;

public class ElectionSelectingView extends SelectionInstructionView
{
    private List<Election> availableElections;

    public ElectionSelectingView(List<Election> availableElections)
    {
        this.availableElections = availableElections;
    }

    public Election act()
    {
        showInstruction();
        showElections();

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        if(line.length() != 1)
            return null;

        int index = -1;
        if(line.charAt(0) < 'a')
        {
            index = 'z' - 'a' + 1;
            index += line.charAt(0) - 'A';
        }
        else
        {
            index = line.charAt(0) - 'a';
        }

        return availableElections.get(index);
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
}
