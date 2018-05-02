package ar.edu.itba.sia.ohh1.Problem;

import java.util.Arrays;

public class Ohh1State {

    private int notFilledPositions;
    private int[][] board;
    public static int BLUE = -1;
    public static int RED = 1;
    public static int EMPTY = 0;

    public Ohh1State( int[][] board,int notFilledPositions) {
        this.board = board;
        this.notFilledPositions = notFilledPositions;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getNotFilledPositions(){
        return notFilledPositions;
    }

    public int[][] cloneBoard(){
        int [][] ans = new int[board.length][board.length];
        for(int i = 0;i < board.length;i++){
            ans[i] = board[i].clone();
        }
        return ans;
    }



    public void printBoard(){
        for ( int i = 0 ; i < board.length ; i++){
            for( int j = 0 ; j < board[i].length ; j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println("");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ohh1State ohh1State = (Ohh1State) o;

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (board[i][j] != ohh1State.board[i][j]){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int[] hashcodes = new int[board.length];
        for (int i = 0; i < board.length; i++){
            hashcodes[i] = 31 * Arrays.hashCode(board[i]);
        }
        return Arrays.hashCode(hashcodes);
    }

}
