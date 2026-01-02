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
// Görevi tamamlandı olarak işaretler
@Override
public void complete() {
    this.completed = true;
}
//Görev tamamlandı mı bilgisini döndürür
@Override
public boolean isCompleted() {
    return completed;
}
//Görev id'sini döndürür
public int getId() {
    return id;
}
// Görev başlığını döndürür
public String getTitle() {
    return title;
}
//Görev başlığını günceller
public void setTitle(String title) {
	this.title = title;
}
// Görev açıklamasını döndürür
public String getDescription() {
	return description;
}
//Görev açıklamasını günceller
public void setDescription(String description) {
	this.description = description;
}
// Görev önceliğini döndürür
public int getPriority() {
	return priority;
}
// Görev önceliğini günceller
public void setPriority(int priority) {
	this.priority = priority;
}
//Görevin deadline nesnesini döndürür
public Deadline getDeadline() {
    return deadline;
}
// Göreve deadline atar/günceller
public void setDeadline(Deadline deadline) {
	this.deadline = deadline;
}

//Deadline'a göre görev gecikmiş mi kontrol eder
public boolean isOverdue(LocalDateTime now) {
    if (deadline == null) {
        return false;
    }
    return deadline.isPast(now);
}
//Görevin kullanıcıya yazdırılacak özet metnini üretir
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


