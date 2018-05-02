package ar.edu.itba.sia.ohh1.Problem;

import ar.com.itba.sia.Heuristic;

public class Ohh1HeuristicMust implements Heuristic<Ohh1State>{

    @Override
    public double getValue(Ohh1State ohh1State) {
        int size = ohh1State.getBoard().length;
        int[][] board = ohh1State.getBoard();
        boolean[][] mustPositions = new boolean[size][size];
        int mustNumber = 0;
        for (int i = 0; i < size; i++){
            if (countColorsInRow(board, i, Ohh1State.BLUE) == size/2
                    || countColorsInRow(board, i, Ohh1State.RED) == size/2 ){
                for (int j = 0; j < size; j++){
                    if (!mustPositions[i][j] && board[i][j] == Ohh1State.EMPTY){
                        mustPositions[i][j] = true;
                        mustNumber++;
                    }
                }
            }

            if (countColorsInColumn(board, i, Ohh1State.BLUE) == size/2
                    || countColorsInColumn(board, i, Ohh1State.RED) == size/2 ){
                for (int j = 0; j < size; j++){
                    if (!mustPositions[j][i] && board[j][i] == Ohh1State.EMPTY){
                        mustPositions[j][i] = true;
                        mustNumber++;
                    }
                }
            }
            for (int j = 0; j < size; j++){
                if (board[i][j] == Ohh1State.EMPTY && !mustPositions[i][j]){
                    if (arePieceEquals(board,i-1,j,i-2,j) ||
                            arePieceEquals(board,i-1,j,i+1,j) ||
                            arePieceEquals(board,i+1,j,i+2,j) ||
                            arePieceEquals(board,i,j-1,i,j-2) ||
                            arePieceEquals(board,i,j-1,i,j+1) ||
                            arePieceEquals(board,i,j+1,i,j+2)){
                        mustPositions[i][j] = true;
                        mustNumber++;
                    }
                }
            }

        }

        return (ohh1State.getNotFilledPositions()-mustNumber)+ mustNumber * 3.0;
    }

    private static int countColorsInColumn(int[][] board, int column, int color){
        int ret = 0;
        for (int i = 0; i < board.length ; i++){
            if (board[column][i] == color){
                ret++;
            }
        }
        return ret;
    }

    private static int countColorsInRow(int[][] board, int row, int color){
        int ret = 0;
        for (int i = 0; i < board.length ; i++){
            if (board[i][row] == color){
                ret++;
            }
        }
        return ret;
    }

    private static boolean arePieceEquals(int[][] board, int x1, int y1, int x2, int y2){
        int size = board.length;
        if (x1 >= size || x2 >= size || y1 >= size || y2 <= size
                || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0){
            return false;
        }
        return board[x1][y1] != Ohh1State.EMPTY && board[x2][y2] != Ohh1State.EMPTY &&
                board[x1][y1] == board[x2][y2];
    }

}
