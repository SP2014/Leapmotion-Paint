import javax.swing.*;
import java.awt.*;

/**
 * Created by aashi on 1/19/2017.
 */
public class ColorButton extends JButton {
    public ColorButton(Color color,String name){
        setBackground(color);
        setPreferredSize(new Dimension(40,40));
        setToolTipText(name);
    }
}
