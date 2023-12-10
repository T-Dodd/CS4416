import java.io.*;
import java.util.regex.*;
import java.util.HashMap;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Regex {
    private HashMap<String,String> dict;

    public Regex() {
        DeprecatedFuncs depFuncs = new DeprecatedFuncs();
        dict = depFuncs.dict;
    }

    public String find(String code, List<String> uncheckedFunctions) {
        String errors = "";
        for (String dep : dict.keySet())
        {
            if(!uncheckedFunctions.contains(dep)) {
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
                            if (!dict.get(dep).isEmpty() && dict.get(dep).length() > 1) {
                                errors += "Suggest with replacing with safer alternative, " + dict.get(dep) + "\n";
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    return errors;
    }

    public void addFunc(ArrayList<String> function)
    {
        String func = function.remove(0);
        String alternatives = "";
        while(!function.isEmpty())
        {
            alternatives += function.remove(0) + " ";
        }
        dict.put(func, alternatives);
    }
    
    public void removeFunc(String function)
    {
        dict.remove(function);
    }
}
