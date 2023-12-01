import java.io.*;
import java.util.regex.*;
import java.util.HashMap;
import java.io.BufferedReader;

public class Regex {
    private HashMap<String,String> dict;

    public Regex() {
        DeprecatedFuncs depFuncs = new DeprecatedFuncs();
        dict = depFuncs.dict;
    }

    public String find(String code) {
        String errors = "";
        for (String dep : dict.keySet())
        {
            String parenthesis = "\\(.*?\\)";
            String pattern = dep + parenthesis;
            Pattern regex = Pattern.compile(pattern);
            try (BufferedReader reader = new BufferedReader(new StringReader(code))) {
                String line;
                int lineNumber = 0;

                // Read each line
                while ((line = reader.readLine()) != null) {
                    lineNumber++;

                    if (regex.matcher(line).find()) {
                        errors += "Match found for " + dep + " on line " + lineNumber + "\n";
                        if (!dict.get(dep).isEmpty() && dict.get(dep).length() > 1)
                        {
                            errors += "Suggest with replacing with safer alternative, " + dict.get(dep) + "\n";
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return errors;
    }
}
