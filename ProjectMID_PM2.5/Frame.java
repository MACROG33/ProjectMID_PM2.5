import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

//สร้าง Frame
class Frame extends JFrame implements WindowListener {

    Frame() {
        setTitle("PM 2.5 Simulation");
        setBounds(50, 50, 950, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        //เพิ่ม Panel ต่างๆ ที่เก็บ Panel อื่นๆ ไว้เข้ามาใน Frame
        add(MyMenu.getPanelMenu(), BorderLayout.CENTER);
    }

    
    @Override
    public void windowOpened(WindowEvent e) {
        //this method use for auto open menu panel
    }

    @Override
    public void windowClosing(WindowEvent e) {
        MyMenu.getCardLayout().show(MyMenu.getPanelMenu(), "menu");
        
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
        MyMenu.getCardLayout().show(MyMenu.getPanelMenu(), "menu");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'windowIconified'");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'windowDeiconified'");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'windowActivated'");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'windowDeactivated'");
    }
}










