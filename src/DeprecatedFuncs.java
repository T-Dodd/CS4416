import java.io.*;
import java.util.HashMap;


public class DeprecatedFuncs {
    HashMap<String,String> dict;

    public DeprecatedFuncs()
    {
        String funcs = readFuncsFile();
        dict = new HashMap<String,String>() {};
        for (String func: funcs.split("\n"))
        {
            if (func.startsWith("//"))
            {
                continue;
            }
            String a[] = func.split(",");
            if (a.length == 2)
            {
                dict.put(a[0], a[1]);
            }
            else{
                dict.put(a[0], "");
            }
        }
    }
    
    private static String readFuncsFile() {
        try{
            FileReader reader = new FileReader(new File("deprecatedFuncs.txt"));
            String fileContents = "";
            boolean reading = true;
            do{
                int nextChar = reader.read();
                if(nextChar == -1){ //EOF reached
                    reading = false;
                }
                fileContents += (char)nextChar;
            }while(reading);


            reader.close();
            return fileContents;
        }
        catch(Exception e){
            System.out.println("Error reading deprecated functions file");
            return "";
        }
    }
}
