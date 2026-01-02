package Main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UserCsvRepositoryTest {
	// Dosya yoksa loadUsers boş liste döndürüyor mu kontrol eder
    @Test
    void dosya_yoksa_loadUsers_bos_liste_donmeli() {
        List<User> users = UserCsvRepository.loadUsers("olmayan_users_12345.csv");
        assertNotNull(users);
        assertEquals(0, users.size());
    }
    // save+load sonrası aynı kullanıcı verileri geri geliyor mu kontrol eder
    @Test
    void saveUsers_ve_loadUsers_roundtrip_ayni_kullanicilari_dondurmeli() throws IOException {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Ad1", "e1@test.com", "u1", "p1"));
        users.add(new User(2, "Ad2", "e2@test.com", "u2", "p2"));

        Path tempFile = Files.createTempFile("users-test", ".csv");

        UserCsvRepository.saveUsers(tempFile.toString(), users);

        List<User> loaded = UserCsvRepository.loadUsers(tempFile.toString());

        assertEquals(2, loaded.size());

        assertEquals(1, loaded.get(0).getId());
        assertEquals("Ad1", loaded.get(0).getName());
        assertEquals("e1@test.com", loaded.get(0).getEmail());
        assertEquals("u1", loaded.get(0).getUsername());
        assertEquals("p1", loaded.get(0).getPassword());

        assertEquals(2, loaded.get(1).getId());
        assertEquals("Ad2", loaded.get(1).getName());
        assertEquals("e2@test.com", loaded.get(1).getEmail());
        assertEquals("u2", loaded.get(1).getUsername());
        assertEquals("p2", loaded.get(1).getPassword());
    }
    // saveUsers safe() ile ';' karakterini ',' yapıyor mu kontrol eder
    @Test
    void saveUsers_safe_semicolon_karakterini_virgule_cevirmeli() throws IOException {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Ad;Soyad", "e;mail@test.com", "u;1", "p;1"));

        Path tempFile = Files.createTempFile("users-safe", ".csv");

        UserCsvRepository.saveUsers(tempFile.toString(), users);

        List<User> loaded = UserCsvRepository.loadUsers(tempFile.toString());

        assertEquals(1, loaded.size());
        assertEquals("Ad,Soyad", loaded.get(0).getName());
        assertEquals("e,mail@test.com", loaded.get(0).getEmail());
        assertEquals("u,1", loaded.get(0).getUsername());
        assertEquals("p,1", loaded.get(0).getPassword());
    }
}
