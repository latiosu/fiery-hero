package com.base.main.framework;

import com.base.main.window.Game;

public class Timer {
    
    long startTime;
    int duration;

    public Timer(){
        startTime = 0;
        duration = 0;
    }
    
    // Call to initialize timer to current time
    public void setStartTime() {
        startTime = Game.runningTime;
    }
    
    // Call to set countdown value
    public void setDuration(int value){
        duration = value;
    }
    
    // Call to see whether duration has passed or not
    public boolean checkTime() {
        if(duration == 0)
            return false;
        else if(Game.runningTime - startTime >= duration)
            return true;
        else 
            return false;
    }
}
