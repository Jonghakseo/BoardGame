package Week4.game.minigame;

import Week4.game.main.Land;
import Week4.game.main.Player;

import javax.swing.*;

class LawsuitData { // 공유 객체 클래스

    Player nowPlayer;
    int myLawyerPower;
    int myLawyerHP;
    int enemyLawyerPower;
    int enemyLawyerHP;
    int[] gameInfo;


    LawsuitData(Player nowPlayer, int[] gameInfo) {
        this.nowPlayer = nowPlayer;
        this.myLawyerPower = ((nowPlayer.getCurrentMoney() / 1000000)) + 1 + (nowPlayer.getMiniGameLawsuitPower()*2);
//        System.out.println(nowPlayer.getMiniGameLawsuitPower());
//        System.out.println(nowPlayer.getName());
//        System.out.println(nowPlayer.getCurrentMoney());
        //  현금/100만 +1 = 공격력 (300만 기준 공격력 4)+@ 추가공격력 재산 10%당 2씩 증가.

        this.myLawyerHP = 10+(nowPlayer.getMiniGameLawsuitPower()*5);
        //땅 1개당 체력 5추가        (땅 3개 기준 체력 20) +@ 추가체력 재산 10%당 5씩 증가.
        for (Land land: nowPlayer.getLands()) {
            if (land.getBc()!=null){
                this.myLawyerHP +=5;
            }
        }
        this.enemyLawyerPower = (int) (Math.random() * 5) + 2;//2~6 랜덤 데미지
        this.enemyLawyerHP = (int) (Math.random() * 20) + 21;//20~40 랜덤 체력
//        this.myLawyerHP = 30; //테스트용 임시 체력
        this.gameInfo = gameInfo;//0,0,0,0 상태.
    }


    public synchronized void methodA() {
        try {
            wait(2000); // ThreadA를 일시 정지 상태로 전환
        } catch (InterruptedException e) {
        }
        notify(); // 일시 정지 상태에 있는 ThreadB를 실행 대기 상태로 전환
        try {
            wait(2500); // ThreadA를 일시 정지 상태로 전환
        } catch (InterruptedException e) {
        }
    }

    public synchronized void methodB() {
        try {
            wait(2000); // ThreadB를 일시 정지 상태로 전환
        } catch (InterruptedException e) {
        }
        notify(); // 일시 정지 상태에 있는 ThreadA를 실행 대기 상태로 전환
        try {
            wait(2500); // ThreadB를 일시 정지 상태로 전환
        } catch (InterruptedException e) {
        }
    }

    public synchronized void waiting() {
        try {
            wait(20000); // ThreadB를 일시 정지 상태로 시작
        } catch (InterruptedException e) {
        }
    }

}

class MyLawyer extends Thread {
    private LawsuitData lawsuitData;
    MovingThread myLawyerAttackThread;
    MovingThread enemyLawyerAttackThread;

    public MyLawyer(LawsuitData LawsuitData, MovingThread myLawyerAttackThread, MovingThread enemyLawyerAttackThread) {
        this.lawsuitData = LawsuitData; // 공유 객체 저장
        this.myLawyerAttackThread = myLawyerAttackThread;
        this.enemyLawyerAttackThread = enemyLawyerAttackThread;
    }

    @Override
    public void run() {
        while (true) {
            if (lawsuitData.myLawyerHP < 1) {
//                System.out.println("아군 변호사 패배!");
                myLawyerAttackThread.activate = false;
                enemyLawyerAttackThread.activate = false;
                lawsuitData.nowPlayer.setMiniGameLawsuit(false);
                lawsuitData.nowPlayer.setMiniGameLawsuitWin(false);
//                System.out.println("승패 판가름"+lawsuitData.nowPlayer.getMiniGameLawsuit());
                myLawyerAttackThread.onSwitch =false;
                enemyLawyerAttackThread.onSwitch =false; // 공격쓰레드 최종 종료
                lawsuitData.gameInfo[0] = 0;
                interrupt();
                break;
            } else if (lawsuitData.enemyLawyerHP < 1) {
//                System.out.println("아군 변호사 승리!");
                myLawyerAttackThread.activate = false;
                enemyLawyerAttackThread.activate = false; //공격 모션 쓰레드 둘 다 종료.
                lawsuitData.nowPlayer.setMiniGameLawsuit(false); //끝났는지 여부
                lawsuitData.nowPlayer.setMiniGameLawsuitWin(true); //승리여부
//                System.out.println("승패 판가름"+lawsuitData.nowPlayer.getMiniGameLawsuit());
                myLawyerAttackThread.onSwitch =false;
                enemyLawyerAttackThread.onSwitch =false; // 공격쓰레드 최종 종료
                lawsuitData.gameInfo[0] = 0;
                interrupt();
                break;
            }
            enemyLawyerAttackThread.activate = !enemyLawyerAttackThread.activate;
//            System.out.println(enemyLawyerAttackThread.activate);
            myLawyerAttackThread.activate = !myLawyerAttackThread.activate;
//            System.out.println(myLawyerAttackThread.activate);

//            System.out.println("아군 변호사의 공격!");
            lawsuitData.enemyLawyerHP -= lawsuitData.myLawyerPower; // 아군 변호사의 공격. 상대 변호사 공격당함.
//            System.out.println("아군 변호사가 상대 변호사에게 피해를 " + lawsuitData.myLawyerPower + "만큼 줍니다.");
//            System.out.println("아군 변호사의 체력: " + lawsuitData.myLawyerHP + " 상대 변호사의 체력: " + lawsuitData.enemyLawyerHP);
            lawsuitData.gameInfo[0] = 1;
            lawsuitData.gameInfo[1] = lawsuitData.myLawyerPower;
            lawsuitData.gameInfo[2] = lawsuitData.myLawyerHP;
            lawsuitData.gameInfo[3] = lawsuitData.enemyLawyerHP;
            //데이터 갱신

            lawsuitData.methodA(); // 공유 객체의 methodA() 호출
        }
        interrupt();
    }
}

class EnemyLawyer extends Thread {
    private LawsuitData lawsuitData;
    MovingThread myLawyerAttackThread;
    MovingThread enemyLawyerAttackThread;

    public EnemyLawyer(LawsuitData lawsuitData, MovingThread myLawyerAttackThread, MovingThread enemyLawyerAttackThread) {
        this.lawsuitData = lawsuitData; // 공유 객체 저장
        this.myLawyerAttackThread = myLawyerAttackThread;
        this.enemyLawyerAttackThread = enemyLawyerAttackThread;
    }

    @Override
    public void run() {
        lawsuitData.waiting();
        while (true) {
            if (lawsuitData.myLawyerHP < 1) {
//                System.out.println("상대 변호사 승리!");
                myLawyerAttackThread.activate = false;
                enemyLawyerAttackThread.activate = false;
                lawsuitData.nowPlayer.setMiniGameLawsuit(false);
                lawsuitData.nowPlayer.setMiniGameLawsuitWin(false); //승리여부

//                System.out.println("승패 판가름"+lawsuitData.nowPlayer.getMiniGameLawsuit());
                myLawyerAttackThread.onSwitch =false;
                enemyLawyerAttackThread.onSwitch =false; // 공격쓰레드 최종 종료
                lawsuitData.gameInfo[0] = 0;
                interrupt();
                break;
            } else if (lawsuitData.enemyLawyerHP < 1) {
//                System.out.println("상대 변호사 패배!");
                myLawyerAttackThread.activate = false;
                enemyLawyerAttackThread.activate = false;
                lawsuitData.nowPlayer.setMiniGameLawsuit(false);
                lawsuitData.nowPlayer.setMiniGameLawsuitWin(true); //승리여부
//                System.out.println("승패 판가름"+lawsuitData.nowPlayer.getMiniGameLawsuit());
                myLawyerAttackThread.onSwitch =false;
                enemyLawyerAttackThread.onSwitch =false; // 공격쓰레드 최종 종료
                lawsuitData.gameInfo[0] = 0;
                interrupt();
                break;
            }
            enemyLawyerAttackThread.activate = !enemyLawyerAttackThread.activate;
//            System.out.println(enemyLawyerAttackThread.activate);
            myLawyerAttackThread.activate = !myLawyerAttackThread.activate;
//            System.out.println(myLawyerAttackThread.activate);

//            System.out.println("상대 변호사의 공격!");
            lawsuitData.myLawyerHP -= lawsuitData.enemyLawyerPower; // 상대 변호사의 공격. 아군 변호사 공격당함.
//            System.out.println("상대 변호사가 아군 변호사에게 피해를 " + lawsuitData.enemyLawyerPower + "만큼 줍니다.");
//            System.out.println("아군 변호사의 체력: " + lawsuitData.myLawyerHP + " 상대 변호사의 체력: " + lawsuitData.enemyLawyerHP);
            lawsuitData.gameInfo[0] = 2;
            lawsuitData.gameInfo[1] = lawsuitData.enemyLawyerPower;
            lawsuitData.gameInfo[2] = lawsuitData.myLawyerHP;
            lawsuitData.gameInfo[3] = lawsuitData.enemyLawyerHP;
            //데이터 갱신

            lawsuitData.methodB(); // 공유 객체의 methodB() 호출
//            System.out.print("b"+i);
        }
        interrupt();
    }
}

public class LawsuitGameEngine extends Thread {
    MovingThread myLawyerAttackThread;
    MovingThread enemyLawyerAttackThread;
    LawsuitData lawsuitData;

    //    public static void main(String args[]){
//        LawsuitData lawsuitData = new LawsuitData(new Player("더미 플레이어",false,3000000,1)); // 공유 객체 생성
//
//        MyLawyer myLawyer = new MyLawyer(lawsuitData);
//        EnemyLawyer enemyLawyer = new EnemyLawyer(lawsuitData);
//
//        // start
//        enemyLawyer.start();
//        myLawyer.start();
//
//    }
    LawsuitGameEngine(Player nowPlayer, MovingThread myLawyerAttackThread, MovingThread enemyLawyerAttackThread, int[] info) {
        this.lawsuitData = new LawsuitData(nowPlayer, info); // 공유 객체 생성
        this.myLawyerAttackThread = myLawyerAttackThread;
        this.enemyLawyerAttackThread = enemyLawyerAttackThread;
        this.myLawyerAttackThread.activate = false;
        this.enemyLawyerAttackThread.activate = true;

    }

    @Override
    public void run() {
        MyLawyer myLawyer = new MyLawyer(lawsuitData, myLawyerAttackThread, enemyLawyerAttackThread);
        EnemyLawyer enemyLawyer = new EnemyLawyer(lawsuitData, myLawyerAttackThread, enemyLawyerAttackThread);

        // start
        enemyLawyer.start();
        myLawyer.start();

    }

}
