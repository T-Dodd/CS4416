import java.io.*;
import java.util.regex.*;
import java.util.HashMap;
import java.io.BufferedReader;

public class Regex {
    private String code;
    private HashMap<String,String> dict;

    public Regex(String codeInput) {
        code = codeInput;
        DeprecatedFuncs depFuncs = new DeprecatedFuncs();
        dict = depFuncs.dict;
    }

    public void find() {
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
                        System.out.println("Match found for " + dep + " on line " + lineNumber);
                        if (dict.get(dep) != "")
                        {
                            System.out.println("Suggest with replacing with safer alternative, " + dict.get(dep));
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
