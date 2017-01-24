import java.awt.*;

/**
 * Created by aashi on 1/17/2017.
 */

public class Line{
    public int startX, startY, endX, endY;
    public Color color;

    Line(int startX, int startY, int endX, int endY, Color color){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
    }
}
