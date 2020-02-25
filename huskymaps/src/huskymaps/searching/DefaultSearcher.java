package huskymaps.searching;

import autocomplete.Autocomplete;
import autocomplete.DefaultTerm;
import autocomplete.Term;
import huskymaps.graph.Node;
import huskymaps.graph.StreetMapGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @see Searcher
 */
public class DefaultSearcher extends Searcher {

    private Autocomplete terms;
    private StreetMapGraph graph;

    public DefaultSearcher(StreetMapGraph graph) {
        this.graph = graph;
        List<Term> allTerms = new LinkedList<>();
        int size = 0;
        for (Node node : graph.allNodes()) {
            if (node.name() != null && node.importance() >= 0) {
                Term term = createTerm(node.name(), node.importance());
                allTerms.add(term);
                size++;
            }
        }
        Term[] t = new Term[size];
        for (int i = 0; i < size; i++) {
            t[i] = allTerms.remove(0);
        }
        this.terms = createAutocomplete(t);
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
        Term[] matches = this.terms.findMatchesForPrefix(prefix);
        Set<String> locByPre = new HashSet<>();
        for (Term term : matches) {
            locByPre.add(term.query());
        }
        List<String> allPre = new ArrayList<>();
        allPre.addAll(locByPre);
        return allPre;
    }

    @Override
    public List<Node> getLocations(String locationName) {
        List<Node> allLoc = new ArrayList<>();
        for (Node node : this.graph.allNodes()) {
            if (node.name().equals(locationName)) {
                allLoc.add(node);
            }
        }
        return allLoc;
    }
}
