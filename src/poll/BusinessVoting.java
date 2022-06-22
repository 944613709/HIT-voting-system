package poll;

import auxiliary.Proposal;
import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
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
    //Ҫ��VoteType�� ֧�ַ�����ȫ

    //votes��VoteItem��ͶƱ��ѡ�ˣ�Ҫ�պø��ǵ������е�candidate��ѡ��
    //votes��VoteItem��ͶƱ��ѡ�ˣ�������������ѡ��
    //votes��VoteItem��valueͶƱѡ����ܰ���VoteType.options��Keys����֮���
    //candidates.size()=statistics.size()=results.size()

    //statistics��results��keyҪ�պø��ǵ������е�candidate��ѡ��
    //statistics��results��key������������ѡ��
    //candidates.size()=statistics.size()=results.size()

    // Abstract Function
    // TODO
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
    public void addVote(Vote<Proposal> vote) throws NoEnoughCandidateException, InvalidCadidatesException, InvalidVoteException, RepeatCandidateException {
        super.addVote(vote);
    }

    @Override
    public void voterWithVote_Before_addVote(Vote<Proposal> vote, Voter voter) throws NoEnoughCandidateException, InvalidCadidatesException, InvalidVoteException, RepeatCandidateException {
        super.voterWithVote_Before_addVote(vote, voter);
    }

    @Override
    public void statistics(StatisticsStrategy ss) throws CanNotVoteException {
        super.statistics(ss);
    }

    @Override
    public void selection(SelectionStrategy ss) {
        super.selection(ss);
    }

    @Override
    public String result() {
        return super.result();
    }

}