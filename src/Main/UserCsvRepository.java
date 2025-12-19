package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserCsvRepository {

    public static void saveUsers(String fileName, List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (User u : users) {
                String line = u.getId() + ";" +
                              safe(u.getName()) + ";" +
                              safe(u.getEmail()) + ";" +
                              safe(u.getUsername()) + ";" +
                              safe(u.getPassword());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kullanıcı dosyası yazma hatası: " + e.getMessage());
        }
    }

    public static List<User> loadUsers(String fileName) {
        List<User> users = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";", -1);
                if (parts.length < 5) {
                    continue;
                }

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];
                String username = parts[3];
                String password = parts[4];

                User user = new User(id, name, email, username, password);
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println("Kullanıcı dosyası okuma hatası: " + e.getMessage());
        }

        return users;
    }

    private static String safe(String s) {
        if (s == null) {
            return "";
        }
        return s.replace(";", ",");
    }
}
