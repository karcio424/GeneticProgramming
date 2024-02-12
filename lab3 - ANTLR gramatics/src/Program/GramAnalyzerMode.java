package Program;

public enum GramAnalyzerMode {
    NONE, // code outside of any blocks
    LOOP, // loop block
    IF,
    ELSE,
    CONDITIONAL_BLOCK, // conditional block corresponding to one of if or else keywords
    BLOCK, // block of code
    STATEMENT // statement in parentheses
}
