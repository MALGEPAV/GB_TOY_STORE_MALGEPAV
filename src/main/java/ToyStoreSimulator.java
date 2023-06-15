import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ToyStoreSimulator {
    public static void main(String[] args) {

        ToyStore toyStore = new ToyStore();
        System.out.println("МАГАЗИН ОТКРЫВАЕТСЯ");
        String command = "";
        while (!command.equals("0")) {
            System.out.println("""
                    
                    '1' - Загрузить игрушки в магазин
                    '2' - Показать игрушки в магазине
                    '3' - Разыграть игрушки
                    '0' - Завершение работы
                    """);
            System.out.print("Выберите действие:");
            Scanner myScanner = new Scanner(System.in);
            Pattern commandPattern = Pattern.compile(" *[0-3] *");
            command = myScanner.nextLine();
            while (!commandPattern.matcher(command).matches()){
                System.out.println("Некорректный ввод, попробуйте еще раз.");
                System.out.print("Выберите действие:");
                command = myScanner.nextLine();
            }
            switch (command) {
                case "1" ->
                    toyStore.fillStore();
                case "2" -> toyStore.showStore();
                case "3" -> {
                    toyStore.fillPrizeToysQueue();
                    toyStore.showPrizes();
                    toyStore.showStatistics();
                }
                case "0" -> System.out.println("МАГАЗИН ЗАКРЫВАЕТСЯ");
            }
        }
    }
}
