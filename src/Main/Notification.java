package Main;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private String message;
    private LocalDateTime notifyAt;
    private Task task;
    // Bildirim oluşturur; mesaj, zaman ve bağlı görevi set eder
    public Notification(int id, String message, LocalDateTime notifyAt, Task task) {
        this.id = id;
        this.message = message;
        this.notifyAt = notifyAt;
        this.task = task;
    }
 // Bildirim id'sini döndürür
    public int getId() {
        return id;
    }
    // Bildirim mesajını döndürür
    public String getMessage() {
        return message;
    }
    // Bildirim zamanını döndürür
    public LocalDateTime getNotifyAt() {
        return notifyAt;
    }
 // Bildirimin bağlı olduğu görevi döndürür
    public Task getTask() {
        return task;
    }
    // Şu an (now) bildirimin gösterilme zamanı geldi mi kontrol eder
    public boolean isTimeToNotify(LocalDateTime now) {
        if (notifyAt == null || now == null) {
            return false;
        }
      
        return !now.isBefore(notifyAt);
    }
    // Bildirimi kullanıcıya yazdırılacak metin formatına çevirir
    @Override
    public String toString() {
        String taskTitle = (task != null ? task.getTitle() : "Bilinmeyen görev");
        return "[" + id + "] " + message +
               " | Görev: " + taskTitle +
               " | Bildirim zamanı: " + notifyAt;
    }
}
