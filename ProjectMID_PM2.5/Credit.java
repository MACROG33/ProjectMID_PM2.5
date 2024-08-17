import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

class Credit extends JPanel {
    private GridBagConstraints grid = new GridBagConstraints();
    private Font fontSet = new FontUIResource("TH Krub", Font.PLAIN, 25);
    private Font titleFont = new FontUIResource("TH Krub", Font.BOLD, 50);
    private Font buttonFont = new FontUIResource("TH Krub", Font.BOLD, 20);
    private JButton button = new JButton("Back to menu");
    static Credit panelCredit = new Credit();

    Credit() {
        setLayout(new GridBagLayout());
        grid.insets = new Insets(20, 10, 20, 10);
        setBackground(new Color(71, 57, 115));

        // Call to Method
        setImage();
        setName();
        setButton();
    }

    public static JPanel getPanelCredit() {
        return panelCredit;
    }

    private void setImage() {
        ImageIcon icon1 = new ImageIcon(
                "image\\Din.jpg");
        ImageIcon icon2 = new ImageIcon(
                "image\\Palm.jpg");
        ImageIcon icon3 = new ImageIcon("image\\D.jpg");

        Image img1 = icon1.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        Image img2 = icon2.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        Image img3 = icon3.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);

        JLabel imageLabel1 = new JLabel(new ImageIcon(img1));
        JLabel imageLabel2 = new JLabel(new ImageIcon(img2));
        JLabel imageLabel3 = new JLabel(new ImageIcon(img3));

        // Add first image to panel and set position
        grid.gridy = 1;
        grid.gridx = 1;
        
        add(imageLabel1, grid);

        // Add second image to panel and set position
        grid.gridx = 2;
        grid.gridy = 1;
        
        add(imageLabel2, grid);

        // Add third image to panel and set position
        grid.gridx = 3;
        grid.gridy = 1;
        
        add(imageLabel3, grid);
    }

    private void setName() {
        JLabel name_1 = new JLabel("ชื่อ: นายหินดินเพชร อโศกตระกูล 66011212145 CS");
        name_1.setFont(fontSet);

        JLabel name_2 = new JLabel("ชื่อ: นายกฤษณพล สุขดี 66011212003 CS");
        name_2.setFont(fontSet);

        JLabel name_3 = new JLabel("ชื่อ: นายวีรพล เจิมสูงเนิน รหัสนิสิต: 65011212205 CS");
        name_3.setFont(fontSet);

        JLabel title = new JLabel("ผู้จัดทำ");
        title.setFont(titleFont);

        // Add title label to the panel and set position
        grid.gridx = 2;
        grid.gridy = 0;
        
        title.setForeground(Color.WHITE);
        add(title, grid);

        // Add names to panel and set position below images
        grid.gridx = 1;
        grid.gridy = 2; 
        grid.weightx = 1;
        
        
        name_1.setForeground(Color.WHITE);
        add(name_1, grid);

        grid.gridx = 2;
        grid.gridy = 2;
        grid.weightx = 1;
        name_2.setForeground(Color.WHITE);
        add(name_2, grid);

        grid.gridx = 3;
        grid.gridy = 2;
        grid.weightx = 1;
        
        name_3.setForeground(Color.WHITE);
        add(name_3, grid);
    }

    private void setButton() {
        button.setFont(buttonFont); // Set the font of the button
        button.setBackground(new Color(253, 165, 15));
        button.setForeground(Color.WHITE);
        grid.gridx = 2; 
        grid.gridy = 3; 
        add(button, grid);

        // Add action listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch back to the menu panel
                ((CardLayout) getParent().getLayout()).show(getParent(), "menu");
            }
        });
    }
}
