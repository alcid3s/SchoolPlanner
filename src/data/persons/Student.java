package data.persons;
import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLFault;
import data.Schedule;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Student extends Person {

    public Student(String name) {
        super(name, getImages());
        target = Schedule.getInstance().getRoomList().get(new Random().nextInt(Schedule.getInstance().getRoomList().size()));

    }

    private static BufferedImage[] getImages() {
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("student.png")));
            BufferedImage[] sprites = new BufferedImage[totalImage.getWidth()/32];
            sprites[0] = totalImage.getSubimage(0,0,32,32);
            return sprites;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        if(isSpawned()) {
            if (getSprites() != null) {
                AffineTransform tx = graphics.getTransform();
                tx.translate(getPosition().getX() - 16, getPosition().getY() - 16);
                graphics.drawImage(getSprites()[0], tx, null);
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        if(isSpawned()){
            if(target != null && !target.isInRoom(this)) {
                int tileX = (int) Math.floor(getPosition().getX() / 32);
                int tileY = (int) Math.floor(getPosition().getY() / 32);

                Point direction = target.getTarget().getDirection(tileX, tileY);
                Point2D goTo = new Point2D.Double(32 * (tileX + direction.x) + 16, 32 * (tileY + direction.y) + 16);
                Point2D neededToMove = new Point2D.Double(goTo.getX() - getPosition().getX(), goTo.getY() - getPosition().getY());
                neededToMove = new Point2D.Double(neededToMove.getX() / neededToMove.distance(0,0) * speed, neededToMove.getY() / neededToMove.distance(0,0) * speed);

                double angleTo = Math.atan2(neededToMove.getY(), neededToMove.getX());
                double aDiff = angleTo - angle;
                while(aDiff < -Math.PI) {
                    aDiff += 2 * Math.PI;
                }
                while(aDiff > Math.PI) {
                    aDiff -= 2 * Math.PI;
                }
                //System.out.println("Needed to move: " + neededToMove.getX() + " " + neededToMove.getY());
                setPosition(new Point2D.Double(getPosition().getX() + (neededToMove.getX() * deltaTime), getPosition().getY() + (neededToMove.getY() * deltaTime)));
            }
        }
    }

    public static String getRandomName(){
        File file = new File("src/Names.txt");
        Random random = new Random();
        int pos = random.nextInt(1081);

        try {
            Scanner scanner = new Scanner(file);

            for(int i = 0; i < pos - 1; i++){
                scanner.nextLine();
            }
            String name = scanner.nextLine();
            scanner.close();

            return name;
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
