package Views;

import Models.Election.Election;
import Models.Statistics.Statistics;

import java.util.Scanner;

public class StatisticsView extends SelectionInstructionView
{
    private Statistics statistics;

    public StatisticsView(Statistics statistics)
    {
        this.statistics = statistics;
    }

    public void act()
    {
        while(true)
        {
            showInstruction();
            showPossibilites();

            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            if(line.length() != 1)
                return;

            switch(line.charAt(0))
            {
                case 'a':
                    break;

                case 'b':
                    break;

                case 'c':
                    break;

                default:
                    return;
            }
        }

    }

    private void showPossibilites()
    {
        System.out.println("a - Procentowe dla partii.");
        System.out.println("b - Procentowo mandaty dla partii.");
        System.out.println("c - Lista mandatów dla partii.");
    }


}
