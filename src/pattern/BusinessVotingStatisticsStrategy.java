package pattern;

import auxiliary.Voter;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BusinessVotingStatisticsStrategy<Proposal> implements StatisticsStrategy<Proposal> {
    /**
     * ͳ�ƻ��֧��Ʊ����������Ȩ��ͣ���
     * @param votes
     * @param voteType
     * @param votersVoteFrequencies
     * @param voteIsLegal
     * @param voters
     * @return
     */
    @Override
    public Map<Proposal, Double> statistics(Set<Vote<Proposal>> votes, VoteType voteType, Map<Voter,Integer> votersVoteFrequencies, Map<Vote<Proposal>,Boolean> voteIsLegal,Map<Voter, Double> voters) {
            HashMap<Proposal, Double> statistics = new HashMap<Proposal, Double>();
            for (Vote<Proposal> vote : votes) {
//                System.out.println("voteIsLegal = " + voteIsLegal.get(vote));
                if(voteIsLegal.get(vote)==false)
                    continue;//���������Ϸ��Ͳ���Ҫ��Ʊ

                //����Ϸ���ʼ��Ʊ
                Set<VoteItem<Proposal>> voteItems = vote.getVoteItems();
                for (VoteItem<Proposal> voteItem : voteItems) {
//                    System.out.println("voteItem = " + voteItem);
                    if(Objects.equals(voteItem.getVoteValue(), "Support"))
                    {
                        Double QuanZhong = 1.0;
                        //����Ȩ��
                        if(vote instanceof RealNameVote)
                        {
                            RealNameVote<Proposal> realNameVote = (RealNameVote<Proposal>) vote;
                            Voter voter = realNameVote.getVoter();
                            QuanZhong = voters.get(voter);
                        }
                        Proposal candidate = voteItem.getCandidate();
                        //����Ȩ��ͳ��֧��Ʊ��
                        statistics.put(candidate,statistics.getOrDefault(candidate,0.0)+1*QuanZhong);
                    }
                }
            }
//            System.out.println("statistics = " + statistics);
            return statistics;
    }
}
