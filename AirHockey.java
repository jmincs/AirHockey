import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Scanner;

		
class MAIN
{	

	public static void main(String[] args){
	Scanner reader = new Scanner(System.in);

		System.out.println("\t");
		System.out.println("DIRECTIONS:");
		System.out.println("Hello! Welcome to 2 player Air Hockey!");
		System.out.println("Player 1 can only use the pusher on the left half, using WASD keys");
		System.out.println("Player 2 can only use the pusher on the right half, using arrow keys");
		System.out.println("Please enter full screen mode.");
		System.out.println("Type in the score to play to:");
		int score = reader.nextInt();
		if(score > 0){
			myJFrame frame = new myJFrame(score);	
			frame.show();
			frame.startGame();
		}
		
	}
}

class myJFrame extends JFrame
{
	myJPanel panel;
	public myJFrame(int score)
	{
		int num = score;
		setSize(new Dimension(850,600));//dimensions of the frame(pixels)
			//note: top left is (0,0)
			//		right is the pos x axis and down is the POS y axis
			//		The top 20 or so rows is not in the panel
			
		//MENU
//		MenuBar menuBar = new MenuBar();
//		Menu menuFile = new Menu("File");
//		MenuItem ExitItem = new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_E));		
//		ExitItem.addActionListener(new ExitListener());
//		
//		menuFile.add(ExitItem);
//		menuBar.add(menuFile);
//		this.setMenuBar(menuBar);
		
		//KeyListener
		KeyList KL = new KeyList();
		addKeyListener(KL);
		
		//PANELS
		panel = new myJPanel(KL, this, num);
		Container container = getContentPane();
		container.add(panel);
		
		repaint();
		
	}
	public void startGame()
	{
		panel.startGame();
	}
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//System.out.println("Exit");
			System.exit(0);
		}
	}
	
}
class KeyList implements KeyListener
{
	boolean up, down, left, right, enter, up1, down1, left1, right1, end;
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case 27:/* esc */			
			case KeyEvent.VK_E:		System.exit(0);
			
			case KeyEvent.VK_LEFT:		left = true;	break;
			case KeyEvent.VK_RIGHT:		right= true;	break;
			case KeyEvent.VK_UP:		up = true;		break;
			case KeyEvent.VK_DOWN:		down = true;	break;
			case KeyEvent.VK_ENTER:		enter = true;	break;
			case KeyEvent.VK_A:		left1= true;	break;
			case KeyEvent.VK_S:		down1 = true;		break;
			case KeyEvent.VK_D:		right1 = true;	break;
			case KeyEvent.VK_W:		up1 = true;	break;

		}
	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:		left = false;	break;
			case KeyEvent.VK_RIGHT:		right= false;	break;
			case KeyEvent.VK_UP:		up = false;		break;
			case KeyEvent.VK_DOWN:		down = false;	break;
			case KeyEvent.VK_ENTER:		enter = false;	break;
			case KeyEvent.VK_A:		left1= false;	break;
			case KeyEvent.VK_S:		down1 = false;		break;
			case KeyEvent.VK_D:		right1 = false;	break;
			case KeyEvent.VK_W:		up1 = false;	break;

		}
	}
		
	public void keyTyped(KeyEvent e){}
}	
class myJPanel extends JPanel
{
	KeyList KL;
	myJFrame frame;
	int count;
	Tank tank;
	Tank1 tank1;
	Puck puck;
	Timer timer;
	int max;
	public myJPanel(KeyList KL1, myJFrame frame1, int num)
	{
		max = num;
		KL = KL1;		//now I have the pointer to the Key Listener in the panel
		frame = frame1;	//now I have the pointer to the Key Listener in the frame
		
		//set Background Color
		setBackground(Color.red);
		puck = new Puck(150,425, 0,0,0,90, max);
		tank = new Tank(1375, 425, 90);
		tank1 = new Tank1(75, 425, 90);
		
	}
	public void startGame()
	{
		while(true)
		{
			repaint();
			
			try{Thread.sleep(10);}
			catch(Exception e){}
		}
	}
	public void paintComponent(Graphics g)
	{
		if(KL.enter){
			System.exit(0);
		}
		super.paintComponent(g);
		
		//I am writing most of the game itself in this method.  Why? Cus Evan Rocks.
		g.setColor(Color.black);
		g.fillRect(0, 0, 1450, 900);
		
		g.setColor(Color.red);
		g.fillRect(50, 50, 1350, 800);

		g.setColor(Color.WHITE);
		g.fillRect(700,50, 25,800);

		g.setColor(Color.WHITE);
		g.fillRect(35,310,15,200);

		g.setColor(Color.WHITE);
		g.fillRect(1400,310,15,200);
		g.setFont(new Font("Times New Roman", 1, 35));

		g.drawString("Air Hockey", 650, 35);

		tank.draw(frame, g);
		tank1.draw(frame, g);
		puck.draw(frame, g);
		puck.hit(tank, tank1, count);
		puck.bounce();
		puck.move(tank, tank1, g, count);

		
		

		if(KL.left){
			tank.turnLeft();
			tank.move(720, 60, 1390, 840);
		}
		if(KL.right){
			tank.turnRight();
			tank.move(720, 60, 1390, 840);
		}
		if(KL.up){
		tank.turnUp();
		tank.move(720, 60, 1390, 840);
	}
		if(KL.down){
			tank.turnDown();
			tank.move(720, 60, 1390, 840);
		}
			if(KL.left1){
			tank1.turnLeft();
			tank1.move(60, 60, 720, 840);
			}
		if(KL.right1){
			tank1.turnRight();
			tank1.move(60, 60, 720, 840);
		}
		if(KL.up1){
			tank1.turnUp();
			tank1.move(60, 60, 720, 840);
		}
		if(KL.down1){
			tank1.turnDown();
			tank1.move(60, 60, 720, 840);
		}
	}
}

class Tank
{
	private static final double SPEED = 6.0;
	private static final int WIDTH = 50;
	private static final int LENGTH = 50;
	
	private double x,y;
	private int direction;
	
	private ImageIcon left, right, up, down;
	
	public Tank(int x1, int y1, int direction1)
	{
		x = x1;
		y = y1;
		direction = direction1;
		
		setDirection(direction1);
	}
	public int getX()
	{
		return (int) x;
	}
	public int getY()
	{
		return (int) y;
	}
	public void setX(int newx){
		x = newx;
	}
	public void setY(int newy){
		y = newy;
	}
	public int getDirection()
	{
		return direction;
	}
	public void setDirection(int newDir)
	{
		direction = newDir;
		if(direction != 0 && direction != 90 && direction != 180 && direction != 270)
			direction = 0;
	}
	public void draw(myJFrame frame, Graphics g)
	{
		ImageIcon toUse = up;
		if(getDirection() == 90)
			toUse = right;
		if(getDirection() == 180)
			toUse = down;
		if(getDirection() == 270)
			toUse = left;
		
			g.fillOval((int) x - 30, (int) y - 30, 60, 60);
	}
	public void move(int minX, int minY, int maxX, int maxY)
	{
//		System.out.println("Moving.");
		
		if(getDirection() == 0 && (y-LENGTH/2 > minY))
			y -= SPEED;
		if(getDirection() == 180 && (y+LENGTH/2 < maxY))
			y += SPEED;
		if(getDirection() == 90 && (x+WIDTH/2 < maxX))
			x += SPEED;
		if(getDirection() == 270 && (x-WIDTH/2 > minX))
			x -= SPEED;
	}
	public void turnLeft()
	{
		setDirection(270);
	}
	public void turnRight()
	{
		setDirection(90);
	}
	public void turnUp()
	{
		setDirection(0);
	}
	public void turnDown()
	{
		setDirection(180);
	}
}
class Tank1
{
	private static final double SPEED = 6.0;
	private static final int WIDTH = 50;
	private static final int LENGTH = 50;
	private static final int MAX_SHOOT_COUNTER =50;
	// Right now, every iteration, it checks to see if the space bar is held down
	// 
	
	private double x,y;
	private int direction;
	
	private int minX, minY, maxX, maxY;
	
	private ImageIcon left1, right1, up1, down1;
	
	private int shootCounter;
	
	public Tank1(int x1, int y1, int direction1)
	{
		x = x1;
		y = y1;
		direction = direction1;
		
		setDirection(direction1);
		
		left1 = new ImageIcon("tankblueW.png");
		right1 = new ImageIcon("tankblueE.png");
		up1 = new ImageIcon("tankblueN.png");
		down1 = new ImageIcon("tankblueS.png");
		
		shootCounter = 0;
	}
	public int getX()
	{
		return (int) x;
	}
	public int getY()
	{
		return (int) y;
	}
	public void setX(int newx){
		x = newx;
	}
	public void setY(int newy){
		y = newy;
	}
	public int getDirection()
	{
		return direction;
	}
	public int getWidth()
	{
		return WIDTH;
	}
	public int getLength()
	{
		return LENGTH;
	}
	public void setDirection(int newDir)
	{
		direction = newDir;
		if(direction != 0 && direction != 90 && direction != 180 && direction != 270)
			direction = 0;
	}
	public void draw(myJFrame frame, Graphics g)
	{
		ImageIcon toUse = up1;
		if(getDirection() == 90)
			toUse = right1;
		if(getDirection() == 180)
			toUse = down1;
		if(getDirection() == 270)
			toUse = left1;
		
			g.fillOval((int) x - 30, (int) y - 30, 60, 60);
		}
	public void move(int minX, int minY, int maxX, int maxY)
	{
//		System.out.println("Moving.");
		
		if(getDirection() == 0 && (y-LENGTH/2 > minY))
			y -= SPEED;
		if(getDirection() == 180 && (y+LENGTH/2 < maxY))
			y += SPEED;
		if(getDirection() == 90 && (x+WIDTH/2 < maxX))
			x += SPEED;
		if(getDirection() == 270 && (x-WIDTH/2 > minX))
			x -= SPEED;
	}
	public void turnLeft()
	{
		setDirection(270);
	}
	public void turnRight()
	{
		setDirection(90);
	}
	public void turnUp()
	{
		setDirection(0);
	}
	public void turnDown()
	{
		setDirection(180);
	}
}
class Puck
{
	private static  double VELOX = 0.0;
	private static  double VELOY = 0.0;
	private static int count3, player1, player2;
	private static double ratex;
	private static double ratey;
	private static final int WIDTH = 20;
	private static final int LENGTH = 20;
	private ImageIcon  up;
	private double x1,y2;
	private int direction;
	private int goal;
//Graphcis.setFont(new Font("String Font Name, can be "" ", 1, size))
		
//Graphics.drawString(String, x, y) ->x,y is bottom left point of text
	public Puck(int x, int y, int count, int p1, int p2, double direction, int max){
		 player1 = p1;
		 player2 = p2;
		 x1 = x;
		 y2 = y;
		 count3 = count;
		 goal = max;
	
	}
	public int getX()
	{
		return (int) x1;
	}
	public int getY()
	{
		return (int) y2;
	}
	public int getDirection()
	{
		return direction;
	}
	public int getWidth()
	{
		return WIDTH;
	}
	public int getLength()
	{
		return LENGTH;
	}
	public void draw(myJFrame frame, Graphics g)
	{
		g.setFont(new Font("Times New Roman", 1, 20));
		g.drawString("Number of hits:	" + count3, 1050, 30);
		g.drawString("Player 1:		" + player1, 400, 875);
		g.drawString("Player 2: 	" + player2, 1100, 875);



		g.fillOval((int) x1 - 15, (int) y2 - 15, 30, 30);
		
	}
	
	public void hit(Tank tank, Tank1 tank1, int count){
		
//essentially this is checking at the moment of tangency between the two circular objects with the distance formula
		if(Math.sqrt((Math.abs(tank.getX() - x1)*Math.abs(tank.getX() - x1)) + (Math.abs(tank.getY() - y2)*Math.abs(tank.getY()- y2))) <= 45){
				VELOX = -(tank.getX() - x1)*1.4; //sets a collision: horizontal deflection, instanteous speed
				VELOY = y2 - tank.getY();
				count3++;

		}
		else if(Math.sqrt((Math.abs(tank1.getX() - x1)*Math.abs(tank1.getX() - x1)) + (Math.abs(tank1.getY() - y2)*Math.abs(tank1.getY()- y2))) <= 45){
				VELOX = -(tank1.getX() - x1)*1.4;
				VELOY = y2 - tank1.getY();
				count3++;

		}
		else{
			if(VELOX > -1 && VELOX < 1){
				VELOX = 0;
			}
			if(VELOY > -1 && VELOY < 1){
				VELOY = 0;
			}
			else{ //all of these if statements are essentially trying to lower the absolute value of the velo.
			if(VELOX < 0 && ratex > 0){
				VELOX = VELOX + ratex;
		
			}
			if(VELOX < 0 && ratex < 0){
				VELOX = VELOX - ratex;
		
			}
			if(VELOY < 0 && ratey > 0){
				VELOY = VELOY + ratey;
			}
			if(VELOY < 0 && ratey < 0){
				VELOY = VELOY - ratey;
			}
				if(VELOY > 0 && ratey < 0){
					VELOY = VELOY + ratey;

				}
				if(VELOY > 0 && ratey > 0){
					VELOY = VELOY - ratey;

				}
				if(VELOX > 0 && ratex > 0){
					VELOX = VELOX - ratex;

				}	
				if(VELOX > 0 && ratex < 0){
					VELOX = VELOX + ratex;

				}	
			}	
		}

		
		/*if(x1 >= tank.getX()-25  && y2 >= tank.getY()-25 && y2 <= tank.getY() + 25){
			VELOX = -VELOX;
		}
		if(x1 <= tank1.getX()+25 && y2 >= tank1.getY()-25 && y2 <= tank1.getY() + 25){
			VELOX = -VELOX;
		}
		*/
	}
	public void bounce(){
		if(y2 >= 800){ //checks for ceiling and floor collisions: y bounce
			VELOY = -VELOY;
			y2 = 800;
		}
		if(y2 <= 75){
			VELOY = -VELOY;
			y2 = 75;
		}
		
	}
	public void move(Tank tank, Tank1 tank1, Graphics g, int count){
		VELOX = 0.96*VELOX; //deeceleration takes place at a rate of 0.96
		VELOY = 0.96*VELOY;
		x1 += VELOX;
		y2 += VELOY;

		if(player2 == goal){ //if either player wins the game
			g.setColor(Color.black);
			g.fillRect(0, 0, 1450, 900);
			g.setColor(Color.white);
			g.setFont(new Font("Times New Roman", 1, 50));
			g.drawString("Winner is Player 2!", 500, 300);
			g.setFont(new Font("Times New Roman", 1, 30));
			g.drawString("Player 2 won in	" + count3 + "	moves!", 500, 400);
			g.drawString("Please click the Enter key to exit", 500, 500);


		}
		if(player1 == goal){
			g.setColor(Color.black);
			g.fillRect(0, 0, 1450, 900);
			g.setColor(Color.white);
			g.setFont(new Font("Times New Roman", 1, 50));
			g.drawString("Winner is Player 1!", 500, 300);
			g.setFont(new Font("Times New Roman", 1, 30));
			g.drawString("Player 1 won in	" + count3 + "	moves!", 500, 400);
			g.drawString("Please click the Enter key to exit", 500, 500);



		}
		if(x1 <= 60 || x1 >= 1350){
 //keeping track of score here: the coordintes needed to score
			if(y2 >= 325 && y2 <= 525){
				if(x1 <= 60){
					player2++;
					
				}
				if(x1 >= 1350){
					player1++;
					
				}

				int num = (int) (Math.random()*2);
				if(num == 1){ //at a score, the reset happens randomly. Either left or right half.
					x1 = 300;
					y2 = 425;
					VELOX = 0;
					VELOY = 0;
					tank.setX(1375);
					tank.setY(425);
					tank1.setX(75);
					tank1.setY(425);
				}
				else{
					x1 = 1100;
					y2 = 425;
					VELOX = 0;
					VELOY = 0;
					tank.setX(1375);
					tank.setY(425);
					tank1.setX(75);
					tank1.setY(425);
				}
				
				
			}
			else{
				VELOX = -VELOX; //this is to account for the wall on either ends that doesn't hit the white post, in which case there is just an x deflection
				if(x1 <= 60){
					x1 = 60;
				}
				if(x1 >= 1350){
					x1 = 1350;
				}
			}
		}
	}
}