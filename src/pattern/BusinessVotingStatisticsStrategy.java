package pattern;

import auxiliary.Voter;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BusinessVotingStatisticsStrategy<Proposal> implements StatisticsStrategy<Proposal> {
    @Override
    public Map<Proposal, Double> statistics(Set<Vote<Proposal>> votes, VoteType voteType, Map<Voter,Integer> votersVoteFrequencies, Map<Vote<Proposal>,Boolean> voteIsLegal) {

            HashMap<Proposal, Double> statistics = new HashMap<Proposal, Double>();
            for (Vote<Proposal> vote : votes) {
                if(voteIsLegal.get(vote)==false)
                    continue;//如果这个不合法就不需要计票
                //如果合法则开始计票
                Set<VoteItem<Proposal>> voteItems = vote.getVoteItems();
                for (VoteItem<Proposal> voteItem : voteItems) {
                    if(Objects.equals(voteItem.getVoteValue(), "Support"))
                    {
                        Proposal candidate = voteItem.getCandidate();
                        statistics.put(candidate,statistics.getOrDefault(candidate,0.0)+1);
                    }
                }
            }
            return statistics;
    }
}
