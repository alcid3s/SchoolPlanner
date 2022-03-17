package data.persons;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Student extends Person {
    private Point2D position;
    public Student(String name){
        super(name);
        position = new Point2D.Double(1000, -1175);
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        if(getImage() != null){
            AffineTransform tx = new AffineTransform();
            tx.translate(graphics.getTransform().getTranslateX() + position.getX(), graphics.getTransform().getTranslateY() + position.getY());
            graphics.drawImage(getImage()[0], tx, null);
        }
    }



    @Override
    public void update() {

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
            System.out.println(e.getMessage());
            return "";
        }
    }
}
