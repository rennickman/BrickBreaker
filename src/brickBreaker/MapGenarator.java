package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenarator {

	// Declare 2d array to contain all the bricks
	public int map[][];
	
	// Declare width and height of bricks
	public int brickWidth;
	public int brickHeight;
	
	// Constructor
	MapGenarator(int row, int col) {
		
		// initialize a 2d array with the number of rows and columns
		map = new int[row][col];
		
		// Loop through the rows
		for (int i = 0; i < map.length; i++) {
			// Loop through the columns
			for (int j = 0; j < map[0].length; j++) {
				// Set each brick to 1 (visible) - meaning it hasn't been hit
				map[i][j] = 1;
			}
		}
		
		// Set height and width of bricks
		brickWidth = 540 / col;
		brickHeight = 150 / row;
	}
	
	public void draw(Graphics2D g) {
		
		// Loop through the rows
		for (int i = 0; i < map.length; i++) {
			// Loop through the columns
			for (int j = 0; j < map[0].length; j++) {
				
				// Check if value of brick is 1
				if (map[i][j] > 0) {
					// Set color of brick white
					g.setColor(Color.white);
					// Paint bricks
					g.fillRect(j * brickWidth + 88, i * brickHeight + 50, brickWidth, brickHeight);
					
					// Create a black border around bricks with stroke of 3
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					// Paint outlines
					g.drawRect(j * brickWidth + 88, i * brickHeight + 50, brickWidth, brickHeight);
					
				}
				
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col) {
		// Set the brick at given location to 0 or 1
		map[row][col] = value;
	}
}
