package pattern;

import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BusinessVotingStatisticsStrategy<Proposal> implements StatisticsStrategy<Proposal> {
    @Override
    public Map<Proposal, Double> statistics(Set<Vote> votes, VoteType voteType) {
        HashMap<Proposal, Integer> totalScores = new HashMap<>();
        for (Vote vote : votes) {
            Set<VoteItem> voteItems = vote.getVoteItems();
            for (Object voteItem : voteItems) {
                VoteItem voteItem1 = (VoteItem) voteItem;
                if(voteItem1.getVoteValue()=="Ö§³Ö")
                    totalScores.put((Proposal) voteItem1.getCandidate(),totalScores.getOrDefault((Proposal)voteItem1.getCandidate(),0)+1);
            }

        }

    }

}
