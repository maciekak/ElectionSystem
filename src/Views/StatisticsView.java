package Views;

import Models.Election.Candidate;
import Models.Statistics.Statistics;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
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
                    HashMap<String, Double> partiesPercent = statistics.getPartiesPercent();
                    for(String party : partiesPercent.keySet())
                    {
                        DecimalFormat df2 = new DecimalFormat(".##");
                        System.out.println(party + " -> " + df2.format(partiesPercent.get(party)));
                    }
                    break;

                case 'b':
                    HashMap<String, Double> partiesMandats = statistics.getMandatsPercent();
                    for(String party : partiesMandats.keySet())
                    {
                        DecimalFormat df2 = new DecimalFormat(".##");
                        System.out.println(party + " -> " + df2.format(partiesMandats.get(party)));
                    }
                    break;

                case 'c':
                    HashMap<String, List<Candidate>> mandats = statistics.getMandats();
                    for(String party : mandats.keySet())
                    {
                        System.out.println(party + ":");

                        for(int i = 0; i < mandats.get(party).size(); i++)
                        {
                            Candidate candidate = mandats.get(party).get(i);
                            System.out.println("\t" + (i+1) + ") " + candidate.getPerson().getFirstName() + " " + candidate.getPerson().getLastName() + " -> " + candidate.getVotesCount());
                        }
                    }
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
