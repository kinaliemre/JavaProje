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
public int getId() {
    return id;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public int getPriority() {
	return priority;
}

public void setPriority(int priority) {
	this.priority = priority;
}

public Deadline getDeadline() {
    return deadline;
}

public void setDeadline(Deadline deadline) {
	this.deadline = deadline;
}


public boolean isOverdue(LocalDateTime now) {
    if (deadline == null) {
        return false;
    }
    return deadline.isPast(now);
}

@Override
public String toString() {
    String status = completed ? "TAMAMLANDI" : "BEKLIYOR";
    String dueText = (deadline != null) ? deadline.getDueDate().toString() : "YOK";
    return "[" + id + "] " + title +
           " | Tarih: " + dueText +
           " | Durum: " + status +
           " | Öncelik: " + priority;
}
	
}

















