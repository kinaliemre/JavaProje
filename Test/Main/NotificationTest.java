package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class NotificationTest {

    @Test
    void ctor_ve_getterlar_dogru_calismali() {
        LocalDateTime notifyAt = LocalDateTime.of(2025, 12, 10, 12, 0);
        Task task = new Task(1, "T1", "D1");

        Notification n = new Notification(7, "Mesaj", notifyAt, task);

        assertEquals(7, n.getId());
        assertEquals("Mesaj", n.getMessage());
        assertEquals(notifyAt, n.getNotifyAt());
        assertEquals(task, n.getTask());
    }

    @Test
    void isTimeToNotify_notifyAt_nullsa_false_donmeli() {
        Notification n = new Notification(1, "M", null, null);
        assertFalse(n.isTimeToNotify(LocalDateTime.now()));
    }

    @Test
    void isTimeToNotify_now_nullsa_false_donmeli() {
        LocalDateTime notifyAt = LocalDateTime.of(2025, 12, 10, 12, 0);
        Notification n = new Notification(1, "M", notifyAt, null);
        assertFalse(n.isTimeToNotify(null));
    }

    @Test
    void isTimeToNotify_now_notifyAt_oncesiyse_false_donmeli() {
        LocalDateTime notifyAt = LocalDateTime.of(2025, 12, 10, 12, 0);
        Notification n = new Notification(1, "M", notifyAt, null);

        LocalDateTime now = notifyAt.minusSeconds(1);
        assertFalse(n.isTimeToNotify(now));
    }

    @Test
    void isTimeToNotify_now_notifyAt_ile_esitse_true_donmeli() {
        LocalDateTime notifyAt = LocalDateTime.of(2025, 12, 10, 12, 0);
        Notification n = new Notification(1, "M", notifyAt, null);

        assertTrue(n.isTimeToNotify(notifyAt));
    }

    @Test
    void isTimeToNotify_now_notifyAt_sonrasiysa_true_donmeli() {
        LocalDateTime notifyAt = LocalDateTime.of(2025, 12, 10, 12, 0);
        Notification n = new Notification(1, "M", notifyAt, null);

        LocalDateTime now = notifyAt.plusMinutes(5);
        assertTrue(n.isTimeToNotify(now));
    }

    @Test
    void toString_task_varken_gorev_basligini_icer_meli() {
        LocalDateTime notifyAt = LocalDateTime.of(2025, 12, 10, 12, 0);
        Task task = new Task(2, "GörevX", "D");
        Notification n = new Notification(9, "Hatırlatma", notifyAt, task);

        String s = n.toString();

        assertTrue(s.contains("[9]"));
        assertTrue(s.contains("Hatırlatma"));
        assertTrue(s.contains("Görev: GörevX"));
        assertTrue(s.contains(notifyAt.toString()));
    }

    @Test
    void toString_task_null_ise_bilinmeyen_gorev_yazmali() {
        LocalDateTime notifyAt = LocalDateTime.of(2025, 12, 10, 12, 0);
        Notification n = new Notification(9, "Hatırlatma", notifyAt, null);

        String s = n.toString();

        assertTrue(s.contains("Bilinmeyen görev"));
    }
}
