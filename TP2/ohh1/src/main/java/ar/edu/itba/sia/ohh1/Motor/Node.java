package ar.edu.itba.sia.ohh1.Motor;

public class Node<E> {

    private E state;
    private Node parent;
    private double cost;
    private int depth;
    private Double hValue;

    public E getState() {
        return state;
    }

    public void setState(E state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getDepth() {return depth;}

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Node(E state,  Node parent, double cost,int depth) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.depth = depth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return this.state.equals(node.state);
    }

    @Override
    public int hashCode() {

        return this.state.hashCode();
    }

    public Double gethValue() {
        return hValue;
    }

    public void sethValue(Double hValue) {
        this.hValue = hValue;
    }
}
