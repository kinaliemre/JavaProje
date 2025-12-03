package Main;

import java.time.LocalDateTime;


public class TimeAsk extends Task {
  
	private LocalDateTime startTime;
    private int durationMinutes;

    public TimeAsk(int id, String title, String description, LocalDateTime startTime, int durationMinutes) {
    
    	super(id, title, description);
    
    	this.startTime = startTime;
    
    	this.durationMinutes = durationMinutes;
    
    
    }
}