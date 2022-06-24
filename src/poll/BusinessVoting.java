package poll;

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

public class BusinessVoting extends GeneralPollImpl<Proposal> implements Poll<Proposal> {
    // Rep Invariants
    //δ˵������ͬ����GeneralPollImplһ��
    //candidates.size()=1;
    //quantity=1;

    //votes��VoteItem��ͶƱ��ѡ�ˣ�Ҫ�պø��ǵ������е�candidate��ѡ��
    //votes��VoteItem��ͶƱ��ѡ�ˣ�������������ѡ��
    //votes��VoteItem��valueͶƱѡ����ܰ���VoteType.options��Keys����֮���
    //candidates.size()=statistics.size()=results.size()

    //statistics��results��keyҪ�պø��ǵ������е�candidate��ѡ��
    //statistics��results��key������������ѡ��
    //candidates.size()=statistics.size()=results.size()

    // Abstract Function
    // AF��businessVoting��->��ҵ�᰸����ѡ��������Ϊ1����ѡ����ѡ����Ϊ0����1��ֻ����ͶƱ֧�ַ񶨺���Ȩ����ͶƱʵ���ģ����ܹ����ݹɷݻ���Ȩ�صģ�
    //��Ʊ������ͳ�ƻ��֧��Ʊ����������ѡ�����ǻ��֧��Ʊ�����Ϸ�ѡƱ��2/3������ʾ���ͨ����
    // Safety from Rep Exposure
    // TODO


    public BusinessVoting() {
        super();

    }
    private void checkRep()
    {
        assert super.getQuantity()==1;
        assert super.getCandidates().size()==1;
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
    public void addCandidates(List<Proposal> candidates) {
        super.addCandidates(candidates);
        checkRep();
    }

    @Override
    public void addVote(Vote<Proposal> vote, Voter voter) {
        super.addVote(vote, voter);
    }

    @Override
    public void checkVote(Vote<Proposal> vote, Voter voter) {
        super.checkVote(vote, voter);
    }

    @Override
    public VoteType getVoteType() {
        return super.getVoteType();
    }

    @Override
    public void accept(Visitor visitor) {
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
    public List<Proposal> getCandidates() {
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
    public Map<Proposal, Double> getResults() {
        return super.getResults();
    }

    @Override
    public Map<Voter, Integer> getVotersVoteFrequencies() {
        return super.getVotersVoteFrequencies();
    }

    @Override
    public void statistics(StatisticsStrategy<Proposal> ss) throws CanNotVoteException {
        // ��ʹ��ǰ,������Vote����֮��
        // �����ʵ��ͶƱ����ǰ�����һ��ͶƱ���ύ�˶��ѡƱ�������Ǿ�Ϊ�Ƿ�����Ʊʱ���ͶƱ�˵Ĳ��������ڡ�
        //Ҫ��ʵ��ͶƱ��Vote���붼��RealNameVote
        for (Vote<Proposal> vote : votes) {
            assert vote instanceof RealNameVote;
            RealNameVote<Proposal> realNameVote = (RealNameVote<Proposal>) vote;
            Voter voter = realNameVote.getVoter();
            if (votersVoteFrequencies.get(voter)>1) {//������ύͶƱ
                voteIsLegal.put(vote,false);
            }
        }
        super.statistics(ss);
    }

    @Override
    public void selection(SelectionStrategy<Proposal> ss) {
        super.selection(ss);
    }

    @Override
    public String result() {

         if(results.size()==0)
             return new String("��ͨ��");
        else
            return new String("ͨ��");
    }

}