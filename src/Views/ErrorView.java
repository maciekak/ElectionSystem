package Views;

import java.io.IOException;

public class ErrorView extends View
{
    private String errorMessage;
    public ErrorView(Exception e)
    {
        errorMessage = e.getMessage();
    }

    public ErrorView(String message)
    {
        errorMessage = message;
    }

    public void act()
    {
        clearConsole();
        System.out.println("Wystąpił następujący błąd:");
        System.out.println(errorMessage);
    }
}
