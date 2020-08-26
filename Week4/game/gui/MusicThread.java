package Week4.game.gui;

import Week4.game.main.Main;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class MusicThread extends Thread{
    private Player player; // 재생용 플레이어
    private boolean isLoop; // 루프여부
    private File file;      // 재생할 파일
    private FileInputStream fis;        // 버퍼
    private BufferedInputStream bis;    // 버퍼

    public MusicThread(String name, boolean isLoop){
        try {
            this.isLoop = isLoop;
            file = new File(Main.class.getResource("../etc/music/"+name).toURI());
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
//            System.out.println("파일찾음");
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    public MusicThread(String name, boolean isLoop, boolean nowStart){
        if (nowStart){
        try {
            this.isLoop = isLoop;
            file = new File(Main.class.getResource("../etc/music/"+name).toURI());
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
//            System.out.println("파일찾음");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        start();
        }
    }

    public int getTime(){
        if(player==null)
            return 0;
        return player.getPosition();
    }//재생시점 반환 (필요없으면 삭제)


    public void close(){
        isLoop = false;
        player.close();
        this.interrupt();
    }//음악중지

    public void run(){
        try {
            do {
                player.play(); // 이걸 빠뜨려서 재생 안 됨...
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
            }while (isLoop);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
