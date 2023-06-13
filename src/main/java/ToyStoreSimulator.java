public class ToyStoreSimulator {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();
        toyStore.fillStore();
        toyStore.fillPrizeToysQueue(10);
        //toyStore.showPrizes();
        toyStore.showStatistics();
    }
}
