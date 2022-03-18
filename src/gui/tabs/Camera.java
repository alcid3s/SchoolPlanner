package gui.tabs;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Camera{
    private Point2D centerPoint = new Point2D.Double(0,0);
    private float zoom = 1f; // TODO 0.75
    private double rotation = 0;
    private Point2D lastMousePos;
    private Canvas canvas;
    private Resizable resizable;
    private FXGraphics2D g2d;

    public Camera(Canvas canvas, Resizable resizable, FXGraphics2D g2d){
        this.canvas = canvas;
        this.resizable = resizable;
        this.g2d = g2d;

        canvas.setOnMousePressed(e -> this.lastMousePos = new Point2D.Double(e.getX(), e.getY()));
        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setOnScroll(e -> mouseScroll(e));
    }

    public AffineTransform getTransform(int windowWidth, int windowHeight)  {
        AffineTransform tx = new AffineTransform();
        tx.translate(windowWidth/2, windowHeight/2);
        tx.scale(zoom, zoom);
        tx.translate(centerPoint.getX(), centerPoint.getY());
        tx.rotate(rotation);
        return tx;
    }

    public void mouseDragged(MouseEvent e) {
        if(e.getButton() == MouseButton.MIDDLE) {
            centerPoint = new Point2D.Double(
                    centerPoint.getX() - (lastMousePos.getX() - e.getX()) / zoom,
                    centerPoint.getY() - (lastMousePos.getY() - e.getY()) / zoom
            );
            lastMousePos = new Point2D.Double(e.getX(), e.getY());
            resizable.draw(g2d);
        }
    }

    public void mouseScroll(ScrollEvent e){
        float zoom = this.zoom * (float)(1 + e.getDeltaY()/500.0f);
        if(zoom > 0.42258725 && zoom < 1.1535156 || true){
            this.zoom = zoom;
            resizable.draw(g2d);
        }
    }
}
