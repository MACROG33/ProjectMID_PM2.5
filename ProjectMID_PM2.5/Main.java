import javax.swing.SwingUtilities;

class Main{
    public static void main(String[] args) {
        Frame myFrame = new Frame();
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                myFrame.setVisible(true);
            }
        });
    }
}