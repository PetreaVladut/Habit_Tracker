import javaswingdev.chart.ModelPieChart;
import javaswingdev.chart.PieChart;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    JButton buton5;
    JButton buton6;
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
    JPanel panel2;
    double avg;
    ArrayList<Entry> today=new ArrayList<Entry>();
    String[] stari={"very bad","bad","neutral","good","very good"};
    Color[] culoare={Color.BLUE,Color.green,Color.yellow,Color.orange,Color.RED};
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
    void menu4()
    {
        clear();
        avg=q.refine_day();
        q.refine_all();
        ArrayList<Entry> highest=new ArrayList<>();
        ArrayList<Entry> lowest=new ArrayList<>();
        ArrayList<Day> daaay=new ArrayList<>();
        q.day_number(daaay);
        JPanel aux11=new JPanel();
        aux11.setBounds(50,40,200,120);
        TitledBorder border = BorderFactory.createTitledBorder("Lowest 5");
        aux11.setBorder(border);
        aux11.setLayout(new BoxLayout(aux11,BoxLayout.Y_AXIS));
        JPanel aux21=new JPanel();
        aux21.setBounds(50,180,200,120);
        TitledBorder border1 = BorderFactory.createTitledBorder("Highest 5");
        aux21.setBorder(border1);
        aux21.setLayout(new BoxLayout(aux21,BoxLayout.Y_AXIS));
        q.most(highest,5);
        q.most(lowest,-5);
        JLabel[] labell=new JLabel[5];
        JLabel[] labelh=new JLabel[5];
        ArrayList<Entry> all=new ArrayList<>();
        int[] a={0,0,0,0,0};
        q.most(all,0);
        PieChart pieChart1 = new PieChart();
        pieChart1.setChartType(PieChart.PeiChartType.DONUT_CHART);
        Entry mx=new Entry(null,0,0,0);
        for(int i=0;i<all.size();i++)
        {
            if(mx.get_id()<all.get(i).get_id())
                mx=all.get(i);
            a[all.get(i).get_feel()]+=all.get(i).get_id();
        }
        for(int i=0;i<5;i++)
        pieChart1.addData(new ModelPieChart(stari[i], a[i], culoare[i]));
        panel.add(pieChart1);
        pieChart1.setBounds(300,80,300,300);
        for(int i=0;i<5;i++)
        {
            labell[i]=new JLabel(String.format("%s(%d)",lowest.get(i).get_text(),lowest.get(i).get_feel()));
            aux11.add(labell[i]);
            labelh[i]=new JLabel(String.format("%s(%d)",highest.get(i).get_text(),highest.get(i).get_feel()));
            aux21.add(labelh[i]);
        }
        label1=new JLabel(String.format("Your daily avg is %d",Math.round(avg)));
        label1.setBounds(80,330,120,30);
        label2=new JLabel(String.format("Your most recurrent activity is %s(%d)",mx.get_text(),mx.get_id()));
        label2.setBounds(80,360,220,30);
        panel.add(label1);
        panel.add(label2);
        panel.add(aux11);
        panel.add(aux21);
        buton3=new JButton("Inapoi");
        buton3.setBounds(x-140,20,100,30);
        buton3.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e){menu2();
                    }
                }
        );
        panel.add(buton3);
        update();
    }
    void menu3(int k,int u)
    {
        clear();
        ArrayList<Entry> specific=new ArrayList<Entry>();
        q.day_query(specific,k,1);
        if(specific.size()<1)
            label1=new JLabel("Nu exista inregistrari");
        else {
            int d= specific.size()/10+1;
            int l=10;
            if(u<d-1)
                l=10;
            else l=specific.size()%10;
            JButton[] actiune=new JButton[specific.size()];
                for (int i = 0; i < l; i++) {
                    actiune[i] = new JButton(specific.get(10*u+i).text);
                    actiune[i].setBackground(culoare[specific.get(10*u+i).feel]);
                    actiune[i].setBounds(40, i * 30 + 40, 300, 25);
                    panel.add(actiune[i]);
                    int finalI = i;
                    actiune[i].addActionListener(
                            new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    if (actiune[finalI].getBackground() != Color.GRAY)
                                        actiune[finalI].setBackground(Color.GRAY);
                                    else
                                        actiune[finalI].setBackground(culoare[specific.get(10*u+finalI).feel]);
                                }
                            }
                    );
                }
            buton1=new JButton("Delete");
            buton1.setBounds(6*x/8,6*y/8,60,30);
            label2=new JLabel("Action");
            label2.setBounds(3*x/8+10,5*y/8+30,100,30);
            text1=new JTextField("");
            text1.setBounds(3*x/8,6*y/8,150,30);
            label1=new JLabel("Mood");
            label1.setBounds(2*x/8,5*y/8+30,100,20);
            text2=new JTextField("");
            text2.setBounds(2*x/8,6*y/8,50,30);
            buton2=new JButton("Add");
            buton2.setBounds(5*x/8,6*y/8,60,30);
            panel.add(text1);
            panel.add(label1);
            panel.add(label2);
            panel.add(buton1);
            panel.add(buton2);
            panel.add(text2);
            buton2.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(!text1.getText().isEmpty()&&!text2.getText().isEmpty()) {
                                Entry p = new Entry(text1.getText(), k, Integer.parseInt(text2.getText()));
                                q.insert_entry(p);
                                menu3(k, 0);
                            }
                        }
                    }
            );
            int finalL = l;
            buton1.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            ArrayList<Entry> remove=new ArrayList<>();
                            for(int i = 0; i < finalL; i++)
                            {
                                if(actiune[i].getBackground()==Color.GRAY)
                                {
                                    remove.add(specific.get(u*10+i));
                                }
                            }
                            q.delete_day(remove);
                            menu3(k,u);
                        }
                    }
            );
            buton4=new JButton("Next");
            buton5=new JButton("Back");
            buton4.setBounds(5*x/8,5*y/8,80,30);
            buton5.setBounds(6*x/8,5*y/8,80,30);
            buton5.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(u>0)
                            menu3(k,u-1);
                            else
                                menu3(k,u);
                        }
                    }
            );
            buton4.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(u<d-1)
                            menu3(k,u+1);
                            else
                                menu3(k,u);
                        }
                    }
            );
            panel.add(buton4);
            panel.add(buton5);
            buton6=new JButton("Statistics");
            buton6.setBounds(x-140,60,100,30);
            buton6.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            menu4();
                        }
                    }
            );
            buton3=new JButton("Inapoi");
            buton3.setBounds(x-140,20,100,30);
            buton3.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e){menu2();
                        }
                    }
            );
            panel.add(buton3);
            panel.add(buton6);
            update();
        }
    }
    void menu2()//this does not execute properly TB fixed
    {
        clear();
        ArrayList<Day> daaay=new ArrayList<>();
        q.day_number(daaay);
        int n=daaay.size();
        if(n<1)
            label1=new JLabel("Nu exista zile inregistrare");
        else {
            JButton[] calendar=new JButton[n+1];
            for (int i = 1; i <= n; i++)
            {
                calendar[i]=new JButton(String.valueOf(i));
                calendar[i].setBounds(i%7*45+120,i/7*45+80,40,40);
                calendar[i].setBackground(culoare[daaay.get(i-1).getFeel()]);
                panel.add(calendar[i]);
                int finalI = i;
                calendar[i].addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                menu3(finalI,0);
                            }
                        }
                );
            }
            buton1=new JButton("Inapoi");
            buton1.setBounds(x-140,20,100,30);
            buton1.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e){menu1();
                        }
                    }
            );
            panel.add(buton1);
                update();
        }
    }
    public void menu1()
    {
        clear();
        update();
        JRadioButton[] buton11=new JRadioButton[5];
        JPanel aux21=new JPanel();
        aux21.setBackground(Color.WHITE);
        G1=new ButtonGroup();
        for(int i=0;i<5;i++)
        {
            buton11[i]=new JRadioButton();
            buton11[i].setBackground(culoare[i]);
            buton11[i].setText(stari[i]);
            G1.add(buton11[i]);
            aux21.add(buton11[i]);
            int finalI = i;
            buton11[i].addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for(int j=0;j<5;j++)
                            if(!(buton11[j].isSelected())&&buton11[j].getBackground()==Color.GRAY)
                                buton11[j].setBackground(culoare[j]);
                            buton11[finalI].setBackground(Color.GRAY);
                        }
                    }
            );
        }
        panel2=new JPanel();
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
        panel2.setBounds(130,100,400,170);
        panel2.setBackground(Color.WHITE);
        label1=new JLabel("WELCOME TO HABIT TRACKER");
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        label1.setFont(new Font("Serif", Font.PLAIN, 23));
        label2=new JLabel("How are you feeling today?");
        label2.setFont(new Font("Serif", Font.PLAIN, 17));
        label3=new JLabel("What have you done lately?");
        label3.setFont(new Font("Serif", Font.PLAIN, 15));
        buton1=new JButton("Calendar");
        buton1.setBounds(x-140,20,100 ,30);
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
        text2.setBounds(x/4,6*y/8,200,20);
        text2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int d=-1;
                for(int i=0;i<5;i++)
                    if(buton11[i].isSelected())
                      d=i;
                if(e.getKeyChar()==10&&text2.getText()!=null&&d!=-1) {
                    Entry new_act;
                    if(today.size()==0)
                    {
                        new_act = new Entry(text2.getText(), q.insert_day(),d);
                    }
                    else {
                        new_act = new Entry(text2.getText(), today.get(0).get_idDate(),d);
                    }
                    q.insert_entry(new_act);
                    today.add(new_act);
                    text2.setText("");
                    buton11[d].setSelected(false);
                    buton11[d].setBackground(culoare[d]);
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
        panel.add(panel2);
        panel2.add(label1);
        panel2.add(label2);
        panel2.add(label3);
        panel2.add(text2);
        aux21.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel2.add(aux21);
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
