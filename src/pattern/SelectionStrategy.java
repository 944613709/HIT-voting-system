package pattern;

import vote.Vote;

import java.util.Map;

public interface SelectionStrategy<C> {
    /**
     * 考虑到不同应用所使用的计票规则有显著差异，并考虑到未来可能出现的其
     * 他应用中会引入新的计票规则，请使用 Strategy 设计模式改造你的设计
     * @param statistics
     * @param quantity
     * @param voteIsLegal
     * @return
     */
    Map<C, Double> selection(Map<C, Double> statistics,int quantity,Map<Vote<C>,Boolean> voteIsLegal);

}
