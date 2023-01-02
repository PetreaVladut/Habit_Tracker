import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Query {
    Connection c;
    public void day_query(ArrayList<Entry> aux,String day)
    {
        if (day == null) {
            LocalDate myObj = LocalDate.now();
            day = String.valueOf(myObj);
        }
        String data=null;
        data = String.format("select * from java.actions_raw natural join java.day where datee='%s'",day);
        System.out.println(data);
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(data);
            while(rs.next()) {
                aux.add(new Entry(rs.getString("data"),rs.getInt("idDay")));
                System.out.println(rs.getString("data"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void day_query(ArrayList<Entry> aux,int day)
    {
        String data=null;
        data = String.format("select * from java.actions_raw natural join java.day where idDay='%d'",day);
        System.out.println(data);
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(data);
            while(rs.next()) {
                aux.add(new Entry(rs.getString("data"),rs.getInt("idDay")));
                System.out.println(rs.getString("data"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete_day(ArrayList<Entry> aux)
    {
        String data=null;
        PreparedStatement stmt = null;
        for(int i=0;i<aux.size();i++) {
            data = String.format("delete from java.actions_raw where idDay=%d and data='%s';", aux.get(i).idDate, aux.get(i).text);
            System.out.println(data);
            try {
                stmt = c.prepareStatement(data);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int insert_day()
    {
        String data=null;
        LocalDate myObj = LocalDate.now();
        String day = String.valueOf(myObj);
        data = String.format("insert into day(datee) values('%s');",day);
        System.out.println(data);
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement(data);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        data = String.format("select idDay from java.day where datee='%s'",day);
        Statement stmt1 = null;
        try {
            stmt1 = c.createStatement();
            ResultSet rs = stmt.executeQuery(data);
            rs.next();
            return rs.getInt("idDay");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insert_entry(Entry p)
    {
        String data=null;
        data = String.format("insert into actions_raw(data,idDay) values('%s',%d);",p.text,p.idDate);
        System.out.println(data);
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement(data);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void data_query()
    {

    }
    public Query() {
        try {
            // Class.forName("org.postgresql.Driver");
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            //Connection con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:3306/test", "root", "vladut28");
            c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/java?characterEncoding=utf8", "root", "vladut28");
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
