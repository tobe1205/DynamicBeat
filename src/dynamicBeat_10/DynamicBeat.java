package dynamicBeat_10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

//GUI (Graphic User Interface) 기반의 프로그램을 만들기 위해 상속받아야하는 클래스
public class DynamicBeat extends JFrame {

    //더블 버퍼링을 위해 전체화면에 대한 이미지를 담는 인스턴스
    private Image screenImage;
    private Graphics screenGraphic;

    //버튼 기본 이미지 설정 및 종료 이미지 설정
    private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
    private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));

    //이미지 설정
    private ImageIcon startButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/startButtonEntered.png"));
    private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
    private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/quitButtonEntered.png"));
    private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/quitButtonBasic.png"));
    private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png"));
    private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
    private ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButtonEntered.png"));
    private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));
    private ImageIcon easyButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/easyButtonEntered.png"));
    private ImageIcon easyButtonBasicImage = new ImageIcon(Main.class.getResource("../images/easyButtonBasic.png"));
    private ImageIcon hardButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/hardButtonEntered.png"));
    private ImageIcon hardButtonBasicImage = new ImageIcon(Main.class.getResource("../images/hardButtonBasic.png"));
    private ImageIcon backButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/backButtonEntered.png"));
    private ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));


    //메인클래스에 위치를 기반으로 리소스를 얻어온뒤에 이미지 인스턴스를 introBackground에 초기화 시킨다.
    //아까 images폴더에 담긴 jpg 이미지를 담을 수 있는 객체 생성
    private Image gameInfoImage = new ImageIcon(Main.class.getResource("../images/gameInfo.png")).getImage();
    private Image judgmentLineImage = new ImageIcon(Main.class.getResource("../images/judgementLine.png")).getImage();
    private Image noteRouteImage = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
    private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("../images/noteRouteLine.png")).getImage();
    private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
    private Image background = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg")).getImage();
    //메뉴바 생성
    private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));

    //버튼 생성
    private JButton exitButton = new JButton(exitButtonEnteredImage);
    private JButton startButton = new JButton(startButtonBasicImage);
    private JButton quitButton = new JButton(quitButtonBasicImage);
    private JButton leftButton = new JButton(leftButtonBasicImage);
    private JButton rightButton = new JButton(rightButtonBasicImage);
    private JButton easyButton = new JButton(easyButtonBasicImage);
    private JButton hardButton = new JButton(hardButtonBasicImage);
    private JButton backButton = new JButton(backButtonBasicImage);

    //x, y좌표
    private int mouseX, mouseY;

    private boolean isMainScreen = false;
    //현재 게임 화면으로 넘어왔는지 확인
    private boolean isGameScreen = false;


    //트랙 클래스를 담을 ArrayList 생성성
    ArrayList<Track> trackList = new ArrayList<Track>();

    private Image titleImage;
    private Image selectedImage;
    private Music selectedMusic;
    //시작하자마자 노래 재생
    private Music introMusic = new Music("bgm.mp3", true);
    private int nowSelected = 0;


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


        introMusic.start();

        trackList.add(new Track("MightyLoveTitleImage.png", "MightyLoveStartImage.jpg",
                "MightyLoveGameImage.jpg", "Mighty Love Selected.mp3"
                , "Mighty Love - Joakim Karud.mp3"));
        trackList.add(new Track("WildFlowerTitleImage.png", "WildFlowerStartImage.jpg",
                "WildFlowerGameImage.jpg", "Wild Flower Selected.mp3"
                , "Wild Flower - Joakim Karud.mp3"));
        trackList.add(new Track("EnergyTitleImage.png", "EnergyStartImage.jpg",
                "EnergyGameImage.jpg", "Bensound Energy Selected.mp3"
                , "Bensound Energy.mp3"));

        //화면창 나가기 버튼 위치, 전환 설정
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
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
                buttonEnteredMusic.start();
            }

            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(exitButtonEnteredImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //종료버튼 누를때 사운드가 나오게 설정
                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
                buttonEnteredMusic.start();
                //노래가 시작하자마자 바로 종료되기때문에 try ~catch로 Thread.sleep(1000); 을하여 약간의 시간 후에 종료되게 설정
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        //화면 창 나가기 버튼 생성
        add(exitButton);

        //시작버튼 위치, 전환 설정
        startButton.setBounds(40, 200, 400, 100);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        //마우스가 올라갔을때는 basic의 이미지로 바꿔준다.
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setIcon(startButtonEnteredImage);
                //마우스가 이미지에 올라갔을 경우 커서의 모양이 손가락 모양으로 바꿔준다.
                startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
                buttonEnteredMusic.start();
            }

            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setIcon(startButtonBasicImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //종료버튼 누를때 사운드가 나오게 설정
                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
                buttonEnteredMusic.start();
                //시작된 음악 종료
                introMusic.close();
                enterMain();

            }
        });
        //시작버튼 생성
        add(startButton);

        //종료버튼 위치 설정, 변환 설정
        quitButton.setBounds(40, 330, 400, 100);
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setFocusPainted(false);
        //마우스가 올라갔을때는 basic의 이미지로 바꿔준다.
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                quitButton.setIcon(quitButtonEnteredImage);
                //마우스가 이미지에 올라갔을 경우 커서의 모양이 손가락 모양으로 바꿔준다.
                quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
                buttonEnteredMusic.start();
            }

            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e) {
                quitButton.setIcon(quitButtonBasicImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //종료버튼 누를때 사운드가 나오게 설정
                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
                buttonEnteredMusic.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }

        });
        //종료버튼 생성성
        add(quitButton);

        //왼쪽버튼 위치 설정, 변환 설정
        leftButton.setVisible(false);
        leftButton.setBounds(140, 310, 60, 60);
        leftButton.setBorderPainted(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setFocusPainted(false);
        //마우스가 올라갔을때는 basic의 이미지로 바꿔준다.
        leftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                leftButton.setIcon(leftButtonEnteredImage);
                //마우스가 이미지에 올라갔을 경우 커서의 모양이 손가락 모양으로 바꿔준다.
                leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
                buttonEnteredMusic.start();
            }

            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e) {
                leftButton.setIcon(leftButtonBasicImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //종료버튼 누를때 사운드가 나오게 설정
                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
                buttonEnteredMusic.start();
                //왼쪽버튼 이벤트 ********
                selectLaft();
            }
        });
        //왼쪽버튼 생성성
        add(leftButton);

        //오른쪽버튼 위치 설정, 변환 설정
        rightButton.setVisible(false);
        rightButton.setBounds(1080, 310, 60, 60);
        rightButton.setBorderPainted(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setFocusPainted(false);
        //마우스가 올라갔을때는 basic의 이미지로 바꿔준다.
        rightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rightButton.setIcon(rightButtonEnteredImage);
                //마우스가 이미지에 올라갔을 경우 커서의 모양이 손가락 모양으로 바꿔준다.
                rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
                buttonEnteredMusic.start();
            }

            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e) {
                rightButton.setIcon(rightButtonBasicImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //종료버튼 누를때 사운드가 나오게 설정
                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
                buttonEnteredMusic.start();
                //오른쪽버튼 이벤트 ********
                selectRight();
            }

        });
        //오른쪽버튼 생성성
        add(rightButton);

        //난이도 easy버튼 위치 설정, 변환 설정
        easyButton.setVisible(false);
        easyButton.setBounds(375, 580, 250, 67);
        easyButton.setBorderPainted(false);
        easyButton.setContentAreaFilled(false);
        easyButton.setFocusPainted(false);
        //마우스가 올라갔을때는 basic의 이미지로 바꿔준다.
        easyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                easyButton.setIcon(easyButtonEnteredImage);
                //마우스가 이미지에 올라갔을 경우 커서의 모양이 손가락 모양으로 바꿔준다.
                easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
                buttonEnteredMusic.start();
            }

            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e) {
                easyButton.setIcon(easyButtonBasicImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {

                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
                buttonEnteredMusic.start();
                //난이도 쉬움 easy 이벤트 ********
                gameStart(nowSelected, "easy");

            }

        });
        //난이도 easy버튼 생성
        add(easyButton);

        //난이도 hard버튼 위치 설정, 변환 설정
        hardButton.setVisible(false);
        hardButton.setBounds(655, 580, 250, 67);
        hardButton.setBorderPainted(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setFocusPainted(false);
        //마우스가 올라갔을때는 basic의 이미지로 바꿔준다.
        hardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hardButton.setIcon(hardButtonEnteredImage);
                //마우스가 이미지에 올라갔을 경우 커서의 모양이 손가락 모양으로 바꿔준다.
                hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
                buttonEnteredMusic.start();
            }

            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e) {
                hardButton.setIcon(hardButtonBasicImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {

                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
                buttonEnteredMusic.start();
                //난이도 어려움 hard 이벤트 ********
                gameStart(nowSelected, "hard");
            }

        });
        //난이도 hard 버튼 생성
        add(hardButton);

        // 뒤로가기 버튼 위치 설정, 변환 설정
        backButton.setVisible(false);
        backButton.setBounds(20, 50, 60, 60);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        //마우스가 올라갔을때는 basic의 이미지로 바꿔준다.
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setIcon(backButtonEnteredImage);
                //마우스가 이미지에 올라갔을 경우 커서의 모양이 손가락 모양으로 바꿔준다.
                backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
                buttonEnteredMusic.start();
            }

            //마우스가 이미지를 벗어났을 경우에 EnteredImage로 바꿔준다.
            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setIcon(backButtonBasicImage);
                //이미지를 벗어났을 경우 기본 커서 모양으로 돌아온다.
                backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {

                Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
                buttonEnteredMusic.start();
                //메인화면으로 돌아가는 뒤로가기 버튼 이벤트 ******
                backMain();
            }

        });
        //뒤로가기버튼 생성
        add(backButton);

        menuBar.setBounds(0, 0, 1280, 30);
        menuBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
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
                setLocation(x - mouseX, y - mouseY);
            }
        });
        add(menuBar);

    }

    /*paint는 JFrame상속받은 GUI게임에서 가장 첫번째로 화면을 그려주는 메서드
    화면크기만큼 생성해서 내가 원하는 내용을 그려준다.
     */
    public void paint(Graphics g) {
        screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        screenGraphic = screenImage.getGraphics();
        screenDraw((Graphics2D) screenGraphic); //글자깨짐을 방지하기 위해 Graphics2D로 형변환
        g.drawImage(screenImage, 0, 0, null);
    }

    //전체화면을 종료되는 시점전까지 계속 반복되면서 그려주는 메서드
    public void screenDraw(Graphics2D g) { //Graphic 타입에서 Graphic2D 타입으로 변경
        //screenImage라는 변수안에 introBackground 같은 이미지들을 그려주는 함수
        g.drawImage(background, 0, 0, null);
        if (isMainScreen) {
            g.drawImage(selectedImage, 340, 100, null); //add()로 추가된 것 이외에 단순 이미지등을 불러올때 사용
            g.drawImage(titleImage, 340, 70, null);
        }

        if (isGameScreen) {
            //노트구분뒤배경 생성
            g.drawImage(noteRouteImage, 228, 30, null);
            g.drawImage(noteRouteImage, 332, 30, null);
            g.drawImage(noteRouteImage, 436, 30, null);
            g.drawImage(noteRouteImage, 540, 30, null);
            g.drawImage(noteRouteImage, 640, 30, null);
            g.drawImage(noteRouteImage, 744, 30, null);
            g.drawImage(noteRouteImage, 848, 30, null);
            g.drawImage(noteRouteImage, 952, 30, null);
            //노트구분선 생성
            g.drawImage(noteRouteLineImage, 224, 30, null);
            g.drawImage(noteRouteLineImage, 328, 30, null);
            g.drawImage(noteRouteLineImage, 432, 30, null);
            g.drawImage(noteRouteLineImage, 536, 30, null);
            g.drawImage(noteRouteLineImage, 740, 30, null);
            g.drawImage(noteRouteLineImage, 844, 30, null);
            g.drawImage(noteRouteLineImage, 948, 30, null);
            g.drawImage(noteRouteLineImage, 1052, 30, null);
            g.drawImage(gameInfoImage, 0, 660, null);
            g.drawImage(judgmentLineImage, 0, 580, null);
            //노트 생성
            g.drawImage(noteBasicImage, 228, 120, null);
            g.drawImage(noteBasicImage, 332, 580, null);
            g.drawImage(noteBasicImage, 436, 500, null);
            g.drawImage(noteBasicImage, 540, 340, null);
            g.drawImage(noteBasicImage, 640, 340, null);
            g.drawImage(noteBasicImage, 744, 325, null);
            g.drawImage(noteBasicImage, 848, 305, null);
            g.drawImage(noteBasicImage, 952, 305, null);

            //게임진행화면에서의 텍스트 작성 및 설정
            g.setColor(Color.white); //글자 색상
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //글자깨짐현상 안티에이리어 적용
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 30)); //폰트, 굵기, 사이즈
            g.drawString("Joakim Karud - Mighty Love", 20, 702); //텍스트 입력 및 위치
            g.drawString("Easy", 1190, 702); //난이도 출력
            g.setFont(new Font("Arial", Font.PLAIN, 26));
            g.setColor(Color.DARK_GRAY); //글자 색상
            g.drawString("S", 270, 609);
            g.drawString("D", 374, 609);
            g.drawString("F", 478, 609);
            g.drawString("Space Bar", 580, 609);
            g.drawString("J", 784, 609);
            g.drawString("K", 889, 609);
            g.drawString("L", 993, 609);
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("Elephant", Font.BOLD, 30));
            g.drawString("000000", 565, 702);

        }
        //메뉴바 같은 항상 존재하는 이미지이며 고정된 이미지로, 버튼등이 paintComponents를 이용해서 그려낸다.
        //add()로 추가된 것들이 paintComponets로 그려낸다.
        paintComponents(g);
        this.repaint();
    }

    public void selectTrack(int nowSelected) {
        if (selectedMusic != null)
            selectedMusic.close();
        titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage())).getImage();
        selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
        selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
        selectedMusic.start();
    }

    public void selectLaft() {
        if (nowSelected == 0)
            nowSelected = trackList.size() - 1;
        else
            nowSelected--;
        selectTrack(nowSelected);
    }

    public void selectRight() {
        if (nowSelected == trackList.size() - 1)
            nowSelected = 0;
        else
            nowSelected++;
        selectTrack(nowSelected);
    }

    //현재 재생하고 있는 음악을 종료
    public void gameStart(int nowSelected, String difficulty) {
        if (selectedMusic != null)
            selectedMusic.close();
        isMainScreen = false;
        leftButton.setVisible(false);
        rightButton.setVisible(false);
        easyButton.setVisible(false);
        hardButton.setVisible(false);
        background = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage())).getImage();
        backButton.setVisible(true);
        isGameScreen = true;
    }

    public void backMain() {
        isMainScreen = true;
        leftButton.setVisible(true);
        rightButton.setVisible(true);
        easyButton.setVisible(true);
        hardButton.setVisible(true);
        background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
        backButton.setVisible(false);
        selectTrack(nowSelected);
        isGameScreen = false;
    }

    public void enterMain() {
        startButton.setVisible(false);
        quitButton.setVisible(false);
        background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
        isMainScreen = true;
        leftButton.setVisible(true);
        rightButton.setVisible(true);
        easyButton.setVisible(true);
        hardButton.setVisible(true);
        introMusic.close();
        selectTrack(0);
    }
}

