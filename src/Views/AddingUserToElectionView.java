package Views;

import javafx.util.Pair;

import java.util.Scanner;

public class AddingUserToElectionView
{
    public Pair<String, String> act()
    {
        showInstructionToPassUser();

        Scanner scanner = new Scanner(System.in);

        String user = scanner.nextLine();

        showInstructionToPassElection();

        String electionId = scanner.nextLine();

        return new Pair<>(user, electionId);
    }

    private void showInstructionToPassUser()
    {
        System.out.println("Podaj uzytkownika, ktoremu chcesz udostepnic wybory: ");
    }

    private void showInstructionToPassElection()
    {
        System.out.println("Podaj ID wyborow: ");
    }
}
