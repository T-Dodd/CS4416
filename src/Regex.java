import java.io.*;
import java.util.regex.*;
import java.util.HashMap;
import java.io.BufferedReader;



public class Regex {
    private String code;
    private HashMap dict;

    public Regex(String codeInput, HashMap dictInput) {
        code = codeInput;
        dict = dictInput;
    }

    public void find() {
        //String pattern = "printf\\(\"(.*?)\"\\s*,\\s*(.*?)\\);";
        String pattern = "printf\\(\"(.*?)\"\\);";
        Pattern regex = Pattern.compile(pattern);
        try (BufferedReader reader = new BufferedReader(new StringReader(code))) {
            String line;
            int lineNumber = 0;

            // Read each line
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (regex.matcher(line).find()) {
                    System.out.println("Match found for printf on line " + lineNumber);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


