import java.util.Scanner;

/**
 * Final Project for CS-112
 *
 * @author Bryce J. Fisher
 *
 */
public class BeanMachine {

	public static void main(final String[] args) {
		final Scanner consoleInputReader = new Scanner(System.in);

		// prompt user for number of columns
		int numberOfColumns;
		while (true) {
			System.out.print("How many columns wide? ");
			numberOfColumns = consoleInputReader.nextInt();

			// prompt zero or negative columns
			if (numberOfColumns > 0)
				break;
			System.out.println("Incorrect input");
		}

		// prompt user for number of rows
		int numberOfRows;
		while (true) {
			System.out.print("How many rows high? ");
			numberOfRows = consoleInputReader.nextInt();

			// prevent zero or negative rows
			if (numberOfRows > 0)
				break;
			System.out.println("Incorrect input");
		}

		// prompt user for number of beans
		int numberOfBeans;
		while (true) {
			System.out.print("How many beans? ");
			numberOfBeans = consoleInputReader.nextInt();

			// prevent too many beans from entering the beanCollector
			if (numberOfBeans > numberOfRows * numberOfColumns) {
				System.out
				.println("There are more beans than there is room for in the bean collector");
				continue;
			}

			// prevent zero or negative beans
			if (numberOfBeans > 0)
				break;
			System.out.println("Incorrect input");
		}

		// print blank line to seprate input and output
		System.out.println();

		// init beanCollector
		final boolean[][] beanCollector = new boolean[numberOfRows][numberOfColumns];

		// perform bean sorting
		for (int i = 0; i < numberOfBeans; i++)
			while (true) {
				// select a random column
				final int randomColumnIndex = (int) (Math.random() * numberOfColumns);

				// if column is full, just try again.
				if (isFull(beanCollector, randomColumnIndex))
					continue;

				// add bean to the column
				addBean(beanCollector, randomColumnIndex);
				break;
			}

		// beanCollector is upside down, correct it.
		flipBeanCollector(beanCollector);

		// display beanCollector
		printBeanCollector(beanCollector);

		// close program
		consoleInputReader.close();
	}

	/**
	 * Check if the specific column has room for more beans
	 *
	 * @param beanCollector
	 * @param columnIndex
	 * @return
	 */
	public static boolean isFull(final boolean[][] beanCollector,
			final int columnIndex) {
		int rowCount = 0;

		// count each bean
		for (final boolean[] beanRow : beanCollector)
			if (beanRow[columnIndex])
				rowCount++;

		// if the number of beans is equal to the size of the column
		// (row.length) of the beanCollector
		return rowCount >= beanCollector.length;
	}

	/**
	 * Add a bean to the specific column in the beanCollector
	 *
	 * @param beanCollector
	 * @param columnIndex
	 */
	public static void addBean(final boolean[][] beanCollector,
			final int columnIndex) {
		int rowCount = 0;

		// find the top most available bean cell in the specific column of the
		// beanCollector
		for (final boolean[] beanRow : beanCollector)
			if (beanRow[columnIndex])
				rowCount++;

		// place a bean in the cell
		beanCollector[rowCount][columnIndex] = true;
	}

	/**
	 * Reverse the beanCollector horizontally
	 *
	 * @param beanCollector
	 */
	public static void flipBeanCollector(final boolean[][] beanCollector) {

		// create temporary beanCollector
		final boolean[][] temporaryBeanCollector = new boolean[beanCollector.length][beanCollector[0].length];

		// copy the reverse of the original beanCollector into the temporary one
		for (int columnIndex = 0; columnIndex < beanCollector[0].length; columnIndex++)
			for (int grabRowIndex = 0, placeRowIndex = beanCollector.length - 1; grabRowIndex < beanCollector.length; grabRowIndex++, placeRowIndex--)
				temporaryBeanCollector[placeRowIndex][columnIndex] = beanCollector[grabRowIndex][columnIndex];

		// copy the reversed temporary beanCollector into the original
		for (int rowIndex = 0; rowIndex < beanCollector.length; rowIndex++)
			for (int columnIndex = 0; columnIndex < beanCollector[rowIndex].length; columnIndex++)
				beanCollector[rowIndex][columnIndex] = temporaryBeanCollector[rowIndex][columnIndex];
	}

	/**
	 * Output the beanCollector and do it with style
	 *
	 * @param beanCollector
	 */
	public static void printBeanCollector(final boolean[][] beanCollector) {
		// set time so that each row prints in half of a second
		final long printDelayTime = 500 / beanCollector.length / 2;

		// print each cell in the beanCollector
		for (final boolean[] rowIndex : beanCollector) {
			for (int columnIndex = 0; columnIndex < rowIndex.length; columnIndex++) {

				// print pipe to create the 'tube' for the beanCollector
				System.out.print('|');
				snooze(printDelayTime);

				// print bean or blank
				System.out.print(rowIndex[columnIndex] ? 'o' : ' ');
				snooze(printDelayTime);

				// print another pipe at the end of the row
				if (columnIndex == rowIndex.length - 1) {
					System.out.println('|');
					snooze(printDelayTime);
				}
			}
			snooze(printDelayTime);
		}
		snooze(printDelayTime);

		// print out the bottom of the beanCollector with pipes and equals tags
		for (int columns = 0; columns < beanCollector[0].length; columns++) {
			System.out.print("|");
			snooze(printDelayTime);
			System.out.print("=");
			snooze(printDelayTime);
		}
		snooze(printDelayTime);

		// print the final pipe to complete the beanCollector
		System.out.println('|');
	}

	/**
	 * Pause execution for so many milliseconds
	 *
	 * @param millis
	 */
	public static void snooze(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {

			// ignore exceptions
		}
	}
}
