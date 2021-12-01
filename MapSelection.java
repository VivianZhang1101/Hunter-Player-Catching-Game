// --== CS400 File Header Information ==--
// Name: Xianfu Luo
// Email: xluo96@wisc.edu
// Team: CG blue
// TA: Xi
// Lecturer: Gary Dahl
// Notes to Grader:
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class is for user to select the map they want
 *
 * @author Xianfu Luo
 * @version 1.0
 */
public class MapSelection {

    /**
     *
     * @param filename
     * @return the filename without suffix
     */
    private String getPrefixNameOne(String filename){
        return filename.substring(filename.lastIndexOf("/")+1,filename.lastIndexOf("."));
    }

    /**
     * user can choose the map they want
     * @return the map user choose
     */
    public String chooseMap(){
        Scanner scan = new Scanner(System.in);
        ArrayList<String> dataFileList = new ArrayList<String>();
        String path = "data/";
        File file = new File(path);
        File[] fs = file.listFiles();	//Iterate through the files and directories under PATH
        for (File f : fs){
            dataFileList.add(f.getAbsolutePath());
        }
        int i = 0;
        for(String name: dataFileList){
            i++;
            System.out.println(i+": "+getPrefixNameOne(name));
        }
        System.out.println("Choose the map you want to play");
        String input = scan.nextLine();
        int result = Integer.parseInt(input)-1;
        return getPrefixNameOne(dataFileList.get(result));
    }
}
