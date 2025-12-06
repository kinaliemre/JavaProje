package Main;

import java.time.LocalDateTime;

public class Notification{
	
	private int id;
	private String message;
	private LocalDateTime notifyAt;
	private Task task;
	
	public Notification(int id, String message, LocalDateTime notifAt, Task task) {
		
		this.id = id;
		this.message = message;
		this.notifyAt = notifyAt;
		this.task = task;
	}
	public Task getTask() {
		return task;
	}
	public boolean isTimeToNotify(LocalDateTime now) {
		return !task.isCompleted() && !now.isBefore(notifyAt);
	}
	
	@Override
    public String toString() {
        return "Bildirim[" + id + "] " + message +
               " | Zaman: " + notifyAt +
               " | Görev: " + task.getTitle();
    }
}
