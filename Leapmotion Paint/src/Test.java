import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by aashi on 1/17/2017.
 */


public class Test extends JFrame {

    private static Test paint;
    public int prevX = -1, prevY = -1;
    public int x = -1, y = -1;
    public double z = -1;
    public int selectedTool = 0;

    public Color inkColor = Color.MAGENTA;


    public ArrayList<Line> lines = new ArrayList<Line>();
    public LeapButton button1, button2, button3, button4;

    public JPanel buttonPanel;
    public JPanel paintPanel;

    //crayons
    ColorButton cblue = new ColorButton(Color.BLUE,"Blue");
    ColorButton cred = new ColorButton(Color.RED,"Red");
    ColorButton cyellow = new ColorButton(Color.YELLOW,"Yellow");
    ColorButton cgreen = new ColorButton(Color.GREEN,"Green");
    ColorButton cmagenta = new ColorButton(Color.MAGENTA,"Magenta");
    ColorButton ccyan = new ColorButton(Color.CYAN,"Cyan");
    ColorButton cblack = new ColorButton(Color.BLACK,"Black");
    ColorButton cpink = new ColorButton(Color.PINK,"Pink");


    ImageIcon brush = new ImageIcon("assets/brush.png");
    ToolButton pbrush = new ToolButton("Brush",brush);
    ImageIcon eraser = new ImageIcon("assets/eraser.png");
    ToolButton peraser = new ToolButton("Eraser",eraser);
    ImageIcon pencil = new ImageIcon("assets/edit.png");
    ToolButton ppencil = new ToolButton("Pencil",pencil);
    ImageIcon text = new ImageIcon("assets/text.png");
    ToolButton ttext = new ToolButton("Text",text);

    ImageIcon circle = new ImageIcon("assets/circle-outline.png");
    ShapeButton scircle = new ShapeButton("Circle",circle);
    ImageIcon square = new ImageIcon("assets/plain-square.png");
    ShapeButton ssquare = new ShapeButton("Square",square);
    ImageIcon ellipse = new ImageIcon("assets/ellipse-outline.png");
    ShapeButton sellipse = new ShapeButton("Ellipse",ellipse);
    ImageIcon star = new ImageIcon("assets/star-outline.png");
    ShapeButton sstar = new ShapeButton("Star",star);

    private JPanel centerPanel;
    private JPanel westPanel;
    private JPanel southPanel;


    private boolean texton;
    private JTextField text2;
    private String font;
    private int topX, topY;
    Label t;




    Test(){
        super("Leap Paint: Interactive Drawing");

        paintPanel = new JPanel(){

            public void paintComponent(Graphics g){
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                if(selectedTool == 0){
                    g2.setStroke(new BasicStroke(8));
                }else if(selectedTool == 1){
                    g2.setStroke(new BasicStroke(2));
                }


                if(z <= 0.5 && (selectedTool == 0 || selectedTool == 1))
                    lines.add(new Line(prevX, prevY, x, y, inkColor));

                /*if(selectedTool == 2)
                    g2.setColor( Color.white );
                    g2.fillRect( 0, 0, getSize().width, getSize().height );*/



                for(Line line : lines){
                    g2.setColor(line.color);
                    g2.drawLine(line.startX, line.startY, line.endX, line.endY);
                    //buttonPanel.repaint();

                    if(z <= 0.95 && z != -1.0){
                        g2.setColor((z <= 0.5) ? inkColor : new Color(0, 255, 153));
                        int cursorSize = (int) Math.max(5, 10 - z * 10);
                        //g2.fillOval(x, y, cursorSize, cursorSize);

                        if(selectedTool == 0){
                            Image img = Toolkit.getDefaultToolkit().getImage("assets/brush_24.png");
                            g2.drawImage(img,x+2,y-16,this);
                        }
                        else if(selectedTool == 1){
                            Image img = Toolkit.getDefaultToolkit().getImage("assets/edit_24.png");
                            g2.drawImage(img,x+2,y-16,this);
                        }
                        else if(selectedTool == 2){
                            Image img = Toolkit.getDefaultToolkit().getImage("assets/eraser_24.png");
                            g2.drawImage(img,x+2,y-16,this);
                        }


                    }
                }
            }
        };

        paintPanel.setOpaque(false);
        //file, help, top
        JMenuBar menuBar = new JMenuBar();
        getContentPane().add(menuBar, BorderLayout.NORTH);
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        westPanel = new JPanel(new GridLayout(6,1));
        westPanel.setBorder(new EmptyBorder(80,0,0,0));
        pbrush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTool = 0;
            }
        });
        westPanel.add(pbrush);
        ppencil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTool = 1;
            }
        });
        westPanel.add(ppencil);
        peraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTool = 2;
            }
        });
        westPanel.add(peraser);
        ttext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTool = 3;
                Graphics g = getGraphics();
                g.setColor( Color.white );
                g.fillRect( 0, 0, getSize().width, getSize().height );

            }
        });
        westPanel.add(ttext);

        southPanel = new JPanel(new GridLayout(1,12));
        cblue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkColor = Color.BLUE;
            }
        });
        southPanel.add(cblue);
        cred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkColor = Color.RED;
            }
        });
        southPanel.add(cred);
        cyellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkColor = Color.YELLOW;
            }
        });
        southPanel.add(cyellow);
        cgreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkColor = Color.GREEN;
            }
        });
        southPanel.add(cgreen);
        cmagenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkColor = Color.MAGENTA;
            }
        });
        southPanel.add(cmagenta);
        ccyan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkColor = Color.CYAN;
            }
        });
        southPanel.add(ccyan);
        cblack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkColor = Color.BLACK;
            }
        });
        southPanel.add(cblack);
        cpink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inkColor = Color.PINK;
            }
        });

        southPanel.add(cpink);
        southPanel.add(scircle);
        southPanel.add(ssquare);
        southPanel.add(sellipse);
        southPanel.add(sstar);


        getContentPane().add(westPanel, BorderLayout.WEST);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        getContentPane().add(paintPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

    }



    public void saveImage(String imageName){
        Point pos = getContentPane().getLocationOnScreen();
        Rectangle screenRect = getContentPane().getBounds();
        screenRect.x = pos.x;
        screenRect.y = pos.y;

        try{
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            ImageIO.write(capture, "bmp", new File(imageName + ".bmp"));
        }catch(Exception e){}
    }



    public static void main(String[] args){
        paint = new Test();
        Controller controller = new Controller();
        SampleListener listener = new SampleListener(paint);

        controller.addListener(listener);
        System.out.println("Enter any key to exit");
        try{
            System.in.read();
        }catch(Exception e){
            e.printStackTrace();
        }

        controller.removeListener(listener);
    }

}
