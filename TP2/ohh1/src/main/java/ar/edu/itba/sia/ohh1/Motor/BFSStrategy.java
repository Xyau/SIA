package ar.edu.itba.sia.ohh1.Motor;

import java.util.LinkedList;
import java.util.Queue;

public class BFSStrategy<E> implements SearchStrategy<E>{
    private Queue<Node<E>> frontierNodes;

    public BFSStrategy(){
        frontierNodes = new LinkedList<Node<E>>();
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
