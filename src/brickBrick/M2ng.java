package brickBrick;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Sätestab elemendid, joonistab pildid/ graafika, sätestab tegevused
 * klahvide vajutamisel ning loogika mängu toimimiseks.
 * abi: https://www.youtube.com/watch?v=K9qMm3JbOH0
 */

public class M2ng extends JPanel implements KeyListener, ActionListener {
	
	private boolean play = false;

	private int points = 0;
	private int totalBricks = 55;

	private Timer timer;
	private int delay = 7;
	
	private int boardLength = 100;
	private int boardX = 310;
	private int ballposX = 120;
	private int ballposY = 350;
	private int balldirX = -1;
	private int balldirY = -2;
	
	private int h = 592;
	private int w = 692;
	
	private static Image[] ball = new Image[100];
	private static Image[] board = new Image[100];
	
	
	private Map map;
	
	public M2ng() {
		map = new Map (5,11);
		ball[0] = new ImageIcon("img/pall.png").getImage();
		board[0] = new ImageIcon("img/laud.png").getImage();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
	}

	//Joonistab kõik välja
	public void paint(Graphics g) {
		
		//Taust
		for (int y = 0; y < h; y++) {
			double concentrate = (double) y / h;
			concentrate = 1 - concentrate; 
			int a = (int) (concentrate * 255);
			Color color = new Color(a, 100, 130); // 0..255
			
			g.setColor(color);
			g.drawLine(0, y, w, y);

		}
		
		//Tellised ehk map
		map.draw((Graphics2D)g);

		//Piirid
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//Skoor
		g.setColor(Color.white);
		g.setFont(new Font("helvetica", Font.PLAIN, 25));
		g.drawString("" + points, 590, 30);

		//Põrkelaud
		g.drawImage(board[0],boardX, 550, boardLength, 8, null);
		
		// Pall
		g.drawImage(ball[0],ballposX, ballposY, 20, 20, null);
		
		//Kiri õidu korral
		if(totalBricks <= 0) {
			play = false;
			balldirX = 0;
			balldirY = 0;
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("helvetica", Font.BOLD, 30));
			g.drawString("WINNN!!! Score: " + points, 190, 300);
			
			g.setFont(new Font("helvetica", Font.BOLD, 20));
			g.drawString("Press Enter to Restart ", 230, 350);
			
		}
		
		//Kiri kaotuse korral
		if(ballposY > 570) {
			play = false;
			balldirX = 0;
			balldirY = 0;
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("helvetica", Font.BOLD, 30));
			g.drawString("LOOOZER! Score: " + points, 190, 300);
			
			g.setFont(new Font("helvetica", Font.BOLD, 20));
			g.drawString("Press Enter to Restart ", 230, 350);
			
		}
		g.dispose();

	}
	
	//https://www.youtube.com/watch?v=K9qMm3JbOH0
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		if(play) {
			
			//Põrkelaualt palli põrkamine
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(boardX,550,boardLength,8))) {	
				balldirY = -balldirY;
				
			}
			
			//Tellistelt põrkamine
			A: for(int i = 0; i < map.map.length; i++) {
				for(int j = 0; j < map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeigth = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeigth);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20); //Palli ümber
						Rectangle brickRect = rect;
						
						//Kui pall puutub tellist
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							points += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								balldirX = -balldirX;	
							} else {
								balldirY = -balldirY;
								
							}
							break A;
							
						}
						
					}
					
				}
				
			}
			ballposX += balldirX;
			ballposY += balldirY;
			
			//Vasak ääris
			if(ballposX < 0) {
				balldirX = -balldirX;
				
			}
			
			//Ülemine ääris
			if(ballposY < 0) {
				balldirY = -balldirY;
				
			}
			
			//Parem ääris
			if(ballposX > 670) {
				balldirX = -balldirX;
				
			}
			
		}
			repaint();
	
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		//Paremale põrkelaua liigutamine
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (boardX >= 600) {
				boardX = 600;			
			} else {
				moveRight();
				
			}
			
		}
		
		//Vasakule põrkelaua liigutamine
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (boardX < 10) {
				boardX = 10;
			} else {
				moveLeft();
				
			}
			
		}
		
		//Enteriga restart
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 80 + (int)(Math.random() * ((600 - 80) + 1));
				ballposY = 350;
				balldirX = -1;
				balldirY = -2;
				boardX = 310;
				points = 0;
				totalBricks = 55; 
				map = new Map (5,11);
				
				repaint();
				
			}
			
		}

	}

	public void moveRight() {
		play = true;
		boardX += 20;

	}

	public void moveLeft() {
		play = true;
		boardX -= 20;

	}

}
