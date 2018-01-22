package Views;

import java.io.IOException;
import java.util.Scanner;

public class UserPanelView extends SelectionInstructionView
{
    public UserDecision act()
    {
        showInstruction();
        showPossibilities();

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        if(line.length() != 1)
            return UserDecision.GO_END;

        switch(line.charAt(0))
        {
            case 'a':
                return UserDecision.GO_ELECT;

            case 'b':
                return UserDecision.SHOW_STATISTICS;

            default:
                return UserDecision.GO_END;
        }
    }

    private void showPossibilities()
    {
        System.out.println("a - Zagłosuj w wyborach.");
        System.out.println("b - Zobacz statystyki w zakończonych wyborach");
    }
}
