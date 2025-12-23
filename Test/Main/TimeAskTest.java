package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class TimeAskTest {

    @Test
    void ctor_ve_getterlar_dogru_calismali() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 10, 10, 0);

        TimeAsk ta = new TimeAsk(5, "TA", "Desc", start, 45);

        assertEquals(5, ta.getId());
        assertEquals("TA", ta.getTitle());
        assertEquals("Desc", ta.getDescription());

        assertEquals(start, ta.getStartTime());
        assertEquals(45, ta.getDurationMinutes());
    }

    @Test
    void getEndTime_start_plus_duration_donmeli() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 10, 10, 0);
        TimeAsk ta = new TimeAsk(1, "T", "D", start, 90);

        LocalDateTime end = ta.getEndTime();

        assertEquals(LocalDateTime.of(2025, 12, 10, 11, 30), end);
    }

    @Test
    void inherited_complete_ve_isCompleted_calismali() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 10, 10, 0);
        TimeAsk ta = new TimeAsk(1, "T", "D", start, 30);

        assertFalse(ta.isCompleted());
        ta.complete();
        assertTrue(ta.isCompleted());
    }

    @Test
    void toString_super_bilgileri_ve_timeask_bilgilerini_icer_meli() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 10, 10, 0);
        TimeAsk ta = new TimeAsk(7, "Deneme", "D", start, 45);
        ta.setPriority(3);
        ta.setDeadline(new Deadline(LocalDateTime.of(2025, 12, 10, 12, 0)));

        String s = ta.toString();

        assertTrue(s.contains("[7]"));
        assertTrue(s.contains("Deneme"));
        assertTrue(s.contains("Öncelik: 3"));
        assertTrue(s.contains("Başlangıç: " + start.toString()));
        assertTrue(s.contains("Süre(dk): 45"));
    }
}
