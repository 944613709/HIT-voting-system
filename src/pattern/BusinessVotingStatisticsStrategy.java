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
        // ��ʹ��ǰ�����ʵ��ͶƱ����ǰ�����һ��ͶƱ���ύ�˶��ѡƱ�������Ǿ�Ϊ�Ƿ�����Ʊʱ���ͶƱ�˵Ĳ��������ڡ�
        //������������������Ʋ������������ͶƱ

        HashMap<Proposal, Double> totalScores = new HashMap<Proposal, Double>();
        HashMap<Proposal, Double> statistics = new HashMap<Proposal, Double>();
        int totalNumber=0;//һ������ͶƱ�˶����к�ѡ��Ͷ���˶���֧��
        for (Vote<Proposal> vote : votes) {
            if(voteIsLegal.get(vote)==false)
                continue;//���������Ϸ��Ͳ���Ҫ��Ʊ
            //����Ϸ���ʼ��Ʊ
            Set<VoteItem<Proposal>> voteItems = vote.getVoteItems();
            for (VoteItem<Proposal> voteItem : voteItems) {
                if(voteItem.getVoteValue()=="֧��")
                {
                    totalNumber++;
                    Proposal candidate = voteItem.getCandidate();
                    totalScores.put(candidate,totalScores.getOrDefault(candidate,0.0)+1);
                }
            }
        }
        //���ּܷ������
        for (Map.Entry<Proposal, Double> entry : totalScores.entrySet()) {
            Double totalScore = entry.getValue();
            Proposal candidate = entry.getKey();
            double proportion = totalScore / totalNumber;
            statistics.put(candidate,proportion);
        }
        return statistics;

}
}
