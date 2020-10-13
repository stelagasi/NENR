package hr.fer.zemris.fuzzy;

public class Relations {
    private Relations() {
    }

    public static boolean isUtimesURelation(IFuzzySet relation) {
        if (!(relation.getDomain() instanceof CompositeDomain)) return false;
        CompositeDomain domain = (CompositeDomain) relation.getDomain();
        if (domain.getNumberOfComponents() != 2) return false;
        return domain.getComponent(0).equals(domain.getComponent(1));
    }

    public static boolean isSymmetric(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;
        SimpleDomain domain = (SimpleDomain) relation.getDomain().getComponent(0);
        for (int i = domain.getFirst(); i < domain.getLast() - 1; i++) {
            for (int j = i + 1; j < domain.getLast(); j++) {
                if (relation.getValueAt(new DomainElement(i, j)) != relation.getValueAt(new DomainElement(j, i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isReflexive(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;
        SimpleDomain domain = (SimpleDomain) relation.getDomain().getComponent(0);
        for (int i = domain.getFirst(); i < domain.getLast() - 1; i++) {
            if (relation.getValueAt(new DomainElement(i, i)) != 1) return false;
        }
        return true;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        if (!isUtimesURelation(relation)) return false;
        SimpleDomain domain = (SimpleDomain) relation.getDomain().getComponent(0);
        for (int x = domain.getFirst(); x < domain.getLast(); x++) {
            for (int z = domain.getFirst(); z < domain.getLast(); z++) {
                double maximum = 0;
                for (int y = domain.getFirst(); y < domain.getLast(); y++) {
                    double minimum = Math.min(relation.getValueAt(new DomainElement(x, y)), relation.getValueAt(new DomainElement(y, z)));
                    if (maximum < minimum) maximum = minimum;
                }
                if (relation.getValueAt(new DomainElement(x, z)) < maximum) return false;
            }
        }
        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet firstRelation, IFuzzySet secondRelation) {
        if (firstRelation.getDomain().getNumberOfComponents() != 2
                || secondRelation.getDomain().getNumberOfComponents() != 2
                || !firstRelation.getDomain().getComponent(1).equals(secondRelation.getDomain().getComponent(0))) {
            throw new IllegalArgumentException();
        }
        SimpleDomain X = (SimpleDomain) firstRelation.getDomain().getComponent(0);
        SimpleDomain Y = (SimpleDomain) firstRelation.getDomain().getComponent(1);
        SimpleDomain Z = (SimpleDomain) secondRelation.getDomain().getComponent(1);
        MutableFuzzySet resultRelation = new MutableFuzzySet(Domain.combine(X, Z));

        for (int x = X.getFirst(); x < X.getLast(); x++) {
            for (int z = Z.getFirst(); z < Z.getLast(); z++) {
                double maximum = 0;
                for (int y = Y.getFirst(); y < Y.getLast(); y++) {
                    double minimum = Math.min(firstRelation.getValueAt(new DomainElement(x, y)), secondRelation.getValueAt(new DomainElement(y, z)));
                    if (maximum < minimum) maximum = minimum;
                }
                resultRelation.set(new DomainElement(x, z), maximum);
            }
        }
        return resultRelation;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet relation) {
        return isReflexive(relation) && isSymmetric(relation) && isMaxMinTransitive(relation);
    }
}
