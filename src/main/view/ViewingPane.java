package main.view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.*;
import main.util.Cartesian;
import main.util.MyMath;

import java.util.ArrayList;

/**
 * Panel class that serves the viewing area of the code
 */
public class ViewingPane extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    /**
     *
     */
    private static final long serialVersionUID = 1317105100159003291L;

    /**
     * Surfaces in pane
     */
    private ArrayList<Surface> surfaces;

    /**
     * Camera to project to pane
     */
    public Camera camera;

    /**
     * Center of screen
     */
    private int centerX;

    /**
     * Center of screen
     */
    private int centerY;

    /**
     * Mouse old coordinates
     */
    private int mouseX, mouseY;

    /**
     * motion types 0 = recenter 1 = rotate 2 = pan 3 = zoom 4 = move
     */
    private int motionType = 0; 

    private Graphics2D g2d;

    /**
     * true if 
     */
    public boolean getResult;

    private Cartesian freeStream;

    /**
     * Constructor
     */
    public ViewingPane() {
        camera = new Camera();
        setOpaque(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        this.camera.setHorizontalAngle(MyMath.DEG2RAD * 27);
        this.camera.setLocation(new Cartesian(0,0,4));
    }

    /**
     * Draws surfaces
     * @param g
     */
    private void drawSurfaces(Graphics g) {
        this.g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

        this.camera.setScreenDimensions(this.getWidth(), this.getHeight());

        if(motionType == 0) {
            centerX = this.getWidth() / 2;
            centerY = this.getHeight() / 2;
        }

        int[] idx = new int[] { 1, 2, 3, 0 };

        for (Surface s : surfaces) {
            for (Panel panel : s.panels) {
                g2d.setColor(Color.black);
                for (int i = 0; i < 4; i++) {
                    int[] point1 = camera.projectToScreenFrame(panel.vertices[i]);
                    int[] point2 = camera.projectToScreenFrame(panel.vertices[idx[i]]);
                    g2d.drawLine(centerX + point1[0], centerY - point1[1], centerX + point2[0],
                            centerY - point2[1]);
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.surfaces != null){
            drawSurfaces(g);
        }
    }

    public void setSurfaces(ArrayList<Surface> s) {
        this.surfaces = s;
    }

    public void plotArrow(Cartesian p1, Cartesian p2) {
        int[] point1 = camera.projectToScreenFrame(p1);
        int[] point2 = camera.projectToScreenFrame(p2);

        g2d.drawLine(centerX + point1[0], centerY - point1[1], centerX + point2[0],
                                centerY - point2[1]);

        int dy = point2[1]-point1[1];
        int dx = point2[0]-point1[0];

        double arrow_x = 0.75*dx;
        double arrow_y = 0.75*dy;

        // double l = Math.sqrt(dx*dx+dy*dy);

        double slope = -arrow_x/arrow_y;
        double delta_x = 5/Math.sqrt(1+slope*slope);

        double point_a_x = arrow_x + delta_x;
        double point_a_y = arrow_y + delta_x*slope;

        double point_b_x = arrow_x - delta_x;
        double point_b_y = arrow_y - delta_x*slope;

        g2d.drawLine(centerX + point1[0] + (int)point_a_x, centerY - point1[1] - (int)point_a_y, centerX + point2[0],
                        centerY - point2[1]);

        g2d.drawLine(centerX + point1[0] + (int)point_b_x, centerY - point1[1] - (int)point_b_y, centerX + point2[0],
                        centerY - point2[1]);

    }

    public void paintForces(Graphics g) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1) {
            this.mouseX = e.getX();
            this.mouseY = e.getY();
            this.camera.startRotation(this.mouseX,this.mouseY);
            this.motionType = 1;
        }
        if(e.getButton() == MouseEvent.BUTTON2) {
            this.mouseX = this.centerX - e.getX();
            this.mouseY = this.centerY - e.getY();
            this.motionType = 2;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            this.mouseX = e.getX();
            this.mouseY = e.getY();
            this.camera.startShift();
            this.motionType = 4;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if( this.motionType == 1) {
            double yaw = 4.0*(this.mouseX-e.getX())/getWidth();
            double pitch = 4.0*(e.getY()-this.mouseY)/getWidth();
            this.camera.rotate(yaw,pitch);
        } else if(this.motionType == 2) {
            this.centerX = e.getX()+this.mouseX;
            this.centerY = e.getY()+this.mouseY;
        } else if(this.motionType == 4) {
            double x = (double)(this.mouseX-e.getX())/getWidth();
            double y = (double)(e.getY()-this.mouseY)/getWidth();
            this.camera.shift(x,y);
        }
        
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //Cartesian point = camera.getPointOnGround(e.getX(), e.getY());
        //System.out.println("x:"+e.getX()+ " y:"+e.getY() + " ground x:" + point.x + " ground y:" + point.y);

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.camera.zoom(e.getUnitsToScroll());
        repaint();
    }

}