package org.aptlist.friday;

import java.util.ArrayList;
import java.util.List;

public class SeatRandomizer {

	public List<Table> createTables(int total, int[] tableSizes) throws Exception {
		int[] resultArray = returnResultArray(total, tableSizes);
		return createTables(resultArray);
	}

	protected List<Table> createTables(int R[]) throws Exception {
		if (R[R.length - 1] == -1) {
			String errMsg = "Not enough employees to meet seat min/max requirements per table.";
			throw new Exception(errMsg);
		}

		List<Table> tableList = new ArrayList<>();
		int resultIndex = R.length - 1;

		// create n tables of size 3, m tables of size 5
		while (resultIndex != 0) {
			int size = R[resultIndex];
			tableList.add(new Table(size));
			resultIndex = resultIndex - size;
		}

		return tableList;
	}

	protected int[] returnResultArray(int total, int[] tableSizes) {
		// S represents the subproblem of total tables used for sum(i...total)
		// R represents the table size used to solve sum(i...total)
		int S[] = new int[total + 1];
		int R[] = new int[total + 1];

		S[0] = 0;
		R[0] = -1;

		// initialize S and R
		for (int i = 1; i <= total; i++) {
			S[i] = Integer.MAX_VALUE - 1;
			R[i] = -1;
		}

		int tableSizeIndex;
		int sum;
		for (tableSizeIndex = 0; tableSizeIndex < tableSizes.length; tableSizeIndex++) {
			for (sum = 1; sum <= total; sum++) {
				int sizeOfTable = tableSizes[tableSizeIndex];

				if ((sum >= sizeOfTable) && (S[sum - sizeOfTable] + 1 < S[sum])) {
					S[sum] = S[sum - sizeOfTable] + 1;
					R[sum] = sizeOfTable;
				}
			}
		}
		return R;
	}
}
