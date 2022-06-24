package pattern;

import auxiliary.Dish;
import auxiliary.Voter;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DinnerOrderStatisticsStrategy<Dish> implements StatisticsStrategy<Dish> {
    /**
     * 应用 3：统计每个菜品的总得分（加权求和）；
     * @param votes 投票
     * @param voteType  投票类型
     * @param votersVoteFrequencies 每个人投票次数
     * @param voteIsLegal   是否合法
     * @param voters    投票人
     * @return
     */
    @Override
    public Map<Dish, Double> statistics(Set<Vote<Dish>> votes, VoteType voteType, Map<Voter, Integer> votersVoteFrequencies, Map<Vote<Dish>, Boolean> voteIsLegal, Map<Voter, Double> voters) {
        HashMap<Dish, Double> statistics = new HashMap<>();
        for (Vote<Dish> vote : votes) {
            if (voteIsLegal.get(vote) == false)
                continue;//如果这个不合法就不需要计票
            //如果合法则开始计票
            Set<VoteItem<Dish>> voteItems = vote.getVoteItems();
            for (VoteItem<Dish> voteItem : voteItems)
                {
                    Double QuanZhong = 1.0;
                    //考虑权重
                    if (vote instanceof RealNameVote) {
                        RealNameVote<Dish> realNameVote = (RealNameVote<Dish>) vote;
                        Voter voter = realNameVote.getVoter();
                        QuanZhong = voters.get(voter);
                    }
                    Dish candidate = voteItem.getCandidate();
                    int score = voteType.getScoreByOption(voteItem.getVoteValue());
                    //根据权重统计支持票数
                    statistics.put(candidate, statistics.getOrDefault(candidate, 0.0) + score * QuanZhong);
                }
        }
//            System.out.println("statistics = " + statistics);
        return statistics;
    }
}
