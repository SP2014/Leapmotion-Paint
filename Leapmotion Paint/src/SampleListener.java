import com.leapmotion.leap.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.InputEvent;

/**
 * Created by aashi on 1/17/2017.
 */
class SampleListener extends Listener {
    private JFrame jframe = new JFrame();
    double windowWidth,windowHeight;
    private JLabel pos_label;
    private Robot robot;
    private InteractionBox normalizedBox;
    public Test paint;
    public com.leapmotion.leap.Frame frame;


    public SampleListener(Test newPaint){
        this.pos_label = new JLabel();
        this.windowWidth = 300;
        this.windowHeight = 300;
        this.paint = newPaint;
        //setUpGui();
    }


    public void setUpGui(){
        jframe = new JFrame("JFrame Example");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton button = new JButton();
        button.setText("Press me");
        pos_label.setText("Cursor Position: ");
        pos_label.setLocation(10,80);
        panel.add(button);
        panel.add(pos_label);
        jframe.add(panel);
        jframe.setSize(300, 300);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }

    public void onConnect(Controller controller){
        System.out.println("Application connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        //controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
    }

    public void onFrame(Controller controller){

        com.leapmotion.leap.Frame frame = controller.frame();

        try{
            robot = new Robot();
        }catch(Exception e){}
        /*try{
            robot = new Robot();
        }catch(Exception e){}
        InteractionBox interactionBox = frame.interactionBox();
        for(Finger finger: frame.fingers()){
            if(finger.type() == Finger.Type.TYPE_INDEX){
                Vector fingerPos = finger.stabilizedTipPosition();
                Vector boxFingerPos = interactionBox.normalizePoint(fingerPos);
                Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                robot.mouseMove((int)(screen.width*boxFingerPos.getX()),(int)(screen.height - boxFingerPos.getY() * screen.height));
                pos_label.setText(""+(int)(screen.width*boxFingerPos.getX())+", "+(int)(screen.height - boxFingerPos.getY() * screen.height));
            }
        }*/
        //System.out.println(""+frame.fingers().count());
        if(!(frame.fingers().isEmpty())){
            Finger frontMost = frame.fingers().frontmost();
            Vector position = new Vector(-1, -1, -1);
            normalizedBox = frame.interactionBox();

            position.setX(normalizedBox.normalizePoint(frontMost.stabilizedTipPosition()).getX());
            position.setY(normalizedBox.normalizePoint(frontMost.stabilizedTipPosition()).getY());
            position.setZ(normalizedBox.normalizePoint(frontMost.stabilizedTipPosition()).getZ());

            position.setX(position.getX() * paint.getBounds().width);
            position.setY(position.getY() * paint.getBounds().height);

            position.setY(position.getY() * -1);
            position.setY(position.getY() + paint.getBounds().height);

            paint.prevX = paint.x;
            paint.prevY = paint.y;

            paint.x = (int) position.getX();
            paint.y = (int) position.getY();
            paint.z = (int) position.getZ();

            if(frame.fingers().extended().count() == 1){
                paint.paintPanel.repaint();
            }

            else if(frame.fingers().extended().count() == 2){

                //System.out.println("X: " + position.getX() + ", Y: " + position.getY());
                InteractionBox interactionBox = frame.interactionBox();
                Vector fingerPos = frontMost.stabilizedTipPosition();
                Vector boxFingerPos = interactionBox.normalizePoint(fingerPos);
                //Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                robot.mouseMove((int)(600*boxFingerPos.getX()),(int)(600 - boxFingerPos.getY() * 600));
            }
            else if(frame.fingers().extended().count() == 3){
                System.out.println("Three Fingers detected...");
                System.out.println("X: " + position.getX()+", Y: " + position.getY());
                System.out.println("Bounds: " + paint.cblack.getBounds().x + "," + paint.cblack.getBounds().y);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

                if(paint.cblack.getBounds().contains((int)position.getX(), (int)position.getY()))
                    paint.cblack.doClick();
            }

        }

    }

    public void onDisconnect(Controller controller){
     System.out.println("Device Disconnected");
    }
}

