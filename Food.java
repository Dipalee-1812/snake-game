// import java.awt.Point;
// import java.util.Random;

// public class Food {
//     private Point position;
//     private int width;
//     private int height;
//     private int x;
//     private int y;

//     public Food(int maxX, int maxY) {
//         generateFood(maxX, maxY);
//     }

//     public int getX() {
//         return x;
//     }

//     public int getY() {
//         return y;
//     }

//     public Point getPosition() {
//         return new Point(x, y);
//     }

//     public void generateFood(int maxX, int maxY) {
//         Random random = new Random();
//         x = random.nextInt(maxX);
//         y = random.nextInt(maxY);
//     }
//     public void updatePositionForResize(int newWidth, int newHeight) {
//         // Scale the position based on the new dimensions
//         int newX = (int) ((double) position.x / width * newWidth);
//         int newY = (int) ((double) position.y / height * newHeight);
//         position.setLocation(newX, newY);
//     }
    
    
// }


import java.awt.Point;
import java.util.Random;

public class Food {
    private Point position;
    private int width;
    private int height;
    private int x;
    private int y;



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public Food(int width, int height) {
        this.width = width;
        this.height = height;
        position = generateRandomPosition();
    }

    public Point getPosition() {
        return position;
    }

    public void updatePositionForResize(int newWidth, int newHeight) {
        int newX = (int) ((double) position.x / width * newWidth);
        int newY = (int) ((double) position.y / height * newHeight);
        position.setLocation(newX, newY);
    }

    private Point generateRandomPosition() {
        Random rand = new Random();
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);
        return new Point(x, y);
    }
    public void generateFood(int maxX, int maxY) {
                Random random = new Random();
                x = random.nextInt(maxX);
                y = random.nextInt(maxY);
            }
}
