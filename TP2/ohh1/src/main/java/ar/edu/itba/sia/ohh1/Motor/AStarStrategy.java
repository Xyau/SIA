package ar.edu.itba.sia.ohh1.Motor;

import ar.com.itba.sia.Heuristic;

import java.util.HashSet;
import java.util.PriorityQueue;

public class AStarStrategy<E> implements SearchStrategy<E> {

    private PriorityQueue<Node<E>> frontierNodes;
    private HashSet<Node<E>> nodes;

    public AStarStrategy(Heuristic<E> h){
        nodes = new HashSet<>();
        frontierNodes = new PriorityQueue<Node<E>>((n1, n2) -> {
            double h1;
            double h2;
            if (n1.gethValue() == null){
                h1 = h.getValue(n1.getState());
            }else{
                h1 = n1.gethValue();
                n1.sethValue(h1);
            }

            if (n2.gethValue() == null){
                h2 = h.getValue(n2.getState());
            }else{
                h2 = n2.gethValue();
                n2.sethValue(h2);
            }
            h1 += n1.getCost();
            h2 += n2.getCost();
            return new Double(h1 - h2).intValue();
        });
    }

    @Override
    public Node<E> getNext() {
        if (frontierNodes.isEmpty()){
            throw new IllegalStateException();
        }
        return frontierNodes.poll();
    }

    @Override
    public void addToFrontier(Node<E> n) {
        if (nodes.contains(n)){
            return;
        }
        nodes.add(n);
        frontierNodes.offer(n);
    }

    @Override
    public Node<E> peekNext() {
        return frontierNodes.peek();
    }

    public int getNumOfNodesInFrontier(){
        return frontierNodes.size();
    }
}
