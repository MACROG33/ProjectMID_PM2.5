import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PM25Frame extends JFrame {
    private static int ROWS = 10; // จำนวนแถวของตาราง
    private static int COLS = 20; // จำนวนคอลัมน์ของตาราง
    private static int[][] pmData; // เก็บข้อมูล PM2.5
    private static JTable table; // เก็บ JTable เพื่อการรีเฟรช
    private static int rainCenterX = -1, rainCenterY = -1; // สำหรับเก็บพิกัดคลิก
    private static JPanel panel = new JPanel(new BorderLayout());
    private static int peopleStart;
    private static int peopleLast;
    private static int betweenPeople;
    private static int rand[][];
    private static int healthy[][];
    private static int people[][];
    private static float percentToxin[][];
    private static PM25Frame panelStart = new PM25Frame();
    private static boolean checkValueStart=false;
    private static boolean checkValueLast=false;
    private static String peopleLastStr;
    private static String peopleStartStr;
    
    public PM25Frame() {
        createAndShowGUI();
    }

    // โหลดข้อมูลPM2.5 จากไฟล์
    private static void loadPMData(String filename) {
    // ตรวจสอบว่าไฟล์เป็น .txt หรือไม่
    if (!filename.toLowerCase().endsWith(".txt")) {
        JOptionPane.showMessageDialog(null, "Error, You could only choose .txt file", "ERROR", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        int row = 0;
        while ((line = br.readLine()) != null) {
            if (row == 0) {
                COLS = line.split("\t").length; // จำนวนคอลัมน์จากข้อมูลบรรทัดแรกของไฟล์
            }
            row++;
        }
        ROWS = row; // กำหนดจำนวนแถวจากจำนวนข้อมูลทั้งหมดในไฟล์
        pmData = new int[ROWS][COLS]; // สร้างอาร์เรย์ไว้ใช้เก็บข้อมูล PM2.5

        //ตัวแปร ที่สัมพันธ์กับ Method ProcessData
        rand = new int[ROWS][COLS];
        healthy = new int[ROWS][COLS];
        people = new int[ROWS][COLS];
        percentToxin = new float[ROWS][COLS];

        row = 0;
        BufferedReader br2 = new BufferedReader(new FileReader(filename));
        while ((line = br2.readLine()) != null) {
            String[] values = line.split("\t");
            for (int col = 0; col < values.length && col < COLS; col++) {
                pmData[row][col] = Integer.parseInt(values[col]); // เก็บข้อมูล PM2.5 ลงในอาร์เรย์
            }
            row++;
        }
        br2.close();
    } catch (IOException e) {
        e.printStackTrace(); // แสดงข้อผิดพลาดการอ่านไฟล์
    }
}


    void processData() {
        int startToxin; // ตัวแปรค่าเริ่มต้นผู้ป่วย
        double maxToxin; // ตัวแปรค่าสูงสุดของผู้ป่วย
        double betweenToxin; //ค่าความต่างระหว่าง startToxin and maxToxin
        betweenPeople = peopleLast - peopleStart; //ค่าความต่างระหว่าง PeopleLast adnd PeopleStart
        for (int i = 0; i < pmData.length; i++) {
            for (int k = 0; k < pmData[i].length; k++) {
                if ((int) (Math.random() * 100) + 1 <= 10) {
                    pmData[i][k] = (int) (Math.random() * 250) + 0;
                }
            }
        }

        for (int i = 0; i < pmData.length; i++) {
            for (int k = 0; k < pmData[i].length; k++) {
                people[i][k] = (int) (Math.random() * betweenPeople) + peopleStart;
                if (pmData[i][k] <= 50 && pmData[i][k] >= 0) {

                    maxToxin = people[i][k] * 0.09;
                    rand[i][k] = (int) (Math.random() * maxToxin) + 0;
                    healthy[i][k] = people[i][k] - rand[i][k];
                    percentToxin[i][k] = (float) rand[i][k] / people[i][k] * 100;
                }

                else if (pmData[i][k] <= 101) {

                    maxToxin = people[i][k] * 0.19;
                    startToxin = people[i][k] * 10 / 100;
                    betweenToxin = maxToxin - startToxin;
                    rand[i][k] = (int) (Math.random() * betweenToxin) + startToxin;
                    healthy[i][k] = people[i][k] - rand[i][k];
                    percentToxin[i][k] = (float) rand[i][k] / people[i][k] * 100;
                }

                else if (pmData[i][k] <= 151) {

                    maxToxin = people[i][k] * 0.29;
                    startToxin = people[i][k] * 20 / 100;
                    betweenToxin = maxToxin - startToxin;
                    rand[i][k] = (int) (Math.random() * betweenToxin) + startToxin;
                    healthy[i][k] = people[i][k] - rand[i][k];
                    percentToxin[i][k] = (float) rand[i][k] / people[i][k] * 100;
                }

                else if (pmData[i][k] <= 250) {

                    maxToxin = people[i][k] * 0.50;
                    startToxin = people[i][k] * 30 / 100;
                    betweenToxin = maxToxin - startToxin;
                    rand[i][k] = (int) (Math.random() * betweenToxin) + startToxin;
                    healthy[i][k] = people[i][k] - rand[i][k];
                    percentToxin[i][k] = (float) rand[i][k] / people[i][k] * 100;
                } else {
                    continue;
                }
            }
        }

    }

    // รับข้อมูล Panel ของ Class นี้
    public static JPanel getStart() {
        return panel;
    }

    // สร้างและแสดง GUI
    private void createAndShowGUI() {

        panel.setBackground(new Color(71, 57, 115)); // ****เปลี่ยนสีพื้นหลัง****
        add(panel, BorderLayout.CENTER);

        // สร้างตาราง
        DefaultTableModel tableModel = new DefaultTableModel(ROWS, COLS) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return Object.class;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // ตั้งค่าฟอนต์ของตาราง
        table.setRowHeight(100); // ความสูงของแถว
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // สร้างแผงล่างของปุ่มและช่องข้อความ
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(71, 57, 115)); // ****สีพื้นหลังที่จะใช้****
        bottomPanel.setLayout(null);
        bottomPanel.setPreferredSize(new Dimension(1200, 130)); // ความสูงของแผงล่าง

        JTextField filePathField = new JTextField();
        filePathField.setBounds(150, 30, 200, 30); // ตำแหน่งและขนาดของช่องข้อความ
        bottomPanel.add(filePathField);

        JButton selectFileButton = new JButton("Select File");
        selectFileButton.setBounds(360, 30, 100, 30); // ตำแหน่งและขนาดของปุ่ม**เลือกไฟล์**
        selectFileButton.setFont(new Font("Arial", Font.PLAIN, 14)); // ฟอนต์ของปุ่ม
        selectFileButton.setMargin(new Insets(0, 0, 0, 0)); // ขนาดขอบของปุ่ม
        bottomPanel.add(selectFileButton);

        JButton okButton = new JButton("OK");
        okButton.setBounds(470, 30, 100, 30); // ตำแหน่งและขนาดของปุ่ม**ตกลง**
        okButton.setFont(new Font("Arial", Font.PLAIN, 14));
        okButton.setMargin(new Insets(0, 0, 0, 0));
        bottomPanel.add(okButton);

        JButton rainButton = new JButton("Rain");
        rainButton.setBounds(580, 30, 100, 30); // ตำแหน่งและขนาดของปุ่ม**ฝนตกธรรมชาติ**
        rainButton.setFont(new Font("Arial", Font.PLAIN, 14));
        rainButton.setMargin(new Insets(0, 0, 0, 0));
        bottomPanel.add(rainButton);

        JButton artificialRainButton = new JButton("Artificial Rain");
        artificialRainButton.setBounds(690, 30, 150, 30); // ตำแหน่งและขนาดของปุ่ม**ฝนเทียม**
        artificialRainButton.setFont(new Font("Arial", Font.PLAIN, 14));
        artificialRainButton.setMargin(new Insets(0, 0, 0, 0));
        bottomPanel.add(artificialRainButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(30, 30, 100, 30); // ตำแหน่งและขนาดของปุ่ม**ย้อนกลับ**
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setMargin(new Insets(0, 0, 0, 0));
        bottomPanel.add(backButton);

        JTextField peopleField = new JTextField();
        peopleField.setBounds(1000, 30, 200, 30); // ตำแหน่งและขนาดของช่องข้อความ
        bottomPanel.add(peopleField);
        peopleField.setText("ป้อนจำนวนประชากร");
        peopleField.setFont(new Font("TH Krub", Font.PLAIN, 20));
        

        JButton submitStartButton = new JButton("Submit as start");
        submitStartButton.setBounds(1210, 30, 100, 30); // ตำแหน่งและขนาดของปุ่ม**ย้อนกลับ**
        submitStartButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitStartButton.setMargin(new Insets(0, 0, 0, 0));
        submitStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    peopleStartStr = peopleField.getText();
                    peopleField.setText("");
                    
                    checkValueStart = true;
                    if (checkValueStart&&checkValueLast) {
                        peopleStart = Integer.parseInt(peopleStartStr);// Convert input to integer
                        peopleLast = Integer.parseInt(peopleLastStr);
                        if(peopleLast>=peopleStart){
                            JOptionPane.showMessageDialog(MyMenu.getFrame(), "Input Success", "Mention", JOptionPane.INFORMATION_MESSAGE);
                            checkValueStart = false;
                            checkValueLast = false;
                            
                        }

                        else{
                            JOptionPane.showMessageDialog(MyMenu.getFrame(), "ERROR, Please try again", "Input Error", JOptionPane.ERROR_MESSAGE);
                            peopleStart = 0;
                            peopleLast = 0;
                            checkValueStart = false;
                            checkValueLast = false;
                        }
                    }
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MyMenu.getFrame(), "Please input the valid value", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        bottomPanel.add(submitStartButton);

        JButton submitLastButton = new JButton("Submit as last");
        submitLastButton.setBounds(1320, 30, 100, 30); // ตำแหน่งและขนาดของปุ่ม**ย้อนกลับ**
        submitLastButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitLastButton.setMargin(new Insets(0, 0, 0, 0));
        submitLastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    peopleLastStr = peopleField.getText();
                    peopleField.setText("");
                    checkValueLast = true;
                    if (checkValueStart&&checkValueLast) {
                        peopleStart = Integer.parseInt(peopleStartStr);// Convert input to integer
                        peopleLast = Integer.parseInt(peopleLastStr);
                        if(peopleLast>=peopleStart){
                            JOptionPane.showMessageDialog(MyMenu.getFrame(), "Input Success", "Mention", JOptionPane.INFORMATION_MESSAGE);
                            checkValueLast = false;
                            checkValueStart = false;
                        }
                        else{
                            JOptionPane.showMessageDialog(MyMenu.getFrame(), "Please input the value as Last value greater than Start value", "Input Error", JOptionPane.ERROR_MESSAGE);
                            peopleStart = 0;
                            peopleLast = 0;
                            checkValueStart = false;
                            checkValueLast = false;
                        }
                    }
                    
                } 
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MyMenu.getFrame(), "Please input the valid value", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });
        bottomPanel.add(submitLastButton);

        JLabel detailColorRed = new JLabel();
        detailColorRed.setText("= มีคนป่วย > 30%"); 
        detailColorRed.setBounds(185, 75,150,30);
        detailColorRed.setFont(new Font("TH Krub", Font.PLAIN, 20));
        detailColorRed.setForeground(Color.WHITE);
        JPanel redBlock = new JPanel();
        redBlock.setBounds(150,75,30,30);
        redBlock.setBackground(Color.RED);
        bottomPanel.add(redBlock);
        bottomPanel.add(detailColorRed);

        JLabel detailColorOrage = new JLabel();
        detailColorOrage.setText("= มีคนป่วย 20-29%"); 
        detailColorOrage.setBounds(360, 75,150,30);
        detailColorOrage.setFont(new Font("TH Krub", Font.PLAIN, 20));
        detailColorOrage.setForeground(Color.WHITE);
        JPanel orangeBlock = new JPanel();
        orangeBlock.setBounds(325,75,30,30);
        orangeBlock.setBackground(Color.ORANGE);
        bottomPanel.add(orangeBlock);
        bottomPanel.add(detailColorOrage);
        
        JLabel detailColorYellow = new JLabel();
        detailColorYellow.setText("= มีคนป่วย 10-19%"); 
        detailColorYellow.setBounds(535, 75,150,30);
        detailColorYellow.setFont(new Font("TH Krub", Font.PLAIN, 20));
        detailColorYellow.setForeground(Color.WHITE);
        JPanel yellowBlock = new JPanel();
        yellowBlock.setBounds(500,75,30,30);
        yellowBlock.setBackground(Color.YELLOW);
        bottomPanel.add(yellowBlock);
        bottomPanel.add(detailColorYellow);

        JLabel detailColorGreen = new JLabel();
        detailColorGreen.setText("= มีคนป่วย 0-9%"); 
        detailColorGreen.setBounds(710, 75,150,30);
        detailColorGreen.setFont(new Font("TH Krub", Font.PLAIN, 20));
        detailColorGreen.setForeground(Color.WHITE);
        JPanel greenBlock = new JPanel();
        greenBlock.setBounds(675,75,30,30);
        greenBlock.setBackground(Color.GREEN);
        bottomPanel.add(greenBlock);
        bottomPanel.add(detailColorGreen);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // เลือกไฟล์เมื่อกดปุ่ม "Select File"
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(PM25Frame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // โหลดข้อมูลเมื่อกดปุ่ม "OK"
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = filePathField.getText();
                loadPMData(filePath);
                processData();
                updateTable(tableModel);
            }
        });

        // จำลองฝนตกเมื่อกดปุ่ม "Rain"
        rainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateRain();
                updateTable(tableModel);
            }
        });

        // จำลองฝนเทียมเมื่อกดปุ่ม "Artificial Rain"
        artificialRainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rainCenterX != -1 && rainCenterY != -1) {
                    simulateArtificialRain(rainCenterX, rainCenterY);
                    updateTable(tableModel);
                }
            }
        });

        // รีเซ็ตตารางเมื่อกดปุ่ม "Back" 
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // รีเซ็ตข้อมูล PM
                pmData = new int[ROWS][COLS];

                // ล้างเส้นทางไฟล์
                filePathField.setText("");

                // รีเซ็ตแถวและคอลัมน์ของตาราง
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(ROWS);
                model.setColumnCount(COLS);

                // อัปเดตตารางให้เป็นตารางโล่งๆ
                updateTable(model);
                MyMenu.getCardLayout().show(MyMenu.getPanelMenu(), "menu");
            }
        });

        // เพิ่ม MouseListener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());
                if (row >= 0 && column >= 0) {
                    rainCenterX = row;
                    rainCenterY = column;
                }

                String message = "Dust: " + pmData[row][column] + "\n" +
                        "People: " + people[row][column] + "\n" +
                        "Healthy: " + healthy[row][column] + "\n" +
                        "Sick: " + rand[row][column] + "\n" +
                        "Percent Toxin: " + Math.round(percentToxin[row][column]) + "%\n";

                JOptionPane.showMessageDialog(MyMenu.getFrame(), message, "Information",
                        JOptionPane.INFORMATION_MESSAGE, getEmojiForPMValuePath(pmData[row][column]));

            }
        });
    }

    // ใส่Emoji แสดงค่าต่างๆ ตามปริมาณฝุ่น
    private static ImageIcon getEmojiForPMValuePath(int pmValue) {

        String imagePath;

        if (pmValue <= 50) {
            imagePath = "image\\Green.png";
        } else if (pmValue <= 101) {
            imagePath = "image\\yellow.png";
        } else if (pmValue <= 151) {
            imagePath = "image\\Orange.png";
        } else {
            imagePath = "image\\red.png";
        }
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image img = originalIcon.getImage(); // รับภาพจาก ImageIcon
        Image resizedImage = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // ปรับขนาดภาพ
        return new ImageIcon(resizedImage); // สร้าง ImageIcon ใหม่ที่มีขนาดที่ปรับแล้ว

    }

    // อัปเดตตารางด้วยข้อมูล PM
    private void updateTable(DefaultTableModel tableModel) {
        // รีเซ็ตสีพื้นหลังของเซลล์ทั้งหมด
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                tableModel.setValueAt("", i, j); // เคลียร์ค่าที่มีอยู่
            }
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int pmValue = pmData != null && i < pmData.length && j < pmData[i].length ? pmData[i][j] : 0;
                String cellContent = String.format("PM: %d", pmValue);
                tableModel.setValueAt(cellContent, i, j);
            }
        }

        // รีเฟรชการแสดงผลของตาราง
        table.repaint();
    }

    // จำลองฝนตกทั่วเมือง
    private void simulateRain() {
        if (pmData == null)
            return;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                pmData[i][j] = Math.max(0, pmData[i][j] - 50); // ลดค่า PM2.5 ลง 50% ต่อการกดแต่ละครั้ง
            }
        }
    }

    // จำลองฝนเทียม ***bug***
    private void simulateArtificialRain(int x, int y) {
        if (pmData == null)
            return;
        // ลดค่าจุดที่คลิกลง 50%
        if (x >= 0 && x < ROWS && y >= 0 && y < COLS) {
            pmData[x][y] = (int) (pmData[x][y] * 0.5);
        }
        // ลดค่าที่เซลล์รอบๆลง 30%
        int[] dRow = { -1, 1, 0, 0, -1, -1, 1, 1 };
        int[] dCol = { 0, 0, -1, 1, -1, 1, -1, 1 };
        for (int i = 0; i < 8; i++) {
            int newX = x + dRow[i];
            int newY = y + dCol[i];
            if (newX >= 0 && newX < ROWS && newY >= 0 && newY < COLS) {
                pmData[newX][newY] = (int) (pmData[newX][newY] * 0.7);
            }
        }
    }
    // จำนวนประชากร ***ประชากร****

    // กำหนดสีตามค่า PM
    private static Color getColorForPMValue(int pmValue) {
        if (pmValue <= 50) {
            return Color.GREEN; // PM ต่ำ
        } else if (pmValue <= 101) {
            return Color.YELLOW; // PM กลาง
        } else if (pmValue <= 151) {
            return Color.ORANGE; // PM สูง
        } else {
            return Color.RED; // PM > 150
        }
    }

    // กำหนดวิธีการแสดงผลของเซลล์ใน JTable ของ Java Swing
    static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (pmData != null && row < pmData.length && column < pmData[row].length) {
                c.setBackground(getColorForPMValue(pmData[row][column]));
            } else {
                c.setBackground(Color.WHITE); // ล้างพื้นหลังให้เป็นสีขาวเมื่อไม่มีข้อมูล
            }
            return c;
        }
    }


}

