package Program;

public enum GramAnalyzerMode {
    NONE, // code outside of any blocks
    LOOP, // loop block
    IF, // if keyword
    ELSE, // else keyword
    BLOCK, // block of code
    STATEMENT // statement in parentheses
}
