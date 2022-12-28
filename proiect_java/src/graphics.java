import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class graphics extends Thread {
    JFrame f=new JFrame();
    int variable=0,y=500,x=700;
    JComponent panel;
    boolean flag=true;
    public void end()
    {
        flag=false;
    }
    ArrayList<Entry> today=new ArrayList<Entry>();
    ArrayList<element> actions=new ArrayList<element>();
    void clear() {
        panel.removeAll();
        panel.revalidate();
        f.repaint(0,0,0,y,x);
        f.setLayout(null);
        f.setVisible(true);
    }
    void update() {
        f.repaint(0,0,0,y,x);
        f.repaint(0,x/2,y/2,y,x);
        f.setLayout(null);
        f.setVisible(true);
    }
    void move(){
        int mod_x,mod_y;
        //Iterator<element> i= actions.iterator();
        for(int j=0;j< today.size();j++)
        {
            mod_x=actions.get(j).getX()+actions.get(j).modifier_x();
            if(mod_x>x)
                mod_x=0;
            if(mod_x<0)
                mod_x=x;
            mod_y=actions.get(j).getY()+actions.get(j).modifier_y();
            if(mod_y>y)
                mod_y=0;
            if(mod_y<0)
                mod_y=y;
            actions.get(j).setBounds(mod_x,mod_y,100,30);
        }
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        update();
    }
    public void run()
    {
        element aux;
        Random ceva=new Random();
        for(int i=0;i< today.size();i++)
        {
            aux=new element(today.get(i).get_text());
            aux.setBounds(ceva.nextInt(20,600),ceva.nextInt(20,400),120,30);
            panel.add(aux);
            actions.add(aux);
        }
        update();
        while(flag)
        move();
        for(int i=0;i< today.size()-1;i++)
        {
            panel.remove(actions.get(i));
        }
        update();
    }
    class element extends JLabel{
        int[] modifier=new int[2];
        public int modifier_x(){return modifier[0];}
        public int modifier_y(){return modifier[1];}
        public element(String text) {
            super(text);
            Random ceva=new Random();
            //here happens the magic
            modifier[0]=ceva.nextInt(-20,20);
            modifier[1]=ceva.nextInt(-20,20);
        }
    }
    public graphics(JFrame frame,    ArrayList<Entry> today)
    {
        f=frame;
        this.today=today;
        panel=(JComponent)f.getContentPane();
    }
}
