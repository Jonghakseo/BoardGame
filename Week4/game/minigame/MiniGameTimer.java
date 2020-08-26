package Week4.game.minigame;

import javax.swing.*;

public class MiniGameTimer extends Thread {
    boolean activate = true;
    int timeCount = 0;
    JFrame miniGame;
    int limitTime;

    MiniGameTimer(JFrame miniGame, int limitTime) {
        this.miniGame = miniGame;
        this.limitTime = limitTime;
    }

    @Override
    public void run() {
        while (this.activate) {
            try {
                Thread.sleep(1100);
                timeCount++;
//                System.out.println(timeCount+"초 경과");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (timeCount==this.limitTime){
                this.miniGame.dispose();
                this.activate = false;
            }
        }
    }
}
