package Views;

import javafx.util.Pair;

import java.util.Scanner;

public class LoginView extends View
{
    public Pair<String, String> act()
    {
        clearConsole();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj login: ");
        String login = scanner.nextLine();

        System.out.print("Podaj hasło: ");
        String password = scanner.nextLine();

        return new Pair<>(login, password);
    }
}
