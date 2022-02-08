package dynamicBeat_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

//GUI (Graphic User Interface) 기반의 프로그램을 만들기 위해 상속받아야하는 클래스
public class DynamicBeat extends JFrame {

    //더블 버퍼링을 위해 전체화면에 대한 이미지를 담는 인스턴스
    private Image screenImage;
    private Graphics screenGraphic;

    //버튼 기본 이미지 설정 및 종료 이미지 설정
    private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
    private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));

    //메인클래스에 위치를 기반으로 리소스를 얻어온뒤에 이미지 인스턴스를 introBackground에 초기화 시킨다.
    //아까 images폴더에 담긴 jpg 이미지를 담을 수 있는 객체 생성
    private Image introBackground = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg")).getImage();
    //메뉴바 생성
    private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));

    //버튼 생성
    private JButton exitButton = new JButton(exitButtonEnteredImage);

    //x, y좌표
    private int mouseX, mouseY;

    //생성자 생성
    public DynamicBeat() {
        setUndecorated(true);
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
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        //종료버튼 위치 설정
        exitButton.setBounds(1245, 0, 30, 30);
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        //마우스가 올라갔을때는 basic의 이미지로 바꿔준다.
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(exitButtonBasicImage);
                //마우스가 이미지에 올라갔을 경우 커서의 모양이 손가락 모양으로 바꿔준다.
                exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3",false);
                buttonEnteredMusic.start();
            }
            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e){
                exitButton.setIcon(exitButtonEnteredImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mousePressed(MouseEvent e){
                //종료버튼 누를때 사운드가 나오게 설정
                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3",false);
                buttonEnteredMusic.start();
                //노래가 시작하자마자 바로 종료되기때문에 try ~catch로 Thread.sleep(1000); 을하여 약간의 시간 후에 종료되게 설정
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        add(exitButton);

        menuBar.setBounds(0, 0, 1280, 30);
        menuBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        //드래그를 하면 x와 y의 좌표를 얻어와 게임창의 위치를 바꿔준다.
        menuBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y-mouseY);
            }
        });
        add(menuBar);



        Music introMusic = new Music("bgm.mp3", true);
        introMusic.start();
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
    public void screenDraw(Graphics g) {
        //screenImage라는 변수안에 introBackground 같은 이미지들을 그려주는 함수
        g.drawImage(introBackground, 0, 0, null);
        //메뉴바 같은 항상 존재하는 이미지이며 고정된 이미지로, 버튼등이 paintComponents를 이용해서 그려낸다.
        paintComponents(g);
        this.repaint();
    }
}
