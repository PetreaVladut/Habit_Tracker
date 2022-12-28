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
    void menu2()
    {
        clear();

        update();
    }
    public void menu1()
    {
        label1=new JLabel("WELCOME TO HHABIT TRACKER");
        label1.setFont(new Font("Serif", Font.PLAIN, 23));
        label1.setBounds(x/8,y/4,500,40);
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
