package pattern;

import vote.Vote;

import java.util.Map;

public interface SelectionStrategy<C> {

    Map<C, Double> selection(Map<C, Double> statistics,int quantity,Map<Vote<C>,Boolean> voteIsLegal);

}
