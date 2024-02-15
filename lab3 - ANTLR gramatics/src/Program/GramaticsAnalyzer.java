package Program;

import java.util.ArrayList;
import java.util.List;

import static Program.GramAnalyzerMode.*;

public class GramaticsAnalyzer {
    private int braces = 0, parentheses = 0; // level of braces and parentheses nesting
    private final List<GramAnalyzerMode> modeStack = new ArrayList<>(); // stack that helps keep track of current mode
    private final List<Integer> mutationPoints = new ArrayList<>(); // points where mutation can occur
    private final List<Integer> crossoverPoints = new ArrayList<>(); // points where crossover can occur
    private final String program; // program code to analyze

    public GramaticsAnalyzer(String code) {
        modeStack.add(NONE);
        this.program = code;
    }

    public PointsContainer analyze() {
        GramAnalyzerMode recentlyFoundKeyword = NONE;
        for (int i = 0; i < program.length() - 1; i++) {
            char c = program.charAt(i);

            // check for white spaces
            if (Character.isWhitespace(c))
                continue;

            // check for loops
            if (c == 'l' && program.length() - i >= 4)
                if (program.charAt(i + 1) == 'o' && program.charAt(i + 2) == 'o' && program.charAt(i + 3) == 'p') {
                    recentlyFoundKeyword = LOOP;
                    modeStack.add(recentlyFoundKeyword);
                    i += 3;
                    continue;
                }

            // check for braces
            if (c == '{') {
                if (recentlyFoundKeyword == IF || recentlyFoundKeyword == ELSE || recentlyFoundKeyword == LOOP) {
                    for (int j = modeStack.size() - 1; j >= 0; j--)
                        if (modeStack.get(j) == IF || modeStack.get(j) == ELSE || recentlyFoundKeyword == LOOP) { // workaround for whitespaces
                            modeStack.remove(j);
                            break;
                        }
                    modeStack.add(recentlyFoundKeyword);
                }
                modeStack.add(BLOCK);
                braces++;
            }
            if (c == '}') {
                if (recentlyFoundKeyword == IF || recentlyFoundKeyword == ELSE || recentlyFoundKeyword == LOOP)
                    modeStack.remove(modeStack.size() - 1);
                recentlyFoundKeyword = modeStack.get(modeStack.size() - 1);
                modeStack.remove(modeStack.size() - 1);
                braces--;
            }

            // check for parentheses
            if (c == '(') {
                recentlyFoundKeyword = STATEMENT;
                modeStack.add(recentlyFoundKeyword);
                parentheses++;
            }
            if (c == ')') {
                modeStack.remove(modeStack.size() - 1);
                recentlyFoundKeyword = modeStack.get(modeStack.size() - 1);
                parentheses--;
            }

            // check for if keyword
            if (c == 'i' && program.length() - i >= 2)
                if (program.charAt(i + 1) == 'f') {
                    recentlyFoundKeyword = IF;
                    modeStack.add(recentlyFoundKeyword);
                    i++;
                    continue;
                }

            // check for else keyword
            if (c == 'e' && program.length() - i >= 4)
                if (program.charAt(i + 1) == 'l' && program.charAt(i + 2) == 's' && program.charAt(i + 3) == 'e') {
                    recentlyFoundKeyword = ELSE;
                    modeStack.add(recentlyFoundKeyword);
                    i += 3;
                    continue;
                }

            // check for possible crossover points
            if (((c == '}' && (recentlyFoundKeyword == ELSE || recentlyFoundKeyword == LOOP))
                    || c == ';') && braces == 0 && parentheses == 0) {
                recentlyFoundKeyword = modeStack.get(modeStack.size() - 1);
                crossoverPoints.add(i + 1);
            }

            // check for possible mutation points
            if (c == '-' || c == '+' || c == '*' || c == '/' || c == '%')
                mutationPoints.add(i);
            if ((c == '>' && program.charAt(i+1) != '=') ||((c == '<' && program.charAt(i+1) != '=')))
                mutationPoints.add(i);
            if ((c == '>' && program.charAt(i+1) == '=') || (c == '<' && program.charAt(i+1) == '=') ||
            (c == '=' && program.charAt(i+1) == '=') || (c == '!' && program.charAt(i+1) == '=')){
                mutationPoints.add(i);
                i+=1;
            }
            if (c == 'i' && program.length() - i >= 5)
                if (program.charAt(i + 1) == 'n' && program.charAt(i + 2) == 'p'
                        && program.charAt(i + 3) == 'u' && program.charAt(i + 4) == 't') {
                    mutationPoints.add(i);
                    i += 4;
                    continue;
                }
            if (c == 'v' && program.length() - i >= 4) {
                if (program.charAt(i + 1) == 'a' && program.charAt(i + 2) == 'r') {
                    mutationPoints.add(i);
                    i += 2;
                }
            }
            if (c >= '0' && c <= '9'){
                if((program.charAt(i-1) != 'r')){
                    mutationPoints.add(i);
                }
                char next = program.charAt(i+1);
                while (program.length() - i >= 1 && next >= '0' && next <= '9'){
                    i+=1;
                    next = program.charAt(i+1);
                }

            }
        }

        return new PointsContainer(mutationPoints, crossoverPoints);
    }
}