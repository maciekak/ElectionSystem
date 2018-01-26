package Views;

public class AddingUserToElectionResultView
{

    public void act(boolean isCorrectUser, boolean isCorrectElection, boolean correctUserAddedToElection)
    {
        if(isCorrectUser)
            System.out.println("Podano nieprawidlowa nazwe uzytkownika.");

        if(isCorrectElection)
            System.out.println("Podano nieprawidlowa nazwe wyborow.");

        if(!correctUserAddedToElection)
            System.out.println("Nie udalo sie dodac uzytkownika do wyborow.");
        else
            System.out.println("Udalo dodac sie uzytkownika jako glosujacego w wyborach.");
    }
}
