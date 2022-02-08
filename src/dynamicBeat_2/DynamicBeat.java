package dynamicBeat_2;

import javax.swing.*;
import java.awt.*;

//GUI (Graphic User Interface) 기반의 프로그램을 만들기 위해 상속받아야하는 클래스
public class DynamicBeat extends JFrame {

    //더블 버퍼링을 위해 전체화면에 대한 이미지를 담는 인스턴스
    private Image screenImage;
    private Graphics screenGraphic;
    //아까 images폴더에 담긴 jpg 이미지를 담을 수 있는 객체 생성
    private Image introBackground;

    //생성자 생성
    public DynamicBeat() {
        setTitle("Dynamic Beat");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        //한번 실행한 창은 임의적으로 줄일 수 없도록 설정
        setResizable(false);
        //실행했을 때 화면이 정확히 정중앙에 뜨게 설정
        setLocationRelativeTo(null);
        //게임창을 껐을 때 게임도 종료되게 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //게임화면이 게임창에 보이게 설정
        setVisible(true);

        //메인클래스에 위치를 기반으로 리소스를 얻어온뒤에 이미지 인스턴스를 introBackground에 초기화 시킨다.
        introBackground = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg")).getImage();
    }

    /*paint는 JFrame상속받은 GUI게임에서 가장 첫번째로 화면을 그려주는 메서드
    화면크기만큼 생성해서 내가 원하는 내용을 그려준다.
     */
    public void paint(Graphics g) {
        screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = screenImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(screenImage, 0, 0, null);
    }
    //전체화면을 종료되는 시점전까지 계속 반복되면서 그려주는 메서드
    public void screenDraw(Graphics g){
        g.drawImage(introBackground,0,0,null);
        this.repaint();
    }
}
