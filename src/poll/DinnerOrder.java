package poll;

import auxiliary.Dish;
import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import pattern.Visitor;
import vote.Vote;
import vote.VoteType;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DinnerOrder extends GeneralPollImpl<Dish> implements Poll<Dish> {
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
        super.addCandidates(candidates);
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
        //TODO
        // 在使用前,在所有Vote加入之后，
        // 仅针对实名投票会提前检查若一个投票人提交了多次选票，则它们均为非法，计票时这个投票人的不计算在内。
        //要求实名投票的Vote必须都是RealNameVote
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
