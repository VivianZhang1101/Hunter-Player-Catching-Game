// --== CS400 File Header Information ==--
// Name: Fangjun Zhou
// Email: fzhou48@wisc.edu
// Team: CG blue
// TA: Xi
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MapDataReader class that implements MapReaderInterface
 *
 * @author fangjunzhou
 * @version 1.0
 */
public class MapDataReader implements MapReaderInterface{
  // PRIVATE FIELD

  private CS400Graph<Coordinate2> map = null;
  private int mapWidth;
  private int mapHeight;

  // CONSTRUCTOR

  /**
   * Basic constructor of MapDataReader
   */
  public MapDataReader(){

  }

  /**
   * Constructor that generate a map from a map file
   *
   * @param filePath the target map file path
   * @throws IOException when the file passed in is not a legal map file
   */
  public MapDataReader(String filePath) throws IOException{
    generateMapByFile(filePath);
  }

  // PRIVATE METHODS

  /**
   * Generate a empty map and store it in the map variable
   *
   * @param width the width of the map
   * @param height the height of the map
   */
  private void generateDefaultMap(int width, int height){
    this.map = new CS400Graph<>();

    // add coordinates into the map
    for (int x=0; x<width; x++){
      for (int y=0; y<height; y++){
        this.map.insertVertex(new Coordinate2(x, y));
      }
    }

    // add edges into the map
    for (int x=0; x<width; x++){
      for (int y=0; y<height; y++){
        // add the edge to the vertex at the left
        if (x != 0){
          map.insertEdge(new Coordinate2(x, y), new Coordinate2(x-1, y), 2);
        }
        // add the edge to the vertex at the top
        if (y != 0){
          map.insertEdge(new Coordinate2(x, y), new Coordinate2(x, y - 1), 2);
        }
        // add the edge tp the vertex at the right
        if (x != width-1){
          map.insertEdge(new Coordinate2(x, y), new Coordinate2(x + 1, y), 2);
        }
        // add the edge to the vertex at the bottom
        if (y != height-1){
          map.insertEdge(new Coordinate2(x, y), new Coordinate2(x, y + 1), 2);
        }
      }
    }

    this.mapWidth = width;
    this.mapHeight = height;
  }

  /**
   * Generate a map from the map file
   * @param path the map file path
   * @throws IOException when the file passed in is not a legal map file
   */
  private void generateMapByFile(String path) throws IOException {
    List<Coordinate2[]> coordinateParis = new ArrayList<>();
    List<Integer> costs = new ArrayList<>();
    int width = 0;
    int height = 0;
    try {
      // open the file
      File fileName = new File(path);
      InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName));
      BufferedReader br = new BufferedReader(reader);

      // read the width of the map
      String widthStr = br.readLine();
      if (widthStr == null){
        throw new IOException("The file is not a legal map file.");
      }
      width = Integer.parseInt(widthStr.split(":")[1]);

      // read the height of the map
      String heightStr = br.readLine();
      if (widthStr == null){
        throw new IOException("The file is not a legal map file.");
      }
      height = Integer.parseInt(heightStr.split(":")[1]);

      // read all the coordinatePairs
      String coordinatePairStr = br.readLine();
      while (coordinatePairStr != null){
        Pattern pattern = Pattern.compile("\\(([0-9]*), ");
        Matcher matcher = pattern.matcher(coordinatePairStr);
        // first x
        matcher.find();
        int x1 = Integer.parseInt(matcher.group(1));
        // second x
        matcher.find();
        int x2 = Integer.parseInt(matcher.group(1));

        pattern = Pattern.compile(", ([0-9]*)\\)");
        matcher = pattern.matcher(coordinatePairStr);
        // first y
        matcher.find();
        int y1 = Integer.parseInt(matcher.group(1));
        // first y
        matcher.find();
        int y2 = Integer.parseInt(matcher.group(1));

        // add the coordinate pair to the List
        coordinateParis.add(new Coordinate2[]{new Coordinate2(x1, y1), new Coordinate2(x2, y2)});

        pattern = Pattern.compile(":([0-9]*)");
        matcher = pattern.matcher(coordinatePairStr);
        matcher.find();
        costs.add(Integer.parseInt(matcher.group(1)));

        coordinatePairStr = br.readLine();
      }

    }catch (Exception e){
      e.printStackTrace();
    }

    // generate the map
    this.map = new CS400Graph<>();

    // add coordinates into the map
    for (int x=0; x<width; x++){
      for (int y=0; y<height; y++){
        this.map.insertVertex(new Coordinate2(x, y));
      }
    }
    this.mapWidth = width;
    this.mapHeight = height;

    // add edges into the map
    for (int x=0; x<width; x++){
      for (int y=0; y<height; y++){
        // add the edge to the vertex at the left
        if (x != 0){
          int index = existInCoordinatePairs(coordinateParis,
              new Coordinate2(x, y), new Coordinate2(x-1, y));
          if (index == -1)
            map.insertEdge(new Coordinate2(x, y), new Coordinate2(x-1, y), 2);
          else
            map.insertEdge(new Coordinate2(x, y), new Coordinate2(x-1, y), costs.get(index));
        }
        // add the edge to the vertex at the top
        if (y != 0){
          int index = existInCoordinatePairs(coordinateParis,
              new Coordinate2(x, y), new Coordinate2(x, y - 1));
          if (index == -1)
            map.insertEdge(new Coordinate2(x, y), new Coordinate2(x, y - 1), 2);
          else
            map.insertEdge(new Coordinate2(x, y), new Coordinate2(x, y - 1), costs.get(index));
        }
        // add the edge tp the vertex at the right
        if (x != width-1){
          int index = existInCoordinatePairs(coordinateParis,
              new Coordinate2(x, y), new Coordinate2(x + 1, y));
          if (index == -1)
            map.insertEdge(new Coordinate2(x, y), new Coordinate2(x + 1, y), 2);
          else
            map.insertEdge(new Coordinate2(x, y), new Coordinate2(x + 1, y), costs.get(index));
        }
        // add the edge to the vertex at the bottom
        if (y != height-1){
          int index = existInCoordinatePairs(coordinateParis,
              new Coordinate2(x, y), new Coordinate2(x, y + 1));
          if (index == -1)
            map.insertEdge(new Coordinate2(x, y), new Coordinate2(x, y + 1), 2);
          else
            map.insertEdge(new Coordinate2(x, y), new Coordinate2(x, y + 1), costs.get(index));
        }
      }
    }
  }

  /**
   * Check if the two coordinates occurs in the coordinate pair List
   * @param pairs the List that stores all the coordinate pairs
   * @param coordinate1 the first coordinate to check
   * @param coordinate2 the second coordinate to check
   * @return the position of coordinate pairs
   */
  private int existInCoordinatePairs(List<Coordinate2[]> pairs, Coordinate2 coordinate1, Coordinate2 coordinate2){
    int index = 0;
    for (Coordinate2[] pair : pairs){
      if (coordinate1.equals(pair[0]) && coordinate2.equals(pair[1]))
        return index;
      if (coordinate1.equals(pair[1]) && coordinate2.equals(pair[0]))
        return index;
      index ++;
    }

    return -1;
  }

  // MapReaderInterface

  /**
   * get the map read from the file, if no map was read before, return a default map
   *
   * @return a 8x8 default map if no map has been read from the file
   */
  @Override public GraphADT getMap() {
    if (this.map == null){
      // generate a 8x8 map
      generateDefaultMap(8, 8);
    }

    return this.map;
  }

  /**
   * Specify the size of the map and generate the map
   *
   * @param width the width of the map
   * @param height the height of the map
   * @return the generated map
   */
  @Override public GraphADT getMap(int width, int height) {
    // if the map size is not correct, regenerate the map
    if (this.mapWidth != width || this.mapHeight != height){
      generateDefaultMap(width, height);
    }

    return this.map;
  }

  @Override public int getMapSizeX() {
    return this.mapWidth;
  }

  @Override public int getMapSizeY() {
    return this.mapHeight;
  }
}
