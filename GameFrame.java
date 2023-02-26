
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Random;

public class GameFrame extends JFrame implements KeyListener {

    private static final int ROWS = 4;
    private static final int COLS = 4;
    Card[][] cards = new Card[ROWS][COLS];

    public GameFrame(){
        
        initFrame();
        initMenuBar();
        initCard();
        setVisible(true);
    }
        
    //generate a random Card to fill in number 2
    private void createRandomNum() {
        int count = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cards[i][j].num == 0) {
                    count++;
                }else if (cards[i][j].num == 2048){
                    URL url = getClass().getResource("img/1.png");
                    ImageIcon icon = new ImageIcon(url);
                    Image image = icon.getImage();
                    Image newImage = image.getScaledInstance(100,100,Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newImage);
                    JOptionPane.showMessageDialog(null, "Congrats！You nailed it!!", "Sucess Message", JOptionPane.PLAIN_MESSAGE, icon);
                }
            }
        }
        if (count == 0) {
            Object[] options = {"OK"};
            int x = JOptionPane.showOptionDialog(null, "Failed. Try again？", "", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (x == 0){
                initCard();
                repaint();
            }
        }
        Random r = new Random();
        int randomNum = r.nextInt(count);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (randomNum == 0 && cards[i][j].num == 0) {
                    cards[i][j].num = 2;
                    return;
                } else if (cards[i][j].num == 0) {
                    randomNum--;
                }
            }
        }
    }

    private void initCard() {
        Random r = new Random();
        int randomNum = r.nextInt(ROWS * COLS);
        int row = randomNum/ROWS;
        int col = randomNum/COLS;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cards[i][j] = new Card(i,j);
                if (i==row && j==col){
                    cards[i][j].num = 2;
                }

            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Card card;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                card = cards[i][j];
                card.draw(g);
            }
        }
    }

    private void initMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        JMenu jMenu = new JMenu("Game");
        jMenu.setFont(new Font("Times", Font.BOLD,11));
        jMenuBar.add(jMenu);
        JMenuItem restartItem = new JMenuItem("New Game");
        restartItem.setFont(new Font("Times", Font.BOLD,11));
        restartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Ok", "Cancel"};
                int x = JOptionPane.showOptionDialog(null, "Sure to restart？", "", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (x == 0){
                    initCard();
                    repaint();
                }
            }
        });
        jMenu.add(restartItem);

    }

    private void initFrame() {
        setTitle("2048");
        setSize(370,420);
        setResizable(false);
        setLocationRelativeTo(null);
//        setAlwaysOnTop(true);
        setDefaultCloseOperation(3);
        getContentPane().setBackground(new Color(56, 84, 3));
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveDown();
        }
        createRandomNum();
        repaint();
    }

    private void moveLeft() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int k = j + 1;
                while (k < COLS) {
                    if (cards[i][k].num != 0) {
                        if (cards[i][j].num == 0) {
                            cards[i][j].num = cards[i][k].num;
                            cards[i][k].num = 0;
                        } else if (cards[i][j].num == cards[i][k].num) {
                            cards[i][j].num = cards[i][j].num * 2;
                            cards[i][k].num = 0;
                            break;
                        } else {
                            int temp = cards[i][k].num;
                            cards[i][k].num = 0;
                            cards[i][j+1].num = temp;
                            break;
                        }
                    }
                    k++;
                }
                if (k == COLS) {
                    break;
                }
            }
        }
    }

    public void moveUp() {
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS; i++) {
                int k = i + 1;
                while (k < ROWS) {
                    if (cards[k][j].num != 0) {
                        if (cards[i][j].num == 0) {
                            cards[i][j].num = cards[k][j].num;
                            cards[k][j].num = 0;
                        } else if (cards[i][j].num == cards[k][j].num) {
                            cards[i][j].num = cards[i][j].num * 2;
                            cards[k][j].num = 0;
                            break;
                        } else {
                            int temp = cards[k][j].num;
                            cards[k][j].num = 0;
                            cards[i + 1][j].num = temp;
                            break;
                        }
                    }
                    k++;
                }
                if (k == ROWS) {
                    break;
                }
            }
        }
    }

    public void moveDown() {
        for (int j = COLS-1; j >= 0; j--) {
            for (int i = ROWS-1; i >= 0; i--) {
                int k = i - 1;
                while (k >= 0) {
                    if (cards[k][j].num != 0) {
                        if (cards[i][j].num == 0) {
                            cards[i][j].num = cards[k][j].num;
                            cards[k][j].num = 0;
                        } else if (cards[i][j].num == cards[k][j].num) {
                            cards[i][j].num = cards[i][j].num * 2;
                            cards[k][j].num = 0;
                            break;
                        } else {
                            int temp = cards[k][j].num;
                            cards[k][j].num = 0;
                            cards[i - 1][j].num = temp;
                            break;
                        }
                    }
                    k--;
                }
                if (k == -1) {
                    break;
                }
            }
        }
    }

    private void moveRight() {
        for (int i = ROWS-1; i >= 0; i--) {
            for (int j = COLS-1; j >= 0; j--) {
                int k = j - 1;
                while (k >=0) {
                    if (cards[i][k].num != 0) {
                        if (cards[i][j].num == 0) {
                            cards[i][j].num = cards[i][k].num;
                            cards[i][k].num = 0;
                        } else if (cards[i][j].num == cards[i][k].num) {
                            cards[i][j].num = cards[i][j].num * 2;
                            cards[i][k].num = 0;
                            break;
                        } else {
                            int temp = cards[i][k].num;
                            cards[i][k].num = 0;
                            cards[i][j-1].num = temp;
                            break;
                        }
                    }
                    k--;
                }
                if (k == -1) {
                    break;
                }
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class Card {

        private int x;
        private int y;
        private int SQUARE_SIDE = 80;
        private int i;
        private int j;
        protected int num;
        private boolean isMerge = false;

        public Card(int i, int j){
            this.i = i;
            this.j = j;
            x = 12+j*SQUARE_SIDE+(j+1)*5;
            y = 60+i*SQUARE_SIDE+(i+1)*5;
        }
        public void draw(Graphics g) {
            g.setColor(getColor());
            g.fillRoundRect(x, y, SQUARE_SIDE, SQUARE_SIDE, 4, 4);
            g.setColor(new Color(125, 78, 51));
            if (num == 0){
                return;
            } else if (num < 10) {
                g.setFont(new Font("Times", Font.BOLD, 30));
                g.drawString(num + "", x + 30, y + 50);
            } else if (num < 100) {
                g.setFont(new Font("Times", Font.BOLD, 30));
                g.drawString(num + "", x + 20, y + 50);
            } else if (num < 1000){
                g.setFont(new Font("Times", Font.BOLD, 30));
                g.drawString(num + "", x + 10, y + 50);
            } else {
                g.setFont(new Font("Times",Font.BOLD,25));
                g.drawString(num+"",x+7,y+50);
            }
        }

        private Color getColor(){
            switch(num){
                case 2:
                    return new Color(238,244,234);
                case 4:
                    return new Color(222,236,200);
                case 8:
                    return new Color(174,213,130);
                case 16:
                    return new Color(142,201,75);
                case 32:
                    return new Color(111,148,48);
                case 64:
                    return new Color(76,174,124);
                case 128:
                    return new Color(60,180,144);
                case 256:
                    return new Color(45,130,120);
                case 512:
                    return new Color(9,97,26);
                case 1024:
                    return new Color(242,177,121);
                case 2048:
                    return new Color(223,185,0);
                default:
                    return new Color(100,140,117);
            }
        }



    }
}
