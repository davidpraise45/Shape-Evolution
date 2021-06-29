import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import java.util.Random;

public class Shapes {

    /*
        Cube
        Sphere
        Cone
        Cylinder
        Cuboid
        Pyramid
     */

    public static void cube(GL2 gl) {
        cube(gl, 1, true);
    }


    public static void sphere(GL2 gl) {
        sphere(gl, 0.5, 16, 16, true);
    }

    public static void cone(GL2 gl) {
        cone(gl, 0, 0, 1, 1, true);
    }

    public static void cylinder(GL2 gl) {
        cylinder(gl, 0, 1, 0, 1, true);
    }

    public static void cuboid(GL2 gl) {
        cuboid(gl, -1, 0, 0, 1, true);
    }

    public static void pyramid(GL2 gl, int nSides) {
        pyramid(gl, 0, 0, 1, 1, true, nSides);
    }

    /*
     * define other objects
     */

    //  Define Cube
    private static void cube(GL2 gl, double side, boolean makeTexCoords) {
        gl.glPushMatrix();
        gl.glRotatef(-90, -1, 0, 0);
        gl.glPushMatrix();
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(90, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(180, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(270, 0, 1, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(90, -1, 0, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotatef(-90, -1, 0, 0);
        gl.glTranslated(0, 0, side / 2);
        square(gl, side, makeTexCoords);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }

    private static void sphere(GL2 gl, double radius, int slices, int stacks, boolean makeTexCoords) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be positive.");
        if (slices < 3)
            throw new IllegalArgumentException("Number of slices must be at least 3.");
        if (stacks < 2)
            throw new IllegalArgumentException("Number of stacks must be at least 2.");
        for (int j = 0; j < stacks; j++) {
            double latitude1 = (Math.PI / stacks) * j - Math.PI / 2;
            double latitude2 = (Math.PI / stacks) * (j + 1) - Math.PI / 2;
            double sinLat1 = Math.sin(latitude1);
            double cosLat1 = Math.cos(latitude1);
            double sinLat2 = Math.sin(latitude2);
            double cosLat2 = Math.cos(latitude2);
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (int i = 0; i <= slices; i++) {
                double longitude = (2 * Math.PI / slices) * i;
                double sinLong = Math.sin(longitude);
                double cosLong = Math.cos(longitude);
                double x1 = cosLong * cosLat1;
                double y1 = sinLong * cosLat1;
                double z1 = sinLat1;
                double x2 = cosLong * cosLat2;
                double y2 = sinLong * cosLat2;
                double z2 = sinLat2;
                gl.glNormal3d(x2, y2, z2);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * (j + 1));
                gl.glVertex3d(radius * x2, radius * y2, radius * z2);
                gl.glNormal3d(x1, y1, z1);
                if (makeTexCoords)
                    gl.glTexCoord2d(1.0 / slices * i, 1.0 / stacks * j);
                gl.glVertex3d(radius * x1, radius * y1, radius * z1);
            }
            gl.glEnd();
        }
    }

    /*Define Cone */
    private static void cone(GL2 gl, float x, float y, float z, float a, Boolean isSelected) {

        GLU glu = new GLU();

        float height = 1f;
        float width_base = 0.5f;
        float width_top = 0.0f;

        // current matrix on the stack
        gl.glPushMatrix();

        // x, y, z coordiantes of a translation vector
        gl.glTranslatef(x, y - height / 2, z);

        gl.glScalef(a, a, a);

        // multiply the current matrix to get a rotation matrix
        gl.glRotatef(-90, 1, 0, 0);

        if (!isSelected) {
            gl.glColor3f(0, 0, 0);
        } else {
            gl.glColor3f(0, 1, 0);
        }

        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluCylinder(quadric, width_base, width_top, height, 10, 1);
        drawCircle(gl, 0.0f, 0.0f, 0.0f, width_base, isSelected);

        gl.glPopMatrix();
    }


    /* Define Cylinder*/
    private static void cylinder(GL2 gl, float x, float y, float z, float a, Boolean isSelected) {


        GLU glu = new GLU();

        float height = 1f;
        float width_base = 0.5f;

        // current matrix on the stack
        gl.glPushMatrix();

        // x, y, z coordiantes of a translation vector
        gl.glTranslatef(x, y - height / 2, z);

        gl.glScalef(a, a, a);

        // multiply the current matrix to get a rotation matrix
        gl.glRotatef(-90, 1, 0, 0);

        if (!isSelected) {
            gl.glColor3f(0, 0, 0);
        } else {
            gl.glColor3f(0, 1, 0);
        }

        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluCylinder(quadric, width_base, width_base, height, 10, 1);
        drawCircle(gl, 0.0f, 0.0f, 0.0f, width_base, isSelected);
        drawCircle(gl, 0.0f, 0.0f, height, width_base, isSelected);

        glu.gluDeleteQuadric(quadric);
        gl.glPopMatrix();
    }

    // Define cuboid
    private static void cuboid(GL2 gl, float x, float y, float z, float a, Boolean isSelected) {

        // current matrix on the stack
        gl.glPushMatrix();

        // x, y, z coordiantes of a translation vector
        gl.glTranslatef(x, y, z);

        gl.glScalef(a, a, a);

        // multiply the current matrix to get a rotation matrix
        gl.glRotatef(0, 0, 0, 0);

        // creating a cube
        gl.glBegin(GL2.GL_QUADS);

        if (!isSelected) {

            gl.glColor3f(0, 0, 0); // back
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(0.5f, -0.5f, -0.8f);
            gl.glVertex3f(0.5f, 0.5f, -0.8f);
            gl.glVertex3f(-0.5f, 0.5f, -0.8f);

            gl.glColor3f(0, 0, 0); // up
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);
            gl.glVertex3f(+0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f);

            gl.glColor3f(0, 0, 0); // down
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, -0.5f, +0.8f);

            gl.glColor3f(0, 0, 0); // left
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f);

            gl.glColor3f(0, 0, 0); // right
            gl.glVertex3f(+0.5f, -0.5f, +0.8f);
            gl.glVertex3f(+0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, +0.5f, -0.8f);
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);

            gl.glColor3f(0, 0, 0); // front
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);
            gl.glVertex3f(+0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f);
        } else {

            gl.glColor3f(1, 0, 0); // back
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(0.5f, -0.5f, -0.8f);
            gl.glVertex3f(0.5f, 0.5f, -0.8f);
            gl.glVertex3f(-0.5f, 0.5f, -0.8f);

            gl.glColor3f(0, 1, 0); // up
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);
            gl.glVertex3f(+0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f);

            gl.glColor3f(0, 0, 1); // down
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, -0.5f, +0.8f);

            gl.glColor3f(1, 0.5f, 0); // left
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, -0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f);

            gl.glColor3f(1, 0, 1); // right
            gl.glVertex3f(+0.5f, -0.5f, +0.8f);
            gl.glVertex3f(+0.5f, -0.5f, -0.8f);
            gl.glVertex3f(+0.5f, +0.5f, -0.8f);
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);

            gl.glColor3f(0.6f, 0, 1); // front
            gl.glVertex3f(+0.5f, +0.5f, +0.8f);
            gl.glVertex3f(+0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, -0.5f, +0.8f);
            gl.glVertex3f(-0.5f, +0.5f, +0.8f);
        }

        gl.glEnd();

        gl.glPopMatrix();
    }

    private static void pyramid(GL2 gl, float x, float y, float z, float a, Boolean isSelected, int noOfSides) {

        // current matrix on the stack
        gl.glPushMatrix();

        // x, y, z coordiantes of a translation vector
        gl.glTranslatef(x, y - 0.5f, z);

        // multiply the current matrix to get a rotation matrix
        gl.glRotatef(0, 0, 0, 0);

        gl.glScalef(a, a, a);

        if (!isSelected) {
            gl.glColor3f(0, 0, 0);

            switch (noOfSides) {
                case 3:
                    // base
                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glVertex3f(-1f, 0, -0.58f);
                    gl.glVertex3f(0f, 0, 1.15f);
                    gl.glVertex3f(1f, 0, -0.58f);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(-1f, 0, -0.58f);
                    gl.glVertex3f(0f, 0, 1.15f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(0f, 0, 1.15f);
                    gl.glVertex3f(1f, 0, -0.58f);
                    gl.glVertex3f(0.0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(1f, 0, -0.58f);
                    gl.glVertex3f(-1f, 0, -0.58f);
                    gl.glVertex3f(0.0f, 1f, 0);
                    gl.glEnd();
                    break;
                case 4:
                    // base
                    gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex3f(-1f, 0, -1f);
                    gl.glVertex3f(1f, 0, -1f);
                    gl.glVertex3f(1f, 0, 1f);
                    gl.glVertex3f(-1f, 0, 1f);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(-1f, 0, -1f);
                    gl.glVertex3f(1f, 0, -1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(1f, 0, -1f);
                    gl.glVertex3f(1f, 0, 1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(1f, 0, 1f);
                    gl.glVertex3f(-1f, 0, 1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(-1f, 0, 1f);
                    gl.glVertex3f(-1f, 0, -1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();
                    break;
                case 5:
                    // base
                    gl.glBegin(GL2.GL_POLYGON);
                    gl.glVertex3f(0f, 0f, -1f);
                    gl.glVertex3f(0.94f, 0f, -0.31f);
                    gl.glVertex3f(0.58f, 0.0f, 0.8f);
                    gl.glVertex3f(-0.59f, 0.0f, 0.8f);
                    gl.glVertex3f(-0.95f, 0.0f, -0.32f);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(0f, 0f, -1f);
                    gl.glVertex3f(0.94f, 0f, -0.31f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(0.94f, 0f, -0.31f);
                    gl.glVertex3f(0.58f, 0.0f, 0.8f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(0.58f, 0.0f, 0.8f);
                    gl.glVertex3f(-0.59f, 0.0f, 0.8f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(-0.59f, 0.0f, 0.8f);
                    gl.glVertex3f(-0.95f, 0.0f, -0.32f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(-0.95f, 0.0f, -0.32f);
                    gl.glVertex3f(0f, 0f, -1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();
                    break;
                case 6:
                    // base
                    gl.glBegin(GL2.GL_POLYGON);
                    gl.glVertex3f(0f, 0.0f, -1.0f);
                    gl.glVertex3f(0.86f, 0.0f, -0.5f);
                    gl.glVertex3f(0.86f, 0.0f, 0.5f);
                    gl.glVertex3f(0f, 0.0f, 1.0f);
                    gl.glVertex3f(-0.87f, 0.0f, 0.5f);
                    gl.glVertex3f(-0.87f, 0.0f, -0.5f);
                    gl.glVertex3f(0f, 0.0f, -1.0f);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(0f, 0.0f, -1.0f);
                    gl.glVertex3f(0.86f, 0.0f, -0.5f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(0.86f, 0.0f, -0.5f);
                    gl.glVertex3f(0.86f, 0.0f, 0.5f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(0.86f, 0.0f, 0.5f);
                    gl.glVertex3f(0f, 0.0f, 1.0f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(0f, 0.0f, 1.0f);
                    gl.glVertex3f(-0.87f, 0.0f, 0.5f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(-0.87f, 0.0f, 0.5f);
                    gl.glVertex3f(-0.87f, 0.0f, -0.5f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0, 0, 0);

                    gl.glVertex3f(-0.87f, 0.0f, -0.5f);
                    gl.glVertex3f(0f, 0.0f, -1.0f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    break;
            }
        } else {
            gl.glColor3f(0, 0, 1);

            switch (noOfSides) {
                case 3:
                    // base
                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glVertex3f(-1f, 0, -0.58f);
                    gl.glVertex3f(0f, 0, 1.15f);
                    gl.glVertex3f(1f, 0, -0.58f);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(1, 0, 1);

                    gl.glVertex3f(-1f, 0, -0.58f);
                    gl.glVertex3f(0f, 0, 1.15f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(1f, 0.3f, 1);

                    gl.glVertex3f(0f, 0, 1.15f);
                    gl.glVertex3f(1f, 0, -0.58f);
                    gl.glVertex3f(0.0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.2f, 0.4f, 1);

                    gl.glVertex3f(1f, 0, -0.58f);
                    gl.glVertex3f(-1f, 0, -0.58f);
                    gl.glVertex3f(0.0f, 1f, 0);
                    gl.glEnd();
                    break;
                case 4:
                    // base
                    gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex3f(-1f, 0, -1f);
                    gl.glVertex3f(1f, 0, -1f);
                    gl.glVertex3f(1f, 0, 1f);
                    gl.glVertex3f(-1f, 0, 1f);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(1f, 0.3f, 1);

                    gl.glVertex3f(-1f, 0, -1f);
                    gl.glVertex3f(1f, 0, -1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.2f, 0.4f, 1);

                    gl.glVertex3f(1f, 0, -1f);
                    gl.glVertex3f(1f, 0, 1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.1f, 0.3f, 0.8f);

                    gl.glVertex3f(1f, 0, 1f);
                    gl.glVertex3f(-1f, 0, 1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(1, 0.98f, 0.6f);

                    gl.glVertex3f(-1f, 0, 1f);
                    gl.glVertex3f(-1f, 0, -1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();
                    break;
                case 5:
                    // base
                    gl.glBegin(GL2.GL_POLYGON);
                    gl.glVertex3f(0f, 0f, -1f);
                    gl.glVertex3f(0.94f, 0f, -0.31f);
                    gl.glVertex3f(0.58f, 0.0f, 0.8f);
                    gl.glVertex3f(-0.59f, 0.0f, 0.8f);
                    gl.glVertex3f(-0.95f, 0.0f, -0.32f);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(1, 0.98f, 0.6f);

                    gl.glVertex3f(0f, 0f, -1f);
                    gl.glVertex3f(0.94f, 0f, -0.31f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.35f, 0.1f, 0.48f);

                    gl.glVertex3f(0.94f, 0f, -0.31f);
                    gl.glVertex3f(0.58f, 0.0f, 0.8f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.32f, 0.9f, 0.1f);

                    gl.glVertex3f(0.58f, 0.0f, 0.8f);
                    gl.glVertex3f(-0.59f, 0.0f, 0.8f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.2f, 0.4f, 0.2f);

                    gl.glVertex3f(-0.59f, 0.0f, 0.8f);
                    gl.glVertex3f(-0.95f, 0.0f, -0.32f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.76f, 0.99f, 0.001f);

                    gl.glVertex3f(-0.95f, 0.0f, -0.32f);
                    gl.glVertex3f(0f, 0f, -1f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();
                    break;
                case 6:
                    // base
                    gl.glBegin(GL2.GL_POLYGON);
                    gl.glVertex3f(0f, 0.0f, -1.0f);
                    gl.glVertex3f(0.86f, 0.0f, -0.5f);
                    gl.glVertex3f(0.86f, 0.0f, 0.5f);
                    gl.glVertex3f(0f, 0.0f, 1.0f);
                    gl.glVertex3f(-0.87f, 0.0f, 0.5f);
                    gl.glVertex3f(-0.87f, 0.0f, -0.5f);
                    gl.glVertex3f(0f, 0.0f, -1.0f);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.035f, 0.0125f, 0);

                    gl.glVertex3f(0f, 0.0f, -1.0f);
                    gl.glVertex3f(0.86f, 0.0f, -0.5f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.76f, 0.99f, 0.001f);

                    gl.glVertex3f(0.86f, 0.0f, -0.5f);
                    gl.glVertex3f(0.86f, 0.0f, 0.5f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.2f, 0.4f, 0.2f);

                    gl.glVertex3f(0.86f, 0.0f, 0.5f);
                    gl.glVertex3f(0f, 0.0f, 1.0f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.32f, 0.9f, 0.1f);

                    gl.glVertex3f(0f, 0.0f, 1.0f);
                    gl.glVertex3f(-0.87f, 0.0f, 0.5f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(0.35f, 0.1f, 0.48f);

                    gl.glVertex3f(-0.87f, 0.0f, 0.5f);
                    gl.glVertex3f(-0.87f, 0.0f, -0.5f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    gl.glBegin(GL2.GL_TRIANGLES);
                    gl.glColor3f(1, 0.98f, 0.6f);

                    gl.glVertex3f(-0.87f, 0.0f, -0.5f);
                    gl.glVertex3f(0f, 0.0f, -1.0f);
                    gl.glVertex3f(0f, 1f, 0);
                    gl.glEnd();

                    break;
            }
        }

        gl.glPopMatrix();
    }



    /*here define transformation and texture of shape*/


    public static void square(GL2 gl, double side, boolean makeTexCoords) {
        double radius = side / 2;
        gl.glBegin(GL2.GL_POLYGON);
        gl.glNormal3f(0, 0, 1);
        if (makeTexCoords)
            gl.glTexCoord2d(0, 1);
        gl.glVertex2d(-radius, radius);
        if (makeTexCoords)
            gl.glTexCoord2d(0, 0);
        gl.glVertex2d(-radius, -radius);
        if (makeTexCoords)
            gl.glTexCoord2d(1, 0);
        gl.glVertex2d(radius, -radius);
        if (makeTexCoords)
            gl.glTexCoord2d(1, 1);
        gl.glVertex2d(radius, radius);
        gl.glEnd();
    }

    // Define Circle
    private static void drawCircle(GL2 gl, float x, float y, float z, float radius, Boolean isSelected) {
        gl.glTranslatef(x, y, z);

        int circle_points = 100;
        float angle = 2.0f * 3.1416f / circle_points;

        gl.glBegin(GL2.GL_POLYGON);

        if (!isSelected) {
            gl.glColor3f(0, 0, 0);
        } else {
            gl.glColor3f(1, 0, 0);
        }

        double angle1 = 0.0;
        gl.glVertex2d(radius * Math.cos(0.0), radius * Math.sin(0.0));
        int i;
        for (i = 0; i < circle_points; i++) {
            gl.glVertex2d(radius * Math.cos(angle1), radius * Math.sin(angle1));
            angle1 += angle;
        }
        gl.glEnd();
    }

}