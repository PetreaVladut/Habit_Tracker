public class Day {
    int id;
    int feel;
    String date;
    public Day(int id, int feel, String date) {
        this.id = id;
        this.feel = feel;
        this.date = date;
    }
    public Day(int id, int feel) {
        this.id = id;
        this.feel = feel;
    }
    public int getId() {
        return id;
    }

    public int getFeel() {
        return feel;
    }
    public String getDate() {
        return date;
    }
}
