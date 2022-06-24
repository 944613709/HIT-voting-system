package pattern;

import auxiliary.Proposal;
import vote.Vote;

import java.util.HashMap;
import java.util.Map;

public class BusinessSelectionStrategy<Proposal> implements SelectionStrategy<Proposal>{
    /**
     * 选择排名前?的菜，若因为有多道菜得分相等而无法自然排出前
     * ?名，则除了那些明确可进入前?名的菜之外，在其他得分相等的菜中随
     * 机选取一部分，凑足?个菜。
     * @param statistics
     * @param quantity
     * @param voteIsLegal
     * @return
     */
    @Override
    public Map<Proposal, Double> selection(Map<Proposal, Double> statistics, int quantity, Map<Vote<Proposal>, Boolean> voteIsLegal)
    {
        int totalLegalNumber= 0;//总共合法票数
        for (Map.Entry<Vote<Proposal>, Boolean> voteBooleanEntry : voteIsLegal.entrySet()) {
            if(voteBooleanEntry.getValue())//如果vote是合法的
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
            //必须严格大于三分之二才能够
            if(number>SanFenZhiEr)
            {
                results.put(candidates,rank++);
            }
        }
        return results;
    }
}

