package Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Project {
    private int id;
    private String name;
    private List<Task> tasks;
 // Proje oluşturur ve görev listesini başlatır
    public Project(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.tasks = new ArrayList<>();
    }
    // Proje id'sini döndürür
    public int getId() {
        return id;
    }
 // Proje adını döndürür
    public String getName() {
        return name;
    }
    // Projeye yeni görev ekler
    public void addTask(Task task) {
        tasks.add(task);
    }
 // Verilen id'ye sahip görevi listeden siler (varsa true)
    public boolean removeTaskById(int taskId) {
        return tasks.removeIf(t -> t.getId() == taskId);
    }
    // Projedeki tüm görev listesini döndürür
    public List<Task> getTasks() {
        return tasks;
    }
    // Verilen id ile görev arar, yoksa null döndürür
    public Task findTaskById(int taskId) {
        for (Task t : tasks) {
            if (t.getId() == taskId) {
                return t;
            }
        }
        return null;
    }

 // Görevleri deadline'a göre artan sırayla döndürür (deadline null olanlar en sonda)
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

    // Tamamlanmamış ve deadline'ı geçmiş görevleri döndürür
    public List<Task> getOverdueTasks(LocalDateTime now) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.isCompleted() && t.isOverdue(now)) {
                result.add(t);
            }
        }
        return result;
    }
    // Tamamlanmış görevleri döndürür
    public List<Task> getCompletedTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }
    // Deadline'ı olan, tamamlanmamış ve gecikmemiş görevleri (upcoming) döndürür
    public List<Task> getUpcomingTasks(LocalDateTime now) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDeadline() != null
                    && !t.isCompleted()
                    && !t.isOverdue(now)) {
                result.add(t);
            }
        }
        return result;
    }
}
