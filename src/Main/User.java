package Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
	
	private int id;
	private String name;
	private String email;
	private List<Project> projects;
	
public User (int id, String name, String email) {
	
	this.id = id;
	this.name = name;
	this.email = email;
	this.projects = new ArrayList<>();
}

public String getName() {
	return name;
}

public void addProject(Project project) {
	projects.add(project);
}

public List<Project> getProjects() {
    return projects;
}

public List<Task> getUpcomingTasks(LocalDateTime now) {
    List<Task> result = new ArrayList<>();
    for (Project p : projects) {
        result.addAll(p.getUpcomingTasks(now));
    }
    return result;
}
}