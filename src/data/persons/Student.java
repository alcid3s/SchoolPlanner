package data.persons;

import data.Schedule;
import data.rooms.Room;
import data.rooms.object.UsableObject;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Student extends Person {
    private int animationCounter = 0;
    private Point direction;

    public Student(String name) {
        super(name, getImages());
        Room r = Schedule.getInstance().getRoomList().get(new Random().nextInt(Schedule.getInstance().getRoomList().size()));
        //Room r = Schedule.getInstance().getRoom("Xplora4Kamer");
        Optional<UsableObject> object = r.getFreeChair(this);
        if(object.isPresent()) {
            target = object.get().getTarget();
            System.out.println("Student -> Found Target! (Room: " + r.getName() + ")");
            target.print();
        } else {
            System.out.println("Student -> Target not found! (Room: " + r.getName() + ")");
        }
    }

    private static BufferedImage[] getImages() {
        final int size = 48;
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("NPCs.png")));
            BufferedImage[] sprites = new BufferedImage[48 * 3];
            int counter = 0;
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 3; x++) {
                    sprites[counter] = totalImage.getSubimage(x * size,y * size,size, size);
                    counter++;
                }
            }
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

                // System.out.println(this.direction);
                if(this.direction != null) {
                    if (this.direction.x == 0 && this.direction.y == -1) {
                        drawAnimation(graphics, tx, Facing.NORTH);
                    } else if (this.direction.x == 1 && this.direction.y == 0) {
                        drawAnimation(graphics, tx, Facing.EAST);
                    } else if (this.direction.x == -1 && this.direction.y == 0) {
                        drawAnimation(graphics, tx, Facing.SOUTH);
                    } else if (this.direction.x == 0 && this.direction.y == 1) {
                        drawAnimation(graphics, tx, Facing.WEST);
                    } else {
                        drawAnimation(graphics, tx, Facing.STATIONARY);
                    }
                }else{
                    drawAnimation(graphics, tx, Facing.STATIONARY);
                }
            }
        }
    }

    private void drawAnimation(FXGraphics2D graphics, AffineTransform tx, Facing state) {
        if(state == Facing.NORTH && this.animationCounter >= 11 || state == Facing.NORTH && this.animationCounter < 9){
            this.animationCounter = 9;
        }else if(state == Facing.EAST && this.animationCounter >= 8 || state == Facing.EAST && this.animationCounter < 6){
            this.animationCounter = 6;
        }else if(state == Facing.SOUTH && this.animationCounter >= 5 || state == Facing.SOUTH && this.animationCounter < 3){
            this.animationCounter = 3;
        }else if(state == Facing.WEST && this.animationCounter >= 2 || state == Facing.WEST && this.animationCounter < 0){
            this.animationCounter = 0;
        }else if(state == Facing.STATIONARY){
            this.animationCounter = 0;
        }
        graphics.drawImage(getSprites()[animationCounter], tx, null);
        animationCounter++;
    }

    @Override
    public void update(double deltaTime) {
        if(isSpawned()) {
            if(target != null) {
                int tileX = (int) Math.floor(getPosition().getX() / 32);
                int tileY = (int) Math.floor(getPosition().getY() / 32);
                if(!target.isAtTarget(tileX, tileY)) {
                    this.direction = target.getDirection(tileX, tileY);
                    if(direction.x != 0 || direction.y != 0) {
                        Point2D neededToMove = calculateMovement(direction, tileX,tileY);
                        move(neededToMove, deltaTime);
                    }
                } else {
                    target = null;
                    this.direction = null;
                }
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

    public void move(Point2D neededToMove, double deltaTime) {
        setPosition(new Point2D.Double(getPosition().getX() + (neededToMove.getX() * deltaTime), getPosition().getY() + (neededToMove.getY() * deltaTime)));

    }

    public Point2D calculateMovement(Point direction, int tileX, int tileY) {
        Point2D goTo = new Point2D.Double(32 * (tileX + direction.x) + 16, 32 * (tileY + direction.y) + 16);
        Point2D neededToMove = new Point2D.Double(goTo.getX() - getPosition().getX(), goTo.getY() - getPosition().getY());
        neededToMove = new Point2D.Double(neededToMove.getX() / neededToMove.distance(0, 0) * speed, neededToMove.getY() / neededToMove.distance(0, 0) * speed);
        double angleTo = Math.atan2(neededToMove.getY(), neededToMove.getX());
        double aDiff = angleTo - angle;
        while (aDiff < -Math.PI) {
            aDiff += 2 * Math.PI;
        }
        while (aDiff > Math.PI) {
            aDiff -= 2 * Math.PI;
        }
        angle = angleTo;
        return neededToMove;
    }
}
