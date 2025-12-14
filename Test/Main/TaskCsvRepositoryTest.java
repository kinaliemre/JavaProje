package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskCsvRepositoryTest {

    @Test
    void save_ve_load_ayni_gorev_listesini_dondurmeli() throws IOException {
        Task t1 = new Task(1, "Görev 1", "Açıklama 1");
        t1.setDeadline(new Deadline(LocalDateTime.of(2025, 12, 10, 12, 0)));

        Task t2 = new Task(2, "Görev 2", "Açıklama 2");
        t2.setDeadline(new Deadline(LocalDateTime.of(2025, 12, 11, 15, 0)));

        List<Task> tasks = new ArrayList<>();
        tasks.add(t1);
        tasks.add(t2);

        Path tempFile = Files.createTempFile("tasks-test", ".csv");

        TaskCsvRepository.saveTasks(tempFile.toString(), tasks);

        List<Task> loaded = TaskCsvRepository.loadTasks(tempFile.toString());

        assertEquals(2, loaded.size());
        assertEquals("Görev 1", loaded.get(0).getTitle());
        assertEquals("Görev 2", loaded.get(1).getTitle());
    }
}
