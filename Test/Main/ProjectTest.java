package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ProjectTest {

    @Test
    void addTask_ve_getTasks_listede_gorev_tutulmali() {
        Project project = new Project(1, "P", "Açıklama");
        Task t1 = new Task(1, "G1", "A");
        Task t2 = new Task(2, "G2", "B");

        project.addTask(t1);
        project.addTask(t2);

        List<Task> tasks = project.getTasks();
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(t1));
        assertTrue(tasks.contains(t2));
    }

    @Test
    void removeTaskById_mevcut_id_icin_true_donmeli_ve_listeden_silmeli() {
        Project project = new Project(1, "P", "A");
        Task t1 = new Task(1, "G1", "A");
        Task t2 = new Task(2, "G2", "B");
        project.addTask(t1);
        project.addTask(t2);

        boolean removed = project.removeTaskById(1);

        assertTrue(removed);
        assertEquals(1, project.getTasks().size());
        assertEquals(2, project.getTasks().get(0).getId());
    }

    @Test
    void removeTaskById_olmayan_id_icin_false_donmeli() {
        Project project = new Project(1, "P", "A");
        Task t1 = new Task(1, "G1", "A");
        project.addTask(t1);

        boolean removed = project.removeTaskById(99);

        assertFalse(removed);
        assertEquals(1, project.getTasks().size());
    }

    @Test
    void findTaskById_varsa_gorevi_donmeli() {
        Project project = new Project(1, "P", "A");
        Task t1 = new Task(1, "G1", "A");
        Task t2 = new Task(2, "G2", "B");
        project.addTask(t1);
        project.addTask(t2);

        Task found = project.findTaskById(2);

        assertNotNull(found);
        assertEquals(2, found.getId());
    }

    @Test
    void findTaskById_yoksa_null_donmeli() {
        Project project = new Project(1, "P", "A");
        Task found = project.findTaskById(5);
        assertNull(found);
    }

    @Test
    void getCompletedTasks_sadece_tamamlananlari_donmeli() {
        Project project = new Project(1, "P", "A");
        Task t1 = new Task(1, "G1", "A");
        Task t2 = new Task(2, "G2", "B");
        t2.complete();
        project.addTask(t1);
        project.addTask(t2);

        List<Task> completed = project.getCompletedTasks();

        assertEquals(1, completed.size());
        assertEquals(2, completed.get(0).getId());
    }

    @Test
    void getOverdueTasks_sadece_gecikmis_ve_tamamlanmamis_gorevleri_donmeli() {
        Project project = new Project(1, "P", "A");
        LocalDateTime now = LocalDateTime.of(2025, 12, 10, 12, 0);

        Task overdue = new Task(1, "Eski", "A");
        overdue.setDeadline(new Deadline(now.minusDays(1)));

        Task future = new Task(2, "Gelecek", "B");
        future.setDeadline(new Deadline(now.plusDays(1)));

        Task overdueCompleted = new Task(3, "Eski tamam", "C");
        overdueCompleted.setDeadline(new Deadline(now.minusDays(2)));
        overdueCompleted.complete();

        project.addTask(overdue);
        project.addTask(future);
        project.addTask(overdueCompleted);

        List<Task> overdueList = project.getOverdueTasks(now);

        assertEquals(1, overdueList.size());
        assertEquals(1, overdueList.get(0).getId());
    }

    @Test
    void getUpcomingTasks_sadece_gelecek_ve_tamamlanmamis_gorevleri_donmeli() {
        Project project = new Project(1, "P", "A");
        LocalDateTime now = LocalDateTime.of(2025, 12, 10, 12, 0);

        Task future = new Task(1, "Gelecek", "A");
        future.setDeadline(new Deadline(now.plusDays(1)));

        Task futureCompleted = new Task(2, "Gelecek tamam", "B");
        futureCompleted.setDeadline(new Deadline(now.plusDays(2)));
        futureCompleted.complete();

        Task overdue = new Task(3, "Eski", "C");
        overdue.setDeadline(new Deadline(now.minusDays(1)));

        Task noDeadline = new Task(4, "Tarihsiz", "D");

        project.addTask(future);
        project.addTask(futureCompleted);
        project.addTask(overdue);
        project.addTask(noDeadline);

        List<Task> upcoming = project.getUpcomingTasks(now);

        assertEquals(1, upcoming.size());
        assertEquals(1, upcoming.get(0).getId());
    }

    @Test
    void getTasksSortedByDeadline_tarihe_gore_artan_sirali_donmeli() {
        Project project = new Project(1, "P", "A");
        LocalDateTime now = LocalDateTime.of(2025, 12, 10, 12, 0);

        Task t1 = new Task(1, "G1", "A");
        t1.setDeadline(new Deadline(now.plusDays(3)));

        Task t2 = new Task(2, "G2", "B");
        t2.setDeadline(new Deadline(now.plusDays(1)));

        Task t3 = new Task(3, "G3", "C");
        t3.setDeadline(new Deadline(now.plusDays(2)));

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
