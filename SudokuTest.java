import junit.framework.TestCase;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.assertThrows;

public class SudokuTest extends TestCase {

    public void test1(){
        Sudoku sudoku = new Sudoku(Sudoku.easyGrid);
        String expectedSolutionText = "1 6 4 7 9 5 3 8 2 \n" +
                            "2 8 7 4 6 3 9 1 5 \n" +
                            "9 3 5 2 8 1 4 6 7 \n" +
                            "3 9 1 8 7 6 5 2 4 \n" +
                            "5 4 6 1 3 2 7 9 8 \n" +
                            "7 2 8 9 5 4 1 3 6 \n" +
                            "8 1 9 6 4 7 2 5 3 \n" +
                            "6 7 3 5 2 9 8 4 1 \n" +
                            "4 5 2 3 1 8 6 7 9 \n";
        int expectedSolutions = 1;


        int solutions = sudoku.solve();
        String solutionText = sudoku.getSolutionText();

        assertEquals(expectedSolutions,solutions);
        assertEquals(expectedSolutionText,solutionText);

    }
    public void test2(){
        Sudoku sudoku = new Sudoku(Sudoku.mediumGrid);
        String expectedSolutionText = "5 3 4 6 7 8 9 1 2 \n" +
                                      "6 7 2 1 9 5 3 4 8 \n" +
                                        "1 9 8 3 4 2 5 6 7 \n" +
                                        "8 5 9 7 6 1 4 2 3 \n" +
                                        "4 2 6 8 5 3 7 9 1 \n" +
                                        "7 1 3 9 2 4 8 5 6 \n" +
                                        "9 6 1 5 3 7 2 8 4 \n" +
                                        "2 8 7 4 1 9 6 3 5 \n" +
                                        "3 4 5 2 8 6 1 7 9 \n";
        int expectedSolutions = 1;


        int solutions = sudoku.solve();
        String solutionText = sudoku.getSolutionText();

        assertEquals(expectedSolutions,solutions);
        assertEquals(expectedSolutionText,solutionText);
    }
    public void test3(){
        Sudoku sudoku = new Sudoku(Sudoku.hardGrid);
        String expectedSolutionText = "3 7 5 1 6 2 4 8 9 \n"+
                                        "8 6 1 4 9 3 5 2 7 \n"+
                                        "2 4 9 7 8 5 1 6 3 \n"+
                                        "4 9 3 8 5 7 6 1 2 \n"+
                                        "7 1 6 2 4 9 8 3 5 \n"+
                                        "5 2 8 3 1 6 7 9 4 \n"+
                                        "6 5 7 9 2 1 3 4 8 \n"+
                                        "1 8 2 5 3 4 9 7 6 \n"+
                                        "9 3 4 6 7 8 2 5 1 \n";
        int expectedSolutions = 1;

        int solutions = sudoku.solve();
        String solutionText = sudoku.getSolutionText();

        assertEquals(expectedSolutions,solutions);
        assertEquals(expectedSolutionText,solutionText);
    }
    public void test4(){
        int[][] grid = Sudoku.stringsToGrid(
                "3 0 0 0 0 0 0 8 0",
                "0 0 1 0 9 3 0 0 0",
                "0 4 0 7 8 0 0 0 3",
                "0 9 3 8 0 0 0 1 2",
                "0 0 0 0 4 0 0 0 0",
                "5 2 0 0 0 6 7 9 0",
                "6 0 0 0 2 1 0 4 0",
                "0 0 0 5 3 0 9 0 0",
                "0 3 0 0 0 0 0 5 1");
        Sudoku sudoku = new Sudoku(grid);
        String expectedSolutionText = "3 5 7 1 6 2 4 8 9 \n"+
                                        "8 6 1 4 9 3 2 7 5 \n"+
                                        "2 4 9 7 8 5 1 6 3 \n"+
                                        "4 9 3 8 5 7 6 1 2 \n"+
                                        "1 7 6 2 4 9 5 3 8 \n"+
                                        "5 2 8 3 1 6 7 9 4 \n"+
                                        "6 8 5 9 2 1 3 4 7 \n"+
                                        "7 1 4 5 3 8 9 2 6 \n"+
                                        "9 3 2 6 7 4 8 5 1 \n";
        int expectedSolutions = 6;

        int solutions = sudoku.solve();
        String solutionText = sudoku.getSolutionText();

        assertEquals(expectedSolutions,solutions);
        assertEquals(expectedSolutionText,solutionText);
    }

    public void test5(){
        String gridString = "000000000"+
                "000000000"+
                "000000000"+
                "000000000"+
                "000000000"+
                "000000000"+
                "000000000"+
                "000000000"+
                "000000000";
        int[][] grid = Sudoku.textToGrid(gridString);

        Sudoku sudoku = new Sudoku(grid);
        int solutions = sudoku.solve();
        assertEquals(100,solutions);
        String expectedText = "1 2 3 4 5 6 7 8 9 \n" +
                                "4 5 6 7 8 9 1 2 3 \n" +
                                "7 8 9 1 2 3 4 5 6 \n" +
                                "2 1 4 3 6 5 8 9 7 \n" +
                                "3 6 5 8 9 7 2 1 4 \n" +
                                "8 9 7 2 1 4 3 6 5 \n" +
                                "5 3 1 6 4 2 9 7 8 \n" +
                                "6 4 2 9 7 8 5 3 1 \n" +
                                "9 7 8 5 3 1 6 4 2 \n";
        String result = sudoku.getSolutionText();

        assertEquals(expectedText,result);
    }
    public void test6(){
        int[][] grid = new int[9][9];
        grid[0] = Sudoku.stringToInts("1 2 3 4 5 6 7 8 9");
        grid[1] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[2] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[3] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[4] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[5] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[6] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[7] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[8] = Sudoku.stringToInts("9 8 7 6 4 5 3 2 1");

        Sudoku sudoku = new Sudoku(grid);
        int solutions = sudoku.solve();
        assertEquals(100,solutions);
    }
    public void test7(){
        int[][] grid = new int[9][9];
        grid[0] = Sudoku.stringToInts("1 2 3 4 5 6 7 8 9");
        grid[1] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[2] = Sudoku.stringToInts("0 0 0 0 0 - 0 0 0");
        grid[3] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[4] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[5] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[6] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[7] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[8] = Sudoku.stringToInts("9 8 7 6 4 5 3 2 1");

        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Sudoku sudoku = new Sudoku(grid);
            }
        });
    }
    public void test8(){
        int[][] grid = new int[8][8];
        grid[0] = Sudoku.stringToInts("1 2 3 4 5 6 7 8");
        grid[1] = Sudoku.stringToInts("0 0 0 0 0 0 0 0");
        grid[2] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 ");
        grid[3] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 ");
        grid[4] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 ");
        grid[5] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 ");
        grid[6] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 ");
        grid[7] = Sudoku.stringToInts("0 0 0 0 0 0 0 0");

        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                Sudoku sudoku = new Sudoku(grid);
            }
        });
    }
    public void test9(){
        int[][] grid = new int[9][9];
        grid[0] = Sudoku.stringToInts("1 2 3 4 5 6 7 8 9");
        grid[1] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[2] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[3] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[4] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[5] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[6] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[7] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");
        grid[8] = Sudoku.stringToInts("0 0 0 0 0 0 0 0 0");

        Sudoku sudoku = new Sudoku(grid);
        String expected =   "1 2 3 4 5 6 7 8 9 \n" +
                            "0 0 0 0 0 0 0 0 0 \n"+
                            "0 0 0 0 0 0 0 0 0 \n"+
                            "0 0 0 0 0 0 0 0 0 \n"+
                            "0 0 0 0 0 0 0 0 0 \n"+
                            "0 0 0 0 0 0 0 0 0 \n"+
                            "0 0 0 0 0 0 0 0 0 \n"+
                            "0 0 0 0 0 0 0 0 0 \n"+
                            "0 0 0 0 0 0 0 0 0 \n";
        assertEquals(expected,sudoku.toString());

    }
    public void test10(){
        Sudoku sudoku = new Sudoku(Sudoku.hardGrid);
        int solutions = sudoku.solve();
        assertEquals(1,solutions);
        long timeElapsed = sudoku.getElapsed();
        assertTrue(timeElapsed <= 30);
    }

}
