package pattern;

import auxiliary.Voter;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BusinessVotingStatisticsStrategy<Proposal> implements StatisticsStrategy<Proposal> {
    @Override
    public Map<Proposal, Double> statistics(Set<Vote> votes, VoteType voteType, Map<Voter,Integer> votersVoteFrequencies, Map<Vote,Boolean> voteIsLegal) {
        //TODO
        // 在使用前仅针对实名投票会提前检查若一个投票人提交了多次选票，则它们均为非法，计票时这个投票人的不计算在内。
        //因此我们在这个里面设计不考虑上述多次投票

        HashMap<Proposal, Double> totalScores = new HashMap<Proposal, Double>();
        HashMap<Proposal, Double> statistics = new HashMap<Proposal, Double>();
        int totalNumber=0;//一共所有投票人对所有候选人投出了多少支持
        for (Vote<Proposal> vote : votes) {
            if(voteIsLegal.get(vote)==false)
                continue;//如果这个不合法就不需要计票
            //如果合法则开始计票
            Set<VoteItem<Proposal>> voteItems = vote.getVoteItems();
            for (VoteItem<Proposal> voteItem : voteItems) {
                if(voteItem.getVoteValue()=="支持")
                {
                    totalNumber++;
                    Proposal candidate = voteItem.getCandidate();
                    totalScores.put(candidate,totalScores.getOrDefault(candidate,0.0)+1);
                }
            }
        }
        //从总分计算比重
        for (Map.Entry<Proposal, Double> entry : totalScores.entrySet()) {
            Double totalScore = entry.getValue();
            Proposal candidate = entry.getKey();
            double proportion = totalScore / totalNumber;
            statistics.put(candidate,proportion);
        }
        return statistics;

}
}
