package pattern;

import auxiliary.Voter;
import poll.Poll;
import vote.Vote;
import vote.VoteType;

import java.util.Map;
import java.util.Set;

public interface StatisticsStrategy<C> {
    Map<C, Double> statistics(Set<Vote<C>> votes, VoteType voteType,Map<Voter,Integer> votersVoteFrequencies,Map<Vote<C>,Boolean> voteIsLegal,Map<Voter, Double> voters);

}
