package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class TaskTest {
	  // Task constructor ve getter'lar doğru mu kontrol eder
    @Test
    void ctor_ve_getterlar_dogru_calismali() {
        int id = 10;
        String title = "Başlık";
        String desc = "Açıklama";
        Task task = new Task(id, title, desc);

        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(desc, task.getDescription());
        assertFalse(task.isCompleted());
        assertEquals(0, task.getPriority());
        assertNull(task.getDeadline());
    }
    // Setter'lar alanları güncelliyor mu kontrol eder
    @Test
    void setterlar_degerleri_guncellemeli() {
        Task task = new Task(1, "Eski", "Eski açıklama");
        task.setTitle("Yeni");
        task.setDescription("Yeni açıklama");
        task.setPriority(3);
        LocalDateTime due = LocalDateTime.of(2025, 12, 10, 12, 0);
        Deadline deadline = new Deadline(due);
        task.setDeadline(deadline);

        assertEquals("Yeni", task.getTitle());
        assertEquals("Yeni açıklama", task.getDescription());
        assertEquals(3, task.getPriority());
        assertEquals(deadline, task.getDeadline());
    }
    // complete çağrısı sonrası isCompleted true oluyor mu kontrol eder
    @Test
    void complete_ve_isCompleted_durumu_degistirmeli() {
        Task task = new Task(1, "Görev", "Açıklama");
        assertFalse(task.isCompleted());
        task.complete();
        assertTrue(task.isCompleted());
    }
    // Deadline geçmişse isOverdue true oluyor mu kontrol eder
    @Test
    void isOverdue_gecmis_tarihteki_gorev_icin_true_donmeli() {
        Task task = new Task(1, "Eski görev", "Açıklama");
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        task.setDeadline(new Deadline(yesterday));

        boolean result = task.isOverdue(LocalDateTime.now());
        assertTrue(result);
    }
    // Deadline gelecekteyse isOverdue false oluyor mu kontrol eder
    @Test
    void isOverdue_gelecekteki_gorev_icin_false_donmeli() {
        Task task = new Task(1, "Gelecek görev", "Açıklama");
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        task.setDeadline(new Deadline(tomorrow));

        boolean result = task.isOverdue(LocalDateTime.now());
        assertFalse(result);
    }
 // Deadline yoksa isOverdue false mu kontrol eder
    @Test
    void isOverdue_deadline_yoksa_false_donmeli() {
        Task task = new Task(1, "Görev", "Açıklama");
        boolean result = task.isOverdue(LocalDateTime.now());
        assertFalse(result);
    }

    // toString beklenen temel bilgileri içeriyor mu kontrol eder
    @Test
    void toString_bilgileri_icermeli() {
        LocalDateTime due = LocalDateTime.of(2025, 12, 10, 12, 0);

        Task task = new Task(7, "Deneme", "Desc");
        task.setPriority(4);
        task.setDeadline(new Deadline(due));

        String s = task.toString();

        assertTrue(s.contains("[7]"));
        assertTrue(s.contains("Deneme"));
        assertTrue(s.contains("Öncelik: 4"));
        assertTrue(s.contains(due.toString()));
        assertTrue(s.contains("BEKLIYOR"));
    }

}
