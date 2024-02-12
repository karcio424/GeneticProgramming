package Program;

import java.util.ArrayList;
import java.util.List;

import static Program.GramAnalyzerMode.*;

public class GramaticsAnalyzer {
    private int braces = 0; // level of braces nesting
    private int loops = 0; // level of loops nesting
    private int conditionals = 0; // level of conditionals nesting (if/else)
    private int parentheses = 0; // level of parentheses nesting
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
        for (int i = 0; i < program.length(); i++) {
            char c = program.charAt(i);

            // check for white spaces
            if (Character.isWhitespace(c)) {
                continue;
            }

            // check for loops
            if (program.charAt(i) == 'l' && program.length() - i >= 4) {
                if (program.charAt(i + 1) == 'o' && program.charAt(i + 2) == 'o' && program.charAt(i + 3) == 'p') {
                    loops++;
                    recentlyFoundKeyword = LOOP;
                    i += 3;
                }
            }

            // check for braces
            if (c == '{') {
                if (recentlyFoundKeyword == IF || recentlyFoundKeyword == ELSE) {
                    for (int j = modeStack.size() - 1; j >= 0; j--)
                        if (modeStack.get(j) == IF || modeStack.get(j) == ELSE) { // workaround for whitespaces
                            modeStack.remove(j);
                            break;
                        }
                }
                modeStack.add(recentlyFoundKeyword);
                braces++;
            }
            if (c == '}') {
                modeStack.remove(modeStack.size() - 1);
                braces--;
                if (recentlyFoundKeyword == LOOP)
                    loops--;
                if (recentlyFoundKeyword == IF || recentlyFoundKeyword == ELSE)
                    conditionals--;
                recentlyFoundKeyword = modeStack.get(modeStack.size() - 1);
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

            // checks for conditionals
            if (program.charAt(i) == 'i' && program.length() - i >= 2) {
                if (program.charAt(i + 1) == 'f') {
                    conditionals++;
                    recentlyFoundKeyword = IF;
                    modeStack.add(recentlyFoundKeyword);
                    i++;
                }
            }
            if (program.charAt(i) == 'e' && program.length() - i >= 2) {
                if (program.charAt(i + 1) == 'l' && program.charAt(i + 2) == 's' && program.charAt(i + 3) == 'e') {
                    conditionals++;
                    recentlyFoundKeyword = ELSE;
                    modeStack.add(recentlyFoundKeyword);
                    i += 3;
                }
            }

            // check for semicolons outside of blocks as possible crossover points
            if ((c == ';' || c == '}') && braces == 0 && parentheses == 0 && loops == 0 && conditionals == 0
                    && modeStack.get(modeStack.size() - 1) == ELSE) {
                crossoverPoints.add(i + 1);
            }

            // check for possible mutation points
            if ((recentlyFoundKeyword == CONDITIONAL_BLOCK || recentlyFoundKeyword == BLOCK || recentlyFoundKeyword == NONE)
                    && (c == ';' || c == '{') && parentheses == 0) {
                mutationPoints.add(i + 1);
            }
        }

        return new PointsContainer(mutationPoints, crossoverPoints, true);
    }
}
