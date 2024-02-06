package Program;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalityTest {
    //TODO: ZROBIC MAX VALUE!!!
    @Test
    //1.1.A Program powinien wygenerować na wyjściu (na dowolnej pozycji w danych wyjściowych)
    // liczbę 1. Poza liczbą 1 może też zwrócić inne liczby.
    public void function_1_1_A() {
        int[][] testCase = {
                {5, 5, 1},  //ILOSC TEST CASE'OW, ILOSC DANYCH INPUTU, ILOSC DANYCH OUTPUTU
                {9, 8, 7, 6, 5, 1},
                {0, 0, 0, 0, 0, 1},
                {9, 9, 9, 9, 9, 1},
                {1, 2, 3, 4, 5, 1},
                {1, 1, 1, 1, 1, 1}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }
    @Test
    //1.1.B Program powinien wygenerować na wyjściu (na dowolnej pozycji w danych wyjściowych)
    // liczbę 789. Poza liczbą 789 może też zwrócić inne liczby.
    public void function_1_1_B() {
        int[][] testCase = {
                {5, 5, 1},
                {9, 8, 7, 6, 5, 789},
                {0, 0, 0, 0, 0, 789},
                {9, 9, 9, 9, 9, 789},
                {1, 2, 3, 4, 5, 789},
                {1, 1, 1, 1, 1, 789}};
        assertEquals(0, GPTesting.main(testCase, 1000));
    }

    @Test
    //1.1.C Program powinien wygenerować na wyjściu (na dowolnej pozycji w danych wyjściowych)
    // liczbę 31415. Poza liczbą 31415 może też zwrócić inne liczby.
    public void function_1_1_C() {
        int[][] testCase = {
                {5, 5, 1},
                {9, 8, 7, 6, 5, 31415},
                {0, 0, 0, 0, 0, 31415},
                {9, 9, 9, 9, 9, 31415},
                {1, 2, 3, 4, 5, 31415},
                {1, 1, 1, 1, 1, 31415}};
        assertEquals(0, GPTesting.main(testCase, 100000));
    }

    @Test
    //1.1.D Program powinien wygenerować na pierwszej pozycji na wyjściu liczbę 1.
    // Poza liczbą 1 może też zwrócić inne liczby.
    public void function_1_1_D() {
        int[][] testCase = {
                {5, 5, 1},
                {9, 8, 7, 6, 5, 1},
                {0, 0, 0, 0, 0, 1},
                {9, 9, 9, 9, 9, 1},
                {1, 2, 3, 4, 5, 1},
                {1, 1, 1, 1, 1, 1}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.1.E Program powinien wygenerować na pierwszej pozycji na wyjściu liczbę 789.
    // Poza liczbą 789 może też zwrócić inne liczby.
    public void function_1_1_E() {
        int[][] testCase = {
                {5, 5, 1},
                {9, 8, 7, 6, 5, 789},
                {0, 0, 0, 0, 0, 789},
                {9, 9, 9, 9, 9, 789},
                {1, 2, 3, 4, 5, 789},
                {1, 1, 1, 1, 1, 789}};
        assertEquals(0, GPTesting.main(testCase, 1000));
    }

    @Test
    //1.1.F Program powinien wygenerować na wyjściu liczbę jako jedyną liczbę 1.
    // Poza liczbą 1 NIE powinien nic więcej wygenerować.
    public void function_1_1_F() {
        int[][] testCase = {
                {5, 5, 1},
                {9, 8, 7, 6, 5, 1},
                {0, 0, 0, 0, 0, 1},
                {9, 9, 9, 9, 9, 1},
                {1, 2, 3, 4, 5, 1},
                {1, 1, 1, 1, 1, 1}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.2.A Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu
    // (jedynie) ich sumę. Na wejściu mogą być tylko całkowite liczby dodatnie w zakresie [0,9]
    public void function_1_2_A() {
        int[][] testCase = {
                {10, 2, 1},
                {9, 8, 17},
                {1, 5, 6},
                {3, 4, 7},
                {9, 0, 9},
                {0, 0, 0},
                {1, 1, 2},
                {9, 3, 12},
                {7, 3, 10},
                {5, 8, 13},
                {0, 1, 1}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.2.B Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu
    // (jedynie) ich sumę. Na wejściu mogą być tylko całkowite liczby w zakresie [-9,9]
    public void function_1_2_B() {
        int[][] testCase = {
                {10, 2, 1},
                {-9, 8, -1},
                {1, -5, -4},
                {3, 4, 7},
                {9, 0, 9},
                {0, 0, 0},
                {1, -1, 0},
                {5, -3, 2},
                {7, 3, 10},
                {-5, 8, 3},
                {0, 1, 1}};
        GPTesting.main(testCase, 100);
    }

    @Test
    //1.2.C Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu
    // (jedynie) ich sumę. Na wejściu mogą być tylko całkowite liczby dodatnie w zakresie [-9999,9999]
    public void function_1_2_C() {
        int[][] testCase = {
                {10, 2, 1},
                {-900, 8000, 7100},
                {1, -5000, -4999},
                {3, 4, 7},
                {900, 0, 900},
                {0, 0, 0},
                {1000, -1000, 0},
                {500, -350, 150},
                {792, 33, 825},
                {-5535, 8121, 2586},
                {1990, 1, 1991}};
        GPTesting.main(testCase, 100);
    }



    /*

1.2.D Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu (jedynie) ich różnicę. Na wejściu mogą być tylko całkowite liczby dodatnie w zakresie [-9999,9999]

1.2.E Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu (jedynie) ich iloczyn. Na wejściu mogą być tylko całkowite liczby dodatnie w zakresie [-9999,9999]

1.3.A Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu (jedynie) większą z nich. Na wejściu mogą być tylko całkowite liczby dodatnie w zakresie [0,9]

1.3.B Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu (jedynie) większą z nich. Na wejściu mogą być tylko całkowite liczby w zakresie [-9999,9999]

1.4.A Program powinien odczytać dziesięć pierwszych liczy z wejścia i zwrócić na wyjściu (jedynie) ich średnią arytmetyczną (zaokrągloną do pełnej liczby całkowitej). Na wejściu mogą być tylko całkowite liczby w zakresie [-99,99]

1.4.B Program powinien odczytać na początek z wejścia pierwszą liczbę (ma być to wartość nieujemna) a następnie tyle liczb (całkowitych) jaka jest wartość pierwszej odczytanej liczby i zwrócić na wyjściu (jedynie) ich średnią arytmetyczną zaokrągloną do pełnej liczby całkowitej (do średniej nie jest wliczana pierwsza odczytana liczba, która mówi z ilu liczb chcemy obliczyć średnią). Na wejściu mogą być tylko całkowite liczby w zakresie [-99,99], pierwsza liczba może być tylko w zakresie [0,99].
     */
}
