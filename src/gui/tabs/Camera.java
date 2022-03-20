package gui.tabs;

import data.tilted.TiledMap;
import javafx.scene.Node;
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
    private Point2D centerPoint;
    private float zoom = 0.75f;
    private double rotation = 0;
    private Point2D lastMousePos;
    private SimulationTab tab;
    private FXGraphics2D g2d;
    private Node clickEvents;

    public Camera(SimulationTab tab){
        this.clickEvents = tab.getPane();
        this.tab = tab;
        centerPoint = new Point2D.Double(0,0);
        clickEvents.setOnMousePressed(e -> this.lastMousePos = new Point2D.Double(e.getX(), e.getY()));
        clickEvents.setOnMouseDragged(e -> mouseDragged(e));
        clickEvents.setOnScroll(e -> mouseScroll(e));
    }

    public AffineTransform getTransform()  {
        AffineTransform tx = new AffineTransform();
        tx.scale(zoom, zoom);
        tx.translate(centerPoint.getX(), centerPoint.getY());
        tx.rotate(rotation);
        return tx;
    }

    public void mouseDragged(MouseEvent e) {
        if(e.getButton() == MouseButton.PRIMARY) {
            centerPoint = new Point2D.Double(
                    centerPoint.getX() - (lastMousePos.getX() - e.getX()) / zoom,
                    centerPoint.getY() - (lastMousePos.getY() - e.getY()) / zoom
            );
            lastMousePos = new Point2D.Double(e.getX(), e.getY());
            tab.setUpdateBackground(true);
        }
    }

    public void mouseScroll(ScrollEvent e){
        float zoom = this.zoom * (float)(1 + e.getDeltaY() / 500.0f);
        if(zoom > 0.42258725 && zoom < 1.1535156 || true){
            this.zoom = zoom;
            tab.setUpdateBackground(true);
        }
    }

    public Point2D getCenterPoint() {
        return centerPoint;
    }

    public float getZoom() {
        return zoom;
    }

    public double getRotation() {
        return rotation;
    }

    public Point2D getLastMousePos() {
        return lastMousePos;
    }

    public SimulationTab getTab() {
        return tab;
    }

    public FXGraphics2D getG2d() {
        return g2d;
    }

    public Node getClickEvents() {
        return clickEvents;
    }
}
