import java.util.ArrayList;

public class Agent {
	public String searchType;
	public String heuristic;
	public int color;

	public Agent(String newSearchType, String newHeuristic, int newColor) {
		searchType = newSearchType;
		heuristic = newHeuristic;
		color = newColor;
	}

	public int[][] makeMove(int[][] board){
		if(searchType.equalsIgnoreCase("minimax")) {
			return minimax(board);
		}
		else if(searchType.equalsIgnoreCase("alphabeta")) {
			return alphaBeta(board);
		}
		else {
			return new int[8][8];
		}
	}

	public int[][] alphaBeta(int[][] board) {
		ArrayList<Move> possibleMoves = getPossibleMoves(board);

		Move bestMove = null;
		double best = Double.NEGATIVE_INFINITY;

		for(Move move : possibleMoves) {
			int[][] newBoard = duplicateBoard(board);
			newBoard = movePiece(move, newBoard);
			double minimax = alphaBetaValue(newBoard, 3, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, false);
			if(minimax > best) {
				bestMove = move;
				best = minimax;
			}
		}

		//		System.out.println(bestMove.toString());

		int[][] newBoard = duplicateBoard(board);
		newBoard = movePiece(bestMove, newBoard);

		return newBoard;

	}

	public double alphaBetaValue(int[][] board, int depth, double alpha, double beta, boolean max) {
		if(depth == 0 || goalCheck(board) != 0) {
			if(heuristic.equalsIgnoreCase("offensive")) {
				return getOffensiveHeuristic(board);
			}
			else if(heuristic.equalsIgnoreCase("defensive")) {
				return getDefensiveHeuristic(board);
			}
			else if(heuristic.equalsIgnoreCase("defensivetwo")) {
				return getDefensiveHeuristicTwo(board);
			}
			else {
				System.out.println("ERROR ERROR ERROR");
				return 0;
			}
		}
		else {
			ArrayList<Move> possibleMoves = getPossibleMoves(board);
			if(max) {
				double v = Double.NEGATIVE_INFINITY;
				for(Move move : possibleMoves) {
					int[][] newBoard = duplicateBoard(board);
					newBoard = movePiece(move, newBoard);
					v = Math.max(v, alphaBetaValue(newBoard, depth - 1, alpha, beta, !max));
					alpha = Math.max(alpha, v);
					if(beta >= alpha) {
						break;
					}
				}
				return v;
			}
			else {
				double v = Double.POSITIVE_INFINITY;
				for(Move move : possibleMoves) {
					int[][] newBoard = duplicateBoard(board);
					newBoard = movePiece(move, newBoard);
					v = Math.min(v, alphaBetaValue(newBoard, depth - 1, alpha, beta, !max));
					beta = Math.min(beta, v);
					if(beta >= alpha) {
						break;
					}
				}
				return v;
			}
		}
	}
	public int[][] minimax(int[][] board){
		ArrayList<Move> possibleMoves = getPossibleMoves(board);

		Move bestMove = null;
		double best = Double.NEGATIVE_INFINITY;

		for(Move move : possibleMoves) {
			int[][] newBoard = duplicateBoard(board);
			newBoard = movePiece(move, newBoard);
			double minimax = getMinimaxValue(newBoard, 2, false);
			if(minimax > best) {
				bestMove = move;
				best = minimax;
			}
		}

		//		System.out.println(bestMove.toString());

		int[][] newBoard = duplicateBoard(board);
		newBoard = movePiece(bestMove, newBoard);

		return newBoard;
	}

	public double getMinimaxValue(int[][] board, int depth, boolean max) {
		if(depth == 0 || goalCheck(board) != 0) {
			if(heuristic.equalsIgnoreCase("offensive")) {
				return getOffensiveHeuristic(board);
			}
			else if(heuristic.equalsIgnoreCase("defensive")) {
				return getDefensiveHeuristic(board);
			}
			else if(heuristic.equalsIgnoreCase("defensivetwo")) {
				return getDefensiveHeuristicTwo(board);
			}
			else {
				return 0;
			}
		}
		else { 
			ArrayList<Move> possibleMoves = getPossibleMoves(board);
			if(max) {
				double bestValue = Double.NEGATIVE_INFINITY;
				for(Move move : possibleMoves) {
					int[][] newBoard = duplicateBoard(board);
					newBoard = movePiece(move, newBoard);
					double v = getMinimaxValue(newBoard, depth-1, !max);
					bestValue = Math.max(v, bestValue);
				}
				return bestValue;
			}
			else {
				double bestValue = Double.POSITIVE_INFINITY;
				for(Move move : possibleMoves) {
					int[][] newBoard = duplicateBoard(board);
					newBoard = movePiece(move, newBoard);
					double v = getMinimaxValue(newBoard, depth-1, !max);
					bestValue = Math.min(v, bestValue);
				}
				return bestValue;
			}
		}
	}

	public ArrayList<Move> getPossibleMoves(int[][] board){
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		ArrayList<Point> pieces = getPieces(color, board);
		for(Point piece : pieces) {
			Point newPointOne = new Point(piece.x, piece.y + color);
			Point newPointTwo = new Point(piece.x - 1, piece.y + color);
			Point newPointThree = new Point(piece.x + 1, piece.y + color);

			if(newPointOne.valid) {
				if(board[newPointOne.x][newPointOne.y] == 0) {
					possibleMoves.add(new Move(piece, newPointOne));
				}
			}
			if(newPointTwo.valid) {
				if(board[newPointTwo.x][newPointTwo.y] == -color || board[newPointTwo.x][newPointTwo.y] == 0) {
					possibleMoves.add(new Move(piece, newPointTwo));
				}
			}
			if(newPointThree.valid) {
				if(board[newPointThree.x][newPointThree.y] == -color || board[newPointThree.x][newPointThree.y] == 0) {
					possibleMoves.add(new Move(piece, newPointThree));
				}
			}
		}
		return possibleMoves;
	}

	public ArrayList<Point> getPieces(int color, int[][] board){
		ArrayList<Point> pieces = new ArrayList<Point>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] == color) {
					pieces.add(new Point(i, j));
				}
			}
		}
		return pieces;
	}

	public int[][] movePiece(Move move, int[][] board){
		Point oldSpot = move.oldSpot;
		Point newSpot = move.newSpot;
		int[][] newBoard = duplicateBoard(board);
		newBoard[newSpot.x][newSpot.y] = newBoard[oldSpot.x][oldSpot.y];
		newBoard[oldSpot.x][oldSpot.y] = 0;
		return newBoard;
	}

	public int[][] duplicateBoard(int[][] board){
		int[][] newBoard = new int[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}

	public int countPieces(int countColor, int[][] board) {
		int count = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] == countColor) {
					count++;
				}
			}
		}
		return count;
	}

	public double getOffensiveHeuristic(int[][] board) {
		return 2 * (30 - countPieces(-color, board)) + Math.random();
	}

	public double getDefensiveHeuristic(int[][] board) {
		return 2 * countPieces(color, board) + Math.random();
	}

	public double getDefensiveHeuristicTwo(int[][] board) {
		double maxWhiteDistance = Double.NEGATIVE_INFINITY;
		ArrayList<Point> whitePieces = getPieces(1, board);
		for(Point piece : whitePieces) {
			double whiteDistance = 7 - piece.y;
			maxWhiteDistance = Math.max(maxWhiteDistance, whiteDistance);
		}

		double maxBlackDistance = Double.NEGATIVE_INFINITY;
		ArrayList<Point> blackPieces = getPieces(1, board);
		for(Point piece : blackPieces) {
			double blackDistance = piece.y;
			maxBlackDistance = Math.max(maxBlackDistance, blackDistance);
		}

		if(color == -1) {
			return 8 - maxBlackDistance - maxWhiteDistance/10 - Math.random()/10;
		}
		else {
			return 8 - maxWhiteDistance - maxBlackDistance/10 - Math.random()/10;
		}

	}


	//	public double getDefensiveHeuristicTwo(int[][] board) {
	//		int whiteCount = 0;
	//		ArrayList<Point> whitePieces = getPieces(1, board);
	//		for(Point piece : whitePieces) {
	//			whiteCount += 7 - piece.y;			//add the distance from goal
	//		}
	//		
	//		int blackCount = 0;
	//		ArrayList<Point> blackPieces = getPieces(-1, board);
	//		for(Point piece : blackPieces) {
	//			blackCount += piece.y;				//add the distance from goal
	//		}
	//		
	//		if(color == 1) {
	//			return blackCount/blackPieces.size() - whiteCount/whitePieces.size() + Math.random()/1000;
	//		}
	//		else {
	//			return whiteCount/whitePieces.size() - blackCount/blackPieces.size() + Math.random()/1000;
	//		}
	//}
	//	public double getDefensiveHeuristicTwo(int[][] board) {
	//		return 
	//	}

	public int goalCheck(int[][] board) {
		if(countPieces(1, board) == 0) {
			return -1;
		}
		else if(countPieces(-1, board) == 0) {
			return 1;
		}
		for(int i = 0; i < 8; i++) {
			if(board[i][0] == -1) {
				return -1;
			}
			else if(board[i][7] == 1) {
				return 1;
			}
		}
		return 0;
	}
}
