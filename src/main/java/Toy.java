public class Toy {
    private final String id;
    private final String name;
    private final int weight;
    private int count;
    private float threshold;

    public Toy(String id, String name, int weight, int count) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.count = count;
        this.threshold = 0.0f;
    }

    public int getWeight() {
        if (count == 0) return 0;
        return weight;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public float getThreshold() {
        return threshold;
    }

    @Override
    public String toString() {
        return "id: " + id + ", Название: " + name;
    }
}
