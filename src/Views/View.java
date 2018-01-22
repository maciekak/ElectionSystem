package Views;

import java.io.IOException;

public abstract class View
{
    protected void clearConsole()
    {
        System.out.print("\033[H\033[2J");
    }
}
