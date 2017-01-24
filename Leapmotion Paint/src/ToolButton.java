import javax.swing.*;

/**
 * Created by aashi on 1/19/2017.
 */
public class ToolButton extends JButton {
    public ToolButton(String name,ImageIcon imageIcon){
        super(imageIcon);
        setToolTipText(name);
    }
}
