package Week4.game.minigame;

import Week4.game.gui.MusicThread;
import Week4.game.gui.TurnTimerThread;
import Week4.game.main.Main;
import Week4.game.main.Player;
import Week4.game.skill.Insurance;
import Week4.game.skill.Lawsuit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MiniGameLawsuit extends JFrame {
    private int screenWidth = 800;
    private int screenHeight = 600;

    private Graphics screenGraphic;
    private Image backGround = new ImageIcon(Main.class.getResource("../etc/images/lawsuitGameBackground.png")).getImage();
    private ImageIcon myLawyerImage = new ImageIcon(Main.class.getResource("../etc/images/myLawyer.png"));
    private ImageIcon myLawyerScriptImage = new ImageIcon(Main.class.getResource("../etc/images/myLawyerScriptBackground.png"));
    private ImageIcon enemyLawyerScriptImage = new ImageIcon(Main.class.getResource("../etc/images/enemyLawyerScriptBackground.png"));
    private ImageIcon enemyLawyerImage = new ImageIcon(Main.class.getResource("../etc/images/enemyLawyer.png"));
    private ImageIcon loadingButtonImage = new ImageIcon(Main.class.getResource("../etc/images/loadingButtonBasic.png"));
    private ImageIcon myLawyerAttackImage = new ImageIcon(Main.class.getResource("../etc/images/myLawyerAttack.png"));
    private ImageIcon enemyLawyerAttackImage = new ImageIcon(Main.class.getResource("../etc/images/enemyLawyerAttack.png"));
    private Image screenImage;

    private JButton loadingButton = new JButton(loadingButtonImage);
    private JButton myLawyerButton = new JButton(myLawyerImage);
    private JButton enemyLawyerButton = new JButton(enemyLawyerImage);
    private JButton scriptButton = new JButton(myLawyerScriptImage);

    JTextField powerText;
    JTextField myLawyerHPText;
    JTextField enemyLawyerHPText;

    MovingThread myLawyerAttackThread;
    MovingThread enemyLawyerAttackThread;
    MessageViewingThread messageViewingThread;
    LawyerScriptThread firstLawyerScriptThread;

    Player nowPlayer;
    TurnTimerThread turnTimerThread;
    MusicThread backgroundMusic;

    int[] gameInfo = {0, 0, 0, 0};


    public MiniGameLawsuit(Player nwPlayer, TurnTimerThread turnTimerThread) {
        JFrame nowGame = this;
        this.nowPlayer = nwPlayer;
        this.turnTimerThread = turnTimerThread;
        setUndecorated(true);
        setTitle("미니게임 - 튜토리얼");//제목
        setSize(screenWidth, screenHeight);//창 크기
        setResizable(false);//크기조절
        setLocationRelativeTo(null); //  다른 컴포넌트와의 상대적 위치 설정 . 정중앙 = 널값
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 닫게 만드는 요소. 프레임의 닫음 버튼
        setVisible(true);//보이게 설정

        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);//정위치
        //프레임 설정

        /***********************************************************/
//        this.nowPlayer = nowPlayer;
//        this.nowPlayer = new Player("더미 플레이어", false, 3000000, 1);
        this.nowPlayer.setMiniGameLawsuit(true);
        backgroundMusic = new MusicThread("lawsuitBGM.mp3",true,true);
        /**********************************************************/
        loadingButton.setBounds(370, 200, 400, 200);//위치와 크기
        loadingButton.setBorderPainted(false);
        loadingButton.setContentAreaFilled(false);
        loadingButton.setFocusPainted(false); //이상한 기본설정 제거
        add(loadingButton);

        LoadingButtonThread loadingButtonThread = new LoadingButtonThread(loadingButton);
        loadingButtonThread.start();

        firstLawyerScriptThread = new LawyerScriptThread(scriptButton, 1, 4, this.nowPlayer, nowGame);

        loadingButton.addMouseListener(new MouseAdapter() { // 버튼 클릭시
            @Override
            public void mousePressed(MouseEvent e) {
                new MusicThread("button.mp3", false, true);
                backGround = new ImageIcon(Main.class.getResource("../etc/images/lawsuitGameBackground2.png")).getImage();
                loadingButton.setVisible(false);
                myLawyerButton.setVisible(true);
                enemyLawyerButton.setVisible(true);
                scriptButton.setVisible(true);
                firstLawyerScriptThread.start();

            }

        });
        myLawyerButton.setBounds(30, 150, 130, 400);//위치와 크기
        myLawyerButton.setBorderPainted(false);
        myLawyerButton.setContentAreaFilled(false);
        myLawyerButton.setFocusPainted(false); //이상한 기본설정 제거
        myLawyerButton.setVisible(false);
        add(myLawyerButton);

        enemyLawyerButton.setBounds(620, 150, 160, 400);//위치와 크기
        enemyLawyerButton.setBorderPainted(false);
        enemyLawyerButton.setContentAreaFilled(false);
        enemyLawyerButton.setFocusPainted(false); //이상한 기본설정 제거
        enemyLawyerButton.setVisible(false);
        add(enemyLawyerButton);

        powerText = new JTextField();
        powerText.setBounds(190, 125, 50, 20);//위치와 크기
        powerText.setFont(new Font("맑은 고딕", 1, 13));
        powerText.setBackground(Color.WHITE);
        powerText.setForeground(Color.black);
        powerText.setHorizontalAlignment(JTextField.CENTER);
        powerText.setVisible(false);
//        add(powerText);

        myLawyerHPText = new JTextField();
        myLawyerHPText.setBounds(330, 175, 50, 20);//위치와 크기
        myLawyerHPText.setFont(new Font("맑은 고딕", 1, 13));
        myLawyerHPText.setBackground(Color.WHITE);
        myLawyerHPText.setForeground(Color.black);
        myLawyerHPText.setHorizontalAlignment(JTextField.CENTER);
        myLawyerHPText.setVisible(false);
//        add(myLawyerHPText);

        enemyLawyerHPText = new JTextField();
        enemyLawyerHPText.setBounds(520, 175, 50, 20);//위치와 크기
        enemyLawyerHPText.setFont(new Font("맑은 고딕", 1, 13));
        enemyLawyerHPText.setBackground(Color.WHITE);
        enemyLawyerHPText.setForeground(Color.black);
        enemyLawyerHPText.setHorizontalAlignment(JTextField.CENTER);
        enemyLawyerHPText.setVisible(false);
//        add(enemyLawyerHPText);

        scriptButton.setBounds(180, 50, 400, 250);//위치와 크기
        scriptButton.setBorderPainted(false);
        scriptButton.setContentAreaFilled(false);
        scriptButton.setFocusPainted(false); //이상한 기본설정 제거
        scriptButton.setVisible(false);
        add(scriptButton);





        LawyerFightThread lawyerFightThread = new LawyerFightThread(nowGame, scriptButton, gameInfo);
        lawyerFightThread.start();

        CheckWinnerThread checkWinnerThread = new CheckWinnerThread(nowGame, this.nowPlayer, myLawyerButton, enemyLawyerButton);
        checkWinnerThread.start();


    }


    public void paint(Graphics g) {
        screenImage = createImage(screenWidth, screenHeight); //크기의 이미지 생성 후 스크린 이미지에 넣음
        screenGraphic = screenImage.getGraphics();
        screenDraw((Graphics2D) screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    } //화면그림

    private void screenDraw(Graphics2D g) {
        g.drawImage(backGround, 0, 0, null);
        paintComponents(g);//메뉴바나 버튼은 컴포넌트를 통해 그림.
        if (gameInfo[0] != 0){
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(new Font("Elephant", Font.BOLD, 18));
        g.drawString(String.valueOf(gameInfo[1]), 200, 145);
        g.drawString(String.valueOf(gameInfo[2]), 340, 195);
        g.drawString(String.valueOf(gameInfo[3]), 530, 195);
        }
        this.repaint();
    } // 화면그림

    public void dispose() {
        nowPlayer.setMiniGameLawsuit(false);
        turnTimerThread.limitSec = 20;
        turnTimerThread.check = true;
        backgroundMusic.close();
        super.dispose();
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

    class LawyerScriptThread extends Thread {
        JButton scriptButton;
        int scriptStartNum;
        int scriptEndNum;
        Player nowPlayer;
        JFrame nowGame;


        LawyerScriptThread(JButton scriptButt, int scriptStartN, int scriptEndN, Player nwPlayer, JFrame nwGame) {
            this.scriptButton = scriptButt;
            this.scriptStartNum = scriptStartN;
            this.scriptEndNum = scriptEndN;
            this.nowPlayer = nwPlayer;
            this.nowGame = nwGame;
        }

        @Override
        public void run() {
            ImageIcon lawyerScriptImage;
            for (int i = scriptStartNum; i <= scriptEndNum; i++) {
                lawyerScriptImage = new ImageIcon(Main.class.getResource("../etc/images/lawyerScript" + (i) + ".png"));
                scriptButton.setIcon(lawyerScriptImage);
                try {
                    Thread.sleep(5000);//5초 적정
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println("스크립트 종료");
            }
            String[] choices = {"그런건 없다.", "현재 재산의 10%", "현재 재산의 20%"};
            String input = (String) JOptionPane.showInputDialog(null, "성공 보수를 선택하세요",
                    "선택창", JOptionPane.QUESTION_MESSAGE, null, // Use
                    choices, // 게임 리스트
                    choices[0]); // 초기 선택값
            if (input == null) {
                input = "그런건 없다.";
            }
            switch (input) {
                case "그런건 없다.":
                    nowPlayer.setMiniGameLawsuitPower(0);
                    lawyerScriptImage = new ImageIcon(Main.class.getResource("../etc/images/lawyerScriptSelect1.png"));
                    scriptButton.setIcon(lawyerScriptImage);
                    break;
                case "현재 재산의 10%":
                    nowPlayer.setMiniGameLawsuitPower(1);
                    lawyerScriptImage = new ImageIcon(Main.class.getResource("../etc/images/lawyerScriptSelect2.png"));
                    scriptButton.setIcon(lawyerScriptImage);
                    nowPlayer.setCurrentMoney((int) (nowPlayer.getCurrentMoney() * 0.9));
                    break;
                case "현재 재산의 20%":
                    nowPlayer.setMiniGameLawsuitPower(2);
                    lawyerScriptImage = new ImageIcon(Main.class.getResource("../etc/images/lawyerScriptSelect3.png"));
                    scriptButton.setIcon(lawyerScriptImage);
                    nowPlayer.setCurrentMoney((int) (nowPlayer.getCurrentMoney() * 0.8));
                    break;
            }
            try {
                Thread.sleep(3000);//4초 적정
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nowGame.setTitle("미니게임 - 시작");
            scriptButton.setVisible(false);// 버튼 감춰버리기
        }
    } // 첫 설명 + 성공보수 선택 쓰레드

    class LawyerFightThread extends Thread {
        JFrame nowGame;
        JButton scriptButton;
        int[] info;


        LawyerFightThread(JFrame nowGame, JButton script, int[] info) {
            this.nowGame = nowGame;
            this.scriptButton = script;
            this.info = info;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (nowGame.getTitle().equals("미니게임 - 시작")) {
//                    System.out.println("그 이후의 실행부분");

                    myLawyerAttackThread = new MovingThread(nowGame, myLawyerButton, myLawyerImage, myLawyerAttackImage, 200);
                    enemyLawyerAttackThread = new MovingThread(nowGame, enemyLawyerButton, enemyLawyerImage, enemyLawyerAttackImage, 200);
                    myLawyerAttackThread.activate = false;
                    myLawyerAttackThread.start();
                    enemyLawyerAttackThread.activate = false;
                    enemyLawyerAttackThread.start();
                    LawsuitGameEngine lawsuitGameEngine = new LawsuitGameEngine(nowPlayer, myLawyerAttackThread, enemyLawyerAttackThread, this.info);
                    lawsuitGameEngine.start();
                    messageViewingThread = new MessageViewingThread(this.nowGame, this.scriptButton, this.info);
                    messageViewingThread.start();
//                    nowGame.dispose();
                    break;
                }
                if (nowGame.getTitle().equals("미니게임 - 튜토리얼")) {
//                    System.out.println("대기중");
                }
            }
        }
    } // 공격 본격적으로 시작하는 쓰레드

    class MessageViewingThread extends Thread {
        int[] infoArray;
        JFrame nowGame;
        JButton scriptButton;

        ImageIcon myLawyerAttackImage = new ImageIcon(Main.class.getResource("../etc/images/myLawyerAttackScript.png"));
        ImageIcon enemyLawyerAttackImage = new ImageIcon(Main.class.getResource("../etc/images/enemyLawyerAttackScript.png"));


        MessageViewingThread(JFrame nowFrame, JButton script, int[] info) {
            this.infoArray = info;
            this.nowGame = nowFrame;
            this.scriptButton = script;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (this.infoArray[0] != 0) {//시작하면 실행.
                    if (infoArray[0] == 1) {//내 공격턴
                        scriptButton.setVisible(true);
                        scriptButton.setIcon(myLawyerAttackImage);
                        viewGameInfo();
                    } else if (infoArray[0] == 2) {
                        scriptButton.setVisible(true);
                        scriptButton.setIcon(enemyLawyerAttackImage);
                        viewGameInfo();
                    }
                } else {// 실행 전
                    scriptButton.setVisible(false);
                    unVisibleGameInfo();
                }
            }
        }


    }//메시지 띄우는 쓰레드

    class CheckWinnerThread extends Thread {
        JFrame nowGame;
        Player nowPlayer;
        JButton myLawyerButton;
        JButton enemyLawyerButton;

        ImageIcon myLawyerWinImage = new ImageIcon(Main.class.getResource("../etc/images/myLawyerWin.png"));
        ImageIcon enemyLawyerWinImage = new ImageIcon(Main.class.getResource("../etc/images/enemyLawyerWin.png"));
        ImageIcon myLawyerLoseImage = new ImageIcon(Main.class.getResource("../etc/images/myLawyerLose.png"));
        ImageIcon enemyLawyerLoseImage = new ImageIcon(Main.class.getResource("../etc/images/enemyLawyerLose.png"));

        CheckWinnerThread(JFrame nowGame, Player nowPlayer, JButton myLawyerButton, JButton enemyLawyerButton) {
            this.nowGame = nowGame;
            this.nowPlayer = nowPlayer;
            this.myLawyerButton = myLawyerButton;
            this.enemyLawyerButton = enemyLawyerButton;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!this.nowPlayer.getMiniGameLawsuit()) {//기본값은 true 이고 승패가 갈리면 false 가 됨
                    try {
                        Thread.sleep(2100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    System.out.println("승패 판가름 후");
                    if (this.nowPlayer.getMiniGameLawsuitWin()) {//승리했다면!
//                        System.out.println("이겼습니다.");
                        unVisibleGameInfo();
                        this.myLawyerButton.setIcon(myLawyerWinImage);//승리이미지
                        this.enemyLawyerButton.setIcon(enemyLawyerLoseImage);//패배이미지
                        String info = "<html><p>당신은 승리하였습니다!<br />보상으로 보험카드 1개와 소송카드 1개를 얻습니다.<br />축하합니다! <br /></p></html>";
                        JLabel inform = new JLabel(info);
                        inform.setFont(new Font("맑은 고딕", Font.BOLD, 15));
                        JOptionPane.showMessageDialog(null, inform, "승리!", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                        turnTimerThread.limitSec = 20;
                        turnTimerThread.check = true;

                        nowPlayer.addHand(new Insurance());
                        nowPlayer.addHand(new Lawsuit());

                        nowGame.dispose();
                        break;
                    } else {
//                        System.out.println("졌습니다.");
                        unVisibleGameInfo();
                        this.myLawyerButton.setIcon(myLawyerLoseImage);//패배이미지
                        this.enemyLawyerButton.setIcon(enemyLawyerWinImage);//승리이미지
                        String info = "<html><p>당신은 패배하였습니다!<br />보상은 없습니다.<br /> 아쉽습니다! <br /></p></html>";
                        JLabel inform = new JLabel(info);
                        inform.setFont(new Font("맑은 고딕", Font.BOLD, 15));
                        JOptionPane.showMessageDialog(null, inform, "패배!", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                        turnTimerThread.limitSec = 20;
                        turnTimerThread.check = true;

                        nowGame.dispose();
                        break;
                    }

//            System.out.println("2로 변경");
                } else {// 기본값일때
//                    System.out.println("승패 판가름 전");
                }
            }

        }
//        lawsuitData.nowPlayer.setMiniGameLawsuit(false); // nowPlayer 의 setMiniGameLawsuit(boolean)을 인자로 받아서 실행되는 승패 메소드

    }

    public void viewGameInfo(){
        powerText.setText("");
        myLawyerHPText.setText("");
        enemyLawyerHPText.setText("");
        powerText.setVisible(true);
        myLawyerHPText.setVisible(true);
        enemyLawyerHPText.setVisible(true);
        powerText.setText(String.valueOf(gameInfo[1]));
        myLawyerHPText.setText(String.valueOf(gameInfo[2]));
        enemyLawyerHPText.setText(String.valueOf(gameInfo[3]));

    }

    public void unVisibleGameInfo(){
        powerText.setVisible(false);
        myLawyerHPText.setVisible(false);
        enemyLawyerHPText.setVisible(false);

    }

}
