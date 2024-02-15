package Program;

import java.util.ArrayList;
import java.util.List;

public class TestMain {
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
        System.out.println(program);
        List<String> l = new ArrayList<>();
        l.add("var40");
        l.add("var55");
        l.add("var18");
        for (int i = 0; i < 10; i++)
            program = GPUtils.mutate(program, l);
        System.out.println("MUTATED:");
        System.out.println(program);
    }
}
