package tom.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

public class StateController implements KeyListener, MouseListener,
        MouseMotionListener {

    protected HashMap<String, State> stateMap;

    private int width;

    private int height;

    public StateController(String initState, int width, int height) {
        stateMap = new HashMap<String, State>();
        this.setWidth(width);
        this.setHeight(height);
        this.setState(initState);
    }

    protected String stateName;

    public State getCurrentState() {
        return getState(stateName);
    }

    public void changeState() {
        String preState = this.stateName;
        this.setState(this.getCurrentState().getNextState());
        this.getCurrentState().setPreState(preState);
    }

    public State createState(String name) {
        return null;
    }

    public void update() {
        if (getCurrentState() != null) {
            if (getCurrentState().getNextState() != null) {
                this.changeState();
            }
            if (getCurrentState().getStatus() == State.PLAYING) {
                getCurrentState().update();
            }
        }
    }

    public void paint(Graphics g) {
        State state = getCurrentState();
        if (state != null) {
            state.paintBackground(g);
            state.paint(g);
            state.paintEffect(g);
            state.paintMenu(g);
        }
    }

    public void setState(String name) {
        if (name != null) {
            State st = stateMap.get(name);
            if (st == null) {
                st = this.createState(name);
                stateMap.put(name, st);
            }
            st.init();
        }
        stateName = name;
    }

    public State getState(String name) {
        return stateMap.get(name);
    }

    public void keyPressed(KeyEvent e) {
        if (getCurrentState() != null) {
            getCurrentState().keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (getCurrentState() != null) {
            getCurrentState().keyReleased(e);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (getCurrentState() != null) {
            getCurrentState().mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        State s = getCurrentState();
        if (s != null) {
            s.mouseReleased(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        State s = getCurrentState();
        if (s != null) {
            s.mouseDragged(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        State s = getCurrentState();
        if (s != null) {
            s.mouseMoved(e);
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

}
