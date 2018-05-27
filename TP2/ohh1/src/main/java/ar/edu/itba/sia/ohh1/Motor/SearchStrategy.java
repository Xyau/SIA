package ar.edu.itba.sia.ohh1.Motor;

public interface SearchStrategy<E> {

    public Node<E> getNext();

    public void addToFrontier(Node<E> n);

    public Node<E> peekNext();

    public int getNumOfNodesInFrontier();

}
