package Views;

public abstract class SelectionInstructionView extends View
{
    protected void showInstruction()
    {
        System.out.println("Wybierz jedną z poniższych opcji i zatwierdź enterem.");
        System.out.println("q - Żeby wrócić do panelu.");
    }
}
