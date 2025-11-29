package Main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Bir tam sayı gir: ");
        int sayi = scan.nextInt();
   

        System.out.println("Girdiğin sayı: " + sayi);
    

        scan.close();
    }
}
