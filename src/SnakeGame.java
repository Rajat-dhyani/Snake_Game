
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class SnakeGame
{
	public static void main(String args[])
	{
		new Snake();
	}

}

@SuppressWarnings("serial")
class Snake extends JFrame implements KeyListener, Runnable {
	
	
    
    public void init() {
    	grow = 3; //initial length of snake
        lbx[0] = 100; //x coordinates of snake
        lby[0] = 150; //y coordinates of snake
      
        //initial direction of snake left to right
        directionx = 10; 
        directiony = 0; 
        
        //initial score calculation
        score = 0;
        food = false;
        runl = false;
        runr = true;
        runu = true;
        rund = true;
        bonusflag = true;
        
        }

    
    public void createFirstSnake() {
        // Initially length of snake = 3
        for (int i = 0; i < 3; i++) {
            jButton[i] = new JButton("lb" + i);
            jButton[i].setEnabled(false);
            jPanel1.add(jButton[i]);
            jButton[i].setBounds(lbx[i], lby[i], 10, 10);
            lbx[i + 1] = lbx[i] - 10;
            lby[i + 1] = lby[i];
        }
        
    }

    public void creatbar() {
        mymbar = new JMenuBar();

        game = new JMenu("Game");

        JMenuItem newgame = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");

        newgame.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        reset();
                    }
                });

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        game.add(newgame);
        game.addSeparator();
        game.add(exit);

        mymbar.add(game);

        level = new JMenu("Level");
        JMenuItem level1 = new JMenuItem("Level 1");
        JMenuItem level2 = new JMenuItem("Level 2");
        JMenuItem level3 = new JMenuItem("Level 3");
        JMenuItem level4 = new JMenuItem("Level 4");
        JMenuItem level5 = new JMenuItem("Level 5");
        
        level1.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                       reset();
                    }
                });
        level2.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                       increaseSpeed(2);
                    }
                });
        level3.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                       increaseSpeed(3);
                    }
                });
        level4.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                       increaseSpeed(4);
                    }
                });
        level5.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                       increaseSpeed(5);
                    }
                });
        level.add(level1);
        level.add(level2);
        level.add(level3);
        level.add(level4);
        level.add(level5);
        mymbar.add(level);

        help = new JMenu("Help");

        JMenuItem creator = new JMenuItem("Creator");
        JMenuItem instruction = new JMenuItem("Instruction");

        creator.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jPanel2, "Name : Rajat Dhyani\nContact : contact@rajatdhyani.xyz");
            }
        });
        instruction.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jPanel2, "1- Press Arrow keys to move the snake in particular direction \n2- Eat the food to gain points \n3- Score atleast 50 to Get Bonus \n4- Change level to inrease the speed of the snake \n");
            }
        });

        help.add(creator);
        help.add(instruction);
        mymbar.add(help);

        setJMenuBar(mymbar);
    }

    @SuppressWarnings("deprecation")
	void reset() {
    	//set initial conditions
        init();
        jPanel1.removeAll();

        myt.stop();

        createFirstSnake();
        jTextArea.setText("Score :" + score);

        myt = new Thread(this);
        speed = 300;
        
        myt.start();
    }
    @SuppressWarnings("deprecation")
	void increaseSpeed(int level) {
        init();
        jPanel1.removeAll();

        myt.stop();

        createFirstSnake();
        jTextArea.setText("Score :" + score);

        myt = new Thread(this);
        
        //this is to increase the speed of the snake
        speed = 300 -(level*50); 
        
        myt.start();
    }

    void growup() {
        jButton[grow] = new JButton();
        jButton[grow].setEnabled(false);
        jPanel1.add(jButton[grow]);

        int a = 10 + (10 * r.nextInt(48));
        int b = 10 + (10 * r.nextInt(23));

        lbx[grow] = a;
        lby[grow] = b;
        jButton[grow].setBounds(a, b, 10, 10);

        grow++;
    }
    // this method contains the logic to move the snake. player will define the direction
    // this method just forward the snake to the right direction which direction is pressed
    // by the player.
    @SuppressWarnings("deprecation")
	void moveForward() {
        for (int i = 0; i < grow; i++) {
            lbp[i] = jButton[i].getLocation();
        }

        lbx[0] += directionx;
        lby[0] += directiony;
        jButton[0].setBounds(lbx[0], lby[0], 10, 10);

        for (int i = 1; i < grow; i++) {
            jButton[i].setLocation(lbp[i - 1]);
        }

        if (lbx[0] == x) {
            lbx[0] = 10;
        } else if (lbx[0] == 0) {
            lbx[0] = x - 10;
        } else if (lby[0] == y) {
            lby[0] = 10;
        } else if (lby[0] == 0) {
            lby[0] = y - 10;
        }

        if (lbx[0] == lbx[grow - 1] && lby[0] == lby[grow - 1]) {
            food = false;
            score += 5;
            jTextArea.setText("Score : " + score);
            if (score % 50 == 0 && bonusflag == true) {
                jPanel1.add(bonusfood);
                bonusfood.setBounds((10 * r.nextInt(50)), (10 * r.nextInt(25)), 15, 15);
                bfp = bonusfood.getLocation();
                bonusflag = false;
            }
        }

        if (bonusflag == false) {
            if (bfp.x <= lbx[0] && bfp.y <= lby[0] && bfp.x + 10 >= lbx[0] && bfp.y + 10 >= lby[0]) {
                jPanel1.remove(bonusfood);
                score += 100;
                jTextArea.setText("Score :" + score);
                bonusflag = true;
            }
        }

        if (food == false) {
            growup();
            food = true;
        } else {
            jButton[grow - 1].setBounds(lbx[grow - 1], lby[grow - 1], 10, 10);
        }

        for (int i = 1; i < grow; i++) {
            if (lbp[0] == lbp[i]) {
            	jTextArea.setText("GAME OVER	" + score);
                try {
                    myt.join();
                } catch (InterruptedException ie) {
                }
                break;
            }
        }


        jPanel1.repaint();
        show();
    }

    public void keyPressed(KeyEvent e) {
        // snake move to left when player presses left arrow
        if (runl == true && e.getKeyCode() == 37) {
            directionx = -10; // snake move right to left by 10 pixels
            directiony = 0;
            runr = false;     // run right, snake can't move from left to right
            runu = true;      // run up, snake can move from down to up
            rund = true;      // run down, snake can move from up to down
        }
        // snake move to up when player presses up arrow
        if (runu == true && e.getKeyCode() == 38) {
            directionx = 0;
            directiony = -10; // snake move from down to up by 10 pixel
            rund = false;     // run down, snake can't move from up to down
            runr = true;      // run right, snake can move from left to right
            runl = true;      // run left, snake can move from right to left
        }
        // snake move to right when player presses right arrow
        if (runr == true && e.getKeyCode() == 39) {
            directionx = +10; // means snake move from left to right by 10 pixel
            directiony = 0;
            runl = false;     // run left, snake can't move from right to left
            runu = true;      // run up, snake can move from down to up
            rund = true;      // run down, snake can move from up to down
         }
        // snake move to down when player presses down arrow
        if (rund == true && e.getKeyCode() == 40) {
            directionx = 0;
            directiony = +10; // means snake move from left to right by 10 pixel
            runu = false;     // run up, snake can't move from down to up
            runr = true;      // run right, snake can move from left to right
            runl = true;      // run left, snake can move from right to left
       }
    }

   
    public void run() {
        for (;;) {
            // Move the snake forward. At the start of the game snake move left to right, 
            // if player press up, down, right or left arrow snake change its direction according to
            // pressed arrow
            moveForward();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ie) {
            }
        }
    }
    @SuppressWarnings("deprecation")
	Snake() {
        super("Snake");
        //Set Size of the window
        setSize(500, 330);
        //Create Menu bar with functions
        creatbar();
        //initialize all variables
        init();
        // Start of UI design
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        // TextArea will Count the score
        jTextArea = new JTextArea("Score : " + score);
        jTextArea.setEnabled(false);
        jTextArea.setBackground(Color.BLACK);
        // snake have to eat bonus food to grow up
        bonusfood = new JButton();
        bonusfood.setEnabled(false);
        // will make first snake
        createFirstSnake();

        jPanel1.setLayout(null);
        jPanel2.setLayout(new GridLayout(0, 1));
        jPanel1.setBounds(0, 0, x, y);
        jPanel1.setBackground(Color.blue);
        jPanel2.setBounds(0, y, x, 30);
        jPanel2.setBackground(Color.RED);

        jPanel2.add(jTextArea); // will contain score board
        // end of UI design
        getContentPane().setLayout(null);
        getContentPane().add(jPanel1);
        getContentPane().add(jPanel2);
        
        //initial instruction
        JOptionPane.showMessageDialog(jPanel2, "Hello There!! Welcome to Snake Game \n A quick Guide to the Game :: \n 1- Press Arrow keys to move the snake in particular direction \n2- Eat the food to gain points \n3- Score atleast 50 to Get Bonus \n4- Change level to inrease the speed of the snake  \n Lets Play The Game :)");
        
        show();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addKeyListener(this);
        // start thread
        myt = new Thread(this);
        speed = 300;
        
        myt.start(); // go to run() method
    }

    private JPanel jPanel1, jPanel2;
    private JButton[] jButton = new JButton[200];
    private JButton bonusfood;
    private JTextArea jTextArea;
    private int x = 500, y = 250, grow = 2, directionx = 1, directiony = 0, speed, score = 0;
    private int[] lbx = new int[300];
    private int[] lby = new int[300];
    private Point[] lbp = new Point[300];
    private Point bfp = new Point();
    private Thread myt;
    private boolean food = false, runl = false, runr = true, runu = true, rund = true, bonusflag = true;
    private Random r = new Random();
    private JMenuBar mymbar;
    private JMenu game, help, level;
	
    @Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

