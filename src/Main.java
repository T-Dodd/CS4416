import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.*;
import java.lang.*;
import java.util.Locale;

public class Main {
    private static final String[] noArgs = {""};



    public static void main(String[] args){

        gui ui = new gui();

    }

    /*
    * Reads input from the command line and returns a string
    * */
    private static String readInput(){
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            return input.readLine();
        }
        catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            System.exit(-1);
            return "";
        }
    }

    /*
    * Reads input from the command line and returns a File object from the user input.
    * If the value entered is an invalid file, an IOException will be thrown and an error
    * message will be displayed to the user
    * */
    private static File inputFilePath(){
        try{
            String userInput = readInput();
            if(userInput.equals("")){
                return new File("src/test.c");
            }
            else{
                return new File(userInput);
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Invalid Input. Please enter a valid file path");
            main(noArgs);
            return new File(".");
        }
    }

    /*
    * Reads the contents of a File object and returns a string.
    * */
    private static String readFile(File FILE){
        try{
            FileReader reader = new FileReader(FILE);
            String fileContents = "";
            boolean reading = true;
            do{
                int nextChar = reader.read();
                if(nextChar == -1){ //EOF reached
                    reading = false;
                }
                fileContents += (char)nextChar;
            }while(reading);
            System.out.println("File contents have been read");

            //DEBUGGING For Testing the file reader contents are correct
            //System.out.print(fileContents);
            //System.out.println();

            return fileContents;
        }
        catch(Exception e){
            System.out.println("Error reading file contents. Returning empty value");
            return "";
        }
    }

    /*Returns true if the operating system is linux / unix based*/
    public static boolean isLinux()
    {
        String OS = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix")){
            return true;
        }
        return false;
    }

    /*
    * Displays detected OS to user, and prints warning to non Linux users regarding accuracy*/
    public void displayOS(){
        if(isLinux()){
            System.out.println("Linux operating system detected. " +
                    "Would you like to scan the man pages for most accurate deprecated / unsafe functions? [y],[n]");
            if(readInput().toLowerCase(Locale.ROOT).equals("y")){
                parseManPages();
            }
        }
        else{
            System.out.println("Non Linux operating system detected. Cannot scan man pages. WARNING: unsafe /" +
                    " deprecated functions detected may not be up to date.");
        }
    }

    /* Currently, placeholder function that will update the list of unsafe / deprecated functions
    if a user on a unix based OS would like the most current version*/
    public static void parseManPages(){
        System.out.println("Man Pages Parsed");
    }

}

