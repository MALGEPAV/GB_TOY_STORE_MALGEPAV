public class ToyStoreSimulator {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();
        toyStore.fillStore();
        toyStore.fillPrizeToysQueue(15);
        toyStore.showPrizes();
        toyStore.showStatistics();
    }
}
