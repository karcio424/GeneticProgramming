package Program;

public class GramAnalyzerTester {
    public static void main(String[] args) {
        String program = """
                var40=15 / 88;
                if (var40) {
                output(var55);
                if (var40) {
                output(var55);
                loop(var55){
                var55=var55;
                }
                }
                 else{
                var55=1;
                }
                }
                 else{
                var55=1;
                }
                output(var55);
                var40=71;
                var18=97;
                var55=input;
                var40=42;
                loop(var55){
                var55=var55;
                }
                                """;
        PointsContainer pc = new GramaticsAnalyzer(program).analyze();
        System.out.println(pc.getAllMutationPoints());
        System.out.println(pc.getAllCrossoverPoints());
    }
}
