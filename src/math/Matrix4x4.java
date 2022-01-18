package jox3d.math;

public class Matrix4x4 {
  public float[][] data = new float[4][4]; // column major ordering
  public float[][] getData() { return data; }

  public Matrix4x4(float[][] data) { this.data = data; }

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

  public static Matrix4x4 rotateX(Matrix4x4 inputMatrix, float radians) {
    Matrix4x4 rotX = new Matrix4x4(1, 0, 0, 0,
                                   0, (float)Math.cos(radians), -(float)Math.sin(radians), 0,
                                   0, (float)Math.sin(radians), (float)Math.cos(radians), 0,
                                   0, 0, 0, 1);

    return multiply(inputMatrix, rotX);
  }

  public static Matrix4x4 rotateY(Matrix4x4 inputMatrix, float radians) {
    Matrix4x4 rotY = new Matrix4x4((float)Math.cos(radians), 0, (float)Math.sin(radians), 0,
                                   0, 1, 0, 0,
                                   -(float)Math.sin(radians), 0, (float)Math.cos(radians), 0,
                                   0, 0, 0, 1);

    return multiply(inputMatrix, rotY);
  }

  public String toString() {
    String matrixString = "";

    for (var r = 0; r < 4; r++) {
      for (var c = 0; c < 4; c++) {
        matrixString += String.valueOf(data[c][r]) + ", ";  
      }

      matrixString += "\n";
    }

    return matrixString;
  }
  
  public static Matrix4x4 multiply(Matrix4x4 matrixA, Matrix4x4 matrixB) {
    float[][] newData = new float[4][4];

    for (var r = 0; r < 4; r++) {
      for (var c = 0; c < 4; c++) {
        for (var i = 0; i < 4; i++) {
          newData[c][r] += matrixA.data[i][r] * matrixB.data[c][i];
        }
      }
    }

    return new Matrix4x4(newData);
  }

  public float[] getFlatData() {
    float[] flatData = new float[16];

    for (var c = 0; c < 4; c++) {
      for (var r = 0; r < 4; r++) {
        flatData[c * 4 + r] = data[c][r];
      }
    }
    
    return flatData;
  }

  public static Matrix4x4 identity = new Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f,
                                                   0.0f, 1.0f, 0.0f, 0.0f,
                                                   0.0f, 0.0f, 1.0f, 0.0f,
                                                   0.0f, 0.0f, 0.0f, 1.0f);
}
