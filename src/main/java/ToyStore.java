import java.io.FileOutputStream;
import java.util.*;

public class ToyStore {
    //private List<Toy> toysInStore;
    private Map<String, Toy> toysInStore;
    private Map<String, Integer> statistics;
    private Queue<String> prizeToysQueue = new ArrayDeque<>();

    public void fillStore() {
        toysInStore = new HashMap<>();
        toysInStore.put("1", new Toy("1", "Кукла Маша", 4, 100000));
        toysInStore.put("2", new Toy("2", "Спиннер", 3, 100000));
        toysInStore.put("3", new Toy("3", "Симпл Димпл", 2, 100000));
        toysInStore.put("4", new Toy("4", "Турбо Фаллос", 1, 100000));
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

    public void fillPrizeToysQueue(int length) {
        statistics = new HashMap<>();
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
    }

    public void showPrizes() {
        System.out.println("ПРИЗЫ:");
        while (!prizeToysQueue.isEmpty()) {
            System.out.println(prizeToysQueue.poll());
        }
    }

    public void showStatistics() {
        System.out.println("Статистика:");
        int totalPrizes = 0;
        for (Integer toyCount :
                statistics.values()) {
            totalPrizes += toyCount;
        }

        for (Map.Entry<String, Integer> entry:
        statistics.entrySet()){
            System.out.println(entry.getKey()+' '+Math.round((float)entry.getValue()/totalPrizes*100)+'%');
        }
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
