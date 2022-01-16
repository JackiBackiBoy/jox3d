package jox3d.math;

public class Matrix4x4 {
  float[][] data = new float[4][4]; // column major [c][r]

  public Matrix4x4(float r1c1, float r1c2, float r1c3, float r1c4,
                   float r2c1, float r2c2, float r2c3, float r2c4,
                   float r3c1, float r3c2, float r3c3, float r3c4,
                   float r4c1, float r4c2, float r4c3, float r4c4) {
                     // Column 1
                     data[0][0] = r1c1;
                     data[0][1] = r2c1;
                     data[0][2] = r3c1;
                     data[0][3] = r4c1;

                     // Column 2
                     data[1][0] = r1c2;
                     data[1][1] = r2c2;
                     data[1][2] = r3c2;
                     data[1][3] = r4c2;

                     // Column 3
                     data[2][0] = r1c3;
                     data[2][1] = r2c3;
                     data[2][2] = r3c3;
                     data[2][3] = r4c3;

                     // Column 4
                     data[3][0] = r1c4;
                     data[3][1] = r2c4;
                     data[3][2] = r3c4;
                     data[3][3] = r4c4;
  }
}
