package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void ctor_ve_getterlar_dogru_calismali() {
        User u = new User(1, "Ad Soyad", "mail@test.com", "user1", "pass");

        assertEquals(1, u.getId());
        assertEquals("Ad Soyad", u.getName());
        assertEquals("mail@test.com", u.getEmail());
        assertEquals("user1", u.getUsername());
        assertEquals("pass", u.getPassword());
        assertNotNull(u.getProjects());
        assertEquals(0, u.getProjects().size());
    }

    @Test
    void checkPassword_dogru_sifre_icin_true_donmeli() {
        User u = new User(1, "A", "E", "U", "1234");
        assertTrue(u.checkPassword("1234"));
    }

    @Test
    void checkPassword_yanlis_sifre_icin_false_donmeli() {
        User u = new User(1, "A", "E", "U", "1234");
        assertFalse(u.checkPassword("0000"));
    }

    @Test
    void checkPassword_password_nullsa_false_donmeli() {
        User u = new User(1, "A", "E", "U", null);
        assertFalse(u.checkPassword("1234"));
    }

    @Test
    void addProject_ve_getProjects_projeyi_tutmalı() {
        User u = new User(1, "A", "E", "U", "P");
        Project p = new Project(10, "P1", "D");

        u.addProject(p);

        assertEquals(1, u.getProjects().size());
        assertEquals(p, u.getProjects().get(0));
    }

    @Test
    void getUpcomingTasks_tum_projelerden_yaklasanlari_toplamali() {
        LocalDateTime now = LocalDateTime.of(2025, 12, 10, 12, 0);

        User u = new User(1, "A", "E", "U", "P");

        Project p1 = new Project(1, "P1", "D1");
        Project p2 = new Project(2, "P2", "D2");

        Task upcoming1 = new Task(1, "G1", "A");
        upcoming1.setDeadline(new Deadline(now.plusDays(1)));

        Task overdue = new Task(2, "G2", "B");
        overdue.setDeadline(new Deadline(now.minusDays(1)));

        Task completedUpcoming = new Task(3, "G3", "C");
        completedUpcoming.setDeadline(new Deadline(now.plusDays(2)));
        completedUpcoming.complete();

        Task upcoming2 = new Task(4, "G4", "D");
        upcoming2.setDeadline(new Deadline(now.plusHours(5)));

        p1.addTask(upcoming1);
        p1.addTask(overdue);

        p2.addTask(completedUpcoming);
        p2.addTask(upcoming2);

        u.addProject(p1);
        u.addProject(p2);

        List<Task> upcoming = u.getUpcomingTasks(now);

        assertEquals(2, upcoming.size());

        boolean has1 = upcoming.stream().anyMatch(t -> t.getId() == 1);
        boolean has4 = upcoming.stream().anyMatch(t -> t.getId() == 4);

        assertTrue(has1);
        assertTrue(has4);
    }
}
