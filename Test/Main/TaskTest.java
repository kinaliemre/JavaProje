package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class TaskTest {

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

    @Test
    void complete_ve_isCompleted_durumu_degistirmeli() {
        Task task = new Task(1, "Görev", "Açıklama");
        assertFalse(task.isCompleted());
        task.complete();
        assertTrue(task.isCompleted());
    }

    @Test
    void isOverdue_gecmis_tarihteki_gorev_icin_true_donmeli() {
        Task task = new Task(1, "Eski görev", "Açıklama");
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        task.setDeadline(new Deadline(yesterday));

        boolean result = task.isOverdue(LocalDateTime.now());
        assertTrue(result);
    }

    @Test
    void isOverdue_gelecekteki_gorev_icin_false_donmeli() {
        Task task = new Task(1, "Gelecek görev", "Açıklama");
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        task.setDeadline(new Deadline(tomorrow));

        boolean result = task.isOverdue(LocalDateTime.now());
        assertFalse(result);
    }

    @Test
    void isOverdue_deadline_yoksa_false_donmeli() {
        Task task = new Task(1, "Görev", "Açıklama");
        boolean result = task.isOverdue(LocalDateTime.now());
        assertFalse(result);
    }
}
