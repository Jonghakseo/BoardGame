package Week4.game.minigame;

import Week4.game.gui.MusicThread;
import Week4.game.gui.TurnTimerThread;
import Week4.game.main.Main;
import Week4.game.main.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiniGameFire extends JFrame {
    private int screenWidth = 600;
    private int screenHeight = 600;

    private Graphics screenGraphic;
    private Image backGround = new ImageIcon(Main.class.getResource("../etc/images/miniGameFireBackground.png")).getImage();
    private ImageIcon burningBuildingImage = new ImageIcon(Main.class.getResource("../etc/images/miniGameBuilding.png"));
    private ImageIcon fireImage = new ImageIcon(Main.class.getResource("../etc/images/miniGameFire.png"));
    private ImageIcon fireMovingImage = new ImageIcon(Main.class.getResource("../etc/images/miniGameFireMoving.png"));
    private ImageIcon buildingOwnerImage = new ImageIcon(Main.class.getResource("../etc/images/miniGameBuildingOwner.png"));
    private ImageIcon buildingOwnerMovingImage = new ImageIcon(Main.class.getResource("../etc/images/miniGameBuildingOwnerMoving.png"));
    private ImageIcon buildingOwnerClickedImage = new ImageIcon(Main.class.getResource("../etc/images/miniGameBuildingOwnerClicked.png"));
    private ImageIcon waterImage;

    private JPanel buildingPanel = new JPanel();
    private Image screenImage;

    private JButton burningBuilding = new JButton(burningBuildingImage);
    private JButton fire = new JButton(fireImage);
    private JButton buildingOwner = new JButton(buildingOwnerImage);

    private MovingThread fireMovingThread;
    private MovingThread fireMovingThread2;
    private MovingThread ownerMovingThread;
    private MiniGameTimer miniGameTimer;

    private int clickedCount = 0;

    Player nowPlayer;
    TurnTimerThread turnTimerThread;
    MusicThread musicThread;

    public MiniGameFire(Player nowPlayer, TurnTimerThread turnTimerThread) {
        this.nowPlayer = nowPlayer;
        this.turnTimerThread = turnTimerThread;
        setUndecorated(true);
        setTitle("미니게임 - 불끄기");//제목
        setSize(screenWidth, screenHeight);//창 크기
        setResizable(false);//크기조절
        setLocationRelativeTo(null); //  다른 컴포넌트와의 상대적 위치 설정 . 정중앙 = 널값
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 닫게 만드는 요소.
        setVisible(true);//보이게 설정

        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);//정위치
        //프레임 설정


        musicThread = new MusicThread("fireBGM.mp3",true,true);



        fire.setBounds(0, 300, 200, 300);//위치와 크기
        fire.setBorderPainted(false);
        fire.setContentAreaFilled(false);
        fire.setFocusPainted(false); //이상한 기본설정 제거
        add(fire);
        //불 설정


        buildingPanel.setBounds(0, 0, 200, 600);
        buildingPanel.setBackground(new Color(0, 0, 0, 0));
        buildingPanel.setVisible(true);
        add(buildingPanel);
        // 빌딩 패널 설정

        burningBuilding.setBounds(0, 0, 200, 600);//위치와 크기
        burningBuilding.setBorderPainted(false);
        burningBuilding.setContentAreaFilled(false);
        burningBuilding.setFocusPainted(false); //이상한 기본설정 제거
        buildingPanel.add(burningBuilding);
        //빌딩 버튼
        fireMovingThread = new MovingThread(this, fire, fireImage, fireMovingImage,600);
        fireMovingThread.start();
        fireMovingThread2 = new MovingThread(this, fire, fireImage, fireMovingImage,100);
        fireMovingThread2.start();
        // 불타기 시작.
        ownerMovingThread = new MovingThread(this, buildingOwner, buildingOwnerImage, buildingOwnerMovingImage,400);
        ownerMovingThread.start();
        //물 붓기 시작.

        int needClick = (int)(Math.random()*10)+7;

        buildingOwner.setBounds(250, 200, 350, 400);//위치와 크기
        buildingOwner.setBorderPainted(false);
        buildingOwner.setContentAreaFilled(false);
        buildingOwner.setFocusPainted(false); //이상한 기본설정 제거
        buildingOwner.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//                fire.setIcon(fireMovingImage);
                clickedCount++;
                buildingOwner.setIcon(buildingOwnerClickedImage);
//                System.out.println("클릭" + needClick);
                if (clickedCount == needClick) {//7~16랜덤
                    miniGameTimer.activate = false;
                    fireMovingThread.activate = false;
                    ownerMovingThread.activate = false;// 세 메소드 끄기
                    nowPlayer.setCurrentMoney(nowPlayer.getCurrentMoney()+50000);
                    dispose();
                    JOptionPane.showMessageDialog(null, "다행입니다... 살림살이는 좀 건졌습니다.\n폐허에서 현금 50000을 획득합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);//간단한 팝업창으로 띄우기
                } else if(clickedCount == (int)(needClick/2)){
                    JOptionPane.showMessageDialog(null, "불이 꺼지고 있습니다! 좀 더 힘을 내보세요!", "알림", JOptionPane.INFORMATION_MESSAGE);
                    fireMovingThread2.activate = false;
                }
            }
        });

        add(buildingOwner);
        //건물주인 설정

        miniGameTimer = new MiniGameTimer(this,10);//10초 타이머
        miniGameTimer.start();

    }

    public void paint(Graphics g) {
        screenImage = createImage(screenWidth, screenHeight); //크기의 이미지 생성 후 스크린 이미지에 넣음
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
        turnTimerThread.limitSec = 20;
    } //화면그림

    private void screenDraw(Graphics g) {
        g.drawImage(backGround, 0, 0, null);
        paintComponents(g);//메뉴바나 버튼은 컴포넌트를 통해 그림.
        this.repaint();
    } // 화면그림

    public void dispose(){
        if (this.fireMovingThread.activate==true){
            nowPlayer.setCurrentMoney(nowPlayer.getCurrentMoney()-50000);
            //현금 패널티
            this.fireMovingThread.activate=false;
            this.fireMovingThread2.activate=false;
            this.ownerMovingThread.activate=false;
            turnTimerThread.limitSec = 20;
            turnTimerThread.check = true;
            super.dispose();
            JOptionPane.showMessageDialog(null, "당신은 시간 초과로 실패했습니다!\n현금이 감소합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
        }
//        synchronized(this.turnTimer) {
//            this.turnTimer.notify();
//        }
        turnTimerThread.limitSec = 20;
        turnTimerThread.check = true;
        musicThread.close();
        nowPlayer.setMiniGameFire(false);
        super.dispose();
    }
}
