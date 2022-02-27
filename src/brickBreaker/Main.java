package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// Create an object of frame
		JFrame frame = new JFrame();
		// Create an object of game play panel
		Gameplay gamePlay = new Gameplay();
	
		// Set size and position of frame
		frame.setBounds(10, 10, 700, 600);
		// Set title of frame
		frame.setTitle("Brick Breaker");
		// Set so the frame can't be resized
		frame.setResizable(false);
		// Set to be visible
		frame.setVisible(true);
		// Set to close by clicking x
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add game play panel to the frame
		frame.add(gamePlay);
	}

}
