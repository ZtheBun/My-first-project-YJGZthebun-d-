import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Random;

public class Main {
    private static final int WINDOW_WIDTH = 420;
    private static final int WINDOW_HEIGHT = 370;
    private static final Random RANDOM = new Random();
    private static final String WHITE_IMAGE = "/YOU_JUST_GOT_ZTHEBUND_HAHAHAHAHAHA.png";
    private static final String BLACK_IMAGE = "/YOU_JUST_GOT_ZTHEBUND_HAHAHAHAHAHA2.png";

    public static void main(String[] args) {
        createWindow(new Point(100, 100));
    }

    private static void createWindow(Point initialPosition) {
        JFrame window = new JFrame("You are an idiot!");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        JLabel imageBackground = new JLabel(scaledIcon(BLACK_IMAGE));
        imageBackground.setHorizontalAlignment(JLabel.CENTER);
        imageBackground.setVerticalAlignment(JLabel.CENTER);
        window.setContentPane(imageBackground);
        window.setLocation(initialPosition);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                createWindow(randomPosition());
                createWindow(randomPosition());
            }
        });

        window.setVisible(true);

        Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        Point windowPos = new Point(window.getLocation());
        Point velocity = new Point(5, 5);

        Timer movementTimer = new Timer(50, event -> {
            int nextX = windowPos.x + velocity.x;
            int nextY = windowPos.y + velocity.y;
            int maxX = screenBounds.x + screenBounds.width - window.getWidth();
            int maxY = screenBounds.y + screenBounds.height - window.getHeight();

            if (nextX <= screenBounds.x || nextX >= maxX) {
                velocity.x = -velocity.x;
                nextX = Math.max(screenBounds.x, Math.min(nextX, maxX));
            }
            if (nextY <= screenBounds.y || nextY >= maxY) {
                velocity.y = -velocity.y;
                nextY = Math.max(screenBounds.y, Math.min(nextY, maxY));
            }

            windowPos.setLocation(nextX, nextY);
            window.setLocation(windowPos);
        });
        movementTimer.start();

        Timer imageSwitcherTimer = new Timer(200, new java.awt.event.ActionListener() {
            private boolean showingWhiteImage;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                showingWhiteImage = !showingWhiteImage;
                String nextImage = showingWhiteImage ? WHITE_IMAGE : BLACK_IMAGE;
                imageBackground.setIcon(scaledIcon(nextImage));
            }
        });
        imageSwitcherTimer.start();
    }

    private static ImageIcon scaledIcon(String resourcePath) {
        URL imageUrl = Main.class.getResource(resourcePath);
        if (imageUrl == null) {
            throw new IllegalStateException("Missing image resource: " + resourcePath);
        }
        Image image = new ImageIcon(imageUrl).getImage();
        Image scaledImage = image.getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private static Point randomPosition() {
        Rectangle screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int maxX = screenBounds.x + screenBounds.width - WINDOW_WIDTH;
        int maxY = screenBounds.y + screenBounds.height - WINDOW_HEIGHT;
        int x = screenBounds.x + RANDOM.nextInt(Math.max(1, maxX - screenBounds.x + 1));
        int y = screenBounds.y + RANDOM.nextInt(Math.max(1, maxY - screenBounds.y + 1));
        return new Point(x, y);
    }
}
