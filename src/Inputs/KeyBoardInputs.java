package Inputs;


import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utils.Constants.Directions.*;

public class KeyBoardInputs implements KeyListener {

    private final GamePanel gamePanel;

    public KeyBoardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W -> gamePanel.setDirection(UP);
            case KeyEvent.VK_A -> gamePanel.setDirection(LEFT);
            case KeyEvent.VK_S -> gamePanel.setDirection(DOWN);
            case KeyEvent.VK_D -> gamePanel.setDirection(RIGHT);

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.setMoving(false);
                break;

        }

    }
}
