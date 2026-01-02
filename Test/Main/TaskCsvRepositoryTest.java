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
	 // save+load sonrası aynı sayıda ve aynı temel alanlarda görev dönüyor mu kontrol eder
    @Test
    void save_ve_load_ayni_sayida_gorev_dondurmeli() throws IOException {
        Task t1 = new Task(1, "Görev 1", "Açıklama 1");
        t1.setPriority(2);
        t1.setDeadline(new Deadline(LocalDateTime.of(2025, 12, 10, 12, 0)));

        Task t2 = new Task(2, "Görev 2", "Açıklama 2");
        t2.setPriority(5);
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
        assertEquals(2, loaded.get(0).getPriority());
        assertEquals(5, loaded.get(1).getPriority());
    }
    // load işlemi deadline ve completed durumunu koruyor mu kontrol eder
    @Test
    void load_deadline_ve_completed_durumunu_korumali() throws IOException {
        Task t1 = new Task(1, "G1", "A");
        LocalDateTime due = LocalDateTime.of(2025, 12, 10, 12, 0);
        t1.setDeadline(new Deadline(due));
        t1.complete();

        List<Task> tasks = new ArrayList<>();
        tasks.add(t1);

        Path tempFile = Files.createTempFile("tasks-test2", ".csv");

        TaskCsvRepository.saveTasks(tempFile.toString(), tasks);

        List<Task> loaded = TaskCsvRepository.loadTasks(tempFile.toString());

        assertEquals(1, loaded.size());
        assertNotNull(loaded.get(0).getDeadline());
        assertEquals(due, loaded.get(0).getDeadline().getDueDate());
        assertTrue(loaded.get(0).isCompleted());
    }
 // TIMEASK türü kaydedilip geri okununca alanları korunuyor mu kontrol eder
    @Test
    void save_load_timeask_olusturup_turu_ve_alanlari_korumali() throws IOException {
        LocalDateTime start = LocalDateTime.of(2025, 12, 10, 10, 0);
        TimeAsk ta = new TimeAsk(5, "TA", "D", start, 45);
        ta.setPriority(3);
        ta.setDeadline(new Deadline(LocalDateTime.of(2025, 12, 10, 12, 0)));

        List<Task> tasks = new ArrayList<>();
        tasks.add(ta);

        Path tempFile = Files.createTempFile("tasks-timeask", ".csv");

        TaskCsvRepository.saveTasks(tempFile.toString(), tasks);

        List<Task> loaded = TaskCsvRepository.loadTasks(tempFile.toString());

        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0) instanceof TimeAsk);

        TimeAsk loadedTa = (TimeAsk) loaded.get(0);
        assertEquals(start, loadedTa.getStartTime());
        assertEquals(45, loadedTa.getDurationMinutes());
        assertEquals(3, loadedTa.getPriority());
        assertNotNull(loadedTa.getDeadline());
    }
    // Dosya yoksa loadTasks boş liste dönüyor mu kontrol eder
    @Test
    void loadTasks_dosya_yoksa_bos_liste_donmeli() {
        List<Task> loaded = TaskCsvRepository.loadTasks("olmayan_dosya_12345.csv");
        assertNotNull(loaded);
        assertEquals(0, loaded.size());
    }

}
