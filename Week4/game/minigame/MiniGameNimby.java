package Week4.game.minigame;

import Week4.game.gui.MusicThread;
import Week4.game.gui.TurnTimerThread;
import Week4.game.main.Main;
import Week4.game.main.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MiniGameNimby extends JFrame {
    private int screenWidth = 800;
    private int screenHeight = 600;

    private Graphics screenGraphics;
    private Image backGround = new ImageIcon(Main.class.getResource("../etc/images/NimbyGameBackground.png")).getImage();
    private ImageIcon loadingButtonImage = new ImageIcon(Main.class.getResource("../etc/images/loadingButtonBasic.png"));
    private Image screenImage;

    private JButton loadingButton = new JButton(loadingButtonImage);

    public static NimbyGameEngine game;
    Player nowPlayer;
    TurnTimerThread turnTimerThread;
    public static boolean isGameScreen = false;
    MusicThread musicThread;


    public MiniGameNimby(Player nwPlayer, TurnTimerThread turnTimerThread) {
        this.nowPlayer = nwPlayer;
        this.turnTimerThread = turnTimerThread;
        JFrame nowGame = this;
        game = new NimbyGameEngine(nowGame, nowPlayer);
        setUndecorated(true);
        setTitle("미니게임 - 님비");//제목
        setSize(screenWidth, screenHeight);//창 크기
        setResizable(false);//크기조절
        setLocationRelativeTo(null); //  다른 컴포넌트와의 상대적 위치 설정 . 정중앙 = 널값
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 닫게 만드는 요소. 프레임의 닫음 버튼
        setVisible(true);//보이게 설정

        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);//정위치
        //프레임 설정

        /***********************************************************/

        musicThread = new MusicThread("nimbyBGM.mp3",true,true);
        /**********************************************************/


        loadingButton.setBounds(200, 400, 400, 200);//위치와 크기
        loadingButton.setBorderPainted(false);
        loadingButton.setContentAreaFilled(false);
        loadingButton.setFocusPainted(false); //이상한 기본설정 제거
        add(loadingButton);


        LoadingButtonThread loadingButtonThread = new LoadingButtonThread(loadingButton);
        loadingButtonThread.start();

        loadingButton.addMouseListener(new MouseAdapter() { // 버튼 클릭시
            @Override
            public void mousePressed(MouseEvent e) {
                new MusicThread("button.mp3", false, true);
                backGround = new ImageIcon(Main.class.getResource("../etc/images/NimbyGameBackground2.png")).getImage();
                loadingButton.setVisible(false);
                isGameScreen = true;
                game.start();
                setFocusable(true);
                requestFocus();
            }
        });
        addKeyListener(new KeyListener());
    }


    public void paint(Graphics g) {
        screenImage = createImage(screenWidth, screenHeight); //크기의 이미지 생성 후 스크린 이미지에 넣음
        screenGraphics = screenImage.getGraphics();
        screenDraw((Graphics2D) screenGraphics);
        g.drawImage(screenImage, 0, 0, null);
        turnTimerThread.limitSec = 20;
    } //화면그림

    private void screenDraw(Graphics2D g) {
        g.drawImage(backGround, 0, 0, null);
        paintComponents(g);//메뉴바나 버튼은 컴포넌트를 통해 그림.
        this.repaint();
        if (isGameScreen){
            game.screenDraw(g);
        }
        paintComponents(g);//정적인 이미지 구현
    } // 화면그림

    public void dispose() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isGameScreen) {
            musicThread.close();
            turnTimerThread.check = true;
            super.dispose();
        }
    }

    class LoadingButtonThread extends Thread {
        JButton loadingButton;

        LoadingButtonThread(JButton loadingButton) {
            this.loadingButton = loadingButton;

        }

        @Override
        public void run() {
            for (int i = 0; i < 11; i++) {
                try {
                    Thread.sleep((int)(Math.random()*400));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ImageIcon loadingButtonImage = new ImageIcon(Main.class.getResource("../etc/images/loadingButton" + (i + 1) + ".png"));
                this.loadingButton.setIcon(loadingButtonImage);
            }
        }
    } // 로딩버튼 쓰레드
}
