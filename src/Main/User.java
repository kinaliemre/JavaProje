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

    public User(int id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.projects = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password != null && this.password.equals(password);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Task> getUpcomingTasks(LocalDateTime now) {
        List<Task> result = new ArrayList<>();
        for (Project p : projects) {
            result.addAll(p.getUpcomingTasks(now));
        }
        return result;
    }
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
