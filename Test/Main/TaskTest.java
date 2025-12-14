package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    void complete_gorev_tamamlanmali() {
        Task task = new Task(1, "Deneme görev", "Açıklama");

        assertFalse(task.isCompleted());

        task.complete();

        assertTrue(task.isCompleted());
    }

    @Test
    void isOverdue_suresi_gecmis_gorev_true_donmeli() {
        Task task = new Task(2, "Eski görev", "Açıklama");
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        task.setDeadline(new Deadline(yesterday));

        boolean result = task.isOverdue(LocalDateTime.now());

        assertTrue(result);
    }

    @Test
    void isOverdue_gelecekteki_gorev_false_donmeli() {
        Task task = new Task(3, "Gelecek görev", "Açıklama");
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        task.setDeadline(new Deadline(tomorrow));

        boolean result = task.isOverdue(LocalDateTime.now());

        assertFalse(result);
    }
}
