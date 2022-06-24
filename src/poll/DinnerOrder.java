package poll;

import auxiliary.Dish;
import auxiliary.Proposal;
import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import pattern.Visitor;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteType;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DinnerOrder extends GeneralPollImpl<Dish> implements Poll<Dish> {
// Rep Invariants
    //δ˵������ͬ����GeneralPollImplһ��
    //candidates.size()>=1;
    //voters.size()<=quantity<=voters.size()<=candidates.size()
    //ʵ��
    //�޶�ΪͶƱ���� ϲ������ϲ��������ν

    //votes��VoteItem��ͶƱ��ѡ�ˣ�Ҫ�պø��ǵ������е�candidate��ѡ��
    //votes��VoteItem��ͶƱ��ѡ�ˣ�������������ѡ��
    //votes��VoteItem��valueͶƱѡ����ܰ���VoteType.options��Keys����֮���
    //candidates.size()=statistics.size()=results.size()

    //statistics��results��keyҪ�պø��ǵ������е�candidate��ѡ��
    //statistics��results��key������������ѡ��
    //candidates.size()=statistics.size()=results.size()

    // Abstract Function
    // AF��businessVoting��->�۲͵�ˣ���ѡ����������=1��ֻ����ͶƱϲ���Ͳ�ϲ��������ν����ͶƱʵ���ģ����ܹ����ݲ�ͬ��ݻ���Ȩ�صģ�
    //    ��Ʊ�����Ǽ�Ȩ�õ�������Ʒ�÷֣���ѡ����ѡ������ǰk�Ĳˣ�����Ϊ�ж���˵÷���ȶ��޷���Ȼ�ų�ǰ
    //    k�����������Щ��ȷ�ɽ���ǰk���Ĳ�֮�⣬�������÷���ȵĲ�����
    //    ��ѡȡһ���֣�����k����

    // Safety from Rep Exposure
    // TODO

    private void checkRep()
    {
        assert candidates.size()>=1;
        assert voters.size()<=quantity;
        assert quantity<=voters.size();
        assert voters.size()<=candidates.size();
    }


    public DinnerOrder() {
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
    public void addCandidates(List<Dish> candidates) {
        super.addCandidates(candidates);checkRep();
    }

    @Override
    public void addVote(Vote<Dish> vote, Voter voter) {
        super.addVote(vote, voter);
    }

    @Override
    public void checkVote(Vote<Dish> vote, Voter voter) {
        super.checkVote(vote, voter);
    }

    @Override
    public void statistics(StatisticsStrategy<Dish> ss) throws CanNotVoteException {
        // ��ʹ��ǰ,������Vote����֮��
        // �����ʵ��ͶƱ����ǰ�����һ��ͶƱ���ύ�˶��ѡƱ�������Ǿ�Ϊ�Ƿ�����Ʊʱ���ͶƱ�˵Ĳ��������ڡ�
        //Ҫ��ʵ��ͶƱ��Vote���붼��RealNameVote
        for (Vote<Dish> vote : votes) {
            assert vote instanceof RealNameVote;
            RealNameVote<Dish> realNameVote = (RealNameVote<Dish>) vote;
            Voter voter = realNameVote.getVoter();
//            System.out.println("votersVoteFrequencies = " + votersVoteFrequencies.size());
            if (votersVoteFrequencies.get(voter)>1) {//������ύͶƱ
                voteIsLegal.put(vote,false);
            }
        }
        super.statistics(ss);
    }

    @Override
    public void selection(SelectionStrategy<Dish> ss) {
        super.selection(ss);
    }

    @Override
    public String result() {
        return super.result();
    }

    @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    //TODO
}
