package autocomplete;

/**
 * This is currently the only implementation of the {@link Term} interface, which is why it's named
 * "default." (Having an interface with a single implementation is a little redundant, but we need
 * it to keep you from accidentally renaming things.)
 *
 * Make sure to check out the interface for method specifications.
 * @see Term
 */
public class DefaultTerm implements Term {
    // TODO: add fields as necessary
    // TODO (recommended): after adding fields, use IntelliJ to generate toString, equals, and hashCode methods

    /**
     * Initializes a term with the given query string and weight.
     * @throws IllegalArgumentException if query is null or weight is negative
     */
    public DefaultTerm(String query, long weight) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public String query() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public long weight() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int queryOrder(Term that) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int reverseWeightOrder(Term that) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int matchesPrefix(String prefix) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
