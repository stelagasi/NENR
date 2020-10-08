package hr.fer.zemris.fuzzy;

public class Operations {

    public static IFuzzySet unaryOperation(IFuzzySet fuzzySet, IUnaryFunction unaryFunction) {
        if (!(fuzzySet.getDomain() instanceof SimpleDomain)) throw new IllegalArgumentException();
        SimpleDomain simpleDomain = (SimpleDomain) fuzzySet.getDomain();
        MutableFuzzySet resultFuzzySet = new MutableFuzzySet(simpleDomain);
        for (int i = simpleDomain.getFirst(); i < simpleDomain.getLast(); i++) {
            DomainElement domainElement = new DomainElement(i);
            resultFuzzySet.set(domainElement, unaryFunction.valueAt(fuzzySet.getValueAt(domainElement)));
        }
        return resultFuzzySet;
    }

    public static IFuzzySet binaryOperation(IFuzzySet firstFuzzySet, IFuzzySet secondFuzzySet, IBinaryFunction binaryFunction) {
        if (!(firstFuzzySet.getDomain().equals(secondFuzzySet.getDomain())) ||
                !(firstFuzzySet.getDomain() instanceof SimpleDomain)) throw new IllegalArgumentException();
        SimpleDomain simpleDomain = (SimpleDomain) firstFuzzySet.getDomain();
        MutableFuzzySet resultFuzzySet = new MutableFuzzySet(simpleDomain);
        for (int i = simpleDomain.getFirst(); i < simpleDomain.getLast(); i++) {
            DomainElement domainElement = new DomainElement(i);
            resultFuzzySet.set(domainElement, binaryFunction.valueAt(
                    firstFuzzySet.getValueAt(domainElement), secondFuzzySet.getValueAt(domainElement)));
        }
        return resultFuzzySet;
    }

    public static IUnaryFunction zadehNot() {
        return x -> 1 - x;
    }

    public static IBinaryFunction zadehAnd() {
        return Math::min;
    }

    public static IBinaryFunction zadehOr() {
        return Math::max;
    }

    public static IBinaryFunction hamacherTNorm(double ni) {
        if (ni < 0) throw new IllegalArgumentException();
        return (x1, x2) -> (x1 * x2) / (ni + (1 - ni) * (x1 + x2 - x1 * x2));
    }

    public static IBinaryFunction hamacherSNorm(double ni) {
        if (ni < 0) throw new IllegalArgumentException();
        return (x1, x2) -> (x1 + x2 - (2 - ni) * x1 * x2) / (1 - (1 - ni) * x1 * x2);
    }

}
