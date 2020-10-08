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

    //todo
    public static IBinaryFunction hamacherTNorm(double x) {
        return null;
    }

    //todo
    public static IBinaryFunction hamacherSNorm(double x) {
        return null;
    }

}
