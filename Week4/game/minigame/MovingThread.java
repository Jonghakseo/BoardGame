package Week4.game.minigame;

import javax.swing.*;

public class MovingThread extends Thread {
    public ImageIcon imageIcon1;
    public ImageIcon imageIcon2;
    public JFrame miniGame;
    public JButton jButton;
    public int interval;
    public boolean activate = true;
    public boolean onSwitch = true;

    MovingThread(JFrame miniGame, JButton jButton, ImageIcon imageIcon1, ImageIcon imageIcon2, int interval) {
        this.imageIcon1 = imageIcon1;
        this.imageIcon2 = imageIcon2;
        this.miniGame = miniGame;
        this.jButton = jButton;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (onSwitch) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (activate) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.jButton.setIcon(this.imageIcon1);
//            System.out.println("1로 변경");
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.jButton.setIcon(this.imageIcon2);
//            System.out.println("2로 변경");
            } else {
                this.jButton.setIcon(this.imageIcon1);
            }
        }
    }
}
