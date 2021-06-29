import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.sun.opengl.util.BufferUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//import com.jogamp.opengl.util.awt.ImageUtil;
//import com.jogamp.opengl.util.texture.Texture;
//import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.net.URL;


/*
 This code was modified by David Praise Chukwuma Kalu - Computer Graphics SDE (Year III)
 Index number 2017230266

 * Instructions
 * Step 1: F - find shape at blueprint (press multiple times till you see selected cube)
 * Step 2: Click on small green Cube on the left
 * Step 3: Click on "Add"
 *  *
 * */

public class Main extends GLCanvas implements GLEventListener, KeyListener, MouseListener {


    private JCheckBox ambientLighting;
    private JCheckBox ambientLight;
    private JCheckBox help;

    private JButton addButton;
    private JFrame frame;
    private JLabel label;
    private Camera camera;
    private TextRenderer textRenderer;
    private TextRenderer textMatch;

    private int randomFront;
    private int randomBack;
    private int randomTop;
    private int randomTopTwo;
    private int randomRight;
    private int randomLeft;
    private int randomLeftTwo;
    private int randomBottom;
    private int randomBottomTwo;


    private int nameID = 0;

    private int left_idn = 0;
    private int left_two_idn = 0;
    private int right_idn = 0;
    private int top_idn = 0;
    private int top_two_idn = 0;
    private int bottom_idn = 0;
    private int bottom_two_idn = 0;
    private int front_idn = 0;
    private int back_idn = 0;

    private float addShapeRed = 1f, addShapeGreen = 102 / 255f, addShapeBlue = 0f;


    private float defaultRed = 0f;
    private float defaultGreen = 0.5f;
    private float defaultBlue = 0.5f;

    private float redL = defaultRed;
    private float greenL = defaultGreen;
    private float blueL = defaultBlue;

    private float redLeftTwo = defaultRed;
    private float greenLeftTwo = defaultGreen;
    private float blueLeftTwo = defaultBlue;

    private float redT = defaultRed;
    private float greenT = defaultGreen;
    private float blueT = defaultBlue;

    private float redTopTwo = defaultRed;
    private float greenTopTwo = defaultGreen;
    private float blueTopTwo = defaultBlue;

    private float redB = defaultRed;
    private float greenB = defaultGreen;
    private float blueB = defaultBlue;

    private float redBottomTwo = defaultRed;
    private float greenBottomTwo = defaultGreen;
    private float blueBottomTwo = defaultBlue;

    private float redR = defaultRed;
    private float greenR = defaultGreen;
    private float blueR = defaultBlue;

    private float redF = defaultRed;
    private float greenF = defaultGreen;
    private float blueF = defaultBlue;

    private float redBack = defaultRed;
    private float greenBack = defaultGreen;
    private float blueBack = defaultBlue;

    // LEFT
    private double leftX;
    private double leftY;
    private double leftZ;

    // LEFT
    private double leftTwoX;
    private double leftTwoY;
    private double leftTwoZ;

    // RIGHT
    private double rightX;
    private double rightY;
    private double rightZ;

    // TOP
    private double topX;
    private double topY;
    private double topZ;

    // TOP TWO
    private double topTwoX;
    private double topTwoY;
    private double topTwoZ;

    // BOTTOM
    private double bottomX;
    private double bottomY;
    private double bottomZ;

    // BOTTOM TWO
    private double bottomTwoX;
    private double bottomTwoY;
    private double bottomTwoZ;

    // FRONT
    private double frontX;
    private double frontY;
    private double frontZ;

    // BACK
    private double backX = 0;
    private double backY = 0;
    private double backZ = -1.5;

    private int travers = 0;
    private float scaleDelta = 0.2f;

    private float scaleLeft = 0.5f;
    private float scaleLeftTwo = 0.5f;
    private float scaleRight = 0.5f;
    private float scaleTop = 0.5f;
    private float scaleTopTwo = 0.5f;
    private float scaleBottom = 0.5f;
    private float scaleBottomTwo = 0.5f;
    private float scaleFront = 0.5f;
    private float scaleBack = 0.5f;

    private int angleLeftY = 90;
    private int angleLeftX = 90;
    private int angleLeftZ = 90;

    private int angleLeftTwoY = 90;
    private int angleLeftTwoX = 90;
    private int angleLeftTwoZ = 90;

    private int angleRightX = 90;
    private int angleRightY = 90;
    private int angleRightZ = 90;

    private int angleTopX = 90;
    private int angleTopY = 90;
    private int angleTopZ = 90;

    private int angleTopTwoX = 90;
    private int angleTopTwoY = 90;
    private int angleTopTwoZ = 90;

    private int angleBottomX = 90;
    private int angleBottomY = 90;
    private int angleBottomZ = 90;

    private int angleBottomTwoX = 90;
    private int angleBottomTwoY = 90;
    private int angleBottomTwoZ = 90;

    private int angleFrontX = 90;
    private int angleFrontY = 90;
    private int angleFrontZ = 90;

    private int angleBackX = 90;
    private int angleBackY = 90;
    private int angleBackZ = 90;

    private float rotate = 1;

    private float paletteRed = 0.5f;
    private float paletteGreen = 1.00f;
    private float paletteBlue = 0.40f;

    private static final int LEFT_ID = 1;
    private static final int LEFT_TWO_ID = 2;
    private static final int RIGHT_ID = 3;
    private static final int TOP_ID = 4;
    private static final int TOP_TWO_ID = 5;
    private static final int BOTTOM_ID = 6;
    private static final int BOTTOM_TWO_ID = 7;
    private static final int FRONT_ID = 8;
    private static final int BACK_ID = 9;


    private final static int SPHERE = 1, CUBE = 2, CONE = 3, CYLINDER = 4, CUBOID = 5, PYRAMID = 6;
    private final static int SPHERE_ID = 10;
    private final static int CUBE_ID = 11;
    private final static int CONE_ID = 12;
    private final static int CYLINDER_ID = 13;
    private final static int CUBOID_ID = 14;
    private final static int PYRAMID_ID = 15;


    private final static String[] shape = {" ", "SPHERE", "CUBE", "CONE", "CYLINDER", "CUBOID", "PYRAMID"};

    Random rand = new Random();
    int nSides = rand.nextInt(4) + 3; // number of sides for the pyramid

    private GLCanvas canvas;
    private FPSAnimator animator;
    private static final int FPS = 60;
    private int windowWidth = 800;
    private int windowHeight = 500;
    private final String TITLE = "David Praise Chukwuma Kalu - Computer Graphics SDE (Year III)";
    private GLU glu;


    // size of buffer
    private static final int BUFSIZE = 512;
    private IntBuffer selectBuffer;

    private boolean inSelectionMode = false;
    private int xCursor, yCursor;


    private int currentAngleOfRotationX = 0;
    private int currentAngleOfRotationY = 0;
    private int currentAngleOfVisibleField = 55;

    private int angleDelta = 5;
    private float aspect;
    private float aspectP;

    private boolean startNewGraphics = true;

    private float translateX;
    private float translateY;
    private float translateZ;

    private float scale;
    private float scaleLeftShape;
    private float scaleLeftTwoShape;
    private float scaleRightShape;
    private float scaleTopShape;
    private float scaleTopTwoShape;
    private float scaleBottomShape;
    private float scaleBottomTwoShape;
    private float scaleFrontShape;
    private float scaleBackShape;

    private GLJPanel display;

    public Main() {


        GLProfile profile = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(profile);
        display = new GLJPanel(caps);
        display.setPreferredSize(new Dimension(800, 600));
        display.addGLEventListener(this);

        caps.setAlphaBits(8);
        caps.setDepthBits(24);
        caps.setDoubleBuffered(true);
        caps.setStencilBits(8);


        SwingUtilities.invokeLater(() -> {

            canvas = new GLCanvas();
            canvas.setPreferredSize(new Dimension(windowWidth, windowHeight));
            canvas.addGLEventListener(this);
            canvas.addKeyListener(this);
            canvas.addMouseListener(this);
            canvas.setFocusable(true);
            canvas.requestFocus();
            canvas.requestFocusInWindow();

            animator = new FPSAnimator(canvas, FPS, true);
            frame = new JFrame();
            addButton = new JButton("  Add ");
            addButton.setPreferredSize(new Dimension(100, 20));

            label = new JLabel("David Praise Chukwuma Kalu Code for Computer Graphics. Index number 2017/230266");
            ambientLighting = new JCheckBox("Ambient Light", true);
            ambientLight = new JCheckBox("Global Ambient Light", false);
            help = new JCheckBox("Help", false);

            JPanel bottom = new JPanel();
            bottom.setLayout(new GridLayout(2, 2));

            JPanel row1 = new JPanel();
            row1.add(addButton);
            row1.add(ambientLighting);
            row1.add(help);
            bottom.add(row1);

            JPanel row2 = new JPanel();
            row2.add(label);
            bottom.add(row2);

            frame.add(bottom, BorderLayout.SOUTH);
            ambientLight.setFocusable(false);
            ambientLighting.setFocusable(false);
            help.setFocusable(false);

            addButton.addActionListener(e -> {
                if (e.getSource() == addButton) {
                    if (travers == 1) {
                        left_idn = nameID;
                    } else if (travers == 2) {
                        left_two_idn = nameID;
                    } else if (travers == 3) {
                        right_idn = nameID;
                    } else if (travers == 4) {
                        top_idn = nameID;
                    } else if (travers == 5) {
                        top_two_idn = nameID;
                    } else if (travers == 6) {
                        bottom_idn = nameID;
                    } else if (travers == 7) {
                        bottom_two_idn = nameID;
                    } else if (travers == 8) {
                        front_idn = nameID;
                    } else if (travers == 9) {
                        back_idn = nameID;
                    }
                }
                addButton.setFocusable(false);
            });


            /* help button / quit or end buton / new game button / remove button etc.*/


            frame.getContentPane().add(canvas);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    new Thread(() -> {
                        if (animator.isStarted()) animator.stop();
                        System.exit(0);
                    }).start();
                }
            });


            camera = new Camera();
            camera.lookAt(-20, 10, 80, 12, 0, 23, 0, 1, 0);
            camera.setScale(15);
            camera.setTrack(display);

            frame.setTitle(TITLE);
            frame.pack();
            frame.setVisible(true);
            animator.start();
        });


    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.95f, 0.95f, 1f, 0);


        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, 1);
        gl.glMateriali(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 100);


        float ambient[] = {0.1f, 0.1f, 0.1f, 1};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);


        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        glu = GLU.createGLU(gl);


        textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 17));
        textMatch = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);


        if (inSelectionMode) {
            modelsFromPack(drawable);
        } else { // normal rendering
            palette(drawable);
            drawBlueprint(drawable);
            drawBackground(drawable);
        }

        camera.apply(gl);
        lights(gl);

        float zero[] = {0, 0, 0, 1};


        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, new float[]{0.1F, 0.1F, 0.1F, 1}, 0);

        /*
         * if game finished or not
         * see given example of startNewGraphics
         */


        if (startNewGraphics) {
            startNewGraphics();
            startNewGraphics = false;
        }
    }

    /**
     * add texture
     */

    private void drawBackground(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glViewport(0, 0, windowWidth, windowHeight);

        aspect = (float) windowHeight / ((float) windowWidth);

        gl.glOrtho((float) -10 / 2, (float) 10 / 2,
                (-10 * aspect) / 2,
                (10 * aspect) / 2, 0, 100);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        gl.glPushMatrix();
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
        gl.glTranslated(0, 0, -100);
        gl.glScalef(1.75f, 1, 1);
        gl.glColor3f(1f, 1f, 1f);

        double radius = 3.25;
        gl.glBegin(GL2.GL_POLYGON);
        gl.glNormal3f(0, 0, 1);

        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(-radius, radius);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(-radius, -radius);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(radius, -radius);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(radius, radius);
        gl.glEnd();

        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glEnd();
        gl.glPopMatrix();

    }

    public void startNewGraphics() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        randomFront = list.get(0);
        randomBack = list.get(1);
        randomTop = list.get(2);
        randomTopTwo = list.get(3);
        randomRight = list.get(4);
        randomLeft = list.get(5);
        randomLeftTwo = list.get(6);
        randomBottom = list.get(7);
        randomBottomTwo = list.get(8);

        // default values
        currentAngleOfRotationX = 0;
        currentAngleOfRotationY = 0;
        currentAngleOfVisibleField = 55;

        translateX = 0;
        translateY = 0;
        translateZ = 0;

        scale = 1;

        nameID = 0;
        left_idn = 0;
        left_two_idn = 0;
        right_idn = 0;
        top_idn = 0;
        top_two_idn = 0;
        bottom_idn = 0;
        bottom_two_idn = 0;
        front_idn = 0;
        back_idn = 0;
        addShapeRed = 1f;
        addShapeGreen = 102 / 255f;
        addShapeBlue = 0f;

        redL = defaultRed;
        greenL = defaultGreen;
        blueL = defaultBlue;

        redLeftTwo = defaultRed;
        greenLeftTwo = defaultGreen;
        blueLeftTwo = defaultBlue;

        redT = defaultRed;
        greenT = defaultGreen;
        blueT = defaultBlue;

        redTopTwo = defaultRed;
        greenTopTwo = defaultGreen;
        blueTopTwo = defaultBlue;

        redB = defaultRed;
        greenB = defaultGreen;
        blueB = defaultBlue;

        redBottomTwo = defaultRed;
        greenBottomTwo = defaultGreen;
        blueBottomTwo = defaultBlue;

        redR = defaultRed;
        greenR = defaultGreen;
        blueR = defaultBlue;

        redF = defaultRed;
        greenF = defaultGreen;
        blueF = defaultBlue;

        redBack = defaultRed;
        greenBack = defaultGreen;
        blueBack = defaultBlue;

        // LEFT
        leftX = -1.25;
        leftY = 0.5;
        leftZ = 0;

        scaleLeftShape = 0.5f;

        // LEFT TWO
        leftTwoX = -1.25;
        leftTwoY = -0.5;
        leftTwoZ = 0;

        scaleLeftTwoShape = 0.5f;

        // RIGHT
        rightX = 1.4;
        rightY = 0;
        rightZ = 0.5;

        scaleRightShape = 0.75f;

        // TOP
        topX = -0.5;
        topY = 1.5;
        topZ = 0.5;

        scaleTopShape = 1;

        // TOP TWO
        topTwoX = 0.5;
        topTwoY = 1.5;
        topTwoZ = -0.5;

        scaleTopTwoShape = 1;

        // BOTTOM
        bottomX = 1;
        bottomY = -1.63;
        bottomZ = 0.5f;

        scaleBottomShape = 1.25f;

        // BOTTOM TWO
        bottomTwoX = -1;
        bottomTwoY = -1.63;
        bottomTwoZ = -0.5f;

        scaleBottomTwoShape = 1.25f;

        // FRONT
        frontX = -0.25;
        frontY = 0;
        frontZ = 1.75;

        scaleFrontShape = 1.5f;

        // BACK
        backX = 0;
        backY = 0;
        backZ = -1.9;

        scaleBackShape = 1.75f;

        travers = 0;

        scaleDelta = 0.05f;
        scaleLeft = 0.5f;
        scaleLeftTwo = 0.5f;
        scaleRight = 0.5f;
        scaleTop = 0.5f;
        scaleTopTwo = 0.5f;
        scaleBottom = 0.5f;
        scaleBottomTwo = 0.5f;
        scaleFront = 0.5f;
        scaleBack = 0.5f;


        angleLeftY = 90;
        angleLeftX = 90;
        angleLeftZ = 90;

        angleLeftTwoY = 90;
        angleLeftTwoX = 90;
        angleLeftTwoZ = 90;

        angleRightX = 90;
        angleRightY = 90;
        angleRightZ = 90;

        angleTopX = 90;
        angleTopY = 90;
        angleTopZ = 90;

        angleTopTwoX = 90;
        angleTopTwoY = 90;
        angleTopTwoZ = 90;

        angleBottomX = 90;
        angleBottomY = 90;
        angleBottomZ = 90;

        angleFrontX = 90;
        angleFrontY = 90;
        angleFrontZ = 90;

        angleBackX = 90;
        angleBackY = 90;
        angleBackZ = 90;
    }

    private void beginFromPack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        selectBuffer = BufferUtil.newIntBuffer(BUFSIZE);
        gl.glSelectBuffer(BUFSIZE, selectBuffer);
        gl.glRenderMode(GL2.GL_SELECT);
        gl.glInitNames();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public void palettePicking(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        int[] viewport = new int[4];
        float[] projMatrix = new float[16];
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        viewport[0] = 0;
        viewport[1] = 0;
        viewport[2] = windowWidth / 2;
        viewport[3] = windowHeight;
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projMatrix, 0);
        glu.gluPickMatrix((double) xCursor, (double) (viewport[3] - yCursor),
                1.0, 1.0, viewport, 0);

        gl.glMultMatrixf(projMatrix, 0);
        gl.glOrtho((float) -10 / 2, (float) 10 / 2,
                (-10 * aspectP) / 2,
                (10 * aspectP) / 2, 1, 11);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(-1, 2, 5.0,
                0.0, 0.0, 0.0,
                0.0, 1.0, 0.0);

    }

    public void blueprintPicking(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        int[] viewport = new int[4];
        viewport[0] = windowWidth / 8;
        viewport[1] = 0;
        viewport[2] = windowWidth;
        viewport[3] = windowHeight;
        float[] projMatrix = new float[16];
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projMatrix, 0);
        System.out.println(viewport[3]);
        glu.gluPickMatrix((double) xCursor, (double) (viewport[3] - yCursor), 1.0, 1.0, viewport, 0);
        System.out.println(viewport[3] - yCursor);
        gl.glMultMatrixf(projMatrix, 0);
        glu.gluPerspective(currentAngleOfVisibleField,
                1.f * windowWidth / windowHeight, 1, 100);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    private void endPicking(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glFlush();
        int numHits = gl.glRenderMode(GL2.GL_RENDER);
        processHits(numHits, drawable);
        inSelectionMode = false;
    }

    public void processHits(int numHits, GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (numHits == 0)
            return;
        int selectedNameID = 0;
        float smallestZ = -1.0f;
        boolean isFirstLoop = true;
        int offset = 0;
        for (int i = 0; i < numHits; i++) {
            int numNames = selectBuffer.get(offset);
            offset++;
            // minZ and maxZ are taken from the Z buffer
            float minZ = getDepth(offset);
            offset++;
            if (isFirstLoop) {
                smallestZ = minZ;
                isFirstLoop = false;
            } else {
                if (minZ < smallestZ)
                    smallestZ = minZ;
            }
            float maxZ = getDepth(offset);
            offset++;

            for (int j = 0; j < numNames; j++) {
                nameID = selectBuffer.get(offset);
                System.out.print(idToString(nameID) + "\n");
                if (j == (numNames - 1)) {
                    if (smallestZ == minZ)
                        selectedNameID = nameID;
                }
                offset++;
            }
        }
    }

    public void addShape(GLAutoDrawable drawable, int nameID) {
        GL2 gl = drawable.getGL().getGL2();
        switch (nameID) {
            case SPHERE_ID:
                Shapes.sphere(gl);
                break;
            case CUBE_ID:
                Shapes.cube(gl);
                break;
            case CONE_ID:
                Shapes.cone(gl);
                break;
            case CYLINDER_ID:
                Shapes.cylinder(gl);
                break;
            case CUBOID_ID:
                Shapes.cuboid(gl);
                break;
            case PYRAMID_ID:
                Shapes.pyramid(gl, nSides);
                break;
        }
    }

    private void addLeftShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(leftX, leftY, leftZ);
        gl.glScalef(scaleLeft, scaleLeft, scaleLeft);
        gl.glRotatef(angleLeftZ, 0, 0, rotate);
        gl.glRotatef(angleLeftY, 0, rotate, 0);
        gl.glRotatef(angleLeftX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();

    }

    private void addLeftTwoShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(leftTwoX, leftTwoY, leftTwoZ);
        gl.glScalef(scaleLeftTwo, scaleLeftTwo, scaleLeftTwo);
        gl.glRotatef(angleLeftTwoZ, 0, 0, rotate);
        gl.glRotatef(angleLeftTwoY, 0, rotate, 0);
        gl.glRotatef(angleLeftTwoX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();

    }

    private void addRightShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(rightX, rightY, rightZ);
        gl.glScalef(scaleRight, scaleRight, scaleRight);
        gl.glRotatef(angleRightZ, 0, 0, rotate);
        gl.glRotatef(angleRightY, 0, rotate, 0);
        gl.glRotatef(angleRightX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addTopShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(topX, topY, topZ);
        gl.glScalef(scaleTop, scaleTop, scaleTop);
        gl.glRotatef(angleTopZ, 0, 0, rotate);
        gl.glRotatef(angleTopY, 0, rotate, 0);
        gl.glRotatef(angleTopX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addTopTwoShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(topTwoX, topTwoY, topTwoZ);
        gl.glScalef(scaleTopTwo, scaleTopTwo, scaleTopTwo);
        gl.glRotatef(angleTopTwoZ, 0, 0, rotate);
        gl.glRotatef(angleTopTwoY, 0, rotate, 0);
        gl.glRotatef(angleTopTwoX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addBottomShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(bottomX, bottomY, bottomZ);
        gl.glScalef(scaleBottom, scaleBottom, scaleBottom);
        gl.glRotatef(angleBottomZ, 0, 0, rotate);
        gl.glRotatef(angleBottomY, 0, rotate, 0);
        gl.glRotatef(angleBottomX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addBottomTwoShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(bottomTwoX, bottomTwoY, bottomTwoZ);
        gl.glScalef(scaleBottomTwo, scaleBottomTwo, scaleBottomTwo);
        gl.glRotatef(angleBottomTwoZ, 0, 0, rotate);
        gl.glRotatef(angleBottomTwoY, 0, rotate, 0);
        gl.glRotatef(angleBottomTwoX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addFrontShape(GLAutoDrawable drawable, int nameID) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(frontX, frontY, frontZ);
        gl.glScalef(scaleFront, scaleFront, scaleFront);
        gl.glRotatef(angleFrontZ, 0, 0, rotate);
        gl.glRotatef(angleFrontY, 0, rotate, 0);
        gl.glRotatef(angleFrontX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }

    private void addBackShape(GLAutoDrawable drawable, int nameID) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(addShapeRed, addShapeGreen, addShapeBlue);
        gl.glTranslated(backX, backY, backZ);
        gl.glScalef(scaleBack, scaleBack, scaleBack);
        gl.glRotatef(angleBackZ, 0, 0, rotate);
        gl.glRotatef(angleBackY, 0, rotate, 0);
        gl.glRotatef(angleBackX, rotate, 0, 0);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        addShape(drawable, nameID);
        gl.glPopMatrix();
    }


    private float getDepth(int offset) {
        long depth = (long) selectBuffer.get(offset);
        return (1.0f + ((float) depth / 0x7fffffff));

    }

    // fetch ID to String
    private String idToString(int nameID) {
        if (nameID == LEFT_ID)
            return "left";
        else if (nameID == LEFT_TWO_ID)
            return "left_two";
        else if (nameID == RIGHT_ID)
            return "right";
        else if (nameID == TOP_ID)
            return "top";
        else if (nameID == TOP_TWO_ID)
            return "top_two";
        else if (nameID == BACK_ID)
            return "back";
        else if (nameID == BOTTOM_ID)
            return "bottom";
        else if (nameID == BOTTOM_TWO_ID)
            return "bottom_two";
        else if (nameID == FRONT_ID)
            return "front";
        else if (nameID == SPHERE_ID)
            return "palette_sphere";

        return "nameID " + nameID;
    }


    //example how to pick a model / shape

    private void modelsFromPack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        beginFromPack(drawable);
        palettePicking(drawable);

        gl.glPushName(SPHERE_ID);
        spherePack(drawable);
        gl.glPopName();
        gl.glPushMatrix();

        gl.glPushName(CUBE_ID);
        cubePack(drawable);
        gl.glPopName();
        gl.glPushMatrix();

        gl.glPushName(CONE_ID);
        conePack(drawable);
        gl.glPopName();
        gl.glPushMatrix();

        gl.glPushName(CYLINDER_ID);
        cylinderPack(drawable);
        gl.glPopName();
        gl.glPushMatrix();

        gl.glPushName(CUBOID_ID);
        cuboidPack(drawable);
        gl.glPopName();
        gl.glPushMatrix();

        gl.glPushName(PYRAMID_ID);
        pyramidPack(drawable);
        gl.glPopName();
        gl.glPushMatrix();
        blueprintPicking(drawable);


        gl.glRotated(currentAngleOfRotationX, 1, 0, 0);
        gl.glRotated(currentAngleOfRotationY, 0, 1, 0);

        gl.glPushName(LEFT_ID);
        drawLeft(drawable);
        gl.glPopName();

        gl.glPushName(LEFT_TWO_ID);
        drawLeftTwo(drawable);
        gl.glPopName();

        gl.glPushName(RIGHT_ID);
        drawRight(drawable);
        gl.glPopName();

        gl.glPushName(TOP_ID);
        drawTop(drawable);
        gl.glPopName();

        gl.glPushName(TOP_TWO_ID);
        drawTopTwo(drawable);
        gl.glPopName();

        gl.glPushName(BOTTOM_ID);
        drawBottom(drawable);
        gl.glPopName();

        gl.glPushName(BOTTOM_TWO_ID);
        drawBottomTwo(drawable);
        gl.glPopName();

        gl.glPushName(FRONT_ID);
        drawFront(drawable);
        gl.glPopName();

        gl.glPushName(BACK_ID);
        drawBack(drawable);
        gl.glPopName();


        gl.glPopMatrix();
        gl.glPopMatrix();
        gl.glPopMatrix();
        gl.glPopMatrix();
        gl.glPopMatrix();
        gl.glPopMatrix();
        endPicking(drawable);
    }


    private void setObserver() {

        glu.gluLookAt(-5, 0f, 3f,
                0, 0, 0,
                0, 1, 0);
    }

    private void palette(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glViewport(0, 0, windowWidth / 3, windowHeight);

        aspectP = (float) windowHeight / ((float) windowWidth / 3);

        gl.glOrtho((float) -10 / 2, (float) 10 / 2,
                (-10 * aspectP) / 2,
                (10 * aspectP) / 2, 1, 11);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

        backgroundPack(drawable);
        gl.glLoadIdentity();

        glu.gluLookAt(-1, 2, 5.0,
                0.0, 0.0, 0.0,
                0.0, 1.0, 0.0);


        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        cubePack(drawable);
        spherePack(drawable);
        conePack(drawable);
        cylinderPack(drawable);
        cuboidPack(drawable);
        pyramidPack(drawable);
    }

    private void backgroundPack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
        gl.glTranslated(-1.5f, -1f, 7);
        gl.glScalef(3.5f, 5, 0);
        gl.glColor3f(1f, 1f, 1f);
        Shapes.square(gl, 2, true);
        Shapes.cube(gl);
        Shapes.cuboid(gl);
        Shapes.cone(gl);
        Shapes.cylinder(gl);
        Shapes.pyramid(gl, nSides);
        Shapes.sphere(gl);
        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glEnd();
        gl.glPopMatrix();
    }

    public void cylinderPack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, 6.2f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.cylinder(gl);
        gl.glPopMatrix();
    }

    public void conePack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.8f, 5.5f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.cone(gl);
        gl.glPopMatrix();
    }

    public void spherePack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, 2.5f, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.sphere(gl);
        gl.glPopMatrix();
    }

    public void cubePack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, 0, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.cube(gl);
        gl.glPopMatrix();
    }

    public void cuboidPack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-2.5f, -2.5, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.cuboid(gl);
        gl.glPopMatrix();
    }

    public void pyramidPack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(paletteRed, paletteGreen, paletteBlue);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glPushMatrix();
        gl.glTranslated(-3.5f, -5.0, 0);
        gl.glScalef(1.25f, 1.25f, 1.25f);
        Shapes.pyramid(gl, nSides);
        gl.glPopMatrix();
    }



    /*
     * create other palettes here
     * */

    private void drawBlueprint(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(windowWidth / 3, 0, windowWidth, windowHeight);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(currentAngleOfVisibleField, 1.f * windowWidth / windowHeight, 1, 75);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        setObserver();

        /**
         * add texture
         */
        backgroundPack(drawable);

        gl.glPushMatrix();
        gl.glTranslated(translateX, translateY, translateZ);
        gl.glScalef(scale, scale, scale);
        gl.glRotated(currentAngleOfRotationX, 1, 1, 1);
        gl.glRotated(currentAngleOfRotationY, 1, 1, 0);

        drawTop(drawable);
        drawTopTwo(drawable);
        drawRight(drawable);
        drawLeft(drawable);
        drawLeftTwo(drawable);
        drawBottom(drawable);
        drawBottomTwo(drawable);
        drawFront(drawable);
        drawBack(drawable);

        addLeftShape(drawable, left_idn);
        addLeftTwoShape(drawable, left_two_idn);
        addRightShape(drawable, right_idn);
        addTopShape(drawable, top_idn);
        addTopTwoShape(drawable, top_two_idn);
        addBottomShape(drawable, bottom_idn);
        addBottomTwoShape(drawable, bottom_two_idn);
        addFrontShape(drawable, front_idn);
        addBackShape(drawable, back_idn);

        gl.glPopMatrix();
    }

    private void drawShape(GLAutoDrawable drawable, int randomShape) {
        GL2 gl = drawable.getGL().getGL2();

        switch (randomShape) {
            case SPHERE:
                Shapes.sphere(gl);
                break;
            case CUBE:
                Shapes.cube(gl);
                break;
            case CONE:
                Shapes.cone(gl);
                break;
            case CYLINDER:
                Shapes.cylinder(gl);
                break;
            case CUBOID:
                Shapes.cuboid(gl);
                break;
            case PYRAMID:
                Shapes.pyramid(gl, nSides);
                break;
        }
    }

    private void drawLeft(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redL, greenL, blueL);
        gl.glTranslated(leftX, leftY, leftZ);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glScalef(scaleLeftShape, scaleLeftShape, scaleLeftShape);
        drawShape(drawable, randomLeft);
        gl.glPopMatrix();
    }

    private void drawLeftTwo(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redLeftTwo, greenLeftTwo, blueLeftTwo);
        gl.glTranslated(leftTwoX, leftTwoY, leftTwoZ);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glScalef(scaleLeftTwoShape, scaleLeftTwoShape, scaleLeftTwoShape);
        drawShape(drawable, randomLeftTwo);
        gl.glPopMatrix();
    }


    private void drawRight(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redR, greenR, blueR);
        gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glTranslated(rightX, rightY, rightZ);
        gl.glScalef(scaleRightShape, scaleRightShape, scaleRightShape);
        drawShape(drawable, randomRight);
        gl.glPopMatrix();
    }


    private void drawTop(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redT, greenT, blueT);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glTranslated(topX, topY, topZ);
        gl.glScalef(scaleTopShape, scaleTopShape, scaleTopShape);
        drawShape(drawable, randomTop);
        gl.glPopMatrix();
    }

    private void drawTopTwo(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redTopTwo, greenTopTwo, blueTopTwo);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glTranslated(topTwoX, topTwoY, topTwoZ);
        gl.glScalef(scaleTopTwoShape, scaleTopTwoShape, scaleTopTwoShape);
        drawShape(drawable, randomTopTwo);
        gl.glPopMatrix();
    }

    private void drawBottom(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redB, greenB, blueB);
        gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);

        gl.glTranslated(bottomX, bottomY, bottomZ);
        gl.glScalef(scaleBottomShape, scaleBottomShape, scaleBottomShape);
        drawShape(drawable, randomBottom);
        gl.glPopMatrix();
    }

    private void drawBottomTwo(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redBottomTwo, greenBottomTwo, blueBottomTwo);
        gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glTranslated(bottomTwoX, bottomTwoY, bottomTwoZ);
        gl.glScalef(scaleBottomTwoShape, scaleBottomTwoShape, scaleBottomTwoShape);
        drawShape(drawable, randomBottomTwo);
        gl.glPopMatrix();
    }

    private void drawFront(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redF, greenF, blueF);
        gl.glPolygonMode(GL2.GL_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glTranslated(frontX, frontY, frontZ);
        gl.glScalef(scaleFrontShape, scaleFrontShape, scaleFrontShape);
        drawShape(drawable, randomFront);
        gl.glPopMatrix();
    }

    private void drawBack(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glPushMatrix();
        gl.glColor3f(redBack, greenBack, blueBack);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        gl.glLineWidth(2);
        gl.glTranslated(backX, backY, backZ);
        gl.glScalef(scaleBackShape, scaleBackShape, scaleBackShape);
        drawShape(drawable, randomBack);
        gl.glPopMatrix();
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        windowWidth = width;
        windowHeight = height;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void colorShape(int travers) {
        switch (travers) {
            case LEFT_ID:
                redL = 1;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case LEFT_TWO_ID:
                redL = 0;
                redLeftTwo = 1;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case RIGHT_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 1;
                redF = 0;
                redBack = 0;
                break;
            case TOP_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 1;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;

            case TOP_TWO_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 1;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case BOTTOM_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 1;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case BOTTOM_TWO_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 1;
                redR = 0;
                redF = 0;
                redBack = 0;
                break;
            case FRONT_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 1;
                redBack = 0;
                break;
            case BACK_ID:
                redL = 0;
                redLeftTwo = 0;
                redT = 0;
                redTopTwo = 0;
                redB = 0;
                redBottomTwo = 0;
                redR = 0;
                redF = 0;
                redBack = 1;
                break;
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            //F - find shape on a blueprint
            case KeyEvent.VK_F:
                travers++;
                colorShape(travers);
                if (travers == 10) {
                    travers = 0;
                }
                break;
            case KeyEvent.VK_ADD:
                if (currentAngleOfVisibleField > 1) {
                    currentAngleOfVisibleField--;
                }
                break;

            /* add other key events (rotation etc.)*/

            case KeyEvent.VK_UP:
                currentAngleOfRotationX += 5;
                break;
            case KeyEvent.VK_DOWN:
                currentAngleOfRotationX -= 5;
                break;
            case KeyEvent.VK_LEFT:
                currentAngleOfRotationY += 5;
                break;
            case KeyEvent.VK_RIGHT:
                currentAngleOfRotationY -= 5;
                break;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: { // left button
                xCursor = e.getX();
                yCursor = e.getY();
                inSelectionMode = true;
                break;
            }
            case MouseEvent.BUTTON3: { // right button
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * background to the paleete and parent shape in the blue print
     *
     * @param gl
     */


    private void lights(GL2 gl) {
        gl.glColor3d(0.5, 0.5, 0.5);
        float zero[] = {0, 0, 0, 1};
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, zero, 0);


        gl.glDisable(GL2.GL_LIGHTING);

        gl.glEnable(GL2.GL_LIGHTING);

        float ambient[] = {0.1f, 0.1f, 0.1f, 1};


        if (ambientLighting.isSelected()) {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, ambient, 0);
            gl.glEnable(GL2.GL_LIGHT0);
        } else {
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0);
            gl.glDisable(GL2.GL_LIGHT0);
        }

        if (help.isSelected()) {
            helpScreen();
        }

        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, zero, 0); // Turn off emission color!
    }

    public void helpScreen() {
        int positionX = windowWidth / 3;
        int positionY = windowHeight;

        textRenderer.beginRendering(windowWidth, windowHeight);
        textRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        textRenderer.draw("↑/←/→/↓ - translate scene", positionX, positionY - 30);
        textRenderer.draw("F - next shape", positionX, positionY - 50);

        textRenderer.endRendering();
    }

    public void writeText(String tekst, int x, int y) {
        textRenderer.beginRendering(windowWidth, windowHeight);
        textRenderer.setColor(0.3f, 0.3f, 0.5f, 1);
        textRenderer.draw(tekst, x, y);
        textRenderer.endRendering();
    }

    public void writeMatch(String text, int x, int y) {
        textMatch.beginRendering(windowWidth, windowHeight);
        textMatch.setColor(0.3f, 0.3f, 0.5f, 1);
        textMatch.draw(text, x, y);
        textMatch.endRendering();
    }


    public void displayMatch(GLAutoDrawable drawable) {

        if (travers == 1) {
            if (shape[randomLeft].equals(shape[left_idn]))
                // appropriate
                if (leftScaleCheck(scaleLeft).equals("correct") && checkingRotation(randomLeft, angleLeftX, angleLeftY, angleLeftZ).equals("ok")) {
                    writeMatch("Well Done!",
                            (int) (windowWidth / 4f), windowHeight - 40);
                }
        }

        /*
         * check rotation and scaling
         * */

		/*
		  	method - if shape is matched 
		
		*/
    }

    public String leftScaleCheck(double scale) {

        double scaling = Math.round(scale * 100.0) / 100.0;

        String text;
        if (scaling == 0.5) {
            text = "appropriate";
        } else {
            text = "not appropriate";
        }
        return text;
    }

		/*
		 scale and return the result if it's not appropriate
		*/


    public String checkingRotation(int shape, int angleX, int angleY, int angleZ) {

        //define rotations and check if rotation and scaling are appropriate

        if (angleX == this.angleLeftY & angleY == this.angleLeftY & angleZ == this.angleLeftZ) {
            return "ok";
        }

        return "Not ok";
    }

    public static void main(String[] args) {
        new Main();
    }
}
