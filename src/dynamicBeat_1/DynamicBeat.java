package dynamicBeat_1;

import javax.swing.*;
//GUI (Graphic User Interface) 기반의 프로그램을 만들기 위해 상속받아야하는 클래스
public class DynamicBeat extends JFrame {

    //생성자 생성
    public DynamicBeat(){
        setTitle("Dynamic Beat");
        setSize(Main.SCREEN_WIDTH,Main.SCREEN_HEIGHT);
        //한번 실행한 창은 임의적으로 줄일 수 없도록 설정
        setResizable(false);
        //실행했을 때 화면이 정확히 정중앙에 뜨게 설정
        setLocationRelativeTo(null);
        //게임창을 껐을 때 게임도 종료되게 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //게임화면이 게임창에 보이게 설정
        setVisible(true);
    }
}
