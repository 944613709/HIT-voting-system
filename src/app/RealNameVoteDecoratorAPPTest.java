package app;

import auxiliary.Person;
import auxiliary.Proposal;
import auxiliary.Voter;
import pattern.BusinessSelectionStrategy;
import pattern.BusinessVotingStatisticsStrategy;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import poll.BusinessVoting;
import poll.CanNotVoteException;
import poll.Election;
import vote.*;

import java.util.*;

public class RealNameVoteDecoratorAPPTest {
    /**
     * 针对RealNameVoteDecorator仅提供思路
     * 由于Vote接口教师框架未给出故只提供思路，无法运行
     */
    void mainTest()
    {
        //演示关键：vote调用时应该用RealNameVoteDecorator这样调用装饰
//        Voter vr1 = new Voter("v1");
//        Vote<Person> rv1 = new Vote<Person>(voteItems1, date);
//        RealNameVoteDecorator<Person> personRealNameVoteDecorator = new RealNameVoteDecorator<>(rv1, vr1);
//        poll.addVote(personRealNameVoteDecorator1, vr1);
//
//        // 创建2个投票人
//        Voter vr1 = new Voter("v1");
//        Voter vr2 = new Voter("v2");
//
//        // 设定2个投票人的权重
//        Map<Voter, Double> weightedVoters = new HashMap<>();
//        weightedVoters.put(vr1, 1.0);
//        weightedVoters.put(vr2, 1.0);
//
//        // 设定投票类型
//        Map<String, Integer> types = new HashMap<>();
//        types.put("Support", 1);
//        types.put("Oppose", -1);
//        types.put("Waive", 0);
//        VoteType voteType = new VoteType(types);
//
//        // 创建候选对象：候选人
//        Person p1 = new Person("candidate1", 19);
//        Person p2 = new Person("candidate2", 20);
//        Person p3 = new Person("candidate3", 21);
//        ArrayList<Person> candidates = new ArrayList<>();
//        candidates.add(p1);
//        candidates.add(p2);
//        candidates.add(p3);
//        // 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
//        VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
//        VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
//        VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
//        Set<VoteItem<Person>> voteItems1 = new HashSet<>();
//        voteItems1.add(vi11);
//        voteItems1.add(vi12);
//        voteItems1.add(vi13);
//
//        VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
//        VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
//        VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
//        Set<VoteItem<Person>> voteItems2 = new HashSet<>();
//        voteItems2.add(vi21);
//        voteItems2.add(vi22);
//        voteItems2.add(vi23);
//
//        // 创建2个投票人vr1、vr2的选票
//        Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//        Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//
//        RealNameVoteDecorator<Person> personRealNameVoteDecorator1 = new RealNameVoteDecorator<>(rv1, vr1);
//        RealNameVoteDecorator<Person> personRealNameVoteDecorator2 = new RealNameVoteDecorator<>(rv2, vr2);
//        // 创建投票活动
//        Election poll = new Election();
//        // 设定投票基本信息：名称、日期、投票类型、选出的数量
//        String name = "代表选举";
//        GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
//        int quantity = 2;
//        poll.setInfo(name, date, voteType, quantity);
//
//        // 增加投票人及其权重
//        poll.addVoters(weightedVoters);
//        poll.addCandidates(candidates);
//        // 增加三个投票人的选票
//        poll.addVote(personRealNameVoteDecorator1, vr1);
//        poll.addVote(personRealNameVoteDecorator2, vr2);
////		System.out.println("poll = " + poll.getVotes());
//        // 按规则计票
//        ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
//        try {
//            poll.statistics(electionStatisticsStrategy);
//        } catch (CanNotVoteException e) {
//            System.out.println("e.getMessage() = " + e.getMessage());
//        }
//
//        // 按规则遴选
//        ElectionSelectionStrategy<Person> electionSelectionStrategy = new ElectionSelectionStrategy<>();
//        poll.selection(electionSelectionStrategy);
//        // 输出遴选结果
//        System.out.println("本次投票类型"+poll.getClass());
//        System.out.println("本次投票具体信息" +poll);
//        System.out.println("统计获得支持票的数量,输出计票结果");
//        System.out.println(poll.getStatistics());
//        System.out.println("选择排名前k的候选人，若有多个候选人的支持票数量相等而无" +
//                "法自然排出前k名，则仅有那些明确可进入前k名的人当选");
//        System.out.println(poll.result());
//

    }
}
