import java.util.Scanner;
public class Global {
    public static Scanner inputKeyboard = new Scanner(System.in);

    public static int readValidInteger(String message) {
        int value = -1;
        while (value < 1) {
            System.out.print(message);
            try {
                value = Integer.parseInt(Global.inputKeyboard.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Only integer numbers");
            }
        }
        return value;
    }
}
