public class Entry {
    public String text;
    public int idDate;
    int feel=-1;
    int id=0;
    public String get_text() { return text; }
    public int get_feel() { return feel; }
    public int get_id() { return id; }
    public int get_idDate() { return idDate; }
    public Entry(String text,int idDate, int f)
    {
        this.text=text;
        this.idDate=idDate;
        feel= f;
    }
    public Entry(String text,int idDate, int f,int id)
    {
        this.text=text;
        this.idDate=idDate;
        feel= f;
        this.id=id;
    }
}
