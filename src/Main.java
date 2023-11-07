import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.InputStream;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.beans.*;
import java.net.*;
import java.nio.*;

public class Main {
    private static File FILE;

    public static void main(String[] args){
        File cwd = new File("src/Main");
        System.out.println("Program file location: " + cwd.getAbsolutePath());
        System.out.println("Enter the relative path of the file that you would like to parse, or press enter for default.");

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput = bufferedReader.readLine();
            try{
                if(userInput.equals("")){
                    FILE = new File("src/test.c");
                }
                else{
                    FILE = new File(userInput);
                }
            }
            catch(Exception e){
                System.out.println(e.toString());
                System.out.println("Invalid Input");
            }

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
            System.out.print(fileContents);
            System.out.println();
        }
        catch(Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

}
