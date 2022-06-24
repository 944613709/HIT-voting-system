package pattern;

import auxiliary.Proposal;
import vote.Vote;

import java.util.HashMap;
import java.util.Map;

public class BusinessSelectionStrategy<Proposal> implements SelectionStrategy<Proposal>{
    /**
     * ѡ������ǰ?�Ĳˣ�����Ϊ�ж���˵÷���ȶ��޷���Ȼ�ų�ǰ
     * ?�����������Щ��ȷ�ɽ���ǰ?���Ĳ�֮�⣬�������÷���ȵĲ�����
     * ��ѡȡһ���֣�����?���ˡ�
     * @param statistics
     * @param quantity
     * @param voteIsLegal
     * @return
     */
    @Override
    public Map<Proposal, Double> selection(Map<Proposal, Double> statistics, int quantity, Map<Vote<Proposal>, Boolean> voteIsLegal)
    {
        int totalLegalNumber= 0;//�ܹ��Ϸ�Ʊ��
        for (Map.Entry<Vote<Proposal>, Boolean> voteBooleanEntry : voteIsLegal.entrySet()) {
            if(voteBooleanEntry.getValue())//���vote�ǺϷ���
            {
                totalLegalNumber++;
            }
        }
        Double SanFenZhiEr = totalLegalNumber*2.0/3.0;
        Double rank =1.0;
        HashMap<Proposal, Double> results = new HashMap<>();
        for (Map.Entry<Proposal, Double> entry : statistics.entrySet()) {
            Proposal candidates = entry.getKey();
            Double number = entry.getValue();
            //�����ϸ��������֮�����ܹ�
            if(number>SanFenZhiEr)
            {
                results.put(candidates,rank++);
            }
        }
        return results;
    }
}

