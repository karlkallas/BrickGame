package brickBrick;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/*
 *Klass mapi ehk telliste genereerimiseks.
 */

public class Map {
	public int map[][]; 
	public int brickWidth;
	public int brickHeight;
	
	//On oluline, et konkreetne tellis ei oleks palliga põrkunud
	public Map(int row, int col) {
		map = new int [row][col];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
				
			}
			
		}
		brickWidth = 500/col;
		brickHeight = 100/row;
		
	}
	
	//Joonistab tellise ja nende äärised sinna, kus on väärtuseks 1 mitte 0
	public void draw(Graphics2D g) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(map[i][j] > 0) {
					g.setColor(Color.green);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(2));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				
				}
			}
			
		}
		
	}
	
	//Tellise väärtus (1 või 0) ning nende ridade ja veergude arv
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
		
	}
	
}