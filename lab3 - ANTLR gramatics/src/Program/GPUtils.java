package Program;

import Interpreter.GPprojectLexer;
import Interpreter.GPprojectParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import Interpreter.Variables.AntlrProgram;

import java.util.List;
import java.util.Arrays;


public class GPUtils {

    private static ParseTree generateRandomProgram(int length, List<String> list) {
//        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(generateRandomStatement(length, list)));
//        GPprojectParser parser = new GPprojectParser(new CommonTokenStream(lexer));
        return parseText(generateRandomStatement(length, list));
//        return generateRandomStatement(length, list);
    }

    private static String generateRandomStatement(int length, List<String> variableList) {
        if (length <= 0) {
            return "";
        }

        String statement = "";
        int randomRule = generateRandomNumber(1, 4);
        int randomVar = generateRandomNumber(0, variableList.size() - 1);

        switch (randomRule) {
            case 1 -> statement = generateRandomLoopStatement(length - 1, variableList.get(randomVar));
            case 2 -> statement = generateRandomConditionalStatement(length - 1, variableList.get(randomVar));
            case 3 -> statement = generateRandomBlockStatement(length - 1, variableList.get(randomVar));
            case 4 -> statement = generateRandomAssignmentStatement(variableList.get(randomVar));
        }

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //TODO: generacja outputu jako kolejny rodzaj statementu => instrukcja output(TUTAJ DOWOLNE DOSTEPNE ZMIENNE)
        // dodatkowo -> zastosowanie koncepcji full i grow (albo którejś z nich)

        return statement + generateRandomStatement(length - 1, variableList);
    }


    private static String generateRandomConditionalStatement(int length, String var) {
        String condition = var;
        String thenStatement = "{" + var + "=" + generateRandomNumber(1, 100) + ";}";
        String elseStatement = "{" + var + "=" + generateRandomNumber(1, 100) + ";}";

        return "if (" + condition + ") " + thenStatement + "else {" + elseStatement + "}\n";
        //TODO: zamiana na 'if' '(' generateRandomExpression ')' generateRandomBlockStatement
        // 'else' generateRandomBlockStatement;
        // MOŻE ALE NIE MUSI BYĆ ELSE
        // dodatkowo!!!: jesli jest samo ID to wybor sposrod zmiennych ktore maja wartosci przypisane
        // .
        // MOŻNA PRZEMYSLEC CZY NA POCZATKU PROGRAMU OD RAZU KAZDEJ ZMIENNEJ NIE PRZYPISAC wartosci (np. 0)
    }

    private static String generateRandomLoopStatement(int length, String var) {
        return "loop(" + var + ") {" + var + "=" + generateRandomNumber(1, 100) + ";}\n";
        //TODO: zamiana na 'loop' '(' generateRandomExpression ')' generateRandomBlockStatement
        // dodatkowo!!!: jesli jest samo ID to wybor sposrod zmiennych ktore maja wartosci przypisane
        // .
        // MOŻNA PRZEMYSLEC CZY NA POCZATKU PROGRAMU OD RAZU KAZDEJ ZMIENNEJ NIE PRZYPISAC wartosci (np. 0)
    }


    private static String generateRandomBlockStatement(int length, String var) {
        return "{" + var + "="+ generateRandomNumber(1,100) + ";}\n";
        //TODO: generateRandomStatement (i tutaj w szczególności pamiętać o tym,
        // żeby nie mogło generować w nieskończoność zagęszczonych instrukcji
    }

    private static String generateRandomAssignmentStatement(String var) {
        return var + "="+ generateRandomNumber(1,100) + ";\n";
        //TODO: var = generateRandomExpression
    }

    private static String generateRandomExpression() {
        return null;
        //TODO: WG GRAMATYKI!!!!!!!!! czyli +,-, etc... => w niej zawarcie inputu też
    }

    private static ParseTree parseText(String text) {
        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GPprojectParser parser = new GPprojectParser(tokens);
//        GPprojectLexer lexer = new GPprojectLexer(CharStreams.fromString(text));
//        CommonTokenStream tokens = new CommonTokenStream(lexer);
        System.out.println(text);
        return parser.program();
    }

    private static int generateRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private static String generateRandomVariableName() {
        return "var" + generateRandomNumber(1, 100);
    }

    public static ParseTree crossover(ParseTree program1, ParseTree program2) {
        String programText1 = program1.getText();
        String programText2 = program2.getText();

        int splitIndex1 = findSplitIndex(programText1);
        int splitIndex2 = findSplitIndex(programText2);

        String newProgramText = programText1.substring(0, splitIndex1) + programText2.substring(splitIndex2);

        return parseText(newProgramText);
    }

    private static int findSplitIndex(String programText) {
        int index = generateRandomNumber(1, programText.length() - 1);

        while (index < programText.length() - 1) {
            if (isStatementBoundary(programText, index)) {
                break;
            }
            index++;
        }

        return index;
    }

    private static boolean isStatementBoundary(String programText, int index) {
        return (programText.charAt(index) == ';' && programText.charAt(index+1) != '}') || (programText.charAt(index) == '}' && programText.charAt(index+1) != 'e');
    }

    public static ParseTree mutate(ParseTree program) {
        String programText = program.getText();

        int mutationIndex = findMutationIndex(programText);

        //TODO: TUTAJ TAK NIE POWINNO BYĆ
        // generujesz dodatkowo ileś statementów (od 1 do 10) do tego co już jest.......
        String mutatedProgramText = programText.substring(0, mutationIndex);
        int statementLength = generateRandomNumber(1, 10);
        mutatedProgramText += generateRandomStatement(statementLength, Arrays.asList("var1", "var2", "var3"));
        mutatedProgramText += programText.substring(mutationIndex);

        return parseText(mutatedProgramText);
    }

    private static int findMutationIndex(String programText) {
        int index = generateRandomNumber(1, programText.length() - 1);

        while (index < programText.length() - 1) {
            if (isStatementBoundary(programText, index)) {
                break;
            }
            index++;
        }

        return index;
    }


    public static void testProgram(ParseTree program, List<Integer> input, List<Integer> output) {
        AntlrProgram programVisitor = new AntlrProgram(100);
        programVisitor.visit(program);
        System.out.println("Testing program: " + program.getText());
        System.out.println("Input program: " + input);
        System.out.println("Output program: " + output);
        //TODO: TA FUNKCJA BEDZIE W KLASIE INTERPRETER INTERFACE, więc nie ma co tutaj robić
    }

    public static ParseTree tournamentSelection(List<ParseTree> programs, int tournamentSize) {
        int winnerIndex = -1;
        double bestFitness = Double.MAX_VALUE;

        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = generateRandomNumber(0, programs.size() - 1);
            ParseTree candidate = programs.get(randomIndex);
            double fitness = calculateFitness(candidate);

            if (fitness < bestFitness) {
                bestFitness = fitness;
                winnerIndex = randomIndex;
            }
        }

        return programs.get(winnerIndex);
    }

    private static double calculateFitness(ParseTree program) {
        AntlrProgram programVisitor = new AntlrProgram(100);
        programVisitor.visit(program);
        // TODO: TO BEDZIE GDZIE INDZIEJ (plik INTERPRETER_INTERFACE)
        return 0.0;
    }

    public static String serialize(ParseTree program) {
        return program.getText();
    }

    public static ParseTree deserialize(String serializedData) {
        return parseText(serializedData);
    }

    public static void main(String[] args) {
        Integer[] inputData = {1, 2, 3};
        Integer[] outputData = {2, 4, 6};

        List<Integer> inputList = Arrays.asList(inputData);
        List<Integer> outputList = Arrays.asList(outputData);
        List<String> first = Arrays.asList("var1", "var2", "var3");
        List<String> second = Arrays.asList("var4", "var5", "var6");

//        System.out.println("Random Program: ");
        ParseTree randomProgram = generateRandomProgram(10, first);
//        System.out.println("Random Program: ");

        if (randomProgram != null) {
//            System.out.println("Random Program: " + randomProgram.getText());
            testProgram(randomProgram, inputList, outputList);
        } else {
            System.out.println("Failed to generate a random program.");
        }

        ParseTree anotherRandomProgram = generateRandomProgram(10, second);

        if (anotherRandomProgram != null) {
//            System.out.println("Random Program: " + anotherRandomProgram.getText());
            testProgram(anotherRandomProgram, inputList, outputList);
        } else {
            System.out.println("Failed to generate a random program.");
        }

        ParseTree crossedProgram = crossover(randomProgram, anotherRandomProgram);
        System.out.println("_______________________________________-");
        System.out.println("Crossed Program: " + crossedProgram.getText());

        testProgram(crossedProgram, inputList, outputList);

        ParseTree mutatedProgram = mutate(randomProgram);
        System.out.println("_______________________________________-");
        System.out.println("Mutated Program: " + mutatedProgram.getText());

        testProgram(mutatedProgram, inputList, outputList);

        List<ParseTree> programList = Arrays.asList(randomProgram, anotherRandomProgram, crossedProgram, mutatedProgram);
        ParseTree selectedProgram = tournamentSelection(programList, 2);
        System.out.println("Selected Program: " + selectedProgram.getText());

        testProgram(selectedProgram, inputList, outputList);
    }
}