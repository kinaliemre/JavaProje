package Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private List<Project> projects;
    // User oluşturur ve proje listesini başlatır
    public User(int id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.projects = new ArrayList<>();
    }
    // Kullanıcı id'sini döndürür
    public int getId() {
        return id;
    }
    // Kullanıcı ad-soyad bilgisini döndürür
    public String getName() {
        return name;
    }
    // Kullanıcının email bilgisini döndürür
    public String getEmail() {
        return email;
    }
 // Kullanıcının kullanıcı adını döndürür
    public String getUsername() {
        return username;
    }
 // Kullanıcının şifresini döndürür (dosyaya yazmak için kullanılıyor)
    public String getPassword() {
        return password;
    }
    // Dışarıdan girilen şifre ile kayıtlı şifre eşleşiyor mu kontrol eder
    public boolean checkPassword(String password) {
        return this.password != null && this.password.equals(password);
    }
    // Kullanıcıya yeni proje ekler
    public void addProject(Project project) {
        projects.add(project);
    }
 // Kullanıcının projelerini döndürür
    public List<Project> getProjects() {
        return projects;
    }
    // Tüm projelerden upcoming görevleri topluca döndürür
    public List<Task> getUpcomingTasks(LocalDateTime now) {
        List<Task> result = new ArrayList<>();
        for (Project p : projects) {
            result.addAll(p.getUpcomingTasks(now));
        }
        return result;
    }
 // Giriş/kayıt menüsünü çalıştırır ve başarılı olunca User döndürür
    public static User authenticate(Scanner scanner, String usersFile) {
        List<User> users = UserCsvRepository.loadUsers(usersFile);

        User user = null;
        boolean authenticated = false;

        while (!authenticated) {
            System.out.println();
            System.out.println("1) Giriş yap 👤:");
            System.out.println("2) Kayıt ol 🆕:");
            System.out.println("0) Çıkış 👋:");
            System.out.print("Seçiminiz: ");
            String firstChoice = scanner.nextLine().trim();

            switch (firstChoice) {
                case "1": {
                    if (users.isEmpty()) {
                        System.out.println("Henüz kayıtlı kullanıcı yok. Lütfen önce kayıt olun.");
                    } else {
                        user = login(scanner, users);
                        if (user == null) {
                            System.out.println("Çok fazla hatalı deneme. Program kapatılıyor.");
                            return null;
                        }
                        authenticated = true;
                    }
                    break;
                }
                case "2": {
                    user = register(scanner, users);
                    users.add(user);
                    UserCsvRepository.saveUsers(usersFile, users);
                    authenticated = true;
                    break;
                }
                case "0": {
                    System.out.println("Program sonlandırılıyor...");
                    return null;
                }
                default: {
                    System.out.println("Geçersiz seçim, tekrar deneyin.");
                    break;
                }
            }
        }

        return user;
    }
    // Kullanıcı adı/şifre ile en fazla 3 denemede giriş yaptırır
    private static User login(Scanner scanner, List<User> users) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Kullanıcı adı: ");
            String username = scanner.nextLine();
            System.out.print("Şifre: ");
            String password = scanner.nextLine();

            for (User u : users) {
                if (u.getUsername() != null &&
                    u.getUsername().equals(username) &&
                    u.checkPassword(password)) {
                    return u;
                }
            }

            System.out.println("Hatalı kullanıcı adı veya şifre.");
            attempts++;
        }
        return null;
    }
    // Yeni kullanıcı kaydı oluşturur (gmail, username ve boş bırakmama kontrolleriyle)
    private static User register(Scanner scanner, List<User> users) {
        System.out.println("=== Kayıt Ol ===");

        String name;
        while (true) {
            System.out.print("Ad Soyad: ");
            name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                break;
            }

            System.out.println("Ad Soyad boş bırakılamaz. Lütfen bir şey yazın.");
        }

        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();

            if (email.endsWith("@gmail.com") && email.length() > "@gmail.com".length()) {
                break;
            }

            System.out.println("Yanlış mail girdiniz. Lütfen '@gmail.com' ile biten geçerli bir Gmail adresi girin.");
        }

        String username;
        while (true) {
            System.out.print("Kullanıcı adı: ");
            username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Kullanıcı adı boş bırakılamaz. Lütfen bir şey yazın.");
                continue;
            }

            boolean exists = false;
            for (User u : users) {
                if (u.getUsername() != null &&
                    u.getUsername().equals(username)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Bu kullanıcı adı zaten kullanılıyor, başka bir tane deneyin.");
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.print("Şifre: ");
            password = scanner.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("Şifre boş bırakılamaz. Lütfen bir şey yazın.");
            } else {
                break;
            }
        }

        int newId = 0;
        for (User u : users) {
            if (u.getId() > newId) {
                newId = u.getId();
            }
        }
        newId++;

        return new User(newId, name, email, username, password);
    }

}
