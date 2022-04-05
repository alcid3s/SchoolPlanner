package managers;

import gui.tabs.SimulationTab;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Class Camera
 * Creates a new view for the SimulationTab
 */

public class Camera {
    private Point2D centerPoint;
    private float zoom = 0.75f;
    private Point2D lastMousePos;
    private final SimulationTab tab;

    /**
     * Constructor
     * @param tab root that needs to receive the new view
     */

    public Camera(SimulationTab tab) {
        Node clickEvents = tab.getPane();
        this.tab = tab;
        centerPoint = new Point2D.Double(-tab.getCanvas().getWidth() / 2, -tab.getCanvas().getHeight() / 2);
        clickEvents.setOnMousePressed(e -> this.lastMousePos = new Point2D.Double(e.getX(), e.getY()));
        clickEvents.setOnMouseDragged(this::mouseDragged);
        clickEvents.setOnScroll(this::mouseScroll);
    }

    /**
     * Method getTransform
     * @return new zoomed transform
     */

    public AffineTransform getTransform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(tab.getCanvas().getWidth() / 2, tab.getCanvas().getHeight() / 2);
        tx.scale(zoom, zoom);
        tx.translate(centerPoint.getX(), centerPoint.getY());
        double rotation = 0;
        tx.rotate(rotation);
        return tx;
    }

    /**
     * Method mouseDragged
     * Checks if the mouse got dragged over the screen
     * @param e event from the mouse
     */

    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            centerPoint = new Point2D.Double(
                    centerPoint.getX() - (lastMousePos.getX() - e.getX()) / zoom,
                    centerPoint.getY() - (lastMousePos.getY() - e.getY()) / zoom
            );
            lastMousePos = new Point2D.Double(e.getX(), e.getY());
            tab.setUpdateBackground(true);
        }
    }

    /**
     * Method mouseScroll
     * Checks if the mouse wheel has been used
     * @param e event from the mouse
     */

    public void mouseScroll(ScrollEvent e) {
        float zoom = this.zoom * (float) (1 + e.getDeltaY() / 500.0f);
        if (zoom > 0.42258725 && zoom < 1.2535156) {
            this.zoom = zoom;
            tab.setUpdateBackground(true);
        }
    }
}
