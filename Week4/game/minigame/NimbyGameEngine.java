package Week4.game.minigame;

import Week4.game.main.Main;
import Week4.game.main.Player;

import javax.swing.*;
import java.awt.*;

import static Week4.game.minigame.MiniGameNimby.isGameScreen;

public class NimbyGameEngine extends Thread {
    private Image emptyBasicImage = new ImageIcon(Main.class.getResource("../etc/images/NimbyGameEmpty.png")).getImage();
    private Image ownerBasicImage = new ImageIcon(Main.class.getResource("../etc/images/NimbyGameOwner.png")).getImage();
    private Image noteRouteSImage = new ImageIcon(Main.class.getResource("../etc/images/NimbyGameEmpty.png")).getImage();
    private Image noteRouteDImage = new ImageIcon(Main.class.getResource("../etc/images/NimbyGameEmpty.png")).getImage();
    private Image noteRouteFImage = new ImageIcon(Main.class.getResource("../etc/images/NimbyGameEmpty.png")).getImage();

    int revenue = 0;
    int countKeyPress = 0;
    int maxKeyPress = 50;

    JFrame nowGame;
    Player nowPlayer;

    NimbyGameEngine(JFrame nwGame, Player nwPlayer){
        nowGame = nwGame;
        nowPlayer = nwPlayer;
    }

    public void screenDraw(Graphics2D g) {
        g.drawImage(noteRouteSImage, 100, 200, null);
        g.drawImage(noteRouteDImage, 300, 200, null);
        g.drawImage(noteRouteFImage, 500, 200, null);
        g.setColor(Color.BLACK);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("Elephant", Font.BOLD, 29));
        g.drawString("S", 150, 500);
        g.drawString("D", 400, 500);
        g.drawString("F", 650, 500);
        g.drawString("획득한(?) 현금 : "+revenue+"원",100,100);
        g.setFont(new Font("Elephant", Font.BOLD, 14));
        g.drawString("소각장 \n(반대수익 낮음)", 70, 180);
        g.drawString("납골당 \n(반대수익 보통)", 300, 180);
        g.drawString("발전소 \n(반대수익 높음)", 530, 180);
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (maxKeyPress > countKeyPress){
//                System.out.println("게임 진행중");
                continue;
            }else {
//                System.out.println("종료합니다.");
                nowPlayer.setCurrentMoney(nowPlayer.getCurrentMoney()-revenue);
                JOptionPane.showMessageDialog(null, revenue+"만큼의 현금을 잃었습니다.", "결과", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "다시는 이기적으로 굴지 마십시오!", "교훈", JOptionPane.INFORMATION_MESSAGE);

                isGameScreen = false;
                nowGame.dispose();
                break;
            }
        }

    }

    public void pressS() {
        noteRouteSImage = ownerBasicImage;
        revenue+=(int)((Math.random())*500);
        countKeyPress++;
//        new Music("hitSound.mp3", false).start();
    }

    public void releasesS() {
        noteRouteSImage = emptyBasicImage;
    }

    public void pressD() {
        noteRouteDImage = ownerBasicImage;
        revenue+=(int)((Math.random())*1000);
        countKeyPress++;
//        new Music("hitSound.mp3", false).start();
    }

    public void releasesD() {
        noteRouteDImage = emptyBasicImage;
    }

    public void pressF() {
        countKeyPress++;
        revenue+=(int)((Math.random())*2500);
        noteRouteFImage = ownerBasicImage;
//        new Music("hitSound.mp3", false).start();
    }

    public void releasesF() {
        noteRouteFImage = emptyBasicImage;
    }


}

