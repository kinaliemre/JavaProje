package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class DeadlineTest {
	// Constructor ve getter doğru mu kontrol eder
    @Test
    void ctor_ve_getDueDate_dogru_calismali() {
        LocalDateTime due = LocalDateTime.of(2025, 12, 10, 12, 0);
        Deadline deadline = new Deadline(due);
        assertEquals(due, deadline.getDueDate());
    }
 // now deadline'dan sonraysa isPast true mu kontrol eder
    @Test
    void isPast_now_dueDate_sonrasiysa_true_donmeli() {
        LocalDateTime due = LocalDateTime.of(2025, 12, 10, 12, 0);
        Deadline deadline = new Deadline(due);

        LocalDateTime now = due.plusMinutes(1);
        assertTrue(deadline.isPast(now));
    }
    // now deadline'dan önceyse isPast false mu kontrol eder
    @Test
    void isPast_now_dueDate_oncesiyse_false_donmeli() {
        LocalDateTime due = LocalDateTime.of(2025, 12, 10, 12, 0);
        Deadline deadline = new Deadline(due);

        LocalDateTime now = due.minusMinutes(1);
        assertFalse(deadline.isPast(now));
    }
    // daysleft gelecek tarih için pozitif döndürüyor mu kontrol eder
    @Test
    void daysleft_gelecek_tarih_icin_pozitif_donmeli() {
        LocalDateTime now = LocalDateTime.of(2025, 12, 1, 0, 0);
        LocalDateTime due = now.plusDays(5);
        Deadline deadline = new Deadline(due);

        assertEquals(5, deadline.daysleft(now));
    }
    // daysleft geçmiş tarih için negatif döndürüyor mu kontrol eder
    @Test
    void daysleft_gecmis_tarih_icin_negatif_donmeli() {
        LocalDateTime now = LocalDateTime.of(2025, 12, 10, 0, 0);
        LocalDateTime due = now.minusDays(3);
        Deadline deadline = new Deadline(due);

        assertEquals(-3, deadline.daysleft(now));
    }
    // daysleft aynı gün için 0 mı kontrol eder
    @Test
    void daysleft_ayni_gun_icin_0_donmeli() {
        LocalDateTime now = LocalDateTime.of(2025, 12, 10, 12, 0);
        Deadline deadline = new Deadline(now);

        assertEquals(0, deadline.daysleft(now));
    }

}
