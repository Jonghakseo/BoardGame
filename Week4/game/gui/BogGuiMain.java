package Week4.game.gui;

import Week4.game.engine.Maker;
import Week4.game.engine.MenuViewer;
import Week4.game.engine.GameMaster;
import Week4.game.main.Card;
import Week4.game.main.Land;
import Week4.game.main.Main;
import Week4.game.main.Player;
import Week4.game.minigame.MiniGame;
import Week4.game.minigame.MiniGameFire;
import Week4.game.minigame.MiniGameLawsuit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BogGuiMain extends JFrame {
    private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../etc/images/exitButtonBasic.png"));
    private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../etc/images/exitButtonEntered.png"));
    private ImageIcon minimizeButtonBasicImage = new ImageIcon(Main.class.getResource("../etc/images/minimizeButtonBasic.png"));
    private ImageIcon minimizeButtonEnteredImage = new ImageIcon(Main.class.getResource("../etc/images/minimizeButtonEntered.png"));
    private ImageIcon gameButtonBasicImage = new ImageIcon(Main.class.getResource("../etc/images/gameButtonBasic.png"));
    private ImageIcon gameButtonEnteredImage = new ImageIcon(Main.class.getResource("../etc/images/gameButtonEntered.png"));
    private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../etc/images/quitButtonBasic.png"));
    private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../etc/images/quitButtonEntered.png"));
    private ImageIcon showDetailBasicImage = new ImageIcon(Main.class.getResource("../etc/images/showDetailButtonBasic.png"));
    private ImageIcon showDetailEnteredImage = new ImageIcon(Main.class.getResource("../etc/images/showDetailButtonEntered.png"));
    private ImageIcon diceButtonBasicImage = new ImageIcon(Main.class.getResource("../etc/images/diceBasic.png"));
    private ImageIcon landButton1pImage = new ImageIcon(Main.class.getResource("../etc/images/landButton1p.png"));
    private ImageIcon landButton2pImage = new ImageIcon(Main.class.getResource("../etc/images/landButton2p.png"));
    private ImageIcon landButton1pSelectedImage = new ImageIcon(Main.class.getResource("../etc/images/landButton1pSelected.png"));
    private ImageIcon landButton2pSelectedImage = new ImageIcon(Main.class.getResource("../etc/images/landButton2pSelected.png"));
    private ImageIcon landButtonBasic = new ImageIcon(Main.class.getResource("../etc/images/landButtonBasic.png"));
    private Image screenImage;

    private Graphics screenGraphic;

    private Image backGround = new ImageIcon(Main.class.getResource("../etc/images/introBackground.png")).getImage();
//    private Image selectedImage = new ImageIcon(Main.class.getResource("../etc/images/introBackground.png")).getImage();

    private JLabel topLine = new JLabel(new ImageIcon(Main.class.getResource("../etc/images/menubar.png"))); // 메뉴바

    private JButton exitButton = new JButton(exitButtonBasicImage); // 닫기버튼
    private JButton minimizeButton = new JButton(minimizeButtonBasicImage);
    private JButton gameButton = new JButton(gameButtonBasicImage);
    private JButton quitButton = new JButton(quitButtonBasicImage);
    private JButton diceShow = new JButton(diceButtonBasicImage);
    private JButton land1Button = new JButton(landButtonBasic); // 땅 버튼 8개
    private JButton land2Button = new JButton(landButtonBasic); // 땅 버튼 8개
    private JButton land3Button = new JButton(landButtonBasic); // 땅 버튼 8개
    private JButton land4Button = new JButton(landButtonBasic); // 땅 버튼 8개
    private JButton land5Button = new JButton(landButtonBasic); // 땅 버튼 8개
    private JButton land6Button = new JButton(landButtonBasic); // 땅 버튼 8개
    private JButton land7Button = new JButton(landButtonBasic); // 땅 버튼 8개
    private JButton land8Button = new JButton(landButtonBasic); // 땅 버튼 8개

    private JMenu helpMenu;
    private JMenu developer;
    private JMenuBar menuBar;
    private JMenuItem cardList;
    private JMenuItem helpRules;
    private JMenuItem viewDeveloper;
    private JPanel settingPanel;
    private JPanel detailPanel;
    private JPanel gameSelectPanel;
    private JPanel gameCardPanel = new JPanel();

    private JLayeredPane gamePanel;
    private JTextArea textEventArea;
    private JTextArea landEventArea;
    private JTextArea countDownArea;
    private JTextArea textEventArea2;
    private JTextArea p1CardListArea;
    private JTextArea p2CardListArea;
    private JScrollPane p1CardPane;
    private JScrollPane p2CardPane;
    private JScrollPane mainEventPane;
    private JScrollPane mainEventPane2;
    private JScrollPane landEventPane;
    private JScrollPane diceEventPane;
    private JComboBox selectCardBox;
    private JTextField p1MoneyField;
    private JTextField p2MoneyField;
    private JTextField p1NameField;
    private JTextField p2NameField;

    private String player1Name = "플레이어 1";
    private String player2Name = "플레이어 2";
    private String selectCard = "턴 넘기기";

    private boolean isEndingTurn = false;
    private boolean gameStart = false;
    private boolean eventCheckStart = false;
    private int showTime = -100;
    private String popUpMessage = null;

    ///////////////////

    private Maker gameMaker = new Maker();
    private int landNum = 8;//땅 생성
    private ArrayList<Land> landList = gameMaker.makingLands(landNum);
    //Land land1
    //섞인 땅 리스트 생성 (8개)
    public GameMaster gameMaster = new GameMaster(landList, 50);//50턴
    private MenuViewer menuViewer = new MenuViewer();
    //각종 툴 생성
    private ArrayList<Player> wholePlayer = new ArrayList<>();
    public Player nowPlayer;
    public Land nowLand;
    private ArrayList<Card> cardArrayList;
    private int cardIndexCount = 0;
    int wholeTurnCount = 0;
    ////////////////

    private MusicThread nowMusicThread;

    private int mouseX, mouseY;

    public BogGuiMain(TurnTimerThread turnTimerThread) {

        setUndecorated(true);
        setTitle("건물주 게임");//제목
        setSize(Main.screenWidth, Main.screenHeight);//창 크기
        setResizable(false);//크기조절
        setLocationRelativeTo(null); //  다른 컴포넌트와의 상대적 위치 설정 . 정중앙 = 널값
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 닫게 만드는 요소. 프레임의 닫음 버튼
        setVisible(true);//보이게 설정

        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);//정위치
        ////////////////////////////////////////////////////////////////////////프레임설정//

        /////////////////////////////세팅화면에 노출할 패널 만들기///////////////////////////////////
        settingPanel = new JPanel();
        detailPanel = new JPanel(); // 패널 두 개 생성

        JButton inputButton1 = new JButton("변경");//1p 입력버튼 추가
        JButton inputButton2 = new JButton("변경");//2p입력버튼 추가
        JButton backButton = new JButton("뒤로가기");// 뒤로가기 버튼 추가
        JTextField inputName1 = new JTextField(player1Name, 8); // 사용자 1 이름 입력칸
        JTextField inputName2 = new JTextField(player2Name, 8); // 사용자 2 이름 입력칸
        inputButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                player1Name = inputName1.getText();///사용자 이름 받아서 처리 가능하게 만듦.
            }
        });
        inputButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                player2Name = inputName2.getText();///사용자 이름 받아서 처리 가능하게 만듦.
            }
        });
        backButton.addMouseListener(new MouseAdapter() {// 뒤로가기 버튼 작용시
            @Override
            public void mousePressed(MouseEvent e) {
                MusicThread buttonEnteredMusicThread = new MusicThread("buttonShort.mp3", false,true);

                // 버튼 2개 제거 후 배경이미지 변경.
                backGround = new ImageIcon(Main.class.getResource("../etc/images/introBackground.png")).getImage();
                settingPanel.setVisible(false);
                detailPanel.setVisible(false);
                gameButton.setVisible(true);
                quitButton.setVisible(true);
            }
        });
        settingPanel.setSize(250, 500);
        settingPanel.setLocation(150, 300);
        settingPanel.add(inputName1);//텍스트 필드 추가
        settingPanel.add(inputButton1);//버튼추가
        settingPanel.add(inputName2);//텍스트 필드 추가
        settingPanel.add(inputButton2);//버튼추가
        backButton.setLayout(null);
        backButton.setBounds(900, 800, 100, 500);
        settingPanel.add(backButton);//뒤로가기 버튼 추가
        settingPanel.setAlignmentX(3);
        settingPanel.setVisible(false);//안 보이게 설정
        settingPanel.setBackground(new Color(0, 0, 0, 0));

        add(settingPanel);
        //////////////////////////////////////////////////////////////////////////////////디테일 패널 만들기
        detailPanel = new JPanel();
        detailPanel.setSize(250, 400);
        detailPanel.setLocation(850, 300);
        detailPanel.setBackground(new Color(0, 0, 0, 0));
        JCheckBox checkIsEndingTurn = new JCheckBox("50턴 제한");
        checkIsEndingTurn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        checkIsEndingTurn.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (((JCheckBox) e.getSource()).isSelected()) {
                    isEndingTurn = true;
                } else {
                    isEndingTurn = false;
                }
            }
        });
        JButton goToGameButton = new JButton("게임시작");// 게임시작 버튼 추가
        goToGameButton.addMouseListener(new MouseAdapter() {// 버튼 작용시
            @Override
            public void mousePressed(MouseEvent e) {
                //todo
                System.out.println("게임시작");
                backGround = new ImageIcon(Main.class.getResource("../etc/images/gameScreenBackground.png")).getImage();
                detailPanel.setVisible(false);
                settingPanel.setVisible(false);
                gamePanel.setVisible(true);
            }
        });
        detailPanel.add(goToGameButton);
        detailPanel.add(checkIsEndingTurn);
        detailPanel.setVisible(false);//안 보이게 설정
        add(detailPanel);

        //////////////////////////////////////////////////////////////////////////////////////////세팅화면


        //////////////////////////////////////////////////////////////////////////메뉴칸 만들기//
        topLine.setBounds(0, 0, 1280, 30);

        topLine.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });//창 이동을 위한 마우스 리스너 생성
        topLine.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });//창 이동을 위한 마우스 모션 리스너 생성. 드래그 위치 받아서 창 위치 이동
        ///////////////////////////////////////////////////////////////////메뉴바 설정///

        exitButton.setBounds(1245, 0, 30, 30);//위치와 크기
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false); //이상한 기본설정 제거
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MusicThread buttonEnteredMusicThread = new MusicThread("button.mp3", false);
                buttonEnteredMusicThread.start();
                try {
                    Thread.sleep(300);
                } catch (Exception except) {
                    except.printStackTrace();
                }
                dispose();
                System.exit(0);


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(exitButtonEnteredImage);
                exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(exitButtonBasicImage);

            }
        });//클릭, 접근, 탈출시 변동
        //////////////////////////////////////////////////////////////////////////////////////////메뉴바 닫기버튼 설정//

        minimizeButton.setBounds(1205, 0, 30, 30);//위치와 크기
        minimizeButton.setBorderPainted(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setFocusPainted(false); //이상한 기본설정 제거
        minimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MusicThread buttonEnteredMusicThread = new MusicThread("button.mp3", false);
                buttonEnteredMusicThread.start();
                getFrames()[0].setState(Frame.ICONIFIED);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                minimizeButton.setIcon(minimizeButtonEnteredImage);
                minimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimizeButton.setIcon(minimizeButtonBasicImage);

            }
        });//클릭, 접근, 탈출시 변동
        add(minimizeButton);
        ////////////////////////////////////////////////////////////////////메뉴바 최소화 버튼 설정//
        gameButton.setBounds(800, 200, 400, 100);//위치와 크기
        gameButton.setBorderPainted(false);
        gameButton.setContentAreaFilled(false);
        gameButton.setFocusPainted(false); //이상한 기본설정 제거
        gameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MusicThread buttonEnteredMusicThread = new MusicThread("button.mp3", false);
                buttonEnteredMusicThread.start();
                // 버튼 2개 제거 후 배경이미지 변경.
                gameButton.setVisible(false);
                quitButton.setVisible(false);
                backGround = new ImageIcon(Main.class.getResource("../etc/images/settingBackground.png")).getImage();
                settingPanel.setVisible(true);//세팅화면 패널들 추가.
                detailPanel.setVisible(true);//세팅화면 패널들 추가
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                gameButton.setIcon(gameButtonEnteredImage);
                gameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gameButton.setIcon(gameButtonBasicImage);

            }
        });//클릭, 접근, 탈출시 변동
        ///////////////////////////////////////////////////////////시작버튼 설정

        quitButton.setBounds(800, 400, 400, 100);//위치와 크기
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setFocusPainted(false); //이상한 기본설정 제거
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MusicThread buttonEnteredMusicThread = new MusicThread("button.mp3", false);
                buttonEnteredMusicThread.start();
                try {
                    Thread.sleep(200);
                } catch (Exception except) {
                    except.printStackTrace();
                }
                dispose();
                System.exit(0);
                //종료
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                quitButton.setIcon(quitButtonEnteredImage);
                quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                quitButton.setIcon(quitButtonBasicImage);

            }
        });//클릭, 접근, 탈출시 변동
        ///////////////////////////////////////////////////////////시작버튼 설정
        add(exitButton); // 닫기버튼 추가       //순서 반대로 하면
        add(topLine); // 컴포넌트에 메뉴바 추가 // 상단 메뉴바가 버튼 덮어버림
        add(gameButton);
        add(quitButton);

        CreateMenu();//메뉴바 추가
/////////////////////////////////////////////////////////////////////////////////////////////////
//        Music introMusic = new Music("BGM.mp3", true);//배경음악 재생
//        introMusic.start();
        nowMusicThread = turnTimerThread.backgroundMusic;
////////////////////////////////////////////////////////////////////////////////////////////////
        p1NameField = new JTextField();
        p1NameField.setBounds(187, 70, 80, 20);//위치와 크기
        p1NameField.setFont(new Font("맑은 고딕", 1, 13));
        p1NameField.setBackground(new Color(50, 102, 138));
        p1NameField.setForeground(new Color(255, 255, 255));
        p1NameField.setHorizontalAlignment(JTextField.CENTER);
        p1NameField.setVisible(false);
        add(p1NameField);
        p2NameField = new JTextField();
        p2NameField.setBounds(1008, 70, 80, 20);//위치와 크기
        p2NameField.setFont(new Font("맑은 고딕", 1, 13));
        p2NameField.setBackground(new Color(178, 50, 50));
        p2NameField.setForeground(new Color(255, 255, 255));
        p2NameField.setHorizontalAlignment(JTextField.CENTER);
        p2NameField.setVisible(false);
        add(p2NameField);
////////////////////////////////////////////////////////////////////////////////////////////////이름 필드
        gamePanel = new JLayeredPane();

        JPanel dicePanel = new JPanel();
        dicePanel.setVisible(true);
        dicePanel.setLocation(500, 30);
        dicePanel.setSize(280, 100);
        dicePanel.setBackground(new Color(0, 0, 0, 0));
        ///////////////////////////////
        diceShow.setBounds(505, 30, 90, 90);//위치와 크기
        diceShow.setBorderPainted(false);
        diceShow.setContentAreaFilled(false);
        diceShow.setFocusPainted(false); //이상한 기본설정 제거
        dicePanel.add(diceShow);
        countDownArea = new JTextArea("", 3, 7);
        countDownArea.setBackground(new Color(128, 128, 128, 50));
//        diceEventArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
//        diceEventArea.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        diceEventPane = new JScrollPane(countDownArea);
        countDownArea.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        dicePanel.add(diceEventPane);
        countDownEvent("제한시간");


        ///////////////////////////////

        JPanel landPanel = new JPanel();
        landPanel.setVisible(true);
        landPanel.setLocation(220, 140);
        landPanel.setSize(840, 240);
        landPanel.setBackground(new Color(0, 0, 0, 0));
        /////////////////////////////////////////////////////////////////////////////////////////////////LAND//////////
        landEventArea = new JTextArea("땅 현황을 나타내는 부분입니다\r\n", 13, 73);
//        landEventArea.setFont(new Font("맑은 고딕",Font.PLAIN,12));
        landEventPane = new JScrollPane(landEventArea);
        landPanel.add(landEventPane);
        addLandEvent("안녕하세요.\r\n");
        addLandEvent("땅 현황입니다.\n\n");
////////////////////////////////////////////////
        JPanel p1CardPanel = new JPanel();
        p1CardPanel.setVisible(true);
        p1CardPanel.setLocation(10, 200);
        p1CardPanel.setSize(180, 350);
        p1CardPanel.setBackground(new Color(0, 0, 0, 0));
//////////////////////////////////////////////
        p1CardListArea = new JTextArea("p1의 카드목록", 18, 10);
        p1CardListArea.setBackground(new Color(245, 245, 220, 50));//베이지색
        p1CardListArea.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        p1CardPane = new JScrollPane(p1CardListArea);
        p1CardPanel.add(p1CardPane);
/////////////////////////////////
        JPanel p2CardPanel = new JPanel();
        p2CardPanel.setVisible(true);
        p2CardPanel.setLocation(1090, 200);
        p2CardPanel.setSize(180, 350);
        p2CardPanel.setBackground(new Color(0, 0, 0, 0));
//////////////////////////////////////////////
        p2CardListArea = new JTextArea("p2의 카드목록", 18, 10);
        p2CardListArea.setBackground(new Color(245, 245, 220, 50));//베이지색
        p2CardListArea.setFont(new Font("맑은 고딕", 0, 12));
        p2CardPane = new JScrollPane(p2CardListArea);
        p2CardPanel.add(p2CardPane);

        ///////////////////////////////////////////
        JPanel p1MoneyPanel = new JPanel();
        p1MoneyPanel.setVisible(true);
        p1MoneyPanel.setLocation(10, 620);
        p1MoneyPanel.setSize(200, 50);
        p1MoneyPanel.setBackground(new Color(0, 0, 0, 0));


        JPanel p2MoneyPanel = new JPanel();
        p2MoneyPanel.setVisible(true);
        p2MoneyPanel.setLocation(1070, 620);
        p2MoneyPanel.setSize(200, 50);
        p2MoneyPanel.setBackground(new Color(0, 0, 0, 3));

        JPanel gameEventPanel = new JPanel();
        gameEventPanel.setVisible(true);
        gameEventPanel.setLocation(230, 390);
        gameEventPanel.setSize(820, 180);
        gameEventPanel.setBackground(new Color(0, 0, 0, 0));
        ////////////////////////////////////////////////////////////////////MAIN EVENT//////////////////////
        textEventArea = new JTextArea(" 게임 이벤트 로그 출력 부분입니다.\r\n", 10, 32);
        textEventArea.setBackground(new Color(245, 245, 220));//베이지색
        textEventArea2 = new JTextArea(" 턴당 현금 변화 이벤트 로그 출력 부분입니다.\r\n", 10, 28);
        textEventArea2.setBackground(new Color(236, 230, 204));//아이보리색
        mainEventPane = new JScrollPane(textEventArea);
        mainEventPane2 = new JScrollPane(textEventArea2);
        gameEventPanel.add(mainEventPane);
        gameEventPanel.add(mainEventPane2);
        addMainEvent("안녕하세요.\n");
        addMainEvent("게임에 오신걸 환영합니다.\n");
        //////////////////////////


//        gameSelectPanel = new JPanel();
//        gameSelectPanel.setVisible(true);
//        gameSelectPanel.setLocation(260, 570);
//        gameSelectPanel.setSize(760, 100);
//        gameSelectPanel.setBackground(Color.yellow);
        //////////////////////////////////////////////////////////////


        // 1:1 노멀게임

        Player p1 = new Player(player1Name, true, 3000000, 1);
        Player p2 = new Player(player2Name, true, 3000000, 2);
        //플레이어 1p 2p 생성.
        int playerNum = 2;
        wholePlayer.add(p1);
        wholePlayer.add(p2);
//        Collections.shuffle(wholePlayer);//시작순서 섞어버리기
//        for (Player player : wholePlayer) {
//            addMainEvent(player.getName() + "\n");
//        }
//        addMainEvent("위 순서대로 진행됩니다.\n");

        for (int i = 0; i < landList.size() / 2; i++) {
            landList.get(i).setOwner(p1);
            p1.setLands(landList.get(i));
        }//절반 땅은 p1로 이동
        for (int i = landList.size() / 2; i < landList.size(); i++) {
            landList.get(i).setOwner(p2);
            p2.setLands(landList.get(i));
        }//절반 땅은 p2로 이동
        addMainEvent("땅 분배 완료\n");

        //초기 카드생성
        cardArrayList = gameMaker.makingCards(6, 4, 3);
        int initCardNum = 9;
        //몇 장씩 줄건지 정함.
        addMainEvent("카드 목록 생성 완료\n");

        for (int i = 0; i < initCardNum; i++) {
            p1.addHand(cardArrayList.get(i));
            //7개 p1 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        for (int i = initCardNum; i < initCardNum * 2; i++) {
            p2.addHand(cardArrayList.get(i));
            //7개 p2 핸드로 넣기
            cardIndexCount++;
            //카드 인덱스를 올려준다.
        }
        addMainEvent("핸드 넣기 완료\n");
        //7개씩 넣고, 덱에서 카드 리스트에서 제거
        ///////////////////////
/////////////////////////////////////////////////////////////////////////     땅 버튼
        land1Button.setBounds(320, 145, 88, 210);//위치와 크기
        land1Button.setBorderPainted(false);
        land1Button.setContentAreaFilled(false);
        land1Button.setFocusPainted(false); //이상한 기본설정 제거
        land1Button.setVisible(false);
        add(land1Button);
        land2Button.setBounds(408, 145, 88, 210);//위치와 크기
        land2Button.setBorderPainted(false);
        land2Button.setContentAreaFilled(false);
        land2Button.setFocusPainted(false); //이상한 기본설정 제거
        land2Button.setVisible(false);
        add(land2Button);
        land3Button.setBounds(496, 145, 88, 210);//위치와 크기
        land3Button.setBorderPainted(false);
        land3Button.setContentAreaFilled(false);
        land3Button.setFocusPainted(false); //이상한 기본설정 제거
        land3Button.setVisible(false);
        add(land3Button);
        land4Button.setBounds(584, 145, 88, 210);//위치와 크기
        land4Button.setBorderPainted(false);
        land4Button.setContentAreaFilled(false);
        land4Button.setFocusPainted(false); //이상한 기본설정 제거
        land4Button.setVisible(false);
        add(land4Button);
        land5Button.setBounds(672, 145, 88, 210);//위치와 크기
        land5Button.setBorderPainted(false);
        land5Button.setContentAreaFilled(false);
        land5Button.setFocusPainted(false); //이상한 기본설정 제거
        land5Button.setVisible(false);
        add(land5Button);
        land6Button.setBounds(760, 145, 88, 210);//위치와 크기
        land6Button.setBorderPainted(false);
        land6Button.setContentAreaFilled(false);
        land6Button.setFocusPainted(false); //이상한 기본설정 제거
        land6Button.setVisible(false);
        add(land6Button);
        land7Button.setBounds(848, 145, 88, 210);//위치와 크기
        land7Button.setBorderPainted(false);
        land7Button.setContentAreaFilled(false);
        land7Button.setFocusPainted(false); //이상한 기본설정 제거
        land7Button.setVisible(false);
        add(land7Button);
        land8Button.setBounds(936, 145, 88, 210);//위치와 크기
        land8Button.setBorderPainted(false);
        land8Button.setContentAreaFilled(false);
        land8Button.setFocusPainted(false); //이상한 기본설정 제거
        land8Button.setVisible(false);
        add(land8Button);
/////////////////////////////////////////////////////////////////////////   땅 버튼
        gameSelectPanel = new JPanel();
        gameSelectPanel.setVisible(true);
        gameSelectPanel.setLocation(660, 600);
        gameSelectPanel.setSize(360, 100);
        gameSelectPanel.setBackground(new Color(0, 0, 0, 0));
        JButton waitButton = new JButton("Ⅱ/▷"); //턴마다 누르는 버튼(턴넘기기 버튼)
        waitButton.setFont(new Font("DotumChe", Font.BOLD, 16));
        waitButton.setVisible(false);
        gameSelectPanel.add(waitButton);
        waitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new MusicThread("buttonShort.mp3", false, true);
                turnTimerThread.check = !(turnTimerThread.check);
            }
        });
        JButton miniGameButton = new JButton("미니게임"); //턴마다 누르는 버튼(턴넘기기 버튼)
        miniGameButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        miniGameButton.setVisible(false);
        gameSelectPanel.add(miniGameButton);
        miniGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new MusicThread("buttonShort.mp3", false, true);
                turnTimerThread.check = false;
                new MiniGame(nowPlayer, turnTimerThread);

            }
        });

        JButton useButton = new JButton("사용"); //턴마다 누르는 버튼(턴넘기기 버튼)
        useButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        useButton.setVisible(false);
        turnTimerThread.bogGuiMain = this;
        useButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
/////////////////////////////////////
                turnTimerThread.limitSec = 20;//타이머 시간
                turnTimerThread.check = true;//타이머 쓰레드
                new MusicThread("buttonShort.mp3", false, true);
//                refreshCardList(nowPlayer, p1, p2);//갱신!!!!!!!!!!!!!!!!!!!!!
                updateLandButton(landList, p1, p2, nowLand);///////////////갱신!
                addMainEvent(gameMaster.useCard(nowPlayer, nowLand, selectCard).toString());
                addMainEvent2(nowPlayer.getName() + "의 현금이 " + gameMaster.moneyChange(nowPlayer, true) + "만큼 변화합니다.\n");
//                refreshCardList(nowPlayer, p1, p2);//갱신!!!!!!!!!!!!!!!!!!!!!
                //랜덤이벤트 발생시킴.
//                refreshMoneyList(p1, p2);
                //현금변화
                gameMaster.checking();
//                //건물,세입자 확인 후 카드.땅에 해당 정보 입력.
//                refreshCardList(nowPlayer, p1, p2);//갱신!!!!!!!!!!!!!!!!!!!!!
                gameMaster.reflect();
//                //사건사고로 사라진 건물, 세입자 내역 반영.
                String randomMSG = gameMaster.randomEvent(nowPlayer).trim();
                String checkMSG = gameMaster.changeCheck(true).trim();
                if (!randomMSG.equals("")) {
                    addMainEvent2(randomMSG + "\r\n");
                }
                if (!checkMSG.equals("")) {
                    addMainEvent2(checkMSG + "\r\n");
                }
//                //넣은 카드에 패널티 혹은 원상복구 실시.
                gameMaster.reflect();
//                //사건사고로 사라진 건물, 세입자 내역 반영.
                selectCard = "턴 넘기기";
//                refreshCardList(nowPlayer, p1, p2);//갱신!!!!!!!!!!!!!!!!!!!!!
                gameNewTurn(cardArrayList);

                if (nowPlayer == p1) {
                    backGround = new ImageIcon(Main.class.getResource("../etc/images/gameScreenBackground" + "1p" + ".png")).getImage();
                    landEventArea.setBackground(new Color(0, 128, 255, 20)); // 하늘색 배경
                } else if (nowPlayer == p2) {
                    backGround = new ImageIcon(Main.class.getResource("../etc/images/gameScreenBackground" + "2p" + ".png")).getImage();
                    landEventArea.setBackground(new Color(218, 38, 38, 20)); // 빨간색 배경
                }
//                refreshCardList(nowPlayer, p1, p2);//갱신!!!!!!!!!!!!!!!!!!!!!
                gameMaster.checkVacancy(nowPlayer);
//                //공실 확인해서 10%씩 가산
                gameMaster.checking();
//                //건물,세입자 확인 후 카드.땅에 해당 정보 입력.
//                refreshCardList(nowPlayer, p1, p2);//갱신!!!!!!!!!!!!!!!!!!!!!
                updateLandButton(landList, p1, p2, nowLand);///////////////갱신
                if (nowPlayer.getMiniGameFire()) {
                    turnTimerThread.limitSec = 40;
                    new MiniGameFire(nowPlayer, turnTimerThread);
//                    turnTimer.check = false;
                }
                if (nowPlayer.getMiniGameLawsuit()) {
                    turnTimerThread.limitSec = 40;
                    new MiniGameLawsuit(nowPlayer, turnTimerThread);
//                    turnTimer.check = false;
                }
            }
        });
        /***************************갱신 쓰레드 모음**********************************************/
//        RefreshMoneyThread refreshMoneyThread = new RefreshMoneyThread(p1, p2);
        RefreshAllThread refreshAllThread = new RefreshAllThread(p1, p2);
        RandomPopUpMessageThread randomPopUpMessageThread = new RandomPopUpMessageThread();
        randomPopUpMessageThread.start();
        /*************************************************************************/

        JButton turnStartButton = new JButton("시작"); // 처음에 누르고 마는 버튼
        turnStartButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        turnStartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MusicThread("buttonShort.mp3", false, true);
                turnTimerThread.start();//타이머 시작
//                refreshMoneyThread.start();//현금 갱신 시작
                refreshAllThread.start();//카드갱신 시작
                p1.setName(player1Name);//이름 설정
                p2.setName(player2Name);
                p1NameField.setText(player1Name);//텍스트 필드에 이름 띄우기
                p2NameField.setText(player2Name);
                p1NameField.setVisible(true);//보이게 하기
                p2NameField.setVisible(true);
                land1Button.setVisible(true);//땅 보이게 만듦
                land2Button.setVisible(true);//땅 보이게 만듦
                land3Button.setVisible(true);//땅 보이게 만듦
                land4Button.setVisible(true);//땅 보이게 만듦
                land5Button.setVisible(true);//땅 보이게 만듦
                land6Button.setVisible(true);//땅 보이게 만듦
                land7Button.setVisible(true);//땅 보이게 만듦
                land8Button.setVisible(true);//땅 보이게 만듦
                updateLandButton(landList, p1, p2, nowLand);
                gameNewTurn(cardArrayList);///////////////////////////////////// 첫 실행부분
                turnStartButton.setVisible(false);
                useButton.setVisible(true);
                waitButton.setVisible(true);
                miniGameButton.setVisible(true);
//                refreshCardList(p1, p1, p2);//갱신!!!!!!!!!!!!!!!!!!!!!
//                refreshCardList(p2, p1, p2);//갱신!!!!!!!!!!!!!!!!!!!!!
                if (nowPlayer == p1) {
                    backGround = new ImageIcon(Main.class.getResource("../etc/images/gameScreenBackground" + "1p" + ".png")).getImage();
                } else if (nowPlayer == p2) {
                    backGround = new ImageIcon(Main.class.getResource("../etc/images/gameScreenBackground" + "2p" + ".png")).getImage();
                }
                updateLandButton(landList, p1, p2, nowLand);///////////////갱신!
//                remove(selectCardBox);
                gameStart = true;
            }
        });
        ///////////////////////////////////////////////
        p1MoneyField = new JTextField(7);
        p1MoneyField.setText(String.valueOf(p1.getCurrentMoney()));
        p1MoneyField.setHorizontalAlignment(JTextField.CENTER);
        p1MoneyField.setBackground(new Color(235, 205, 0));
        p1MoneyField.setFont(new Font("맑은 고딕", 1, 13));
//        p1MoneyField.setForeground(new Color(255,255,255));
        p1MoneyPanel.add(p1MoneyField);
        p2MoneyField = new JTextField(7);
        p2MoneyField.setText(String.valueOf(p2.getCurrentMoney()));
        p2MoneyField.setHorizontalAlignment(JTextField.CENTER);
        p2MoneyField.setBackground(new Color(235, 205, 0));
        p2MoneyField.setFont(new Font("맑은 고딕", 1, 13));
//        p2MoneyField.setForeground(new Color(255,255,255));
        p2MoneyPanel.add(p2MoneyField);
///////////////////////////////////////////////////////현금필드 2개 설정
        //게임화면에 설정될 패널 8개 생성.
        gamePanel.setVisible(false);
        gamePanel.add(dicePanel);
        gamePanel.add(landPanel);
        gamePanel.add(p1CardPanel);
        gamePanel.add(p1MoneyPanel);
        gamePanel.add(p2CardPanel);
        gamePanel.add(p2MoneyPanel);
        gamePanel.add(gameEventPanel);
        gamePanel.setSize(1280, 720);
        gamePanel.setLayout(null);
        add(gamePanel);
//////////////////////////////////////////////////////////////
        gameSelectPanel.add(useButton);
        gameSelectPanel.add(turnStartButton);
        gamePanel.add(gameSelectPanel);
        ///////////////////////


    }

    public void paint(Graphics g) {
        screenImage = createImage(Main.screenWidth, Main.screenHeight); //크기의 이미지 생성 후 스크린 이미지에 넣음
        screenGraphic = screenImage.getGraphics();
        screenDraw((Graphics2D) screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    } //화면그림

    private void screenDraw(Graphics2D g) {
        g.drawImage(backGround, 0, 0, null);
        paintComponents(g);//메뉴바나 버튼은 컴포넌트를 통해 그림.
//        System.out.println(showTime);
        if (gameStart) {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setFont(new Font("Elephant", Font.BOLD, 138));
            g.drawString("게임 시작!!", 300, 345);
            showTime++;
            if (showTime > 0) {
                gameStart = false;
                eventCheckStart = true;
                showTime = 0;
            }
        }
        if (eventCheckStart) {
            showTime++;
//            if (showTime > 300) {
//                popUpMessage = null;
//                showTime = 0;
//            } else {
                if (popUpMessage != null) {
                    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g.setFont(new Font("GulimChe", Font.BOLD, 22));
                    g.drawString(popUpMessage, 330, 355);
//                }
            }
        }
        this.repaint();
    } // 화면그림

    class RandomPopUpMessageThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int select = (int) (Math.random() * 10); // 0~9 랜덤 선택지
                if (eventCheckStart) {
                    switch (select) {
                        case 0:
                            popUpMessage = "팁:건물에 불이 나면, 불을 끄는 미니게임이 시작됩니다.";
                            break;
                        case 1:
                            popUpMessage = "팁:소송카드를 사용하면, 땅 주인은 소송전을 하게됩니다.";
                            break;
                        case 2:
                            popUpMessage = "팁:이기적으로 구는 것이 항상 도움이 될지 고민해야 합니다.";
                            break;
                        case 3:
                            popUpMessage = "팁:공실률을 고려하여, 대형빌딩은 조심해서 지어야 합니다.";
                            break;
                        case 4:
                            popUpMessage = "팁:사실 이 게임은 재미가 없습니다. 하지만 교훈은 있습니다.";
                            break;
                        case 5:
                            popUpMessage = "팁:소송전에 승리하면 좋은 스킬카드를 얻을 수 있습니다.";
                            break;
                        case 6:
                            popUpMessage = "팁:이미 불타버린 건물은 복구 할 수 없습니다.";
                            break;
                        case 7:
                            popUpMessage = "팁:5턴 마다 게임 전체에 영향을 주는 이벤트가 발생합니다.";
                            break;
                        case 8:
                            popUpMessage = "팁:장시간의 게임 이용은 건강에 좋지 않습니다.";
                            break;
                        case 9:
                            popUpMessage = "팁:일확천금을 노린다면, 대형빌딩에 대기업을 입주시키세요.";
                            break;
                    }
                }
            }
        }
    }


    private void CreateMenu() {
        menuBar = new JMenuBar(); //메뉴바를 생성
        menuBar.setLocation(0, 30);
        helpMenu = new JMenu("도움말"); //'도움말'이라는 메뉴를 생성
        menuBar.add(helpMenu); //메뉴바에 '도움말'이라는 메뉴를 추가
        developer = new JMenu("개발자");
        menuBar.add(developer);
        viewDeveloper = new JMenuItem("정보");
        developer.add(viewDeveloper);
        cardList = new JMenuItem("카드목록"); //'카드목록'라는 메뉴아이템 생성
        helpMenu.add(cardList);  //'도움말' 메뉴 안에 '카드목록'라는 메뉴아이템 추가
        helpRules = new JMenuItem("게임 규칙");
        helpMenu.add(helpRules);

        menuBar.setBorder(BorderFactory.createLineBorder(Color.black)); //메뉴바 색상 지정

        CompoListener cpListener = new CompoListener(); //아래의 컴포넌트 리스너 클래스 생성
        cardList.addActionListener(cpListener); //'카드목록' 메뉴 아이템 선택시 발생하는 이벤트 지정
        helpRules.addActionListener(cpListener);//'규칙' 메뉴 아이템 선택시 발생하는 이벤트 지정
        viewDeveloper.addActionListener(cpListener);
        setJMenuBar(menuBar);

    }
    //메뉴생성 카드목록 넣어둠

    public BogGuiMain(int menu) {

        if (menu == 0) {
            Main.screenHeight = 1000;
            Main.screenWidth = 1800;
            setUndecorated(true);
            setTitle("카드목록");//제목
            setSize(Main.screenWidth, Main.screenHeight);//창 크기
            setResizable(false);//크기조절
            setLocationRelativeTo(null); //  다른 컴포넌트와의 상대적 위치 설정 . 정중앙 = 널값
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 닫게 만드는 요소. 프레임의 닫음 버튼
            setVisible(true);//보이게 설정
            setBackground(new Color(0, 0, 0, 0));

            setLayout(null);//정위치

            backGround = new ImageIcon(Main.class.getResource("../etc/images/cardListBackground.png")).getImage();

            topLine.setBounds(0, 0, 1800, 30);

            topLine.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            });//창 이동을 위한 마우스 리스너 생성
            topLine.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();
                    setLocation(x - mouseX, y - mouseY);
                }
            });//창 이동을 위한 마우스 모션 리스너 생성. 드래그 위치 받아서 창 위치 이동
            ///////////////////////////////////////////////////////////////////메뉴바 설정///

            exitButton.setBounds(1765, 0, 30, 30);//위치와 크기
            exitButton.setBorderPainted(false);
            exitButton.setContentAreaFilled(false);
            exitButton.setFocusPainted(false); //이상한 기본설정 제거
            exitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    MusicThread buttonEnteredMusicThread = new MusicThread("button.mp3", false);
                    buttonEnteredMusicThread.start();
                    try {
                        Thread.sleep(200);
                    } catch (Exception except) {
                        except.printStackTrace();
                    }
                    Main.screenWidth = 1280;
                    Main.screenHeight = 720;
                    dispose();
                    //닫기

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    exitButton.setIcon(exitButtonEnteredImage);
                    exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    exitButton.setIcon(exitButtonBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ////////////////////////////////////////////////////////////////////메뉴바 닫기버튼 설정//
            minimizeButton.setBounds(1725, 0, 30, 30);//위치와 크기
            minimizeButton.setBorderPainted(false);
            minimizeButton.setContentAreaFilled(false);
            minimizeButton.setFocusPainted(false); //이상한 기본설정 제거
            minimizeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    MusicThread buttonEnteredMusicThread = new MusicThread("button.mp3", false);
                    buttonEnteredMusicThread.start();
                    setState(Frame.ICONIFIED);

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    minimizeButton.setIcon(minimizeButtonEnteredImage);
                    minimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    minimizeButton.setIcon(minimizeButtonBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            add(minimizeButton);
            /////////////////////////////////////////////////////////////최소화버튼
            ///////////////////////////////////////////////////////////시작버튼 설정


            add(exitButton); // 닫기버튼 추가       //순서 반대로 하면
            add(topLine); // 컴포넌트에 메뉴바 추가 // 상단 메뉴바가 버튼 덮어버림
            ////////////////////////////////////////

            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///건물
            int fontSize = 18; //카드 리스트 버튼 클릭시 나오는 폰트 사이즈
            /////////////////////////////
            JButton bcStore = new JButton(showDetailBasicImage);
            bcStore.setBounds(280, 70, 200, 240);//위치와 크기
            bcStore.setBorderPainted(false);
            bcStore.setContentAreaFilled(false);
            bcStore.setFocusPainted(false); //이상한 기본설정 제거
            bcStore.setToolTipText("스토어 카드의 정보입니다.");
            bcStore.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 일반상가 <br />유지비 : 보통 <br />적합한 세입자 : 자영업자, 스타트업, 중소기업 <br />화재확률 : 높음 <br />특이사항 : 재개발</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    bcStore.setIcon(showDetailEnteredImage);
                    bcStore.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    bcStore.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///건물
            JButton bcComplex = new JButton(showDetailBasicImage);
            bcComplex.setBounds(534, 70, 200, 240);//위치와 크기
            bcComplex.setBorderPainted(false);
            bcComplex.setContentAreaFilled(false);
            bcComplex.setFocusPainted(false); //이상한 기본설정 제거
            bcComplex.setToolTipText("주상복합 카드의 정보입니다.");
            bcComplex.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 주상복합 <br />유지비 : 보통 <br />적합한 세입자 : 자영업자, 스타트업, 중소기업, 거주자 <br />화재확률 : 보통 <br />특이사항 : 폭등</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    bcComplex.setIcon(showDetailEnteredImage);
                    bcComplex.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    bcComplex.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///건물
            JButton bcApart = new JButton(showDetailBasicImage);
            bcApart.setBounds(788, 70, 200, 240);//위치와 크기
            bcApart.setBorderPainted(false);
            bcApart.setContentAreaFilled(false);
            bcApart.setFocusPainted(false); //이상한 기본설정 제거
            bcApart.setToolTipText("아파트 카드의 정보입니다.");
            bcApart.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 아파트 <br />유지비 : 낮음 <br />적합한 세입자 : 거주자 <br />화재확률 : 낮음 <br />특이사항 : 재개발</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    bcApart.setIcon(showDetailEnteredImage);
                    bcApart.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    bcApart.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///건물
            JButton bcOfficeBuilding = new JButton(showDetailBasicImage);
            bcOfficeBuilding.setBounds(1042, 70, 200, 240);//위치와 크기
            bcOfficeBuilding.setBorderPainted(false);
            bcOfficeBuilding.setContentAreaFilled(false);
            bcOfficeBuilding.setFocusPainted(false); //이상한 기본설정 제거
            bcOfficeBuilding.setToolTipText("대형빌딩 카드의 정보입니다.");
            bcOfficeBuilding.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 대형빌딩 <br />유지비 : 높음 <br />적합한 세입자 : 대기업 <br />화재확률 : 낮음 <br />특이사항 : 대기업 외 세입자 월세 패널티 80%</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    bcOfficeBuilding.setIcon(showDetailEnteredImage);
                    bcOfficeBuilding.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    bcOfficeBuilding.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            add(bcApart);
            add(bcStore);
            add(bcComplex);
            add(bcOfficeBuilding);
            /////////////////////////////////////////////////////////건물버튼 추가 완료
            ////////////////////////////////////////////////////////세입자 버튼 시작
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///세입자
            JButton tcSelfEmployment = new JButton(showDetailBasicImage);
            tcSelfEmployment.setBounds(280, 385, 200, 240);//위치와 크기
            tcSelfEmployment.setBorderPainted(false);
            tcSelfEmployment.setContentAreaFilled(false);
            tcSelfEmployment.setFocusPainted(false); //이상한 기본설정 제거
            tcSelfEmployment.setToolTipText("자영업자 카드의 정보입니다.");
            tcSelfEmployment.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 자영업자 <br />월세 : 보통 <br />적합한 건물 : 일반상가, 주상복합 <br />계약기간 : 보통 <br />특이사항 : 야반도주</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    tcSelfEmployment.setIcon(showDetailEnteredImage);
                    tcSelfEmployment.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tcSelfEmployment.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///세입자
            JButton tcStartUp = new JButton(showDetailBasicImage);
            tcStartUp.setBounds(534, 385, 200, 240);//위치와 크기
            tcStartUp.setBorderPainted(false);
            tcStartUp.setContentAreaFilled(false);
            tcStartUp.setFocusPainted(false); //이상한 기본설정 제거
            tcStartUp.setToolTipText("스타트업 카드의 정보입니다.");
            tcStartUp.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 스타트업 <br />월세 : 낮음 <br />적합한 건물 : 일반상가, 주상복합 <br />계약기간 : 짧음 <br />특이사항 : 야반도주, 대박</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    tcStartUp.setIcon(showDetailEnteredImage);
                    tcStartUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tcStartUp.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///세입자
            JButton tcSmallCorp = new JButton(showDetailBasicImage);
            tcSmallCorp.setBounds(788, 385, 200, 240);//위치와 크기
            tcSmallCorp.setBorderPainted(false);
            tcSmallCorp.setContentAreaFilled(false);
            tcSmallCorp.setFocusPainted(false); //이상한 기본설정 제거
            tcSmallCorp.setToolTipText("중소기업 카드의 정보입니다.");
            tcSmallCorp.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 중소기업 <br />월세 : 보통 <br />적합한 건물 : 일반상가, 주상복합 <br />계약기간 : 장기 <br />특이사항 : 대박</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    tcSmallCorp.setIcon(showDetailEnteredImage);
                    tcSmallCorp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tcSmallCorp.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///세입자
            JButton tcMajorCorp = new JButton(showDetailBasicImage);
            tcMajorCorp.setBounds(1042, 385, 200, 240);//위치와 크기
            tcMajorCorp.setBorderPainted(false);
            tcMajorCorp.setContentAreaFilled(false);
            tcMajorCorp.setFocusPainted(false); //이상한 기본설정 제거
            tcMajorCorp.setToolTipText("대기업 카드의 정보입니다.");
            tcMajorCorp.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 대기업 <br />월세 : 높음 <br />적합한 건물 : 대형빌딩 <br />계약기간 : 보통 <br />특이사항 : 계약기간 종료 후 건물 매입</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    tcMajorCorp.setIcon(showDetailEnteredImage);
                    tcMajorCorp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tcMajorCorp.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///세입자
            JButton tcResident = new JButton(showDetailBasicImage);
            tcResident.setBounds(1296, 385, 200, 240);//위치와 크기
            tcResident.setBorderPainted(false);
            tcResident.setContentAreaFilled(false);
            tcResident.setFocusPainted(false); //이상한 기본설정 제거
            tcResident.setToolTipText("거주자 카드의 정보입니다.");
            tcResident.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 거주자 <br />월세 : 낮음 <br />적합한 건물 : 아파트 <br />계약기간 : 장기 <br />특이사항 : 계약기간 중 건물 매입</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    tcResident.setIcon(showDetailEnteredImage);
                    tcResident.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tcResident.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            add(tcSelfEmployment);
            add(tcStartUp);
            add(tcSmallCorp);
            add(tcMajorCorp);
            add(tcResident);
            ////////////////////////////////////////////////////////////////세입자 버튼 추가완료///
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///스킬카드
            JButton scArson = new JButton(showDetailBasicImage);
            scArson.setBounds(280, 695, 200, 240);//위치와 크기
            scArson.setBorderPainted(false);
            scArson.setContentAreaFilled(false);
            scArson.setFocusPainted(false); //이상한 기본설정 제거
            scArson.setToolTipText("방화 카드의 정보입니다.");
            scArson.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 방화카드 <br />사용비용 : 보통 <br />대상 : 모든 건물 <br />사용형태 : 1회성 <br />효과 : 사용된 땅의 건물을 불태워 없앱니다.</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    scArson.setIcon(showDetailEnteredImage);
                    scArson.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    scArson.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///스킬카드
            JButton scComplain = new JButton(showDetailBasicImage);
            scComplain.setBounds(534, 695, 200, 240);//위치와 크기
            scComplain.setBorderPainted(false);
            scComplain.setContentAreaFilled(false);
            scComplain.setFocusPainted(false); //이상한 기본설정 제거
            scComplain.setToolTipText("민원 카드의 정보입니다.");
            scComplain.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 민원카드 <br />사용비용 : 보통 <br />대상 : 일부 건물 및 모든 세입자 <br />사용형태 : 1회성 <br />효과 : <br />일반상가, 주상복합 건물 - 영업정지(세입자 퇴거)+벌금<br />아파트 - 세입자에게 보상금 지급<br />대형빌딩 - 월세 40% 차감</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    scComplain.setIcon(showDetailEnteredImage);
                    scComplain.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    scComplain.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///스킬카드
            JButton scRumor = new JButton(showDetailBasicImage);
            scRumor.setBounds(788, 695, 200, 240);//위치와 크기
            scRumor.setBorderPainted(false);
            scRumor.setContentAreaFilled(false);
            scRumor.setFocusPainted(false); //이상한 기본설정 제거
            scRumor.setToolTipText("루머 카드의 정보입니다.");
            scRumor.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 루머카드 <br />사용비용 : 보통 <br />대상 : 대형빌딩, 대기업을 제외한 모든 건물, 세입자<br />사용형태 : 1회성 <br />효과 : <br />건물 - 폭등, 재개발 확률 감소<br />세입자 - 대박확률 감소, 야반도주 확률 증가</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    scRumor.setIcon(showDetailEnteredImage);
                    scRumor.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    scRumor.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///스킬카드
            JButton scLawsuit = new JButton(showDetailBasicImage);
            scLawsuit.setBounds(1042, 695, 200, 240);//위치와 크기
            scLawsuit.setBorderPainted(false);
            scLawsuit.setContentAreaFilled(false);
            scLawsuit.setFocusPainted(false); //이상한 기본설정 제거
            scLawsuit.setToolTipText("소송 카드의 정보입니다.");
            scLawsuit.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 소송카드 <br />사용비용 : 보통 <br />대상 : 모든 세입자<br />사용형태 : 지속형 <br />효과 : 해당 땅에 입주한 세입자의 계약기간이 36개월, 월세 수입이 0이 됩니다.</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    scLawsuit.setIcon(showDetailEnteredImage);
                    scLawsuit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    scLawsuit.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////각 디테일 버튼들 만들기///스킬카드
            JButton scInsurance = new JButton(showDetailBasicImage);
            scInsurance.setBounds(1296, 695, 200, 240);//위치와 크기
            scInsurance.setBorderPainted(false);
            scInsurance.setContentAreaFilled(false);
            scInsurance.setFocusPainted(false); //이상한 기본설정 제거
            scInsurance.setToolTipText("보험 카드의 정보입니다.");
            scInsurance.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String info = "<html><p>이름 : 보험카드 <br />사용비용 : 높음 <br />대상 : 땅, 건물, 세입자 <br />사용형태 : 혼합형(지속, 1회성) <br />효과 : <br />1회성 - 건물(화재확률 제거) , 세입자(야반도주 불가)<br />지속형 - 해당 땅의 스킬카드를 방어합니다.</p></html>";
                    JLabel inform = new JLabel(info);
                    inform.setFont(new Font("맑은 고딕", Font.BOLD, fontSize));
                    JOptionPane.showMessageDialog(null, inform, "카드 정보", JOptionPane.WARNING_MESSAGE);//간단한 팝업창으로 띄우기
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    scInsurance.setIcon(showDetailEnteredImage);
                    scInsurance.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    scInsurance.setIcon(showDetailBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ///////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////스킬카드
            add(scArson);
            add(scComplain);
            add(scRumor);
            add(scLawsuit);
            add(scInsurance);
            ////////////////////////////////////////////////////스킬카드 버튼 끝


        } else if (menu == 1) {//
            //규칙부분//////////////////규칙부분//////!!!!!!!!!!!!!!규칙부분!!!!!!!!!!!!!!!!!!//////////규칙부분//////////////!!!!!!!!!!!!!!!!규칙부분
            Main.screenWidth = 1800;
            Main.screenHeight = 1000;
            setUndecorated(true);
            setTitle("규칙");//제목
            setSize(Main.screenWidth, Main.screenHeight);//창 크기
            setResizable(false);//크기조절
            setLocationRelativeTo(null); //  다른 컴포넌트와의 상대적 위치 설정 . 정중앙 = 널값
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 닫게 만드는 요소. 프레임의 닫음 버튼
            setVisible(true);//보이게 설정
            setBackground(new Color(0, 0, 0, 0));

            setLayout(null);//정위치

            backGround = new ImageIcon(Main.class.getResource("../etc/images/rulesBackground.png")).getImage();

            topLine.setBounds(0, 0, 1800, 30);

            topLine.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mouseX = e.getX();
                    mouseY = e.getY();
                }
            });//창 이동을 위한 마우스 리스너 생성
            topLine.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();
                    setLocation(x - mouseX, y - mouseY);
                }
            });//창 이동을 위한 마우스 모션 리스너 생성. 드래그 위치 받아서 창 위치 이동
            ///////////////////////////////////////////////////////////////////메뉴바 설정///

            exitButton.setBounds(1765, 0, 30, 30);//위치와 크기
            exitButton.setBorderPainted(false);
            exitButton.setContentAreaFilled(false);
            exitButton.setFocusPainted(false); //이상한 기본설정 제거
            exitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    MusicThread buttonEnteredMusicThread = new MusicThread("button.mp3", false);
                    buttonEnteredMusicThread.start();
                    try {
                        Thread.sleep(200);
                    } catch (Exception except) {
                        except.printStackTrace();
                    }
                    Main.screenWidth = 1280;
                    Main.screenHeight = 720;
                    //원상복구
                    dispose();
                    //닫기

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    exitButton.setIcon(exitButtonEnteredImage);
                    exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    exitButton.setIcon(exitButtonBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            ////////////////////////////////////////////////////////////////////메뉴바 닫기버튼 설정//
            minimizeButton.setBounds(1725, 0, 30, 30);//위치와 크기
            minimizeButton.setBorderPainted(false);
            minimizeButton.setContentAreaFilled(false);
            minimizeButton.setFocusPainted(false); //이상한 기본설정 제거
            minimizeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    MusicThread buttonEnteredMusicThread = new MusicThread("button.mp3", false);
                    buttonEnteredMusicThread.start();
                    setState(Frame.ICONIFIED);

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    minimizeButton.setIcon(minimizeButtonEnteredImage);
                    minimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    minimizeButton.setIcon(minimizeButtonBasicImage);

                }
            });//클릭, 접근, 탈출시 변동
            add(minimizeButton);
            ///////////////////////////////////////////////////////////시작버튼 설정


            add(exitButton); // 닫기버튼 추가       //순서 반대로 하면
            add(topLine); // 컴포넌트에 메뉴바 추가 // 상단 메뉴바가 버튼 덮어버림
            ////////////////////////////////////////
        }
    }//도움말 팝업창(0) , 규칙 팝업창(1) 출력부분

    public class CompoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cardList) {
                //누르면 작동할 부분 만들기
                //카드 리스트 팝업해서 보여주는 방법 사용.
                new BogGuiMain(0);// 카드리스트 출력
            } else if (e.getSource() == helpRules) {
                new BogGuiMain(1);// 규칙 출력
            } else if (e.getSource() == viewDeveloper) {
                String info = "<html><p>팀노바 5기 서종학 <br />Phone Number : 010-4129-7219<br />E-mail : unqocn@gmail.com <br /></p></html>";
                JLabel inform = new JLabel(info);
                inform.setFont(new Font("맑은 고딕", Font.BOLD, 18));
                JOptionPane.showMessageDialog(null, inform, "  개발자 정보", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    } // 이벤트 리스너

    public void addMainEvent(String event) {
        textEventArea.append(" " + event);  // 로그 내용을 JTextArea 위에 붙여주고
        textEventArea.setCaretPosition(textEventArea.getDocument().getLength());  // 맨아래로 스크롤한다.
    }//메인 텍스트박스 내용 추가

    public void addMainEvent2(String event) {
        textEventArea2.append(" " + event);  // 로그 내용을 JTextArea 위에 붙여주고
        textEventArea2.setCaretPosition(textEventArea2.getDocument().getLength());  // 맨아래로 스크롤한다.
    }//메인 텍스트박스2 내용 추가

    public void resetLandEvent() {
        landEventArea.setText("");
    }   //땅 현황(텍스트 내용) 비우기

    public void addLandEvent(String event) {
        landEventArea.append(event);  // 로그 내용을 JTextArea 위에 붙여주고
        landEventArea.setCaretPosition(landEventArea.getDocument().getLength());  // 맨아래로 스크롤한다.
    }//랜드 이벤트박스 내용 추가

    public void addP1CardList(String event) {
        p1CardListArea.setText("");
        p1CardListArea.append(event);  // 로그 내용을 JTextArea 위에 붙여주고
        p1CardListArea.setCaretPosition(p1CardListArea.getDocument().getLength());  // 맨아래로 스크롤한다.
    }//p1 cardlist add

    public void addP2CardList(String event) {
        p2CardListArea.setText("");
        p2CardListArea.append(event);  // 로그 내용을 JTextArea 위에 붙여주고
        p2CardListArea.setCaretPosition(p2CardListArea.getDocument().getLength());  // 맨아래로 스크롤한다.
    }//p2 cardlist add

    public void countDownEvent(String event) {
        countDownArea.setText("\r\n");//기존 내용 지움
        countDownArea.append("    " + event);  // 로그 내용을 JTextArea 위에 붙여주고
        countDownArea.setCaretPosition(countDownArea.getDocument().getLength());  // 맨아래로 스크롤한다.
    }//주사위 이벤트박스 내용 추가

//    public void refreshMoneyList(Player p1, Player p2) {
//        p1MoneyField.setText(String.valueOf(p1.getCurrentMoney()));
//        p2MoneyField.setText(String.valueOf(p2.getCurrentMoney()));
//    }

//    class RefreshMoneyThread extends Thread {
//        Player player1;
//        Player player2;
//
//        RefreshMoneyThread(Player p1, Player p2) {
//            player1 = p1;
//            player2 = p2;
//        }
//
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    Thread.sleep(250);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                p1MoneyField.setText(String.valueOf(player1.getCurrentMoney()));
//                p2MoneyField.setText(String.valueOf(player2.getCurrentMoney()));
//            }
//
//        }
//
//    }


//    public void refreshCardList(Player nowPlayer, Player p1, Player p2) {
//        if (nowPlayer == p1) {
//            addP1CardList(menuViewer.viewHandCardList(nowPlayer, true));//카드 목록 갱신
//        } else if (nowPlayer == p2) {
//            addP2CardList(menuViewer.viewHandCardList(nowPlayer, true));//카드 목록 갱신
//        }
//    } //카드리스트 갱신

    class RefreshAllThread extends Thread {
        Player player1;
        Player player2;

        RefreshAllThread(Player p1, Player p2) {
            player1 = p1;
            player2 = p2;

        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameMaster.handSorting(player1);
                gameMaster.handSorting(player2);
                addP1CardList(menuViewer.viewHandCardList(player1, true));//카드 목록 갱신
                addP2CardList(menuViewer.viewHandCardList(player2, true));//카드 목록 갱신
                gameMaster.checking();
                gameMaster.reflect();
                p1MoneyField.setText(String.valueOf(player1.getCurrentMoney()));
                p2MoneyField.setText(String.valueOf(player2.getCurrentMoney()));
                updateLandButton(landList, player1, player2, nowLand);

                if (player1.getCurrentMoney() < 0) {
                    JOptionPane.showMessageDialog(null, player1.getName() + "의 현금이 0이 되어 패배했습니다.\n"+player2.getName() + "의 승리로 게임을 종료합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }else if(player2.getCurrentMoney() < 0){
                    JOptionPane.showMessageDialog(null, player2.getName() + "의 현금이 0이 되어 패배했습니다.\n"+player1.getName() + "의 승리로 게임을 종료합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
//            dispose();
                }
            }
        }


    }

    public void updateLandButton(ArrayList<Land> lands, Player p1, Player p2, Land nowLand) {
        for (Land land : lands) {
            switch (land.getLocation() + 1) {
                case 1:
                    if (land.getOwner() == p1) {
                        if (land == nowLand) {
                            land1Button.setIcon(landButton1pSelectedImage);
                        } else
                            land1Button.setIcon(landButton1pImage);
                    } else if (land.getOwner() == p2) {
                        if (land == nowLand) {
                            land1Button.setIcon(landButton2pSelectedImage);
                        } else
                            land1Button.setIcon(landButton2pImage);
                    } else {
                        land1Button.setIcon(landButtonBasic);
                    }
                    break;
                case 2:
                    if (land.getOwner() == p1) {
                        if (land == nowLand) {
                            land2Button.setIcon(landButton1pSelectedImage);
                        } else
                            land2Button.setIcon(landButton1pImage);
                    } else if (land.getOwner() == p2) {
                        if (land == nowLand) {
                            land2Button.setIcon(landButton2pSelectedImage);
                        } else
                            land2Button.setIcon(landButton2pImage);
                    } else {
                        land2Button.setIcon(landButtonBasic);
                    }
                    break;
                case 3:
                    if (land.getOwner() == p1) {
                        if (land == nowLand) {
                            land3Button.setIcon(landButton1pSelectedImage);
                        } else
                            land3Button.setIcon(landButton1pImage);
                    } else if (land.getOwner() == p2) {
                        if (land == nowLand) {
                            land3Button.setIcon(landButton2pSelectedImage);
                        } else
                            land3Button.setIcon(landButton2pImage);
                    } else {
                        land3Button.setIcon(landButtonBasic);
                    }
                    break;
                case 4:
                    if (land.getOwner() == p1) {
                        if (land == nowLand) {
                            land4Button.setIcon(landButton1pSelectedImage);
                        } else
                            land4Button.setIcon(landButton1pImage);
                    } else if (land.getOwner() == p2) {
                        if (land == nowLand) {
                            land4Button.setIcon(landButton2pSelectedImage);
                        } else
                            land4Button.setIcon(landButton2pImage);
                    } else {
                        land4Button.setIcon(landButtonBasic);
                    }
                    break;
                case 5:
                    if (land.getOwner() == p1) {
                        if (land == nowLand) {
                            land5Button.setIcon(landButton1pSelectedImage);
                        } else
                            land5Button.setIcon(landButton1pImage);
                    } else if (land.getOwner() == p2) {
                        if (land == nowLand) {
                            land5Button.setIcon(landButton2pSelectedImage);
                        } else
                            land5Button.setIcon(landButton2pImage);
                    } else {
                        land5Button.setIcon(landButtonBasic);
                    }
                    break;
                case 6:
                    if (land.getOwner() == p1) {
                        if (land == nowLand) {
                            land6Button.setIcon(landButton1pSelectedImage);
                        } else
                            land6Button.setIcon(landButton1pImage);
                    } else if (land.getOwner() == p2) {
                        if (land == nowLand) {
                            land6Button.setIcon(landButton2pSelectedImage);
                        } else
                            land6Button.setIcon(landButton2pImage);
                    } else {
                        land6Button.setIcon(landButtonBasic);
                    }
                    break;
                case 7:
                    if (land.getOwner() == p1) {
                        if (land == nowLand) {
                            land7Button.setIcon(landButton1pSelectedImage);
                        } else
                            land7Button.setIcon(landButton1pImage);
                    } else if (land.getOwner() == p2) {
                        if (land == nowLand) {
                            land7Button.setIcon(landButton2pSelectedImage);
                        } else
                            land7Button.setIcon(landButton2pImage);
                    } else {
                        land7Button.setIcon(landButtonBasic);
                    }
                    break;
                case 8:
                    if (land.getOwner() == p1) {
                        if (land == nowLand) {
                            land8Button.setIcon(landButton1pSelectedImage);
                        } else
                            land8Button.setIcon(landButton1pImage);
                    } else if (land.getOwner() == p2) {
                        if (land == nowLand) {
                            land8Button.setIcon(landButton2pSelectedImage);
                        } else
                            land8Button.setIcon(landButton2pImage);
                    } else {
                        land8Button.setIcon(landButtonBasic);
                    }
                    break;
            }
        }
        // nowLand 받아서 현재 땅 위치에 하이라이트 작업 필요
    }//땅 현황(색상) 업데이트

    public void gameNewTurn(ArrayList<Card> cardArrayList) {
        gameCardPanel.removeAll();
        this.wholeTurnCount++; //1 부터 시작함.
        //        System.out.println(wholeTurnCount);
//      //만든 카드 - 처음에 나눠주고 - 한 턴에 나눠주기.
//      //사건사고로 사라진 건물, 세입자 내역 반영.
        this.nowLand = new Land();
        int select = 0;
        this.nowPlayer = wholePlayer.get(wholeTurnCount % 2);
        // 순서대로 돌려서 nowPlayer 확인
        addMainEvent("[" + this.nowPlayer.getName() + "] 의 턴입니다.\r\n");
        //누구 턴인지 알려줌
        int cardDraw = 1;
        //카드 몇장 뽑아서 나눠줄지.
        if (this.cardIndexCount != cardArrayList.size()) {
            //카드가 다 떨어지면 종료
            for (int i = this.cardIndexCount; i < this.cardIndexCount + cardDraw; i++) {
                this.nowPlayer.addHand(cardArrayList.get(i));
                //2개 p1 핸드로 넣기
                addMainEvent(this.nowPlayer.getName() + "핸드로 [" + cardArrayList.get(i).getName() + "]카드 드로우 합니다.\n");
                addMainEvent("현재 핸드에는 카드가 " + this.nowPlayer.getHand().size() + "장 있습니다.\n");
            }
            this.cardIndexCount += cardDraw;//카드 인덱스를 올려준다.
        } else {
            addMainEvent("더 이상 뽑을 카드가 없습니다.\n");
            addMainEvent("이제부터 각 플레이어의 현금이 턴마다 200000씩 감소합니다.\n");
            this.nowPlayer.setCurrentMoney(this.nowPlayer.getCurrentMoney() - 200000);
        }
        //카드 드로우 완료
        gameMaster.handSorting(this.nowPlayer);
        // 핸드 정렬로 인덱스 갱신하기.

//      // 가지고 있는 카드 요약해서 정보 보여주기 ---- 카드리스트로 갱신하면 됨.
//
        //주사위 굴려서 이동
        int diceNum = gameMaster.rollingDice(landNum, true);//주사위 굴림.
        diceButtonBasicImage = new ImageIcon(Main.class.getResource("../etc/images/dice" + (diceNum + 1) + ".png"));
        diceShow.setIcon(diceButtonBasicImage);// 주사위 이미지 갱신
        addMainEvent(this.nowPlayer.getName() + "은 " + (diceNum + 1) + "으로 이동합니다.\n");
//
        for (Land land : landList) {
            if (land.getLocation() == diceNum) {
                this.nowLand = land;
            }
        }
        //땅 목록에서 diceNum이랑 일치하는 land찾아서 this.nowLand 설정
//                ////////////////////////////////////

        addMainEvent("" + "남은 돈 : [" + this.nowPlayer.getCurrentMoney() + "]\n");

        resetLandEvent();
//                addLandEvent("\r\n==============================================================\n");
        addLandEvent(menuViewer.viewGameBoardGUI(landList, nowPlayer, this.nowLand));
        //확인완료. landEvent패널로 옮겨야함.
        ArrayList<String> cardListString = menuViewer.viewTurnCardGui(this.nowLand, nowPlayer);
        gameCardPanel = new JPanel();
        gameCardPanel.setVisible(true);
        gameCardPanel.setLocation(378, 600);
        gameCardPanel.setSize(250, 100);
        gameCardPanel.setBackground(new Color(0, 0, 0, 0));
        String[] tempArray = new String[cardListString.size()];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = cardListString.get(i);
        }

        selectCardBox = new JComboBox(tempArray);//배열
        selectCardBox.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        selectCardBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                selectCard = (String) cb.getSelectedItem();
            }
        });
        JButton helpButton = new JButton("무슨카드?"); //턴마다 누르는 버튼
        helpButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectCard.equals("턴 넘기기")) {
                    JOptionPane.showMessageDialog(null, "사용을 누르면 턴을 넘깁니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "해당 카드에 대한 자세한 정보는 도움말 - 카드목록에서 확인하세요 ^^", "알림", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        gameCardPanel.add(selectCardBox);
        gameCardPanel.add(helpButton);
        gamePanel.add(gameCardPanel);

        addMainEvent("======" + this.nowPlayer.getName() + " 턴!=====\n");
        JOptionPane.showMessageDialog(null, this.nowPlayer.getName() + "의 턴이 시작됩니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
        // 턴마다 출력되는 구분선
        //플레이어 턴 끝나는 부분
        ////check winner 50턴 부분.
        if (isEndingTurn && (this.wholeTurnCount == 50)) {
            Player winner = new Player("temp", false, 0, 0);
            for (Player player : wholePlayer) {
                if (player.getCurrentMoney() > winner.getCurrentMoney()) {
                    winner = player;
                }
            }
            JOptionPane.showMessageDialog(null, "50 턴이 지났습니다. 승자는 " + winner.getName() + "입니다.\n게임을 종료합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
//            dispose();
        }
        if (this.nowPlayer.getCurrentMoney() < 0) {
            JOptionPane.showMessageDialog(null, this.nowPlayer.getName() + "의 현금이 0이 되어 패배했습니다.\n게임을 종료합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
//            dispose();
        }

        //확률에 따른 사건 발생
        if (wholeTurnCount % 2 == 0) {
            //사건사고로 사라진 건물, 세입자 내역 반영.
            addMainEvent(menuViewer.wholeTurnEnd(this.wholeTurnCount));
            addMainEvent(gameMaster.suddenEvent(this.wholeTurnCount, true));
        }
    } // 턴마다 일어나는 모든 작업


}


