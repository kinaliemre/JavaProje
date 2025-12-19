package Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private List<Project> projects;

    public User(int id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.projects = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password != null && this.password.equals(password);
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
