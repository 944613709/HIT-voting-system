package pattern;

import auxiliary.Voter;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  ����֧��Ʊ������
 * @param <Person>
 */
public class ElectionStatisticsStrategy <Person> implements StatisticsStrategy<Person>{
    @Override
    /**
     * ����֧��Ʊ������
     */
    public Map<Person, Double> statistics(Set<Vote<Person>> votes, VoteType voteType, Map<Voter,Integer> votersVoteFrequencies, Map<Vote<Person>,Boolean> voteIsLegal) {
        // ��ʹ��ǰ�����ʵ��ͶƱ����ǰ�����һ��ͶƱ���ύ�˶��ѡƱ�������Ǿ�Ϊ�Ƿ�����Ʊʱ���ͶƱ�˵Ĳ��������ڡ�
        //������������������Ʋ������������ͶƱ
        HashMap<Person, Double> statistics = new HashMap<Person, Double>();
        for (Vote<Person> vote : votes) {
            if(voteIsLegal.get(vote)==false)
                continue;//���������Ϸ��Ͳ���Ҫ��Ʊ
            //����Ϸ���ʼ��Ʊ
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
