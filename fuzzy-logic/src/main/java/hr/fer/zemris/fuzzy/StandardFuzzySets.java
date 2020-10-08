package hr.fer.zemris.fuzzy;

public class StandardFuzzySets {

    public static IIntUnaryFunction lFunction(int alpha, int beta) {
        if (alpha == beta) throw new IllegalArgumentException();
        return index -> {
            if (index < alpha) return 1;
            if (index >= beta) return 0;
            return (double) (beta - index) / (beta - alpha);
        };
    }

    public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
        if (alpha == beta) throw new IllegalArgumentException();
        return index -> {
            if (index < alpha) return 0;
            if (index >= beta) return 1;
            return (double) (index - alpha) / (beta - alpha);
        };
    }

    public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma) {
        if (alpha == beta || beta == gamma) throw new IllegalArgumentException();
        return index -> {
            if (index < alpha) return 0;
            if (index >= gamma) return 0;
            if (index < beta) {
                return (double) (index - alpha) / (beta - alpha);
            } else {
                return (double) (gamma - index) / (gamma - beta);
            }
        };
    }
}
