package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ProjectTest {

    @Test
    void getOverdueTasks_sadece_gecikmis_ve_tamamlanmamis_gorevleri_donmeli() {
        Project project = new Project(1, "Test Proje", "Açıklama");

        Task gecikmis = new Task(1, "Eski görev", "Açıklama");
        gecikmis.setDeadline(new Deadline(LocalDateTime.now().minusDays(1)));

        Task gelecek = new Task(2, "Gelecek görev", "Açıklama");
        gelecek.setDeadline(new Deadline(LocalDateTime.now().plusDays(1)));

        Task gecikmisAmaTamamlanmis = new Task(3, "Eski ama tamam", "Açıklama");
        gecikmisAmaTamamlanmis.setDeadline(new Deadline(LocalDateTime.now().minusDays(1)));
        gecikmisAmaTamamlanmis.complete();

        project.addTask(gecikmis);
        project.addTask(gelecek);
        project.addTask(gecikmisAmaTamamlanmis);

        List<Task> overdue = project.getOverdueTasks(LocalDateTime.now());

        assertEquals(1, overdue.size());
        assertEquals(1, overdue.get(0).getId());
    }

    @Test
    void getTasksSortedByDeadline_tarihe_gore_artan_sirali_donmeli() {
        Project project = new Project(1, "Test Proje", "Açıklama");

        Task t1 = new Task(1, "En geç", "A");
        t1.setDeadline(new Deadline(LocalDateTime.now().plusDays(3)));

        Task t2 = new Task(2, "En erken", "B");
        t2.setDeadline(new Deadline(LocalDateTime.now().plusDays(1)));

        Task t3 = new Task(3, "Ortanca", "C");
        t3.setDeadline(new Deadline(LocalDateTime.now().plusDays(2)));

        project.addTask(t1);
        project.addTask(t2);
        project.addTask(t3);

        List<Task> sorted = project.getTasksSortedByDeadline();

        assertEquals(3, sorted.size());
        assertEquals(2, sorted.get(0).getId());
        assertEquals(3, sorted.get(1).getId());
        assertEquals(1, sorted.get(2).getId());
    }
}
