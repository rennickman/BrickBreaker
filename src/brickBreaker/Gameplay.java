package brickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
	// Initialize boolean to store whether the game is running or not
	private boolean running = false;
	
	// Initialize starting score and total starting bricks
	private int score = 0;
	private int totalBricks = 21;
	
	// Declare timer object and set delay
	private Timer timer;
	private int delay = 8;
	
	// Initialize player's x position
	private int playerX = 310;
	
	// Initialize the ball's position and direction
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXDir = -1;
	private int ballYDir = -2;
	
	// Declare map generator
	private MapGenarator myMap;

	//Constructor
	Gameplay() {
		
		// Create an instance of map generator
		myMap = new MapGenarator(3, 7);
		
		// Add key event listener
		addKeyListener(this);
		
		// Set as focusable and disable focus traversal keys
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		// Assign and start timer using delay variable
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		
		// Paint background black
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// Convert map to 2d graphics and paint
		myMap.draw((Graphics2D) g);
		
		// Paint 3 borders yellow
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// Set color of score text to white
		g.setColor(Color.white);
		// Set font of score
		g.setFont(new Font("serif", Font.BOLD, 25));
		// Paint the score
		g.drawString("" + score, 590, 30);
		
		// Paint the paddle green
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		// Paint the ball yellow
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		// Check if game is won
		if (totalBricks < 0) {
			
			// Set game running to false
			running = false;
			// Stop ball by changing x and y direction to 0
			ballXDir = 0;
			ballYDir = 0;
						
			// Set color of game over text to red
			g.setColor(Color.red);
			// Set font of game over text
			g.setFont(new Font("serif", Font.BOLD, 30));
			// Paint game over text
			g.drawString("You won, score: " + score, 260, 300);
						
			// Set font of start again text
			g.setFont(new Font("serif", Font.BOLD, 20));
			// Paint start again text
			g.drawString("Press enter to restart", 230, 350);
		}
		
		// Check for game over
		if (ballPosY > 570) {
			
			// Set game running to false
			running = false;
			// Stop ball by changing x and y direction to 0
			ballXDir = 0;
			ballYDir = 0;
			
			// Set color of game over text to red
			g.setColor(Color.red);
			// Set font of game over text
			g.setFont(new Font("serif", Font.BOLD, 30));
			// Paint game over text
			g.drawString("Game over, Score: " + score, 190, 300);
			
			// Set font of start again text
			g.setFont(new Font("serif", Font.BOLD, 20));
			// Paint start again text
			g.drawString("Press enter to restart", 230, 350);
		}
		
		// Dispose of old graphics components
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Start the timer
		timer.start();
		
		// Check if game is running
		if (running) {
			
			// Create rectangle shape around ball and paddle and see if they intercept
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				// Reverse direction of y
				ballYDir = -ballYDir;
			}
			
			// Loop through the rows
			A: for (int i = 0; i < myMap.map.length; i++) {
				// Loop through the columns
				for (int j = 0; j < myMap.map[0].length; j++) {
					
					// Check if the bricks value is 1
					if (myMap.map[i][j] > 0) {
						
						// Initialize variables to store x, y, height and width of bricks
						int brickX = j * myMap.brickWidth + 80;
						int brickY = i * myMap.brickHeight + 50;
						int brickWidth = myMap.brickWidth;
						int brickHeight = myMap.brickHeight;
						
						// Create a new rectangle object around each brick
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						// Create a new rectangle object around the ball
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						//  Create a new rectangle with same values as first
						Rectangle brickRect = rect;
						
						// Check if ball intersects with a brick
						if(ballRect.intersects(brickRect)) {
							
							// Set the value of the brick to 0
							myMap.setBrickValue(0, i, j);
							// Update the number of total bricks left
							totalBricks --;
							// Add 5 to the score
							score += 5;
							
							// Check if ball hits left or right side of brick
							if (ballPosX + 19 <= ballRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								// Reverse direction of x
								ballXDir = -ballXDir;
							} else {
								// Reverse direction of y
								ballYDir = -ballYDir;
							}
							// Break out of first loop labeled A
							break A;
						}
						
					}
				}
				
			}
			
			// Make ball move by adding value of direction to x and y
			ballPosX += ballXDir;
			ballPosY += ballYDir;
			
			// Check if ball hits left border
			if (ballPosX < 0) {
				// Reverse direction of x
				ballXDir = -ballXDir; 
			}
			
			// Check if ball hits top border
			if (ballPosY < 0) {
				// Reverse direction of y
				ballYDir = -ballYDir; 
			}
			
			// Check if ball hits right border
			if (ballPosX > 670) {
				// Reverse direction of x
				ballXDir = -ballXDir; 
			}
		}
		
		// Repaint after movement
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// Check if right button is pressed
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				// Stop player going past right border
				playerX = 600;
			} else {
				// Call move right method
				moveRight();
			}
		}
		
		// Check if left button is pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX <= 10) {
				// Stop player going past left border
				playerX = 10;
			} else {
				// Call move left method
				moveLeft();
			}
		}
		
		// Check if enter is pressed and game is finished
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!running) {
				
				// Reset variables to original value
				running = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXDir = -1;
				ballYDir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				
				// Create a new instance of map
				myMap = new MapGenarator(3, 7);
				
				// Repaint all the components
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {	
	}
	
	public void moveRight() {
		
		// Set running to be true
		running = true;
		// Move players x coordinate right
		playerX += 20;
	}

	public void moveLeft() {
		
		// Set running to be true
		running = true;
		// Move players x coordinate left
		playerX -= 20;
	}
}
