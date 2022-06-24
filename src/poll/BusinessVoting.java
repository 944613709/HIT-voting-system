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
    //未说明部分同父类GeneralPollImpl一致
    //candidates.size()=1;
    //quantity=1;

    //votes的VoteItem的投票候选人，要刚好覆盖到了所有的candidate候选人
    //votes的VoteItem的投票候选人，不能有其他候选人
    //votes的VoteItem的value投票选项，不能包含VoteType.options的Keys集合之外的
    //candidates.size()=statistics.size()=results.size()

    //statistics与results的key要刚好覆盖到了所有的candidate候选人
    //statistics与results的key不能有其他候选人
    //candidates.size()=statistics.size()=results.size()

    // Abstract Function
    // AF（businessVoting）->商业提案，候选对象数量为1，拟选出候选对象为0或者1，只允许投票支持否定和弃权，且投票实名的，且能够根据股份划分权重的，
    //计票规则是统计获得支持票的数量，遴选规则是获得支持票超过合法选票的2/3，即表示表决通过。
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
        // 在使用前,在所有Vote加入之后，
        // 仅针对实名投票会提前检查若一个投票人提交了多次选票，则它们均为非法，计票时这个投票人的不计算在内。
        //要求实名投票的Vote必须都是RealNameVote
        for (Vote<Proposal> vote : votes) {
            assert vote instanceof RealNameVote;
            RealNameVote<Proposal> realNameVote = (RealNameVote<Proposal>) vote;
            Voter voter = realNameVote.getVoter();
            if (votersVoteFrequencies.get(voter)>1) {//若多次提交投票
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
             return new String("不通过");
        else
            return new String("通过");
    }

}