import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private static final int CELL_SIZE = 20; // Size of each cell in pixels
    private LinkedList<Point> bodySegments;
    private Point foodPosition;
    private Direction direction;
    private Timer timer;
    private boolean gameOver;

    public SnakeGame() {
        bodySegments = new LinkedList<>();
        bodySegments.add(new Point(5, 5)); // Initial position
        direction = Direction.RIGHT; // Initial direction

        // Generate initial food position
        generateFoodPosition();

        setPreferredSize(new Dimension(400, 400)); // Set preferred panel size
        setFocusable(true);
        setBackground(Color.BLACK);
        addKeyListener(this);

        // Create timer to update game state
        timer = new Timer(100, this); // Adjust speed of the game
        timer.start();

        // Add control buttons
        JButton newGameButton = new JButton("New Game");
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });

        add(newGameButton);
        add(playButton);
        add(pauseButton);
    }

    private void resetGame() {
        // Reset game state
        bodySegments.clear();
        bodySegments.add(new Point(5, 5));
        direction = Direction.RIGHT;
        generateFoodPosition();
        gameOver = false;
        timer.start(); // Start the game loop
        requestFocusInWindow(); // Ensure panel has keyboard focus
    }

    private void pauseGame() {
        // Pause the game by stopping the timer
        timer.stop();
        requestFocusInWindow(); // Ensure panel has keyboard focus
    }

    private void resumeGame() {
        // Resume the game by starting the timer
        timer.start();
        requestFocusInWindow(); // Ensure panel has keyboard focus
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw food
        g.setColor(Color.RED);
        g.fillRect(foodPosition.x * CELL_SIZE, foodPosition.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Draw snake
        g.setColor(Color.GREEN);
        for (Point segment : bodySegments) {
            g.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        if (gameOver) {
            // Draw game over message
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", getWidth() / 2 - 40, getHeight() / 2);
        }
    }

    private void generateFoodPosition() {
        // Generate a random position for the food within the panel bounds
        int x = (int) (Math.random() * (getWidth() / CELL_SIZE));
        int y = (int) (Math.random() * (getHeight() / CELL_SIZE));
        foodPosition = new Point(x, y);
    }

    private void moveSnake() {
        // Move the snake by adding a new head position and removing the tail
        Point head = bodySegments.getFirst();
        Point newHead = new Point(head);
        switch (direction) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }
        bodySegments.addFirst(newHead);

        // Check if the snake eats the food
        if (newHead.equals(foodPosition)) {
            generateFoodPosition();
        } else {
            bodySegments.removeLast(); // Remove the tail
        }

        // Check for collisions
        if (collidedWithItself() || collidedWithBoundary()) {
            gameOver = true;
            timer.stop(); // Stop the game loop
        }

        repaint();
    }

    private boolean collidedWithItself() {
        Point head = bodySegments.getFirst();
        for (int i = 1; i < bodySegments.size(); i++) {
            if (head.equals(bodySegments.get(i))) {
                return true; // Snake collided with itself
            }
        }
        return false;
    }

    private boolean collidedWithBoundary() {
        Point head = bodySegments.getFirst();
        int width = getWidth() / CELL_SIZE;
        int height = getHeight() / CELL_SIZE;
        return head.x < 0 || head.x >= width || head.y < 0 || head.y >= height;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveSnake();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new SnakeGame());
        frame.pack();
        frame.setVisible(true);
    }
}
