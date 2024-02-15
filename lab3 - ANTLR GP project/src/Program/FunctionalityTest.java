package Program;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalityTest {
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
        assertEquals(0, GPTesting.main(testCase, 100));
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
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.2.D Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu
    // (jedynie) ich różnicę. Na wejściu mogą być tylko całkowite liczby dodatnie w zakresie [-9999,9999]
    public void function_1_2_D() {
        int[][] testCase = {
                {10, 2, 1},
                {-900, 8000, -8900},
                {1, -5000, 5001},
                {3, 4, -1},
                {900, 0, 900},
                {0, 0, 0},
                {1000, -1000, 2000},
                {500, -350, 850},
                {792, 33, 759},
                {-5535, 8121, -13656},
                {1990, 1, 1989}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.2.E Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu
    // (jedynie) ich iloczyn. Na wejściu mogą być tylko całkowite liczby dodatnie w zakresie [-9999,9999]
    public void function_1_2_E() {
        int[][] testCase = {
                {10, 2, 1},
                {-900, 8000, -7200000},
                {1, -5000, -5000},
                {3, 4, 12},
                {900, 0, 0},
                {0, 0, 0},
                {1000, -1000, -1000000},
                {500, -350, -175000},
                {792, 33, 26136},
                {-5535, 8121, -44949735},
                {1990, 1, 1990}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.3.A Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu (jedynie) większą z nich.
    // Na wejściu mogą być tylko całkowite liczby dodatnie w zakresie [0,9]
    public void function_1_3_A() {
        int[][] testCase = {
                {10, 2, 1},
                {9, 8, 9},
                {1, 5, 5},
                {3, 4, 4},
                {9, 0, 9},
                {0, 0, 0},
                {1, 1, 1},
                {9, 3, 9},
                {7, 3, 7},
                {5, 8, 8},
                {0, 1, 1}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.3.B Program powinien odczytać dwie pierwsze liczy z wejścia i zwrócić na wyjściu (jedynie)
    // większą z nich. Na wejściu mogą być tylko całkowite liczby w zakresie [-9999,9999]
    public void function_1_3_B() {
        int[][] testCase = {
                {10, 2, 1},
                {-900, 8000, 8000},
                {1, -5000, 1},
                {3, 4, 4},
                {900, 0, 900},
                {0, 0, 0},
                {1000, -1000, 1000},
                {500, -350, 500},
                {792, 33, 792},
                {-5535, 8121, 8121},
                {1990, 1, 1990}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.4.A Program powinien odczytać dziesięć pierwszych liczy z wejścia i zwrócić na wyjściu
    // (jedynie) ich średnią arytmetyczną (zaokrągloną do pełnej liczby całkowitej).
    // Na wejściu mogą być tylko całkowite liczby w zakresie [-99,99]
    public void function_1_4_A() {
        int[][] testCase = {
                {10, 10, 1},
                {-25, -30, -67, -24, 25, 30, 67, 24, -2, 2, 0},
                {20, -23, 33, 19, 2, -70, 80, 56, -99, 17, 4},
                {20, -23, 33, 19, 2, 70, 80, 56, 99, 17, 37},
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 5},
                {0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -5},
                {85, -98, 18, 15, -60, -17, 65, -74, 16, 27, -2},
                {31, -34, -48, 73, -29, -42, 75, -35, 49, 35, 8},
                {61, -32, 72, 72, 30, -68, 8, -83, 4, -17, 5},
                {66, 61, 4, 85, 43, 90, 92, -59, 25, -81, 33},
                {81, -57, -21, 93, 15, 14, 48, -74, 45, -5, 11}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    //1.4.B Program powinien odczytać na początek z wejścia pierwszą liczbę (ma być to wartość nieujemna)
    // a następnie tyle liczb (całkowitych) jaka jest wartość pierwszej odczytanej liczby i zwrócić
    // na wyjściu (jedynie) ich średnią arytmetyczną zaokrągloną do pełnej liczby całkowitej
    // (do średniej nie jest wliczana pierwsza odczytana liczba, która mówi z ilu liczb chcemy obliczyć średnią).
    // Na wejściu mogą być tylko całkowite liczby w zakresie [-99,99],
    // pierwsza liczba może być tylko w zakresie [0,99].
    public void function_1_4_B() {
        int[][] testCase = {
                {4, 5, 1}, //INFO: DAJEMY ZAWSZE ŚREDNIĄ Z 4 LICZB
                {4, -73, -19, 25, 21, -12},
                {4, -7, -58, 16, -94, -36},
                {4, 98, 27, 58, -63, 30},
                {4, 94, -86, 73, 47, 32}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    public void TestBenchmark_2() { //Small or Large: if n < 1000 return 0 if n >= 2000 return 1
        int[][] testCase = {
                {10, 1, 1},
                {10, 0},
                {330, 0},
                {2340, 1},
                {2000, 1},
                {990, 0},
                {82, 0},
                {9, 0},
                {2740, 1},
                {2620, 1},
                {2390, 1}};

        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBenchmark_21() { //Negative to zero: Given a vector of integers,
        // return the vector where all negative int have been replaced by 0
        int[][] testCase = {
                {10, 3, 3},
                {1, 0, 3, 1, 0, 3},
                {33, 13, 23, 33, 13, 23},
                {0, 10, -2, 0, 10, 0},
                {10, -33, 2, 10, 0, 2},
                {0, 1, 2, 0, 1, 2},
                {-80, 11, 0, 0, 11, 0},
                {-90, -22, -3, 0, 0, 0},
                {-274, -32, 2, 0, 0, 2},
                {0, 0, 0, 0, 0, 0},
                {-239, -1, -2, 0, 0, 0}};

        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBenchmark_27() { //Median of 3 integers
        int[][] testCase = {
                {10, 3, 1},
                {1, 0, 3, 1},
                {33, 13, 23, 23},
                {0, 10, -2, 0},
                {10, -33, 2, 2},
                {0, 1, 2, 1},
                {-80, -11, 0, -11},
                {90, 22, -3, 22},
                {-274, -32, 2, -32},
                {0, 0, 0, 0},
                {-239, -1, -200, -200}};

        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBool_AND() {
        int[][] testCase = {
                {4, 2, 1},
                {0, 0, 0},
                {0, 1, 0},
                {1, 0, 0},
                {1, 1, 1}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBool_OR() {
        int[][] testCase = {
                {4, 2, 1},
                {0, 0, 0},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 1}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBool_XOR() {
        int[][] testCase = {
                {4, 2, 1},
                {0, 0, 0},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBool_NOT() {
        int[][] testCase = {
                {2, 1, 1},
                {0, 1},
                {1, 0}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBool_NOR() {
        int[][] testCase = {
                {4, 2, 1},
                {0, 0, 1},
                {0, 1, 0},
                {1, 0, 0},
                {1, 1, 0}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBool_NAND() {
        int[][] testCase = {
                {4, 2, 1},
                {0, 0, 1},
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

    @Test
    void TestBool_XNOR() {
        int[][] testCase = {
                {4, 2, 1},
                {0, 0, 1},
                {0, 1, 0},
                {1, 0, 0},
                {1, 1, 1}};
        assertEquals(0, GPTesting.main(testCase, 100));
    }

}
