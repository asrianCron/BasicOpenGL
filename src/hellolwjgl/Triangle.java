/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellolwjgl;

/**
 *
 * @author asrianCron
 */
public class Triangle {

    Vertex A, B, C;
    TColor color;

    public Triangle() {
        A = new Vertex(0, 0);
        B = new Vertex(0, 0);
        C = new Vertex(0, 0);
        color = new TColor(1f, 1f, 1f, 0f);
    }

    public Triangle(Triangle trig) {
        this.A = new Vertex(trig.A);
        this.B = new Vertex(trig.B);
        this.C = new Vertex(trig.C);
        color = new TColor(1f, 1f, 1f, 0f);
    }

    public Triangle(Vertex A, Vertex B, Vertex C) {
        this.A = A;
        this.B = B;
        this.C = C;
        color = new TColor(1f, 1f, 1f, 0f);
    }

    public Triangle(float AX, float AY, float BX, float BY, float CX, float CY) {
        A = new Vertex(AX, AY);
        B = new Vertex(BX, BY);
        C = new Vertex(CX, CY);
        color = new TColor(1f, 1f, 1f, 0f);
    }

    public void addXOffset(float offset) {
        A.X += offset;
        B.X += offset;
        C.X += offset;
    }

    public void addYOffset(float offset) {
        A.Y += offset;
        B.Y += offset;
        C.Y += offset;
    }

    public float[] getArray() {
        float[] output = new float[6];
        output[0] = A.X;
        output[1] = A.Y;
        output[2] = B.X;
        output[3] = B.Y;
        output[4] = C.X;
        output[5] = C.Y;
        return output;
    }

    public void setVertices(float AX, float AY, float BX, float BY, float CX, float CY) {
        A.X = AX;
        A.Y = AY;
        B.X = BX;
        B.Y = BY;
        C.X = CX;
        C.Y = CY;
    }

    public static float[] getMultipleTriangles(Triangle[] triangles) {
        float[] output = new float[6 * triangles.length];
        int index = 0;
        for (int i = 0; i < triangles.length; i++) {
            float[] temp = triangles[i].getArray();
            for (int j = 0; j < temp.length; j++) {
                output[index] = temp[j];
                index++;
            }
        }
        return output;
    }

    public boolean collisionCheck(float X, float Y, float minusX, float minusY) {
        return A.X < X && A.Y < Y && A.X > minusX && A.Y > minusY;
    }

    @Override
    public String toString() {
        return "Triangle[" + "A.X=" + A.X + " A.Y=" + A.Y + ", B.X=" + B.X + " B.Y=" + B.Y + ", C.X=" + C.X + " C.Y=" + C.Y + ", \n" + color + '}';
    }

}
