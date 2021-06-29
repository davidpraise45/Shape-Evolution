import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Camera {

    private double eyex, eyey, eyez = 20;
    private double refx, refy, refz;
    private double upx, upy = 1, upz;

    private double xminRequested = -5, xmaxRequested = 5;
    private double yminRequested = -5, ymaxRequested = 5;
    private double zmin = -10, zmax = 10;
    private boolean orthographic;
    private boolean preserveAspect = true;

    private FollowMouse trackball;
    private Component setComp;

    private double xminActual, xmaxActual, yminActual, ymaxActual;
    private GLU glu = new GLU();

    public double[] getViewParameters() {
        return new double[]{eyex, eyey, eyez, refx, refy, refz, upx, upy, upz};
    }


    public double[] getActualXYLimits() {
        return new double[]{xminActual, xmaxActual, yminActual, ymaxActual};
    }

    public boolean getOrthographic() {
        return orthographic;
    }

    public void setOrthographic(boolean orthographic) {
        this.orthographic = orthographic;
    }

    public boolean getPreserveAspect() {
        return preserveAspect;
    }

    public void setPreserveAspect(boolean preserveAspect) {
        this.preserveAspect = preserveAspect;
    }

    public void setLimits(double xmin, double xmax, double ymin, double ymax, double zmin, double zmax) {
        xminRequested = xminActual = xmin;
        xmaxRequested = xmaxActual = xmax;
        yminRequested = yminActual = ymin;
        ymaxRequested = ymaxActual = ymax;
        this.zmin = zmin;
        this.zmax = zmax;
    }


    public void setScale(double limit) {
        setLimits(-limit, limit, -limit, limit, -2 * limit, 2 * limit);
    }

    public void setTrack(Component c) {
        if (setComp != null && setComp != c) {
            setComp.removeMouseListener(trackball);
        }
        if (setComp == c) {
            return;
        }
        setComp = c;
        if (setComp == null) {
            return;
        }
        if (trackball == null) {
            trackball = new FollowMouse();
        }
        setComp.addMouseListener(trackball);
    }

    public double[] getLimits() {
        return new double[]{xminRequested, xmaxRequested, yminRequested, ymaxRequested, zmin, zmax};
    }


    public void lookAt(double eyeX, double eyeY, double eyeZ,
                       double viewCenterX, double viewCenterY, double viewCenterZ,
                       double viewUpX, double viewUpY, double viewUpZ) {
        eyex = eyeX;
        eyey = eyeY;
        eyez = eyeZ;
        refx = viewCenterX;
        refy = viewCenterY;
        refz = viewCenterZ;
        upx = viewUpX;
        upy = viewUpY;
        upz = viewUpZ;
    }

    public void apply(GL2 gl) {
        int[] viewport = new int[4];
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        xminActual = xminRequested;
        xmaxActual = xmaxRequested;
        yminActual = yminRequested;
        ymaxActual = ymaxRequested;
        if (preserveAspect) {
            double viewWidth = viewport[2];
            double viewHeight = viewport[3];
            double windowWidth = xmaxActual - xminActual;
            double windowHeight = ymaxActual - yminActual;
            double aspect = viewHeight / viewWidth;
            double desired = windowHeight / windowWidth;
            if (desired > aspect) { //expand width
                double extra = (desired / aspect - 1.0) * (xmaxActual - xminActual) / 2.0;
                xminActual -= extra;
                xmaxActual += extra;
            } else if (aspect > desired) {
                double extra = (aspect / desired - 1.0) * (ymaxActual - yminActual) / 2.0;
                yminActual -= extra;
                ymaxActual += extra;
            }
        }
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double viewDistance = norm(new double[]{refx - eyex, refy - eyey, refz - eyez});
        if (orthographic) {
            gl.glOrtho(xminActual, xmaxActual, yminActual, ymaxActual, viewDistance - zmax, viewDistance - zmin);
        } else {
            double near = viewDistance - zmax;
            if (near < 0.1)
                near = 0.1;
            double centerx = (xminActual + xmaxActual) / 2;
            double centery = (yminActual + ymaxActual) / 2;
            double newwidth = (near / viewDistance) * (xmaxActual - xminActual);
            double newheight = (near / viewDistance) * (ymaxActual - yminActual);
            double x1 = centerx - newwidth / 2;
            double x2 = centerx + newwidth / 2;
            double y1 = centery - newheight / 2;
            double y2 = centery + newheight / 2;
            gl.glFrustum(x1, x2, y1, y2, near, viewDistance - zmin);
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(eyex, eyey, eyez, refx, refy, refz, upx, upy, upz);
    }

    private double norm(double[] v) {
        double norm2 = v[0] * v[0] + v[1] * v[1] + v[2] * v[2];
        if (Double.isNaN(norm2) || Double.isInfinite(norm2) || norm2 == 0)
            throw new NumberFormatException("Vector length zero, undefined, or infinite.");
        return Math.sqrt(norm2);
    }

    private void normalizedVector(double[] v) {
        double normalizationOfVectors = norm(v);
        v[0] /= normalizationOfVectors;
        v[1] /= normalizationOfVectors;
        v[2] /= normalizationOfVectors;
    }


    private void axisRReflections(double[] axisR, double[] sourceR, double[] fDestination) {
        double s = 2 * (axisR[0] * sourceR[0] + axisR[1] * sourceR[1] + axisR[2] * sourceR[2]);
        fDestination[0] = s * axisR[0] - sourceR[0];
        fDestination[1] = s * axisR[1] - sourceR[1];
        fDestination[2] = s * axisR[2] - sourceR[2];
    }

    private double[] transformToViewCoords(double[] v, double[] x, double[] y, double[] z) {
        double[] w = new double[3];
        w[0] = v[0] * x[0] + v[1] * y[0] + v[2] * z[0];
        w[1] = v[0] * x[1] + v[1] * y[1] + v[2] * z[1];
        w[2] = v[0] * x[2] + v[1] * y[2] + v[2] * z[2];
        return w;
    }

    private void rotateVectors(double[] e1, double[] e2) {
        double[] directionOfZ = new double[]{eyex - refx, eyey - refy, eyez - refz};
        double viewDistance = norm(directionOfZ);
        normalizedVector(directionOfZ);
        double[] directionOfY = new double[]{upx, upy, upz};
        double upLength = norm(directionOfY);
        double proj = directionOfY[0] * directionOfZ[0] + directionOfY[1] * directionOfZ[1] + directionOfY[2] * directionOfZ[2];
        directionOfY[0] = directionOfY[0] - proj * directionOfZ[0];
        directionOfY[1] = directionOfY[1] - proj * directionOfZ[1];
        directionOfY[2] = directionOfY[2] - proj * directionOfZ[2];
        normalizedVector(directionOfY);
        double[] xDirection = new double[]{directionOfY[1] * directionOfZ[2] - directionOfY[2] * directionOfZ[1],
                directionOfY[2] * directionOfZ[0] - directionOfY[0] * directionOfZ[2],
                directionOfY[0] * directionOfZ[1] - directionOfY[1] * directionOfZ[0]};
        e1 = transformToViewCoords(e1, xDirection, directionOfY, directionOfZ);
        e2 = transformToViewCoords(e2, xDirection, directionOfY, directionOfZ);
        double[] e = new double[]{e1[0] + e2[0], e1[1] + e2[1], e1[2] + e2[2]};
        normalizedVector(e);
        double[] temp = new double[3];
        axisRReflections(e, directionOfZ, temp);
        axisRReflections(e1, temp, directionOfZ);
        axisRReflections(e, xDirection, temp);
        axisRReflections(e1, temp, xDirection);
        axisRReflections(e, directionOfY, temp);
        axisRReflections(e1, temp, directionOfY);
        eyex = refx + viewDistance * directionOfZ[0];
        eyey = refy + viewDistance * directionOfZ[1];
        eyez = refz + viewDistance * directionOfZ[2];
        upx = upLength * directionOfY[0];
        upy = upLength * directionOfY[1];
        upz = upLength * directionOfY[2];
    }

    private class FollowMouse implements MouseListener, MouseMotionListener {

        private boolean rotationDrag;
        private double[] prevPostionXY;

        public void mousePressed(MouseEvent e) {
            if (rotationDrag) {
                return;
            }
            rotationDrag = true;
            prevPostionXY = pointToPosition(e.getX(), e.getY());
            setComp.addMouseMotionListener(this);
        }

        public void mouseReleased(MouseEvent e) {
            if (!rotationDrag) {
                return;
            }
            rotationDrag = false;
            setComp.removeMouseMotionListener(this);
        }

        public void mouseDragged(MouseEvent e) {
            if (!rotationDrag) {
                return;
            }
            double[] thisRay = pointToPosition(e.getX(), e.getY());
            rotateVectors(prevPostionXY, thisRay);
            prevPostionXY = thisRay;
            setComp.repaint();
        }

        private double[] pointToPosition(int x, int y) {
            double dx, dy, dz, normalizationOfVectors;
            int centerX = setComp.getWidth() / 2;
            int centerY = setComp.getHeight() / 2;
            double scale = 0.8 * Math.min(centerX, centerY);
            dx = (x - centerX);
            dy = (centerY - y);
            normalizationOfVectors = Math.sqrt(dx * dx + dy * dy);
            if (normalizationOfVectors >= scale) {
                dz = 0;
            } else {
                dz = Math.sqrt(scale * scale - dx * dx - dy * dy);
            }
            double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
            return new double[]{dx / length, dy / length, dz / length};
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

    }


}