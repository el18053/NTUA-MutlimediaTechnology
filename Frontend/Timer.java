package Frontend;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;

/**
* <h1> Timer </h1> 
* The Timer class implements a timer that counts down t seconds.
* When Timer hits 0 it simply presses the Solution Button from the Menu.
* @author  Symeon Porgiotis
*
* @param tm is a java.util.Timer that can count seconds.
* @param StartTime how many seconds does the Clock have when it is initialized.
* @param timeLeft how many seconds are left in the Timer.
* @param Solution when Timer hits 0 we need to notify the Main class to show the Games Solution.
* @param clock_label is a javafx.Label which displays the seconds left in the Timer.
*/

public class Timer {
    //create object for timer
    java.util.Timer tm;
    int StartTime, timeLeft;
    MenuItem Solution;
    Label clock_label;

    /**
   * This method is the Constructor method which initializes the Timer
   * with t seconds. 
   * @param t How many seconds will the Timer have.
   * @param Solution Which Button to press when the Timer hits 0.
   */
    public Timer(int t, MenuItem Solution) {
        tm = new java.util.Timer();
        StartTime = t;
        timeLeft = t;
        this.Solution = Solution;
    }

    /**
   * This method return the seconds remaining in the Timer.
   * We use this method to find out how long was the Games duration in the Main class. 
   * @return int This returns how many seconds are left in the Timer (timeLeft).
   */
    public int getTime() {
        return timeLeft;
    }

    /**
   * This method is used to set Timers Label.
   * We need the Timer to have a Label wich displays the seconds remaining in it.
   */
    public void set(Label label) {
        this.clock_label = label;
    }

    /**
   * This is a void method which is used to Start the Timer.
   * First we need to know how many seconds will the Timer have (StartTime).
   * Then we use java.util.Timer to count (period) every 1000ms or 1 second.
   * if the timeLeft equals to 1 then we need to notify that the player is going to lose.
   * Otherwise we need to Update Timers Label with the correct Time value and for that purpose
   * we use Platform.runLater which is a javafx.Thread that's responsible for changing Timers Label 
   * in the Interface. 
   */
    public void StartTimer() {
        timeLeft = StartTime;
        //tIMER that repeats each 20second
        //schedule the repeating timer
        tm.scheduleAtFixedRate(new java.util.TimerTask() {
            //override run methid
            @Override
            public void run(){
                if(timeLeft == 1) {
                    StopTimer();
                    Platform.runLater(() -> { Solution.fire(); });
                }
                timeLeft -= 1;
                Platform.runLater(() -> {
                    clock_label.setText(String.valueOf(timeLeft));
                    clock_label.setStyle("-fx-font-size: 3.5em;");
                });
            }
        }, 0, 1000);
    }
    
    /**
   * This is a void method which is used to stop the Timer.
   */
    public void StopTimer() {
        tm.cancel();
    }
    
    /**
   * This is a void method which is used to restart the Timer.
   */
    public void update() {
        tm.cancel();
        tm = new java.util.Timer();
    }
}
