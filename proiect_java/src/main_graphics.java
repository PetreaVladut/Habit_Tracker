import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class main_graphics{
    int variable=0,y=500,x=700;
    JFrame f=new JFrame();
    Query q=new Query();
    JButton buton1;
    JButton buton2;
    JButton buton3;
    JButton buton4;
    JTextField text1;
    JTextField text2;
    JTextField text3;
    JTextField text4;
    JDialog dialog;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JComponent panel;
    JRadioButton radio1;
    JRadioButton radio2;
    ButtonGroup G1;
    ArrayList<Entry> today=new ArrayList<Entry>();
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
    void menu3(int k)
    {
        clear();
        ArrayList<Entry> specific=new ArrayList<Entry>();
        q.day_query(specific,k);
        if(specific.size()<1)
            label1=new JLabel("Nu exista inregistrari");
        else {
            JButton[] actiune=new JButton[specific.size()];
            for (int i = 0; i < specific.size(); i++)
            {
                actiune[i]=new JButton(specific.get(i).text);
                actiune[i].setBackground(Color.CYAN);
                actiune[i].setBounds(40,i*35+40,150,25);
                panel.add(actiune[i]);
                int finalI = i;
                actiune[i].addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if(actiune[finalI].getBackground()==Color.CYAN)
                                    actiune[finalI].setBackground(Color.GRAY);
                                else
                                    actiune[finalI].setBackground(Color.CYAN);
                            }
                        }
                );
            }
            buton1=new JButton("Delete");
            buton1.setBounds(6*x/8,6*y/8,60,30);
            text1=new JTextField("");
            text1.setBounds(3*x/8,6*y/8,150,30);
            buton2=new JButton("Add");
            buton2.setBounds(5*x/8,6*y/8,60,30);
            panel.add(text1);
            panel.add(buton1);
            panel.add(buton2);
            buton2.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Entry p=new Entry(text1.getText(),k);
                            q.insert_entry(p);
                            menu3(k);
                        }
                    }
            );
            buton1.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            ArrayList<Entry> remove=new ArrayList<>();
                            for(int i = 0; i < specific.size(); i++)
                            {
                                if(actiune[i].getBackground()==Color.GRAY)
                                {
                                    remove.add(specific.get(i));
                                }
                            }
                            q.delete_day(remove);
                            menu3(k);
                        }
                    }
            );
            update();
        }
    }
    void menu2()//this does not execute properly TB fixed
    {
        clear();
        if(today.size()<1)
            label1=new JLabel("Nu exista zile inregistrare");
        else {
            JButton[] calendar=new JButton[today.size()];
            for (int i = 1; i < today.size(); i++)
            {
                calendar[i]=new JButton(String.valueOf(i));
                calendar[i].setBounds(i%7*45+180,i/7*45+80,40,40);
                panel.add(calendar[i]);
                int finalI = i;
                calendar[i].addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                menu3(finalI);
                            }
                        }
                );
            }
                update();
        }
    }
    public void menu1()
    {
        label1=new JLabel("WELCOME TO HABIT TRACKER");
        label1.setFont(new Font("Serif", Font.PLAIN, 23));
        label1.setBounds(x/8,y/4-50,500,40);
        label2=new JLabel("How are you feeling today?");
        label2.setFont(new Font("Serif", Font.PLAIN, 17));
        label2.setBounds(x/8,y/4,500,40);
        buton1=new JButton("Calendar");
        buton1.setBounds(x-60,20,20,20);
        buton1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        menu2();
                    }
                }
        );
        text2=new JTextField("");
        final graphics[] ceva = {new graphics(f, today)};
        ceva[0].start();
        text2.setBounds(x/4,4*y/8,200,20);
        text2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==10&&text2.getText()!=null) {
                    Entry new_act;
                    if(today.size()==0)
                    {
                        new_act = new Entry(text2.getText(), q.insert_day());
                    }
                    else {
                        new_act = new Entry(text2.getText(), today.get(0).get_idDate());
                    }
                    q.insert_entry(new_act);
                    today.add(new_act);
                    text2.setText("");
                    update();
                    ceva[0].end();
                    try {
                        ceva[0].join();
                        ceva[0] =new graphics(f,today);
                        ceva[0].start();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        panel.add(label1);
        panel.add(label2);
        panel.add(text2);
        panel.add(buton1);
        update();
    }
    public main_graphics()
    {
        //frame
        f.setTitle("Habit tracker");
        f.setLayout(new FlowLayout(FlowLayout.CENTER));
        f.setSize(x, y);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        panel=(JComponent)f.getContentPane();
        q.day_query(today, null);
    }
}
