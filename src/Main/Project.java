

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

    public Project(int id, String name, String description) {
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


    public List<Task> getTasksSortedByDeadline() {
        List<Task> copy = new ArrayList<>(tasks);
        Collections.sort(copy, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                if (t1.getDeadline() == null && t2.getDeadline() == null) return 0;
                if (t1.getDeadline() == null) return 1;
                if (t2.getDeadline() == null) return -1;
                return t1.getDeadline().getDueDate()
                         .compareTo(t2.getDeadline().getDueDate());
            }
        });
        return copy;
    }

   
    public List<Task> getOverdueTasks(LocalDateTime now) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.isCompleted() && t.isOverdue(now)) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Task> getCompletedTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    public List<Task> getUpcomingTasks(LocalDateTime now) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.isCompleted() && !t.isOverdue(now)) {
                result.add(t);
            }
        }
        return result;
    }
}
