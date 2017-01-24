import com.leapmotion.leap.*;

/**
 * Created by aashi on 1/17/2017.
 */
public class CustomListener extends Listener {

    //public ImageSlider slider;

    public CustomListener(){
        //this.slider = imageSlider;
    }

    public void onConnect(Controller controller){
      controller.enableGesture(Gesture.Type.TYPE_SWIPE);
    }

    public void onFrame(Controller controller){
        Frame frame = controller.frame();

        Finger finger = frame.fingers().frontmost();
        Vector position = finger.stabilizedTipPosition();
        InteractionBox interactionBox = frame.interactionBox();
        Vector normalizedPosition = interactionBox.normalizePoint(position);

        int no_of_fingers = frame.fingers().extended().count();
        System.out.println("No of fingers: "+no_of_fingers);

        for(Finger fing: frame.fingers().extended()){
            System.out.println("Finger Type: "+ fing.type().toString());
        }

        //System.out.println("x: "+normalizedPosition.getX() + ",y: "+normalizedPosition.getY());

        /*for(Finger fingers: frame.fingers()){

            switch(fingers.type()){
                case TYPE_INDEX: System.out.println("Index Finger detected..");
                                 break;
                case TYPE_THUMB: System.out.println("Thumb detected..");
                                 break;
                case TYPE_RING: System.out.println("Ring Finger detected..");
                                break;
            }
            try{
                Thread.sleep(100);
            }catch(Exception e){
                e.printStackTrace();
            }

        }*/

        /*for(Gesture gesture: frame.gestures()){
            if(gesture.type() == Gesture.Type.TYPE_SWIPE){
                System.out.println("Swipe Gesture detected...");
                SwipeGesture swipeGesture = new SwipeGesture(gesture);
                System.out.println("Swipe direction: "+ swipeGesture.direction());
                //if(swipeGesture.direction() == Vector.right()){
                    slider.changeImage();
                try{
                    Thread.sleep(100);
                }catch (Exception e){}
                //}
            }
        }*/
    }



}
