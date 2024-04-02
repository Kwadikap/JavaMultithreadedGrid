import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class JavaMultithreadedGrid{
	public static class ThreadClass implements Runnable{
		private int startIdx, endIdx, position;
		private int rowStart, rowEnd, colStart, colEnd;
		private int count;  //keeps track of how many numbers have been iterated over in the grid
		private boolean rowChecker, colChecker, gridChecker = false; //used to indicate whether the thread will be iterating over a row or col
		private Set<Integer> set = new HashSet<Integer>();
		
		public ThreadClass(int position, int startIdx, int endIdx, String rowOrCol){
			this.position = position;
			this.startIdx = startIdx;
			this.endIdx = endIdx;

			if(rowOrCol == "r") {this.rowChecker = true;}
			else if(rowOrCol == "c") {this.colChecker = true;}
			else {System.err.println("You must enter either the letter \"r\" or \"c\" to indicate whether this thread will be checking the rows or columns");}
		}
		
		public ThreadClass(int rowStart, int rowEnd, int colStart, int colEnd){
			this.rowStart = rowStart;
			this.rowEnd = rowEnd;
			this.colStart = colStart;
 			this.colEnd = colEnd; 			
			this.gridChecker = true;
		}


		public boolean isValid(){
			return this.set.size() == this.count;	
		}
		
		
		private void checkRow(){
			// iterate over a row in the grid and add each number to the set delcared above
			for (int i = this.startIdx; i <= this.endIdx; i++) {
				this.set.add(grid[this.position][i]);
				this.count++;  //track how many elements were in the row
			}
		}

		private void checkColumn(){
			// iterate over a column in the grid and add each number to the set declared above
			for (int i = this.startIdx; i <= this.endIdx; i++) {
				this.set.add(grid[i][this.position]);
				this.count++;  //track how many elements were in the column
			}
		}

		private void checkGrid(){
			// iterate over a section in the grid and add each number to the set declared above
			for (int i = this.rowStart; i <= this.rowEnd; i++){
				for(int j = this.colStart; j <= colEnd; j++){
					this.set.add(grid[i][j]);
					this.count++;  //track number of elements seen in this section of the grid
				}
			}

		}

		@Override
		public void run(){
			if (this.rowChecker == true) {this.checkRow();}
			
			else if (this.colChecker == true) {this.checkColumn();}
			
			else {this.checkGrid();}
		
		}
	}


	
	// 9 x 9 sudoku grid array
	static int[][] grid = { 
		{6, 2, 4, 5, 3, 9, 1, 8, 7}, 
		{5, 1, 9, 7, 2, 8, 6, 3, 4}, 
		{8, 3, 7, 6, 1, 4, 2, 9, 5}, 
		{1, 4, 3, 8, 6, 5, 7, 2, 9}, 
		{9, 5, 8, 2, 4, 7, 3, 6, 1}, 
		{7, 6, 2, 3, 9, 1, 4, 5, 8}, 
		{3, 7, 1, 9, 5, 6, 8, 4, 2}, 
		{4, 9, 6, 1, 8, 2, 5, 7, 3}, 
		{2, 8, 5, 4, 7, 3, 9, 1, 6} 
	};

	
	public static void main(String[] args){

		ArrayList<Thread> threadList = new ArrayList<Thread>();
		ArrayList<ThreadClass> threadClassList = new ArrayList<ThreadClass>(); 	
		
		
		// create threads to check each row and column in the grid
		for (int i = 0; i < 9; i++){
			// create row threads
			ThreadClass rThread = new ThreadClass(i, 0, 8, "r");
			Thread rowThread = new Thread(rThread, "RowThread");
			threadClassList.add(rThread);
			threadList.add(rowThread);

			// create column threads
			ThreadClass cThread = new ThreadClass(i, 0, 8, "c");
			Thread colThread = new Thread(cThread, "ColumnThread");
			threadClassList.add(cThread); 	
			threadList.add(colThread);
		}

		// create threads to check each 3x3 grid section
		ThreadClass gThread1 = new ThreadClass(0, 2, 0, 2);
		Thread gridThread1 = new Thread(gThread1, "Grid Thread 1");
		threadClassList.add(gThread1); 					
		threadList.add(gridThread1);                          


		ThreadClass gThread2 = new ThreadClass(0, 2, 3, 5);
		Thread gridThread2 = new Thread(gThread2, "Grid Thread 2");
		threadClassList.add(gThread2); 	
		threadList.add(gridThread2);                          

		ThreadClass gThread3 = new ThreadClass(0, 2, 6, 8);
		Thread gridThread3 = new Thread(gThread3, "Grid Thread 3");
		threadClassList.add(gThread3); 	
		threadList.add(gridThread3);                          

		ThreadClass gThread4 = new ThreadClass(3, 5, 0, 2);
		Thread gridThread4 = new Thread(gThread4, "Grid Thread 4");
		threadClassList.add(gThread4); 	
		threadList.add(gridThread4);                          

		ThreadClass gThread5 = new ThreadClass(3, 5, 3, 5);
		Thread gridThread5 = new Thread(gThread5, "Grid Thread 5");
		threadClassList.add(gThread5); 	
		threadList.add(gridThread5);                          

		ThreadClass gThread6 = new ThreadClass(3, 5, 6, 8);
		Thread gridThread6 = new Thread(gThread6, "Grid Thread 6");
		threadClassList.add(gThread6); 	
		threadList.add(gridThread6);                          

		ThreadClass gThread7 = new ThreadClass(6, 8, 0, 2);
		Thread gridThread7 = new Thread(gThread7, "Grid Thread 7");
		threadClassList.add(gThread7); 	
		threadList.add(gridThread7);                          

		ThreadClass gThread8 = new ThreadClass(6, 8, 3, 5);
		Thread gridThread8 = new Thread(gThread8, "Grid Thread 8");
		threadClassList.add(gThread8); 	
		threadList.add(gridThread8);                          

		ThreadClass gThread9 = new ThreadClass(6, 8, 6, 8);
		Thread gridThread9 = new Thread(gThread9, "Grid Thread 9");
		threadClassList.add(gThread9); 	
		threadList.add(gridThread9);                          

				
		int number_of_threads = threadList.size();

		//run all threads 
		for (int i = 0; i < number_of_threads; i++){
			threadList.get(i).start();
		}	
	
		// wait for all threads to complete execution
		for (int i = 0; i < number_of_threads; i++){
			try{
				threadList.get(i).join();
			}catch(Exception e){
				System.err.println("Error occured when trying to run the join method on a thread");
			}
		}

		// check the isValid method of all threads, if any are false print invalid msg to screen
		for (int i = 0; i < number_of_threads; i++){
			if (threadClassList.get(i).isValid() == false) { 
				System.out.println("Your solution to the Sudoku puzzle is not valid! Please try again with a different solution this time.");
				System.exit(0);
			}
		}
		// print valid grid msg
		System.out.println("Yes, your solution to the Sudoku puzzle is valid! Good job!");		

	}
	


}
