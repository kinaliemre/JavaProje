package Main;

import java.time.LocalDateTime;
import java.time.Duration;


public class Deadline {

	private LocalDateTime dueDate;
	
	public Deadline(LocalDateTime dueDate) {
		this.dueDate = dueDate;
		
	}
	public LocalDateTime  getDueDate() {
		return dueDate;
	}
	
	public boolean isPast(LocalDateTime now) {
        return now.isAfter(dueDate);
		
	}
	
	public long daysleft(LocalDateTime now) {
		return Duration.between(now, dueDate).toDays();
	}

}
