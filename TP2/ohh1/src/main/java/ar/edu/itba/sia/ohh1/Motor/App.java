package ar.edu.itba.sia.ohh1.Motor;

import ar.com.itba.sia.Heuristic;
import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.ohh1.Problem.Ohh1HeuristicMust;
import ar.edu.itba.sia.ohh1.Problem.Ohh1HeuristicMustHalf;
import ar.edu.itba.sia.ohh1.Problem.Ohh1Problem;
import ar.edu.itba.sia.ohh1.Problem.Ohh1State;
import org.apache.commons.cli.*;

import static java.lang.System.exit;

public class App {

    public static void main(String[] args){
        Options options = getOptions();
        CommandLineParser parser = new BasicParser();

        String algorithm = "dfs", board = null;
        int heuristic = 0;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h"))
                help(options);
            if (cmd.hasOption("he"))
                heuristic = Integer.parseInt(cmd.getOptionValue("he"));
            if (cmd.hasOption("a"))
                algorithm = cmd.getOptionValue("a");
            if (cmd.hasOption("b")){
                board = cmd.getOptionValue("b");
            }else{
                System.out.println("No board found");
                exit(0);
            }
            Heuristic h = null;
            switch (heuristic){
                case 0:
                    h = new Ohh1HeuristicMust();
                    break;
                case 1:
                    h = new Ohh1HeuristicMustHalf();
                    break;
                default:
                    System.out.println("Wrong heuristic.");
                    exit(0);
                    break;    
            }
            algorithm = algorithm.toLowerCase();
            SearchStrategy<Ohh1State> s = null;
            boolean isIterative = false;
            switch (algorithm){
                case "astar":
                    s = new AStarStrategy<Ohh1State>(h);
                    break;
                case "bfs":
                    s = new BFSStrategy<>();
                    break;
                case "dfs":
                    s = new DFSStrategy<>();
                    break;
                case "greedy":
                    s = new GreedySearchStrategy<Ohh1State>(h);
                    break;

                case "iterative":
                    isIterative = true;
                    s = new IterativeDeepeningStrategy<>();
                    break;
                default:
                    System.out.println("Wrong algorithm.");
                    exit(0);
                    break;
            }
            Problem<Ohh1State> p = new Ohh1Problem(board);
            Motor<Ohh1State> m = new Motor<>(s);
            System.out.println("Start!");
            long init = System.currentTimeMillis();
            if (!isIterative)
                Motor.run(m,p);
            else
                Motor.runIter(m,p);
            long time = System.currentTimeMillis() - init;
            System.out.println("Finished:" + time + "ms");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("I ded.");
        }
    }

    private static Options getOptions(){
        Options options = new Options();
        options.addOption("h", "help", false, "Shows this screen.");
        options.addOption("a", "algorithm", true, "Choose the algorithm.");
        options.addOption("b", "board", true, "Path to the board.");
        options.addOption("he", "heuristic", true, "Choose the heuristic");
        return options;
    }

    private static void help(Options options){
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("0hh1", options);
        exit(0);
    }
}
