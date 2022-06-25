package poll;

import auxiliary.Dish;
import auxiliary.Person;
import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import pattern.Visitor;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Election extends GeneralPollImpl<Person> implements Poll<Person> {
    // Rep Invariants
    //未说明部分同父类GeneralPollImpl一致
    //candidates.size()>=1;
    //quantity<=candidates.size();
    //要求VoteType是 支持反对弃权(1|-1|0)
    //要求每个投票者投的支持票个数<=quantity

    // Abstract Function
    // AF(Election)->代表选举，候选对象数量>=1,只允许投票支持反对弃权，匿名投票，没有权重之分，
    // 计票规则统计支持票总数，遴选规则选择排名前k的候选人，若有多个候选人的支持票数量相等而无
    // 法自然排出前k名，则仅有那些明确可进入前k名的人当选；

    // Safety from Rep Exposure
    // 没有使用public而是protected
    // 对于date可变类型采用深拷贝clone
    // 在addVoter等方法中使用防御性拷贝
    private void checkRep()
    {
        assert candidates.size()>=1;
        assert quantity<=candidates.size();
    }
    public Election() {
        super();
    }

    @Override
    public void setInfo(String name, Calendar date, VoteType type, int quantity) {
        super.setInfo(name, date, type, quantity);
    }

    @Override
    public void addVoters(Map<Voter, Double> voters) {
        super.addVoters(voters);
    }

    @Override
    public void addCandidates(List<Person> candidates) {
        super.addCandidates(candidates);
        checkRep();
    }

    @Override
    public void addVote(Vote<Person> vote, Voter voter) {
        super.addVote(vote, voter);
    }

    @Override
    public void checkVote(Vote<Person> vote, Voter voter) {
        super.checkVote(vote,voter);

        //检查额外的条件
        //（仅应用 2）一张选票中对所有候选对象的支持票数量<=k(quantity)。
        if(super.voteIsLegal.get(vote)==true)//若在前面几个条件均满足
        {
            int countSupport=0;
            Set<VoteItem<Person>> voteItems = vote.getVoteItems();
            for (VoteItem<Person> voteItem : voteItems) {
                if (voteItem.getVoteValue()=="支持") {
                    countSupport++;
                }
            }
            if(countSupport>quantity)//不满足条件
                super.voteIsLegal.put(vote,false);//则标记不合法
        }


    }


    @Override
    public void statistics(StatisticsStrategy<Person> ss) throws CanNotVoteException {
        super.statistics(ss);
    }

    @Override
    public void selection(SelectionStrategy<Person> ss) {
        super.selection(ss);
    }

    @Override
    public String result() {
        return super.result();
    }

    @Override
    public void accept(Visitor<Person> visitor) {
          super.accept(visitor);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Calendar getDate() {
        return super.getDate();
    }

    @Override
    public List<Person> getCandidates() {
        return super.getCandidates();
    }

    @Override
    public Map<Voter, Double> getVoters() {
        return super.getVoters();
    }

    @Override
    public int getQuantity() {
        return super.getQuantity();
    }

    @Override
    public VoteType getVoteType() {
        return super.getVoteType();
    }

    @Override
    public Map<Person, Double> getResults() {
        return super.getResults();
    }

    @Override
    public Map<Voter, Integer> getVotersVoteFrequencies() {
        return super.getVotersVoteFrequencies();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
