package app;

import auxiliary.Proposal;
import auxiliary.Voter;
import org.junit.Test;
import pattern.BusinessSelectionStrategy;
import pattern.BusinessVotingStatisticsStrategy;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import poll.BusinessVoting;
import poll.CanNotVoteException;
import poll.Election;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class BusinessVotingApp {
    /**
     * 表决成功->最终这个候选对象被选中
     * * 1个候选对象
     * * 候选对象candidate1
     * * 投票人vr1，权重为3，对candidate1-support
     * * 投票人vr2，权重为1，对candidate1-Oppose
     * * 投票人vr3，权重为1，对candidate1-Oppose
     * 计票得到	candidate1->3
     * 总共合法选票数量3
     * 均大于(3*(2/3))
     * 遴选得到candidate1>1
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        //要求实名投票的Vote必须都是RealNameVote
        Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
        // 创建2个投票人
        Voter vr1 = new Voter("v1");
        Voter vr2 = new Voter("v2");
        Voter vr3 = new Voter("v3");

        // 设定2个投票人的权重
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(vr1, 3.0);
        weightedVoters.put(vr2, 1.0);
        weightedVoters.put(vr3, 1.0);

        // 设定投票类型
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType voteType = new VoteType(types);

        // 创建候选对象：候选人
        Proposal p1 = new Proposal("candidate1", date);

        ArrayList<Proposal> candidates = new ArrayList<>();
        candidates.add(p1);

        // 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
        VoteItem<Proposal> vi11 = new VoteItem<>(p1, "Support");
        Set<VoteItem<Proposal>> voteItems1 = new HashSet<>();
        voteItems1.add(vi11);

        VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Oppose");
        Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
        voteItems2.add(vi21);

        VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Oppose");
        Set<VoteItem<Proposal>> voteItems3 = new HashSet<>();
        voteItems3.add(vi31);

        // 创建2个投票人vr1、vr2的选票
        RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date, vr1);
        RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date, vr2);
        RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(voteItems3, date, vr3);

        // 创建投票活动

        BusinessVoting poll = new BusinessVoting();
        // 设定投票基本信息：名称、日期、投票类型、选出的数量
        String name = "商业表决";
        int quantity = 1;
        poll.setInfo(name, date, voteType, quantity);

        // 增加投票人及其权重
        poll.addVoters(weightedVoters);
        poll.addCandidates(candidates);
        // 增加三个投票人的选票
        poll.addVote(rv1, vr1);
        poll.addVote(rv2, vr2);
        poll.addVote(rv3, vr3);
        //		System.out.println("poll = " + poll.getVotes());
        // 按规则计票
        BusinessVotingStatisticsStrategy<Proposal> businessVotingStatisticsStrategy = new BusinessVotingStatisticsStrategy<>();
        try {
            poll.statistics(businessVotingStatisticsStrategy);
        } catch (CanNotVoteException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
//			System.out.println("poll = " + poll.getStatistics());
        // 按规则遴选
        BusinessSelectionStrategy<Proposal> businessSelectionStrategy = new BusinessSelectionStrategy<>();
        poll.selection(businessSelectionStrategy);
        // 输出遴选结果
        System.out.println("本次投票类型"+poll.getClass());
        System.out.println("本次投票具体信息" +poll);
        System.out.println("加权统计获得支持票的数量,输出计票结果");
        System.out.println(poll.getStatistics());
        System.out.println("获得支持票超过合法选票的2/3，即表示表决通过,输出遴选结果");
        System.out.println(poll.result());

    }
}
