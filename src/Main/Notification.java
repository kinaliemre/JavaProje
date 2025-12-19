package Main;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private String message;
    private LocalDateTime notifyAt;
    private Task task;

    public Notification(int id, String message, LocalDateTime notifyAt, Task task) {
        this.id = id;
        this.message = message;
        this.notifyAt = notifyAt;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getNotifyAt() {
        return notifyAt;
    }

    public Task getTask() {
        return task;
    }

    public boolean isTimeToNotify(LocalDateTime now) {
        if (notifyAt == null || now == null) {
            return false;
        }
      
        return !now.isBefore(notifyAt);
    }

    @Override
    public String toString() {
        String taskTitle = (task != null ? task.getTitle() : "Bilinmeyen görev");
        return "[" + id + "] " + message +
               " | Görev: " + taskTitle +
               " | Bildirim zamanı: " + notifyAt;
    }
}
