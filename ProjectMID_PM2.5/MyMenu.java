import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//สร้าง Panel || Card
class MyMenu{
    private static CardLayout cl = new CardLayout();
    private static JPanel panelCont = new JPanel(); // The main Panel use for stack other Panel
    private JPanel blankBox = new JPanel(); // a panel that contain MENU GUI Component

    private Label blank1 = new Label(""); // as name said THESE'RE BLANK PANEL Using with border to make the component keep on the box
    private Label blank2 = new Label("");
    private Label blank3 = new Label("");
    private Label blank4 = new Label("");

    private static Frame frame;
    private static MyMenu menuObj = new MyMenu();

    private JButton butStart = new JButton("Start");// these are buttons
    private JButton butCredit = new JButton("Credit");
    private JButton butExit = new JButton("Exit");

    //หน้า Menu แรกเริ่ม
    MyMenu() {
        // Set the Panel Layout into CardLayout
        panelCont.setLayout(cl);
        
        blankBox.setLayout(new BorderLayout(250, 200));
        blankBox.add(blank1, BorderLayout.WEST);
        blankBox.add(blank2, BorderLayout.EAST);
        blankBox.add(blank3, BorderLayout.SOUTH);
        blankBox.add(blank4, BorderLayout.NORTH);
        blankBox.add(menuPanel(), BorderLayout.CENTER);
        blankBox.setBackground(new Color(71, 57, 115));

        //add Panel เข้า Panel Container โดยแค่ละ Panel จะมี ID ที่กำกับไว้โดยชนิดข้อมูล String
        panelCont.add(blankBox, "menu");
        panelCont.add(Credit.getPanelCredit(), "credit");
        panelCont.add(PM25Frame.getStart(), "start");

        //แสดงข้อมูลของ Card โดยการเรียก Object ของ card และใช้ Method show Parameter ใส่ (Panel Container หรือ Panel ที่เก็บ Panel อื่นๆ ไว้ ตามด้วย ID String)
        cl.show(panelCont, "menu");

    }

    //this method just to make the Constructor work
    public static MyMenu getClassMyMenu(){
        return menuObj;
    }

    public static Frame getFrame(){
        return frame;
    }

    //Method ใช้ส่งข้อมูล Panel ให้ Class ภายนอก
    public static JPanel getPanelMenu() {
        return panelCont;
    }

    public static CardLayout getCardLayout() {
        return cl;
    }

    //สร้างหน้า Menu
    JPanel menuPanel() {
        // MenuLayout gonna be main container Panel
        JPanel menuLayout = new JPanel();
        menuLayout.setLayout(new GridLayout(4, 1));
        menuLayout.setBackground(new Color(71, 57, 115));

        JPanel titleSpecificPanel = new JPanel();
        titleSpecificPanel.setLayout(new FlowLayout(1));
        titleSpecificPanel.setBackground(new Color(71, 57, 115));

        JLabel title = new JLabel("PM2.5");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(239,178,97));

        //These're button action for switching each panel also Exit from the program
        butStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "start");
            }
        });

        butCredit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "credit");
            }
        });

        butExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        titleSpecificPanel.add(title);
        menuLayout.add(titleSpecificPanel);
        menuLayout.add(butStart);
        menuLayout.add(butCredit);
        menuLayout.add(butExit);

        return menuLayout;
    }
}
