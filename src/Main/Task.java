package Main;

import java.time.LocalDateTime;

public class Task implements Completable {
	
	private int id;
	private String title;
	private String description;
	private boolean completed;
	private int priority;
	private Deadline deadline;
	
public Task(int id, String title, String description) {
	this.id = id;
	this.title = title;
	this.description = description;
	this.completed = false;
	this.priority = 0;
	this.deadline = null;
	
}

@Override
public void complete() {
    this.completed = true;
}

@Override
public boolean isCompleted() {
    return completed;
}


}
