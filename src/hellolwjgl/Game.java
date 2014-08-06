/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellolwjgl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.PixelFormat;

/**
 *
 * @author asrianCron
 */
public class Game {

    static int width = 1600;
    static int height = 900;
    static float mouseX, mouseY;
    static boolean running = false;
    static PixelFormat pFormat;
    static ContextAttribs cAttrib;
    static ShaderProgram shProg;
    static int vertexArrayObjId;
    static int vertexBufferObjId;
    static int colorBufferObjId;
    static List<Triangle> triangleList = new ArrayList<>();
    static List<TColor> colorList = new ArrayList<>();
    static FloatBuffer vertexBuffer, colorBuffer;
    static int numbers = 0;
    static long lastFrame;
    static long currentFrame;
    static Triangle[] triangles;
    static TColor[] colors;

    public static void start() {

        running = true;
        Game.createDisplay();
        Game.init();
//        Triangle thingie = new Triangle(0.0f, 0.002f, 0.002f, -0.002f, -0.002f, -0.002f);
//        thingie.color = new TColor(1f, 1f, 1f, 0f);
//        triangleList.add(thingie);
//        sendTriangle(thingie.getArray());
//        sendColor(thingie.color.getArray());
        int numbers = 10000;
//        float size = 0.05f;
//        long interval = 100 / 60l;
        long interval = 10l;
        createRandomTriangles(numbers, 0f, 0.01f);
        lastFrame = System.currentTimeMillis();
        while (running) {

            currentFrame = System.currentTimeMillis();
            if (currentFrame > (lastFrame + interval)) {
                lastFrame = System.currentTimeMillis();
                updateRandomTriangles(numbers, -0.003f, 0.003f);
            }

//            mouseX = Mouse.getX();
//            mouseY = Mouse.getY();
//            System.out.println(mouseX + " " + mouseY);
            render();

            Display.update();
            Display.sync(60);

            if (!running || Display.isCloseRequested()) {
                Game.exit();
            }
        }

    }

    public static void collisionChecking() {
        Keyboard.next();
//            while (Keyboard.next()) {

        if (triangleList.get(0).collisionCheck(1, 1, -1, -1)) {
            if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                triangleList.get(0).addXOffset(-0.01f);
                updateTriangle(triangleList.get(0).getArray());
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                triangleList.get(0).addXOffset(0.01f);
                updateTriangle(triangleList.get(0).getArray());
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                triangleList.get(0).addYOffset(0.01f);
                updateTriangle(triangleList.get(0).getArray());

            }
            if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                triangleList.get(0).addYOffset(-0.01f);
                updateTriangle(triangleList.get(0).getArray());

            }
        } else {
            triangleList.get(0).setVertices(0.0f, 0.2f, 0.2f, -0.2f, -0.2f, -0.2f);
            updateTriangle(triangleList.get(0).getArray());
        }
//        }
    }

    public static void snapShot(ByteBuffer buff) {
        glReadBuffer(GL_FRONT);
        int width = Display.getDisplayMode().getWidth();
        int height = Display.getDisplayMode().getHeight();
        int bytesPerPixel = 4; // R G B A
        buff = BufferUtils.createByteBuffer(width * height * bytesPerPixel);
        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buff);
    }

    public static float rand(float bot, float top) {
        int iBot = (int) (bot * 10000);
        int iTop = (int) (top * 10000);
        int result = (int) (iBot + Math.round((iTop - iBot) * Math.random()));
        return (float) result / 10000;
    }

    public static int randInt(int bot, int top) {
        return (int) (bot + Math.round((top - bot) * Math.random()));
    }

    public static void createRandomTriangles(int numbers, float bot, float top) {
//        Triangle[] triangles = new Triangle[numbers];
        triangles = new Triangle[numbers];
//        TColor[] colors = new TColor[numbers];
        colors = new TColor[numbers];
//        triangleList.clear();
//        colorList.clear();
        float size;
        for (int i = 0; i < numbers; i++) {
            size = rand(bot, top);
            triangles[i] = new Triangle(0.0f, 0.002f + size, 0.002f + size, -0.002f - size, -0.002f - size, -0.002f - size);
//            triangles[i].color.setColor(rand(0f, 1f), rand(0f, 1f), rand(0f, 1f), rand(0f, 1f));
            colors[i] = triangles[i].color;

            triangles[i].addXOffset(rand(-1f, 1f));
            triangles[i].addYOffset(rand(-1f, 1f));
//            triangleList.add(triangles[i]);
//            colorList.add(colors[i]);
        }
        int colorChange = randInt(0, numbers);
        triangles[colorChange].color = new TColor(1f, 0f, 0f, 0f);
        colors[colorChange] = triangles[colorChange].color;
        Game.numbers = numbers;
        sendTriangle(Triangle.getMultipleTriangles(triangles));
        sendColor(TColor.getMultipleColors(colors));
    }

    public static void updateRandomTriangles(int numbers, float bot, float top) {
//        Triangle[] triangles = new Triangle[numbers];
//        TColor[] colors = new TColor[numbers];
//        triangleList.clear();
//        colorList.clear();
        float size;
        for (int i = 0; i < numbers; i++) {
//            size = rand(bot, top);
//            triangles[i] = new Triangle(0.0f, 0.002f + size, 0.002f + size, -0.002f - size, -0.002f - size, -0.002f - size);

//            triangles[i].color.setColor(rand(0f, 1f), rand(0f, 1f), rand(0f, 1f), rand(0f, 1f));
//            colors[i] = triangles[i].color;
            triangles[i].addXOffset(rand(bot, top));
            triangles[i].addYOffset(rand(bot, top));
//            triangleList.add(triangles[i]);
//            colorList.add(colors[i]);
        }

//        Game.numbers = numbers;
        updateTriangle(Triangle.getMultipleTriangles(triangles));
        updateColor(TColor.getMultipleColors(colors));
    }

    public static void createDisplay() {
        pFormat = new PixelFormat();
        cAttrib = new ContextAttribs(3, 2).withForwardCompatible(false).withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(pFormat, cAttrib);
            Display.setResizable(true);
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
    }

    public static void resize() {
//        Display.setDisplayMode(new Displaymode());
    }

    public static void init() {
        shProg = new ShaderProgram();
        shProg.init();
        shProg.attachVertexShader("src/hellolwjgl/vertexShader.vert");
        shProg.attachFragmentShader("src/hellolwjgl/fragmentShader.frag");
        shProg.link();

        try {
            Keyboard.create();
            Keyboard.enableRepeatEvents(true);

        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }

    }

    public static float[] generateSquare(float size, float offsetX, float offsetY) {
        float[] buff = new float[]{-size, size, size, size, size, -size, -size, size, size, -size, -size, -size};
        for (int i = 0; i < buff.length; i++) {
            if (i % 2 == 0) {
                buff[i] += offsetX;
            } else {
                buff[i] += offsetY;
            }

        }
        return buff;
    }

    public static void sendTriangle(float[] array) {
        vertexBuffer = BufferUtils.createFloatBuffer(array.length);

        vertexBuffer.put(array); // created a vertex

        vertexBuffer.rewind(); // rewinded the buffer for reading; position = 0

        // VAO = Vertex Array Object (openGL object), a link between shaders and openGL; stores attributes and pointers
        // VBO = Vertex Buffer Objects
        vertexArrayObjId = glGenVertexArrays(); // creating a VAO (ARRAY)
        System.out.println("VAOID:" + vertexArrayObjId);

        glBindVertexArray(vertexArrayObjId);

        vertexBufferObjId = glGenBuffers(); // creating a VBO (BUFFER)
        System.out.println("VBOID:" + vertexBufferObjId);

        //WHERE, WHAT
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObjId); // binds buffer object to specificed target

        //WHERE, WHAT, WHAT WAY
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW); // creating VBO datastore; copies the vertices from the buffer into the GPU; UPLOADS DATA TO GPU

        //glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long buffer_buffer_offset)
        //index         Specifies the index of the generic vertex attribute to be modified.
        //size          Specifies the number of components per generic vertex attribute. Must be 1, 2, 3, 4. Additionally, the symbolic constant GL_BGRA is accepted.
        //type          Specifies the data type of each component in the array. The symbolic constants GL_BYTE, GL_UNSIGNED_BYTE, GL_SHORT, GL_UNSIGNED_SHORT, GL_INT, and GL_UNSIGNED_INT, GL_HALF_FLOAT, GL_FLOAT, GL_DOUBLE are accepted. The initial value is GL_FLOAT
        //normalized 	Specifies whether fixed-point data values should be normalized GL_TRUE or converted directly as fixed-point values GL_FALSE when they are accessed.
        //stride 	Specifies the byte offset between consecutive generic vertex attributes. If stride is 0, the generic vertex attributes are understood to be tightly packed in the array. The initial value is 0.
        //offset 	Specifies a offset of the first component of the first generic vertex attribute in the array in the data store of the buffer currently bound to the GL_ARRAY_BUFFER target. The initial value is 0.
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0); // setting pointers in the VAO

        glBindVertexArray(0); // unbinding vao
    }

    public static void updateTriangle(float[] array) {
        vertexBuffer.clear();
        vertexBuffer.put(array);
        vertexBuffer.rewind();

//        glBindVertexArray(vertexArrayObjId);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObjId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW); // updating buffer data
//        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

//        glBindVertexArray(0);
    }

    public static void updateColor(float[] array) {
        colorBuffer.clear();
        colorBuffer.put(array);
        colorBuffer.rewind();

        glBindBuffer(GL_ARRAY_BUFFER, colorBufferObjId);
        glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
    }

    public static void sendColor(float[] array) {
        colorBuffer = BufferUtils.createFloatBuffer(array.length);
        colorBuffer.put(array); // uploading color x times in the buffer

        colorBuffer.rewind(); // rewinded the buffer for reading; position = 0

        // VAO = Vertex Array Object (openGL object), a link between shaders and openGL; stores attributes and pointers
        // VBO = Vertex Buffer Objects
        colorBufferObjId = glGenBuffers(); // creating a VBO (BUFFER)

        System.out.println("VBOID:" + colorBufferObjId);

        glBindVertexArray(vertexArrayObjId); // binding the vao; we can't send data otherwise

        //WHERE, WHAT
        glBindBuffer(GL_ARRAY_BUFFER, colorBufferObjId); // binds buffer object to specificed target

        //WHERE, WHAT, WHAT WAY
        glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW); // creating VBO datastore; copies the vertices from the buffer into the GPU; UPLOADS DATA TO GPU

        //glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long buffer_buffer_offset)
        //index         Specifies the index of the generic vertex attribute to be modified.
        //size          Specifies the number of components per generic vertex attribute. Must be 1, 2, 3, 4. Additionally, the symbolic constant GL_BGRA is accepted.
        //type          Specifies the data type of each component in the array. The symbolic constants GL_BYTE, GL_UNSIGNED_BYTE, GL_SHORT, GL_UNSIGNED_SHORT, GL_INT, and GL_UNSIGNED_INT, GL_HALF_FLOAT, GL_FLOAT, GL_DOUBLE are accepted. The initial value is GL_FLOAT
        //normalized 	Specifies whether fixed-point data values should be normalized GL_TRUE or converted directly as fixed-point values GL_FALSE when they are accessed.
        //stride 	Specifies the byte offset between consecutive generic vertex attributes. If stride is 0, the generic vertex attributes are understood to be tightly packed in the array. The initial value is 0.
        //offset 	Specifies a offset of the first component of the first generic vertex attribute in the array in the data store of the buffer currently bound to the GL_ARRAY_BUFFER target. The initial value is 0.
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0); // setting pointers in the VAO

        glBindVertexArray(0); // unbinding vao
    }

    public static void render() {

        glClear(GL_COLOR_BUFFER_BIT); // clears the screen

        shProg.bind();
        glBindVertexArray(vertexArrayObjId);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        //glDrawArrays(int mode, int first, int count)
        glDrawArrays(GL_TRIANGLES, 0, numbers * 3); // drawing a triangle with the first 3 vertices that we sent to the gpu
//        glDrawArrays(GL_TRIANGLES, 3, 6);
        glDisableVertexAttribArray(0); // disabling location 0
        glDisableVertexAttribArray(1);
        glBindVertexArray(0); // unbinding vao

        shProg.unBind();
    }

    public static long getTime() {
//        return Sys.getTime() * 1000 / Sys.getTimerResolution();
        return System.currentTimeMillis();
    }

    public static void dispose() {

        shProg.dispose();

        glBindVertexArray(0);
        glDeleteVertexArrays(vertexArrayObjId);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vertexBufferObjId);
        glDeleteBuffers(colorBufferObjId);
    }

    public static void exit() {
        Game.dispose();
        Display.destroy();
        running = false;
    }

}
