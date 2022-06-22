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
    //未说明部分同父类GeneralPollImpl一致
    //candidates.size()=1;
    //quantity=1;
    //要求VoteType是 支持反对齐全

    //votes的VoteItem的投票候选人，要刚好覆盖到了所有的candidate候选人
    //votes的VoteItem的投票候选人，不能有其他候选人
    //votes的VoteItem的value投票选项，不能包含VoteType.options的Keys集合之外的
    //candidates.size()=statistics.size()=results.size()

    //statistics与results的key要刚好覆盖到了所有的candidate候选人
    //statistics与results的key不能有其他候选人
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