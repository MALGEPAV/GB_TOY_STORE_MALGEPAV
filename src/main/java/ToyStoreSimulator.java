public class ToyStoreSimulator {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();
        toyStore.fillStore();
        toyStore.setThresholds();
//        for (Toy toy :
//                toyStore.toysInStore) {
//            System.out.println(toy.getThreshold());
//        }
        toyStore.fillPrizeToysQueue();
        toyStore.showPrizes();
    }
}
