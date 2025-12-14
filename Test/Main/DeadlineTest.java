package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    void ctor_ve_getDueDate_dogru_calismali() {
        LocalDateTime due = LocalDateTime.of(2025, 12, 10, 12, 0);
        Deadline deadline = new Deadline(due);
        assertEquals(due, deadline.getDueDate());
    }
}
