import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;

	/* Grid Information */
	private int[][] grid;
	private int[][] solution;
	private int solutions;
	private long elapsedTime;

	private List<Spot> emptySpots;

	/* Spot class */
	private class Spot implements Comparable{
		private int row;
		private int column;
		private int validValuesCount;

		public Spot(int row,int column){
			this.row = row;
			this.column = column;
			validValuesCount = getValidValues().size();
		}
		public Set<Integer> getValidValues(){
			Set<Integer> rv = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
			for(int index=0; index< SIZE; index++){
				/* Removing occupied column values */
				rv.remove(grid[index][column]);
				/* Removing occupied row values */
				rv.remove(grid[row][index]);
				/*
					Removing occupied square values

					(row / PART) - which square is it
					(row / PART) * PART - On which index current square starts
				 */
				int squareRow = (row / PART) * PART + index / PART;
				int squareColumn = (column / PART) * PART + index % PART;
				rv.remove(grid[squareRow][squareColumn]);
			}
			return rv;
		}

		public void set(int value){
			grid[row][column] = value;
		}

		@Override
		public int compareTo(Object elem){
			Spot spot = (Spot) elem;
			//int firstCount = this.validValuesCount;
			//int secondCount = spot.validValuesCount;
			//return firstCount - secondCount;
			if(row != spot.row) return row - spot.row;
			return column - spot.column;
		}
	}
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(mediumGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	
	private boolean isValidGridSize(int[][] ints){
		if(ints.length != SIZE)
			return false;
		for(int i=0 ; i<SIZE;i++){
			if(ints[i].length != SIZE) return false;
		}
		return true;
	}
	private boolean containsOnlyDigits(int[][] grid){
		for(int i=0; i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				if(grid[i][j] < 0 || grid[i][j] > 9) return false;
			}
		}
		return true;
	}
	private void createEmptySpots(){
		emptySpots = new ArrayList<>();
		for(int i=0; i<SIZE;i++){
			for(int j=0; j<SIZE;j++){
				if(grid[i][j] == 0)
					emptySpots.add(new Spot(i,j));
			}
		}
		Collections.sort(emptySpots);
	}

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		// YOUR CODE HERE
		if(!isValidGridSize(ints))
			throw new IllegalArgumentException("Size should be 9x9");
		if(!containsOnlyDigits(ints))
			throw new IllegalArgumentException("Sudoku contains only digits");
		this.grid = ints;
		this.solutions = 0;
		this.elapsedTime = 0;

		createEmptySpots();
	}
	public Sudoku(String ints){
		this(textToGrid(ints));
	}

	private void saveFirstSolution(){
		solution = new int[SIZE][SIZE];
		for(int i=0; i<SIZE;i++){
			for(int j=0; j<SIZE;j++){
				solution[i][j] = grid[i][j];
			}
		}
	}
	private void sudokuSolveHelper(int currentSpotIndex){
		/* If maximum solution size is reached */
		if(solutions >= MAX_SOLUTIONS) return;
		/* If solution has been found */
		if(currentSpotIndex >= emptySpots.size()){
			if(solutions == 0)
				saveFirstSolution();
			solutions++; return;
		}

		/* Try every possible value for the current spot */
		Spot currentSpot = emptySpots.get(currentSpotIndex);
		Set<Integer> validValues = currentSpot.getValidValues();

		for(Integer currentValue: validValues){
			currentSpot.set(currentValue);
			sudokuSolveHelper(currentSpotIndex+1);
			currentSpot.set(0);
		}

	}
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		long start = System.currentTimeMillis();
		sudokuSolveHelper(0);
		long end = System.currentTimeMillis();
		elapsedTime = end - start;
		return solutions; // YOUR CODE HERE
	}
	
	public String getSolutionText() {
		return this.convertGridToString(solution);
	}
	
	public long getElapsed() {
		return elapsedTime; // YOUR CODE HERE
	}

	private String convertGridToString(int[][] array){

		if(array == null)
			return "";

		StringBuilder rv = new StringBuilder();
		for(int i=0; i<array.length; i++){
			for(int j=0; j<array[i].length; j++){
				rv.append(String.valueOf(array[i][j]));
				rv.append(" ");
			}
			rv.append("\n");
		}
		return rv.toString();
	}

	@Override
	public String toString(){
		return convertGridToString(grid);
	}

}
