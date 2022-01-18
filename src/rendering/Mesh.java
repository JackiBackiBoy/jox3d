package jox3d.rendering;

import jox3d.math.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.nio.FloatBuffer;

public class Mesh {
  private ArrayList<Vector3D> m_Positions = new ArrayList<Vector3D>();
  private ArrayList<Vector3D> m_Normals = new ArrayList<Vector3D>();
  private ArrayList<Float> m_Vertices = new ArrayList<Float>();
  private float[] m_VertexData;

  public float[] getVertexData() { return m_VertexData; }

  public void loadFromFile(String filePath) {
    try {
      File modelFile = new File(filePath).getAbsoluteFile();
      Scanner fileReader = new Scanner(modelFile); 

      while (fileReader.hasNextLine()) {
        String currentLine = fileReader.nextLine();
        String[] formattedData = currentLine.split(" ");
         
        if (currentLine.charAt(0) == 'v') { // the line either contains vertices or vertex coords
          // Vertex positions
          if (currentLine.charAt(1) == ' ') {
            // ignore first element since it only specifies data type
            Vector3D position = new Vector3D(0.0f, 0.0f, 0.0f);
            position.x = Float.parseFloat(formattedData[1]);
            position.y = Float.parseFloat(formattedData[2]);
            position.z = Float.parseFloat(formattedData[3]);

            m_Positions.add(position);
          }
          // Vertex normals
          else if (currentLine.charAt(1) == 'n') {
            Vector3D normal = new Vector3D(0.0f, 0.0f, 0.0f); 
            normal.x = Float.parseFloat(formattedData[1]);
            normal.y = Float.parseFloat(formattedData[2]);
            normal.z = Float.parseFloat(formattedData[3]);

            //m_Normals.add(m_Normals);
          }
        }
        // Assemble faces from position and normal data
        else if (currentLine.charAt(0) == 'f') {
          Vector3D position = new Vector3D(0.0f, 0.0f, 0.0f);

          // TODO: Make this method more efficient
          for (var i = 1; i < formattedData.length; i++) {
            position.x = m_Positions.get(Integer.parseInt(formattedData[i].split("/")[0]) - 1).x;
            position.y = m_Positions.get(Integer.parseInt(formattedData[i].split("/")[0]) - 1).y;
            position.z = m_Positions.get(Integer.parseInt(formattedData[i].split("/")[0]) - 1).z;

            Collections.addAll(m_Vertices, position.x, position.y, position.z); 
          }
        }
      }
      
      m_VertexData = new float[m_Vertices.size()];

      for (var i = 0; i < m_Vertices.size(); i++) {
        m_VertexData[i] = m_Vertices.get(i); 
      }
    } catch (FileNotFoundException e) {
      System.out.println("Error: file at path " + filePath + " not found!");
      e.printStackTrace();
    }
  }
}
