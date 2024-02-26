// import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class Snake {
    private int length;
    private int width; // Width of the game board
    private int height;
    private Direction direction;
    private LinkedList<Point> bodySegments;

    public Snake(int startX, int startY) {
        length = 1;
        direction = Direction.RIGHT;
        bodySegments = new LinkedList<>();
        bodySegments.add(new Point(startX, startY));
        direction = Direction.RIGHT;
    }

    public void move() {
        Point head = bodySegments.getFirst();
        Point newHead = new Point(head.x, head.y);

        int sensorInput = readSensor();
        switch (sensorInput) {
            case 0:
                direction = Direction.UP;
                break;
            case 1:
                direction = Direction.RIGHT;
                break;
            case 2:
                direction = Direction.DOWN;
                break;
            case 3:
                direction = Direction.LEFT;
                break;
            default:
                // Do nothing or handle unexpected input
                break;
        }

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
        // if (bodySegments.size() > length) {
        //     bodySegments.removeLast();
        // }
    }

  

    public void grow() {
        length++;
    }

    public Point getHead() {
        return bodySegments.getFirst(); // Returns the position of the head
    }

    public boolean collidedWithItself() {
        Point head = bodySegments.getFirst();
        for (int i = 1; i < bodySegments.size(); i++) {
            Point segment = bodySegments.get(i);
            if (head.x == segment.x && head.y == segment.y) {
                return true;
            }
        }
        return false;
    }

    public boolean collidedWithBoundary(int maxX, int maxY) {
        Point head = bodySegments.getFirst();
        return head.x < 0 || head.x >= maxX || head.y < 0 || head.y >= maxY;
    }

    public LinkedList<Point> getBodySegments() {
        return bodySegments;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void reset(int startX, int startY) {
        bodySegments.clear(); // Clear existing body segments
        bodySegments.add(new Point(startX, startY)); // Set initial position
        direction = Direction.RIGHT; // Set initial direction
    }

   
    public void updatePositionForResize(int newWidth, int newHeight) {
        // Scale the position based on the new dimensions
        Point head = getHead();
        int newX = (int) ((double) head.x / width * newWidth);
        int newY = (int) ((double) head.y / height * newHeight);
        bodySegments.set(0, new Point(newX, newY)); 
    }
    

    public class InputHandler {
    private Random random;

    public InputHandler() {
        // Initialize the random number generator
        random = new Random();
    }
    }
    private int readSensor() {
       
        return (int) (Math.random() * 4);
    }
}
