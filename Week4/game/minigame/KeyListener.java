package Week4.game.minigame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        if (MiniGameNimby.game==null)
            return;//키보드 입력 무력화

        if (e.getKeyCode() == KeyEvent.VK_S) {
            MiniGameNimby.game.pressS();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            MiniGameNimby.game.pressD();
        } else if (e.getKeyCode() == KeyEvent.VK_F) {
            MiniGameNimby.game.pressF();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (MiniGameNimby.game==null)
            return;

        if (e.getKeyCode() == KeyEvent.VK_S) {
            MiniGameNimby.game.releasesS();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            MiniGameNimby.game.releasesD();
        } else if (e.getKeyCode() == KeyEvent.VK_F) {
            MiniGameNimby.game.releasesF();
        }
    }

}