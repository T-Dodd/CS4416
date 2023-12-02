import java.util.ArrayList;

public class Tokenizer {
    private String source;
    private ArrayList<String> tokens;

    public Tokenizer(String source){}

    /*
     * Takes the contents of a source file as a string and tokenizes the contents
     * */
    private ArrayList<String> tokenize(String source)
    {
        String token = "";
        for(char c: source.toCharArray()){
            if(c == '\n' || c == '\r' || c == ' ' || c == ',' || c =='\0'){
                tokens.add(token);
                token = "";
            }
            token += c;
        }
        return tokens;
    }

    public ArrayList<String> getTokens(){
        if(this.tokens.isEmpty()){
            tokenize(this.source);
        }
        return this.tokens;
    }
}
