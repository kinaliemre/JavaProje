package Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String ADMIN_USERNAME = "Admin";
    private static final String ADMIN_PASSWORD = "admin1234";
    private static final String TASK_FILE = "tasks.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Görev Yönetim Sistemi ===");

        if (!login(scanner)) {
            System.out.println("Çok fazla hatalı deneme. Program kapatılıyor.");
            return;
        }

        User user = new User(1, ADMIN_USERNAME, "admin@example.com");
        Project project = new Project(1, "Varsayılan Proje", "Admin görevleri");
        user.addProject(project);

        List<Task> loadedTasks = TaskCsvRepository.loadTasks(TASK_FILE);
        for (Task t : loadedTasks) {
            project.addTask(t);
        }

        int nextTaskId = findNextTaskId(project);

        List<Notification> notifications = new ArrayList<>();
        int nextNotificationId = 1;
        for (Task t : project.getTasks()) {
            if (t.getDeadline() != null) {
                LocalDateTime notifyAt =
                        t.getDeadline().getDueDate().minusMinutes(30);
                notifications.add(
                        new Notification(
                                nextNotificationId++,
                                "Görev süresi yaklaşıyor",
                                notifyAt,
                                t
                        )
                );
            }
        }

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner);

            switch (choice) {
                case 1: {
                    Task task = createTask(scanner, nextTaskId++);
                    project.addTask(task);

                    if (task.getDeadline() != null) {
                        LocalDateTime notifyAt =
                                task.getDeadline().getDueDate().minusMinutes(30);
                        notifications.add(
                                new Notification(
                                        nextNotificationId++,
                                        "Görev süresi yaklaşıyor",
                                        notifyAt,
                                        task
                                )
                        );
                    }

                    TaskCsvRepository.saveTasks(TASK_FILE, project.getTasks());
                    System.out.println("Görev eklendi ve kaydedildi.");
                    break;
                }
                case 2: {
                    List<Task> sorted = project.getTasksSortedByDeadline();
                    System.out.println("Görevler (tarihe göre sıralı):");
                    printTaskList(sorted);
                    break;
                }
                case 3: {
                    List<Task> overdue = project.getOverdueTasks(LocalDateTime.now());
                    System.out.println("Tarihi geçen görevler:");
                    printTaskList(overdue);
                    break;
                }
                case 4: {
                    List<Task> completed = project.getCompletedTasks();
                    System.out.println("Tamamlanmış görevler:");
                    printTaskList(completed);
                    break;
                }
                case 5: {
                    System.out.print("Tamamlanacak görev id: ");
                    int id = readInt(scanner);
                    Task task = project.findTaskById(id);
                    if (task == null) {
                        System.out.println("Bu id'ye sahip görev yok.");
                    } else {
                        task.complete();
                        TaskCsvRepository.saveTasks(TASK_FILE, project.getTasks());
                        System.out.println("Görev tamamlandı ve kaydedildi.");
                    }
                    break;
                }
                case 6: {
                    List<Task> upcoming = project.getUpcomingTasks(LocalDateTime.now());
                    System.out.println("Yaklaşan görevler:");
                    printTaskList(upcoming);
                    break;
                }
                case 7: {
                    System.out.println("Zamanı gelmiş bildirimler:");
                    checkNotifications(notifications, LocalDateTime.now());
                    break;
                }
                case 0:
                    TaskCsvRepository.saveTasks(TASK_FILE, project.getTasks());
                    System.out.println("Görevler kaydedildi. Program sonlandırılıyor...");
                    running = false;
                    break;
                default:
                    System.out.println("Geçersiz seçim.");
            }
        }

        scanner.close();
    }

    private static boolean login(Scanner scanner) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Kullanıcı adı: ");
            String username = scanner.nextLine();
            System.out.print("Şifre: ");
            String password = scanner.nextLine();

            if (ADMIN_USERNAME.equals(username) &&
                ADMIN_PASSWORD.equals(password)) {
                System.out.println("Giriş başarılı, hoş geldin " + ADMIN_USERNAME + ".");
                return true;
            }

            System.out.println("Hatalı kullanıcı adı veya şifre.");
            attempts++;
        }
        return false;
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== MENÜ ===");
        System.out.println("1) Yeni görev oluştur");
        System.out.println("2) Görevleri tarihe göre listele");
        System.out.println("3) Tarihi geçen görevleri listele");
        System.out.println("4) Tamamlanmış görevleri listele");
        System.out.println("5) Görevi tamamlandı işaretle");
        System.out.println("6) Yaklaşan görevleri listele");
        System.out.println("7) Bildirimleri kontrol et");
        System.out.println("0) Çıkış");
        System.out.print("Seçiminiz: ");
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String text = scanner.nextLine();
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.print("Geçerli bir sayı girin: ");
            }
        }
    }

    
    private static void printTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Görev yok.");
        } else {
            for (Task t : tasks) {
                System.out.println(t);
            }
        }
    }

    private static void checkNotifications(List<Notification> notifications,
                                           LocalDateTime now) {
        boolean any = false;
        for (Notification n : notifications) {
            if (n.isTimeToNotify(now)) {
                System.out.println(n);
                any = true;
            }
        }
        if (!any) {
            System.out.println("Şu anda zamanı gelen bildirim yok.");
        }
    }private static Task createTask(Scanner scanner, int id) {
        System.out.print("Görev başlığı: ");
        String title = scanner.nextLine();

        System.out.print("Açıklama: ");
        String description = scanner.nextLine();

        System.out.print("Öncelik (1-5): ");
        int priority = readInt(scanner);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime due;

        while (true) {
            System.out.println("Bitiş tarihi:");
            System.out.print("Yıl: ");
            int year = readInt(scanner);
            System.out.print("Ay (1-12): ");
            int month = readInt(scanner);
            System.out.print("Gün: ");
            int day = readInt(scanner);
            System.out.print("Saat (0-23): ");
            int hour = readInt(scanner);
            System.out.print("Dakika (0-59): ");
            int minute = readInt(scanner);

            due = LocalDateTime.of(year, month, day, hour, minute);

            if (due.isBefore(now)) {
                System.out.println("Geçmiş bir bitiş tarihi girdiniz. Lütfen bugünden sonraki bir tarih girin.");
            } else {
                break;
            }
        }

        System.out.print("Bu görev zamanlanmış (TimeAsk) olsun mu? (E/H): ");
        String answer = scanner.nextLine().trim().toUpperCase();

        Task task;

        if (answer.equals("E")) {
            LocalDateTime start;

            while (true) {
                System.out.println("Başlangıç zamanı:");
                System.out.print("Yıl: ");
                int sy = readInt(scanner);
                System.out.print("Ay (1-12): ");
                int sm = readInt(scanner);
                System.out.print("Gün: ");
                int sd = readInt(scanner);
                System.out.print("Saat (0-23): ");
                int sh = readInt(scanner);
                System.out.print("Dakika (0-59): ");
                int smin = readInt(scanner);

                start = LocalDateTime.of(sy, sm, sd, sh, smin);

                if (start.isBefore(now)) {
                    System.out.println("Geçmiş bir başlangıç zamanı girdiniz. Lütfen bugünden sonraki bir zaman girin.");
                } else if (start.isAfter(due)) {
                    System.out.println("Başlangıç zamanı bitiş tarihinden sonra olamaz. Lütfen tekrar girin.");
                } else {
                    break;
                }
            }

            System.out.print("Süre (dakika): ");
            int duration = readInt(scanner);

            task = new TimeAsk(id, title, description, start, duration);
            task.setDeadline(new Deadline(due));
        } else {
            task = new Task(id, title, description);
            task.setDeadline(new Deadline(due));
        }

        task.setPriority(priority);

        return task;
    }


    private static int findNextTaskId(Project project) {
        int max = 0;
        for (Task t : project.getTasks()) {
            if (t.getId() > max) {
                max = t.getId();
            }
        }
        return max + 1;
    }
}
