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
    //δ˵������ͬ����GeneralPollImplһ��
    //candidates.size()>=1;
    //quantity<=candidates.size();
    //Ҫ��VoteType�� ֧�ַ�����Ȩ(1|-1|0)
    //Ҫ��ÿ��ͶƱ��Ͷ��֧��Ʊ����<=quantity

    // Abstract Function
    // AF(Election)->����ѡ�٣���ѡ��������>=1,ֻ����ͶƱ֧�ַ�����Ȩ������ͶƱ��û��Ȩ��֮�֣�
    // ��Ʊ����ͳ��֧��Ʊ��������ѡ����ѡ������ǰk�ĺ�ѡ�ˣ����ж����ѡ�˵�֧��Ʊ������ȶ���
    // ����Ȼ�ų�ǰk�����������Щ��ȷ�ɽ���ǰk�����˵�ѡ��

    // Safety from Rep Exposure
    // û��ʹ��public����protected
    // ����date�ɱ����Ͳ������clone
    // ��addVoter�ȷ�����ʹ�÷����Կ���
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

        //�����������
        //����Ӧ�� 2��һ��ѡƱ�ж����к�ѡ�����֧��Ʊ����<=k(quantity)��
        if(super.voteIsLegal.get(vote)==true)//����ǰ�漸������������
        {
            int countSupport=0;
            Set<VoteItem<Person>> voteItems = vote.getVoteItems();
            for (VoteItem<Person> voteItem : voteItems) {
                if (voteItem.getVoteValue()=="֧��") {
                    countSupport++;
                }
            }
            if(countSupport>quantity)//����������
                super.voteIsLegal.put(vote,false);//���ǲ��Ϸ�
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
