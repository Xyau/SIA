package ar.edu.itba.sia.ohh1.Motor;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;
import ar.edu.itba.sia.ohh1.Problem.Ohh1State;

public class Motor<E> {
    private SearchStrategy<E> strategy;

    public Motor(SearchStrategy s){
        this.strategy = s;
    }
    public SearchStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public Node getNext(){
         return strategy.getNext();
    }

    public void addToFrontier(Node n){
         strategy.addToFrontier(n);
    }

    public Node peekNext(){
        return strategy.peekNext();
    }

    public int getNumOfNodesInFrontier(){
        return strategy.getNumOfNodesInFrontier();
    }

    public static void run(Motor<Ohh1State> m, Problem<Ohh1State> prob){
        Node<Ohh1State> initial = new Node(prob.getInitialState(),null,0,0);
        m.addToFrontier(initial);
        Node<Ohh1State> current = m.getNext();
        int nodesExpanded = 0;
        while (!prob.isResolved(current.getState())){
            Node auxNode;
            Ohh1State auxState;
            for (Rule<Ohh1State> r : prob.getRules(current.getState())){
                auxState = r.applyToState(current.getState());
                auxNode = new Node(auxState,current,current.getCost()+r.getCost(),current.getDepth()+1);
                m.addToFrontier(auxNode);
            }
            current = m.getNext();
            nodesExpanded++;
        }
        System.out.println(current.getDepth());
        Node aux = current;
        while (aux != null) {
            ((Ohh1State)aux.getState()).printBoard();
            aux = aux.getParent();
        }
        System.out.println("Nodes expanded: " + nodesExpanded);
    }

    public static void runIter(Motor<Ohh1State> m, Problem<Ohh1State> prob){
        Node<Ohh1State> initial = new Node(prob.getInitialState(),null,0,0);
        m.addToFrontier(initial);
        Node<Ohh1State> current = m.getNext();
        for (int limit = 0;;limit++){
            System.out.println("Profundidad: "+limit);
            while (!prob.isResolved(current.getState()) ){
                Node auxNode;
                Ohh1State auxState;
                if (current.getDepth() <= limit){
                    for (Rule<Ohh1State> r : prob.getRules(current.getState())){
                        auxState = r.applyToState(current.getState());
                        auxNode = new Node(auxState,current,current.getCost()+r.getCost(),current.getDepth()+1);
                        m.addToFrontier(auxNode);
                    }
                }
                try {
                    current = m.getNext();
                } catch(IllegalStateException e){
                    break;
                }
            }
            if(prob.isResolved(current.getState())){
                break;
            }
            current = new Node(prob.getInitialState(),null,0,0);
        }
        Node aux = current;
        while (aux != null) {
            ((Ohh1State)aux.getState()).printBoard();
            aux = aux.getParent();
        }
    }

//    public static void main(String[] args) {
//        Motor<Ohh1State> m = new Motor<Ohh1State>(new AStarStrategy<Ohh1State>(new Ohh1HeuristicMust()));
//        Problem<Ohh1State> prob = new Ohh1Problem("./ohh1/src/main/resources/ohh1Board2.txt");
//        Node<Ohh1State> initial = new Node(prob.getInitialState(),null,0);
//        m.addToFrontier(initial);
//        Node<Ohh1State> current = m.getNext();
//        while (!prob.isResolved(current.getState())){
//            Node auxNode;
//            Ohh1State auxState;
//            for (Rule<Ohh1State> r : prob.getRules(current.getState())){
//                auxState = r.applyToState(current.getState());
//                auxNode = new Node(auxState,current,current.getCost()+r.getCost());
//                m.addToFrontier(auxNode);
//            }
//            current = m.getNext();
//        }
//        Node aux = current;
//        while (aux != null) {
//            ((Ohh1State)aux.getState()).printBoard();
//            aux = aux.getParent();
//        }
//    }
}
