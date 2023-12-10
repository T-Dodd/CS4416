import java.io.*;
/*
* Manages the interaction with the GUI and the backend
* */
import java.util.Map;
public class GUIManager {




    public void editSuggestions(String badFunc, String suggestions, Map<String, String> dict) {
        if (dict.containsKey(badFunc)) {
            dict.remove(badFunc);
        }
        dict.put(badFunc, suggestions);
    }

}
