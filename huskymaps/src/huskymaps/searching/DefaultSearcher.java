package huskymaps.searching;

import autocomplete.Autocomplete;
import autocomplete.DefaultTerm;
import autocomplete.Term;
import huskymaps.graph.Node;
import huskymaps.graph.StreetMapGraph;

import java.util.List;

/**
 * @see Searcher
 */
public class DefaultSearcher extends Searcher {
    public DefaultSearcher(StreetMapGraph graph) {
        // TODO: replace this with your code
    }

    @Override
    protected Term createTerm(String name, int weight) {
        return new DefaultTerm(name, weight);
    }

    @Override
    protected Autocomplete createAutocomplete(Term[] termsArray) {
        return new Autocomplete(termsArray);
    }

    @Override
    public List<String> getLocationsByPrefix(String prefix) {
        // TODO: replace this with your code
        return List.of();
    }

    @Override
    public List<Node> getLocations(String locationName) {
        // TODO: replace this with your code
        return List.of();
    }
}
