package hr.fer.zemris.fuzzy.controlsystem;

public enum InputValue {
    L(0),
    D(1),
    LK(2),
    DK(3),
    V(4),
    S(5);

    private final int order;

    InputValue(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
