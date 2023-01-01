package tom.game;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    private Game game;

    public static void main(String[] args) {
        GamePanel game = new GamePanel(20, new StateController(null, 800, 600));
        game.run("HELLO");
        // game.runFullScreenFrame();
    }

    public void run(String title) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame(title);
        frame.setContentPane(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
//        frame.setUndecorated(true);
//        frame.setOpacity(0.5f);
        frame.setVisible(true);
        frame.setTitle(title);
        frame.pack();
        game.start();
    }

    @Deprecated
    public void runFrame(String title) {
        this.run(title);
    }

    public GamePanel(int fps, StateController control) {
        this.setOpaque(false);
        this.setSize(control.getWidth(), control.getHeight());
        this.setPreferredSize(this.getSize());
        this.setIgnoreRepaint(true);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        //this.setBackground(new Color(0,0,0,125));
        this.requestFocus();
        // this.requestFocusInWindow();
        game = new Game(fps, this, control);
    }

    @Override
    public void paintComponent(Graphics g) {
        game.paint(g);
    }

}
