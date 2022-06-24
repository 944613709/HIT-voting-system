package pattern;

import auxiliary.Voter;
import poll.Poll;
import vote.Vote;
import vote.VoteType;

import java.util.Map;
import java.util.Set;

/**
 * 考虑到不同应用所使用的遴选规则有显著差异，并考虑到未来可能出现的其
 * 他应用中会引入新的遴选规则，请使用 Strategy 模式改造你的设计
 * @param <C>
 */
public interface StatisticsStrategy<C> {
    Map<C, Double> statistics(Set<Vote<C>> votes, VoteType voteType,Map<Voter,Integer> votersVoteFrequencies,Map<Vote<C>,Boolean> voteIsLegal,Map<Voter, Double> voters);

}
