package brickBrick;

import javax.swing.JFrame;

/**
 * Programm kujutab endast lihtsat ja kaasahaaravat mängu, kus peab palliga katki laskma tellised.
 * 
 * @author Karl Kallas
 * @version 1.0 2 Dec 2016
 * 
 */

public class Main {
	
	/**
	 * Sätestab Frame'i omadused ning. Peameetod.
	 */
	
	public static void main(String[] args) {
		
		JFrame raam = new JFrame();
		
		raam.setTitle("Brick Game v1.0");
		raam.setBounds(10, 10, 700, 600);
		raam.setResizable(false);
		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		M2ng gamePlay = new M2ng();
		raam.add(gamePlay);
		raam.setVisible(true);
		
	}

}
