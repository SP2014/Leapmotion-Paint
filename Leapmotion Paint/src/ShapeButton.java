import javax.swing.*;
import java.awt.*;

/**
 * Created by aashi on 1/19/2017.
 */
public class ShapeButton extends JButton {
    public ShapeButton(String name,ImageIcon imageIcon){
        super(imageIcon);
        setToolTipText(name);
        setPreferredSize(new Dimension(40,40));
    }
}
