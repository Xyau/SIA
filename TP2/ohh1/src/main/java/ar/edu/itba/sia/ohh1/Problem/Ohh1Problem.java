package ar.edu.itba.sia.ohh1.Problem;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ohh1Problem implements Problem<Ohh1State> {

    private List<Rule<Ohh1State>> rules;
    private int size;
    private Ohh1State initialState;

    public Ohh1Problem(String configFile)  {
        File f = new File(configFile);
        try {
            Scanner sc = new Scanner(f);
            this.size =sc.nextInt();
            int[][] initialBoard = new int[this.size][this.size];
            int notFilledPositions = 0;
            for (int i = 0 ; i < this.size;i++){
                for(int j = 0; j < this.size ; j++){
                    initialBoard[i][j] = sc.nextInt();
                    if (initialBoard[i][j] == 0){
                        notFilledPositions++;
                    }
                }
            }
            this.initialState = new Ohh1State(initialBoard,notFilledPositions);
            generateRules();
        }catch(FileNotFoundException e){
            System.out.println("El path provisto es incorrecto");
        }
    }



    @Override
    public Ohh1State getInitialState() {
        return initialState;
    }

    @NotNull
    @Override
    public List<Rule<Ohh1State>> getRules(Ohh1State ohh1State) {
        List<Rule<Ohh1State>> ans = new LinkedList<>();
        for(Rule<Ohh1State> rule : this.rules){
            if( ((Ohh1Rule)rule).isApplicable(ohh1State)){
                ans.add(rule);
            }
        }
        return ans;
    }

    @Override
    public boolean isResolved(Ohh1State state) {
        return state.getNotFilledPositions() == 0;
    }

    private void generateRules(){
        this.rules = new LinkedList<Rule<Ohh1State>>();
        for (int i = 0; i < this.size ; i++){
            for (int j = 0; j < this.size ; j++){
                final int finalJ = j;
                final int finalI = i;
                final double finalCost = 3.0;
                Ohh1Rule rule1 = new Ohh1Rule(finalCost, currentState -> {
                    Ohh1State ans = null;
                        int[][] aux = currentState.cloneBoard();
                        aux[finalI][finalJ] = Ohh1State.RED;

                        ans =  new Ohh1State(aux,currentState.getNotFilledPositions()-1);

                    return ans;
                },currentState -> {
                    return checkConditions(currentState.getBoard(), Ohh1State.RED, finalI, finalJ);
                });
                Ohh1Rule rule2 = new Ohh1Rule(finalCost, currentState -> {
                    Ohh1State ans = null;
                        int[][] aux = currentState.cloneBoard();
                        aux[finalI][finalJ] = Ohh1State.BLUE;

                        ans =  new Ohh1State(aux,currentState.getNotFilledPositions()-1);

                    return ans;
                },currentState -> {
                    return checkConditions(currentState.getBoard(), Ohh1State.BLUE, finalI, finalJ);
                });
                rules.add(rule1);
                rules.add(rule2);
            }
        }
    }

    public static boolean checkConditions(int[][] board, int color, int ix, int iy){
        if (board[ix][iy] == 0){
            int colorX = 0, colorY = 0, limit = board.length / 2;
            int consecutiveX = 0, consecutiveY = 0;
            for (int x = 0; x < board.length; x++){
                if (board[x][iy] == color || x == ix){
                    colorX++;
                    consecutiveX++;
                }else{
                    consecutiveX = 0;
                }

                if (consecutiveX == 3){
                    return false;
                }

                if (board[ix][x] == color || x == iy){
                    colorY++;
                    consecutiveY++;
                }
                else{
                    consecutiveY = 0;
                }

                if (consecutiveY == 3){
                    return false;
                }
            }
            if ( colorX > limit || colorY > limit ){
                return false;
            }

            // Check columns
            for (int i = 0; i < board.length ; i++){
                if ( i == ix ){
                    continue;
                }

                boolean equals = true;
                for ( int j = 0; j < board.length; j++){
                    if ((j == iy && board[i][j] != color) || (j != iy && (board[i][j] != board[ix][j] || board[i][j] == 0))){
                        equals = false;
                        break;
                    }
                }
                if (equals){
                    return false;
                }
            }

            for (int i = 0; i < board.length ; i++){
                if ( i == iy ){
                    continue;
                }
                boolean equals = true;
                for ( int j = 0; j < board.length; j++){
                    if ((j == ix && board[j][i] != color) || ( j != ix && (board[j][i] != board[j][iy] || board[j][i] == 0))){
                        equals = false;
                        break;
                    }
                }
                if (equals){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
}
