package Views;

public class GoodbyeView extends View
{
    public void act()
    {
        showGoodbyeMessage();
    }

    private void showGoodbyeMessage()
    {
        System.out.println("Zdecydowales ze chcesz wyjsc. Dowidzenia");
    }
}
