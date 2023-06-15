import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToyStore {
    private Map<String, Toy> toysInStore = new HashMap<>();
    private Map<String, Integer> statistics = new HashMap<>();
    private Queue<String> prizeToysQueue = new ArrayDeque<>();

    public void fillStore() {
        File shipment = new File("src/main/java/shipment.txt");
        try (Scanner myScanner = new Scanner(shipment)) {
            Pattern pat = Pattern.compile(" *[1-9][0-9]* *[^ ]+ *[1-9][0-9]* *[1-9][0-9]* *");
            while (myScanner.hasNextLine()) {
                String testLine = myScanner.nextLine();
                Matcher mat = pat.matcher(testLine);
                if (mat.matches()) {
                    Scanner testLineScanner = new Scanner(testLine);
                    String id = testLineScanner.next(Pattern.compile("[1-9][0-9]*"));
                    String name = testLineScanner.next(Pattern.compile("[^ ]+"));
                    int weight = testLineScanner.nextInt();
                    int count = testLineScanner.nextInt();
                    toysInStore.put(id, new Toy(id, name, weight, count));
                } else {
                    System.out.println("Строка" + " '" + testLine + "' " + "имеет неверный формат");
                }
            }
        } catch (IOException e) {
            System.out.println("I/O error: " + e);
        }
        System.out.println("Игрушки загружены из файла shipment.txt");
    }

    private boolean storeIsEmpty() {
        if (getTotalCount() == 0) {
            System.out.println("Магазин пуст");
            return true;
        }
        return false;
    }

    public void showStore() {
        if (storeIsEmpty()) return;
        int totalWeight = getTotalWeight();
        System.out.println("Игрушки:");
        for (Toy toy :
                toysInStore.values()) {
            System.out.println(toy + " количество: " + toy.getCount()
                    + " вероятность выпадания: " + Math.round(100 * (float) toy.getWeight() / (float) totalWeight) + '%');
        }
        System.out.println("Всего игрушек: " + getTotalCount());
    }

    private void setThresholds() {
        float totalWeight = (float) getTotalWeight();
        float currentThreshold = 0.0f;
        for (Toy toy :
                toysInStore.values()) {
            currentThreshold += (float) toy.getWeight() / totalWeight;
            toy.setThreshold(currentThreshold);
        }
    }

    public void fillPrizeToysQueue() {
        statistics.clear();
        if (storeIsEmpty()) return;
        Scanner amountScanner = new Scanner(System.in);
        System.out.print("Сколько игрушек разыграть?: ");
        String lengthString = amountScanner.nextLine();
        Pattern natNumberPattern = Pattern.compile(" *[1-9][0-9]* *");
        while (!natNumberPattern.matcher(lengthString).matches()) {
            System.out.println("Требуется ввести натуральное число.");
            System.out.print("Сколько игрушек разыграть?: ");
            lengthString = amountScanner.nextLine();
        }
        int length = Integer.parseInt(lengthString.strip());
        length = Math.min(length, getTotalCount());
        System.out.println("Всего игрушек в магазине: " + getTotalCount());
        System.out.println("Будет разыграно игрушек: "+ length);
        setThresholds();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            float r = rand.nextFloat();
            for (Toy toy :
                    toysInStore.values()) {
                if (r <= toy.getThreshold()) {
                    prizeToysQueue.offer(toy.toString());
                    toy.decrementCount();
                    if (toy.getCount() == 0) {
                        setThresholds();
                    }
                    statistics.computeIfPresent(toy.toString(), (k, v) -> v += 1);
                    statistics.putIfAbsent(toy.toString(), 1);
                    break;
                }
            }
        }
        System.out.println("Игрушки разыграны");
    }

    public void showPrizes() {
        if (prizeToysQueue.isEmpty()) {
            System.out.println("Ничего не разыграно");
            return;
        }
        try (PrintWriter pw = new PrintWriter("src/main/java/PrizeToys.txt")) {
            pw.println("ПРИЗЫ:");
            while (!prizeToysQueue.isEmpty()) {
                pw.println(prizeToysQueue.poll());
            }
        } catch (IOException e) {
            System.out.println("I/O error: " + e);
        }
        System.out.println("Розыгрыш записан в файл PrizeToys.txt");
    }

    public void showStatistics() {
        try (PrintWriter pw = new PrintWriter("src/main/java/Stats.txt")) {
            int totalPrizes = 0;
            for (Integer toyCount :
                    statistics.values()) {
                totalPrizes += toyCount;
            }
            if (totalPrizes == 0) {
                System.out.println("Статистики нет");
                return;
            }
            pw.println("СТАТИСТИКА:");
            for (Map.Entry<String, Integer> entry :
                    statistics.entrySet()) {
                pw.println(entry.getKey() + ' ' + Math.round((float) entry.getValue() / totalPrizes * 100) + '%');
            }
        } catch (IOException e) {
            System.out.println("I/O error: " + e);
        }
        System.out.println("Статистика записана в файл Stats.txt");
    }

    private int getTotalWeight() {
        int totalWeight = 0;
        for (Toy toy :
                toysInStore.values()) {
            totalWeight += toy.getWeight();
        }
        return totalWeight;
    }

    public int getTotalCount() {
        int totalCount = 0;
        for (Toy toy :
                toysInStore.values()) {
            totalCount += toy.getCount();
        }
        return totalCount;
    }
}
