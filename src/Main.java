import java.util.*;

public class Main {
	public static void main(String[] args) {
		
		int trials = 3;
		Agent whitePlayer = new Agent("alphabeta", "offensive", 1);
		Agent blackPlayer = new Agent("alphabeta", "defensivetwo", -1);
		
		
		int whiteWins = 0;
		int blackWins = 0;
		for(int i = 0; i < trials; i++) {
			int gameResult = playGame(whitePlayer, blackPlayer);
			if(gameResult == 1) {
				whiteWins++;
			}
			if(gameResult == -1) {
				blackWins++;
			}
			System.out.println("Game #" + i + ": " + gameResult);
		}
		System.out.println("White wins: " + whiteWins);
		System.out.println("Black wins: " + blackWins);

	}

	public static int playGame(Agent white, Agent black) {
		int[][] board = getDefaultBoard();
		boolean whiteTurn = true;
		printBoard(board);

		while(goalCheck(board) == 0) {
			if(whiteTurn) {
				board = white.makeMove(board);
			}
			else {
				board = black.makeMove(board);
			}
			printBoard(board);
			whiteTurn = !whiteTurn;
		}
		return goalCheck(board);
	}
	
	public static int[][] getDefaultBoard(){
		int[][] board = new int[8][8];
		//set up board
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(j < 2) {
					board[i][j] = 1;
				}
				if(j > 5) {
					board[i][j] = -1;
				}
			}
		}
		return board;
	}

	public static void printBoard(int[][] board) {
		char[][] readableBoard = new char[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] == 0) {
					readableBoard[i][j] = ' ';
				}
				else if(board[i][j] == 1) {
					readableBoard[i][j] = 'A';
				}
				else if(board[i][j] == -1) {
					readableBoard[i][j] = 'B';
				}
			}
		}
		System.out.println("--------------------");
		for(int i = 0; i < 8; i++) {
			System.out.println(Arrays.toString(readableBoard[i]));
		}
	}

	public static int countPieces(int countColor, int[][] board) {
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

	public static int goalCheck(int[][] board) {
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
