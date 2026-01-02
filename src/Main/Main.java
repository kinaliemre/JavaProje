package Main;

import java.util.Scanner;

public class Main {

    private static final String USERS_FILE = "users.csv";
 // Programın giriş noktası; TaskApp'i başlatır
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Görev Yönetim Sistemi ===");

        new TaskApp(USERS_FILE).start(scanner);

        scanner.close();
    }
}
