package pattern;

import poll.Poll;
import vote.Vote;
import vote.VoteType;

import java.util.Map;
import java.util.Set;

public interface StatisticsStrategy<C> {
    Map<C, Double> statistics(Set<Vote> votes, VoteType voteType);

}
