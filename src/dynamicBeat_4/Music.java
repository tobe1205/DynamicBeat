package dynamicBeat_4;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

//Thread 하나의 작은 프로그램이라 생각 Thread 상속
public class Music extends Thread {
    //라이브러리
    private Player player;
    //무한반복 등의 설정
    private boolean isLoop;
    private File file;
    private FileInputStream fis;
    private BufferedInputStream bis;

    //곡 명과 무한반복인지 유무 입력 받는 메소드
    public Music(String name, boolean isLoop) {
        //예외 처리를 위한 try ~ catch 구문 사용
        try {
            this.isLoop = isLoop;
            //경로안에 있는 mp3파일을 불러온다.
            file = new File(Main.class.getResource("/music/" + name).toURI());
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //음악이 어떤 위치에 재생되고있는지, 만 단위 0.001초단위까지 알려준다.
    // 노트에 떨어트릴때 시간을 분석할때 사용
    public int getTime() {
        if (player == null)
            return 0;
        return player.getPosition();
    }

    //해당 곡을 그만하고 싶을 때 종료
    public void close() {
        isLoop = false;
        player.close();
        //곡 실행하는 프로그램을 종료
        this.interrupt();
    }

    //Thread를 상속받으면 반드시 오버라이딩 해야하는 메서드드
    @Override
    public void run() {
        try {
            do {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
                player.play();
            } while (isLoop); //true 값을 가지게되면 무한 재생
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
