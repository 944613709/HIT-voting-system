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
     * Ӧ�� 3��ͳ��ÿ����Ʒ���ܵ÷֣���Ȩ��ͣ���
     * @param votes ͶƱ
     * @param voteType  ͶƱ����
     * @param votersVoteFrequencies ÿ����ͶƱ����
     * @param voteIsLegal   �Ƿ�Ϸ�
     * @param voters    ͶƱ��
     * @return
     */
    @Override
    public Map<Dish, Double> statistics(Set<Vote<Dish>> votes, VoteType voteType, Map<Voter, Integer> votersVoteFrequencies, Map<Vote<Dish>, Boolean> voteIsLegal, Map<Voter, Double> voters) {
        HashMap<Dish, Double> statistics = new HashMap<>();
        for (Vote<Dish> vote : votes) {
            if (voteIsLegal.get(vote) == false)
                continue;//���������Ϸ��Ͳ���Ҫ��Ʊ
            //����Ϸ���ʼ��Ʊ
            Set<VoteItem<Dish>> voteItems = vote.getVoteItems();
            for (VoteItem<Dish> voteItem : voteItems)
                {
                    Double QuanZhong = 1.0;
                    //����Ȩ��
                    if (vote instanceof RealNameVote) {
                        RealNameVote<Dish> realNameVote = (RealNameVote<Dish>) vote;
                        Voter voter = realNameVote.getVoter();
                        QuanZhong = voters.get(voter);
                    }
                    Dish candidate = voteItem.getCandidate();
                    int score = voteType.getScoreByOption(voteItem.getVoteValue());
                    //����Ȩ��ͳ��֧��Ʊ��
                    statistics.put(candidate, statistics.getOrDefault(candidate, 0.0) + score * QuanZhong);
                }
        }
//            System.out.println("statistics = " + statistics);
        return statistics;
    }
}
