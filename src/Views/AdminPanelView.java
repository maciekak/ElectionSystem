package Views;

import java.util.Scanner;

public class AdminPanelView extends SelectionInstructionView
{
    public AdminDecision act()
    {
        showInstruction();
        showPossibilities();

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        if(line.length() != 1)
            return AdminDecision.GO_END;

        switch(line.charAt(0))
        {
            case 'a':
                return AdminDecision.END_ELECTION;

            case 'b':
                return AdminDecision.SHOW_STATISTICS;

            default:
                return AdminDecision.GO_END;
        }
    }

    private void showPossibilities()
    {
        System.out.println("a - Zakończ wybory.");
        System.out.println("b - Zobacz statystyki w wyborach");
    }
}
