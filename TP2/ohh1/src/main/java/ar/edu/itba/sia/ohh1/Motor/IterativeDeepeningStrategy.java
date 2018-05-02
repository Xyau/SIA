package ar.edu.itba.sia.ohh1.Motor;

import java.util.Stack;

public class IterativeDeepeningStrategy<E> implements SearchStrategy<E> {
    private Stack<Node<E>> frontierNodes;

    public IterativeDeepeningStrategy(){
        frontierNodes = new Stack<Node<E>>();
    }

    @Override
    public Node<E> getNext() {
        if (frontierNodes.empty()){
            throw new IllegalStateException();
        }
        return frontierNodes.pop();
    }

    @Override
    public void addToFrontier(Node<E> n) {
        frontierNodes.push(n);
    }

    @Override
    public Node<E> peekNext() {
        return frontierNodes.peek();
    }

    public int getNumOfNodesInFrontier(){
        return frontierNodes.size();
    }
}
