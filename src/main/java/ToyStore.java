import java.util.*;

public class ToyStore {
    public List<Toy> toysInStore;
    private Queue<String> prizeToysQueue  = new ArrayDeque<>();;
    private final int prizeToysQueueLength = 10;

    public void fillStore() {
        toysInStore = new ArrayList<>();
        toysInStore.add(new Toy("1", "Кукла Маша", 6, 10));
        toysInStore.add(new Toy("2", "Спиннер", 4, 8));
        //toysInStore.add(new Toy("3", "Симпл Димпл", 3, 6));
        //toysInStore.add(new Toy("4", "Турбо Фаллос", 2, 4));
        //toysInStore.add(new Toy("4", "Турбо Фаллос", 1, 2));
    }

    public void setThresholds() {
        int totalWeight = 0;
        for (Toy toy :
                toysInStore) {
            totalWeight += toy.getWeight();
        }
        float currentThreshold = 0.0f;
//        ListIterator<Toy> toysIterator = toysInStore.listIterator();
//        while (toysIterator.hasNext()) {
//            Toy currentToy = toysIterator.next();
//            currentToy.setThreshold(currentThreshold += ((float)currentToy.getWeight()) / (float)totalWeight);
//        }
        for (Toy toy :
                toysInStore) {
            currentThreshold += (float) toy.getWeight() / (float) totalWeight;
            toy.setThreshold(currentThreshold);
        }
    }
    public void fillPrizeToysQueue(){
        Random rand = new Random();
        for (int i = 0; i < prizeToysQueueLength; i++) {
            float r = rand.nextFloat();
            for (Toy toy :
                    toysInStore) {
                if (r<=toy.getThreshold()) {
                    prizeToysQueue.offer(toy.toString());
                    break;
                }
            }
        }
    }
    public void showPrizes(){
        System.out.println("ПРИЗЫ:");
        while (!prizeToysQueue.isEmpty()){
            System.out.println(prizeToysQueue.poll());
        }
    }

}
