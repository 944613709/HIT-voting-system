package pattern;

import auxiliary.Voter;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  计算支持票总总数
 * @param <Person>
 */
public class ElectionStatisticsStrategy <Person> implements StatisticsStrategy<Person>{
    @Override
    /**
     * 无权重计算支持票总总数
     */
    public Map<Person, Double> statistics(Set<Vote<Person>> votes, VoteType voteType, Map<Voter,Integer> votersVoteFrequencies, Map<Vote<Person>,Boolean> voteIsLegal,Map<Voter, Double> voters) {
        HashMap<Person, Double> statistics = new HashMap<Person, Double>();
        for (Vote<Person> vote : votes) {
            if(voteIsLegal.get(vote)==false)
                continue;//如果这个不合法就不需要计票
            //如果合法则开始计票
            Set<VoteItem<Person>> voteItems = vote.getVoteItems();
            for (VoteItem<Person> voteItem : voteItems) {
                if(voteItem.getVoteValue()=="Support")
                {
                    Person candidate = voteItem.getCandidate();
                    statistics.put(candidate,statistics.getOrDefault(candidate,0.0)+1);
                }
            }
        }
        return statistics;

    }
}
