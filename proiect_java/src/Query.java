import java.math.RoundingMode;
import java.sql.*;
import java.text.DecimalFormat;
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
                aux.add(new Entry(rs.getString("data"),rs.getInt("idDay"),rs.getInt("feel")));
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
                aux.add(new Entry(rs.getString("data"),rs.getInt("idDay"),rs.getInt("feel")));
                System.out.println(rs.getString("data"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void day_query(ArrayList<Entry> aux,int day,int flag)
    {
        String data=null;
        data = String.format("select * from java.actions_raw natural join java.day where idDay='%d'",day);
        System.out.println(data);
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(data);
            while(rs.next()) {
                aux.add(new Entry(rs.getString("data"),rs.getInt("idDay"),rs.getInt("feel"),rs.getInt("id")));
                System.out.println(rs.getString("data"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public double refine_day()
    {
        double n=0;
        int ct1=0;
        String data=null;
        data = String.format("select * from java.day");
        System.out.println(data);
        float sum,ct;
        String m;
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(data);
            while(rs.next()) {
                sum=0;
                ct=0;
                ct1++;
                m=rs.getString("idDay");
                data = String.format("select * from java.actions_raw where idDay='%s';",m);
                Statement stmt1 = null;
                try {
                    stmt1 = c.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(data);
                    while(rs1.next()) {
                        sum+=rs1.getInt("feel");
                        ct++;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if(ct==0)
                {
                    sum=2;
                    ct=1;
                }
                PreparedStatement stmt2 = null;
                data = String.format("update java.day set mood=%d where idDay='%s';",Math.round(sum/ct),m);
                n+=sum/ct;
                try {
                    stmt2 = c.prepareStatement(data);
                    stmt2.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n/ct1;
    }
    public void refine_all()
    {
        ArrayList<Entry> all=new ArrayList<>();
        String data=null,data1=null;
        System.out.println(data);
        float sum,ct;
        Statement stmt = null;
        PreparedStatement stmt1 = null;
        data = String.format("delete from java.actions_refined;");
            System.out.println(data);
            try {
                stmt1 = c.prepareStatement(data);
                stmt1.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        data = String.format("select * from java.actions_raw");
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(data);
            while(rs.next()) {
                all.add(new Entry(rs.getString("data"),rs.getInt("idDay"),rs.getInt("feel"),rs.getInt("id")));
            }
            for(int i=0;i<all.size();i++)
            {
                sum=all.get(i).feel;
                ct=1;
                for(int j=i+1;j<all.size();j++)
                {
                    if(all.get(j).get_text().equals(all.get(i).get_text())) {
                        sum += all.get(j).feel;
                        ct++;
                        all.remove(j);
                        j--;
                    }
                }
                data = String.format("insert into actions_refined(data,feel,occurances) values('%s',%d,%d);",all.get(i).get_text(),Math.round(sum/ct),(int)ct);
                System.out.println(data);
                try {
                    stmt1 = c.prepareStatement(data);
                    stmt1.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                //all.remove(i);
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
            data = String.format("delete from java.actions_raw where idDay=%d and id='%s';", aux.get(i).idDate, aux.get(i).id);
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
        data = String.format("insert into actions_raw(data,idDay,feel) values('%s',%d,%d);",p.text,p.idDate,p.feel);
        System.out.println(data);
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement(data);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void most(ArrayList<Entry> aux, int flag)
    {
        String data=null;
        if(flag==0)
            data = String.format("select * from actions_refined order by feel asc, occurances desc;");
        else
        if(flag<0)
            data = String.format("select * from actions_refined order by feel asc, occurances desc limit %d;",-flag);
        else
            data = String.format("select * from actions_refined order by feel desc, occurances desc limit %d;",flag);
        System.out.println(data);
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(data);
            while(rs.next()) {
                aux.add(new Entry(rs.getString("data"),0,rs.getInt("feel"), rs.getInt("occurances")));
                System.out.println(rs.getString("data"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int day_number(ArrayList<Day> daaay)
    {
        int n=0;
        String data=null;
        data = String.format("select * from java.day");
        System.out.println(data);
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(data);
            while(rs.next())
                daaay.add(new Day(rs.getInt("idDay"),rs.getInt("mood")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n;
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
