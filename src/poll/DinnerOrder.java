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
    //未说明部分同父类GeneralPollImpl一致
    //candidates.size()>=1;
    //voters.size()<=quantity<=voters.size()<=candidates.size()
    //实名
    //限定为投票类型 喜欢、不喜欢、无所谓

    //votes的VoteItem的投票候选人，要刚好覆盖到了所有的candidate候选人
    //votes的VoteItem的投票候选人，不能有其他候选人
    //votes的VoteItem的value投票选项，不能包含VoteType.options的Keys集合之外的
    //candidates.size()=statistics.size()=results.size()

    //statistics与results的key要刚好覆盖到了所有的candidate候选人
    //statistics与results的key不能有其他候选人
    //candidates.size()=statistics.size()=results.size()

    // Abstract Function
    // AF（businessVoting）->聚餐点菜，候选对象数量》=1，只允许投票喜欢和不喜欢和无所谓，且投票实名的，且能够根据不同身份划分权重的，
    //    计票规则是加权得到各个菜品得分，遴选规则选择排名前k的菜，若因为有多道菜得分相等而无法自然排出前
    //    k名，则除了那些明确可进入前k名的菜之外，在其他得分相等的菜中随
    //    机选取一部分，凑足k个菜

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
        // 在使用前,在所有Vote加入之后，
        // 仅针对实名投票会提前检查若一个投票人提交了多次选票，则它们均为非法，计票时这个投票人的不计算在内。
        //要求实名投票的Vote必须都是RealNameVote
        for (Vote<Dish> vote : votes) {
            assert vote instanceof RealNameVote;
            RealNameVote<Dish> realNameVote = (RealNameVote<Dish>) vote;
            Voter voter = realNameVote.getVoter();
//            System.out.println("votersVoteFrequencies = " + votersVoteFrequencies.size());
            if (votersVoteFrequencies.get(voter)>1) {//若多次提交投票
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
