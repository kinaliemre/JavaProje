package Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Project {
	private int id;
	private String name;
	private String description;
	private List<Task> tasks;
	
public Project(int id, String name, String desription) {
	this.id = id;
	this.name = name;
	this.description = description;
	this.tasks = new ArrayList<>();
}

public int getId() {
	return id;
}

public String getName() {
	return name;
}

public void addTask(Task task) {
	tasks.add(task);
}

public boolean removeTaskById(int taskId) {
    return tasks.removeIf(t -> t.getId() == taskId);
}

public List<Task> getTasks() {
    return tasks;
}

public Task findTaskById(int taskId) {
    for (Task t : tasks) {
        if (t.getId() == taskId) {
            return t;
        }
    }
    return null;
}





}
