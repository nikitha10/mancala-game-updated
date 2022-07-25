import javax.swing.JButton;
/**
 * Write a description of class MancalaButton here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MancalaButton extends JButton
{
    // instance variables - replace the example below with your own
    private int index;
    public MancalaButton(String s, int index)
    {
        super(s);
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

}
