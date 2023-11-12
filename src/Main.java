import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.*;
import java.lang.*;

public class Main {
    private static final String[] noArgs = {""};

    public static void main(String[] args){
        File cwd = new File("src/Main");
        System.out.println("Program file location: " + cwd.getAbsolutePath());
        System.out.println("Enter the full path of the file that you would like to parse, or press enter for default.");

        String fileContents = readFile(readInput());
    }

    /*
    * Reads input from the command line and returns a File object from the user input.
    * If the value entered is an invalid file, an IOException will be thrown and an error
    * message will be displayed to the user
    * */
    private static File readInput(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput = bufferedReader.readLine();
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
            System.out.print(fileContents);
            System.out.println();

            return fileContents;
        }
        catch(Exception e){
            System.out.println("Error reading file contents. Returning empty value");
            return "";
        }
    }
}

