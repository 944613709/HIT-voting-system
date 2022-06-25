package poll;

import auxiliary.Person;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.CountProportionVisitor;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitorTest {
    //测试策略：
    //测试accept方法,访问者Visitor模式
//	 1.全部合法
//	  2.部分合法
    /**
     * 测试任务11中在ADT 设计中引入 Visitor 设计模式，
     * 计算合法选票在所有选票中所占比例
     * 测试accept方法
     *1.全部合法
     * 	 * 	  选择排名前2的候选人
     * 	 * 	  候选人candidate1，candidate2，candidate3
     * 	 * 	  投票人vr1，对candidate1-support，对candidate2-oppose，对candidate3-support
     * 	 * 	  投票人vr2，对candidate1-Oppose，对candidate2-Waive，对candidate3-Waive
     * 	 比例2/2
     * 2.部分合法
     * 	  选择排名前2的候选人
     * 	  候选人candidate1，candidate2，candidate3
     * 	  投票人vr1，对candidate1-support，对candidate2-oppose，对candidate3-support
     * 	  投票人vr2，对candidate1-Oppose，对candidate2-Waive，对candidate3-Waive
     * 	 投票人vr3，对candidate1-Oppose，对candidate2-Waive
     * 	 2张合法，3张选票
     * 	 比例2/3
     */
    @Test
    void acceptTest_AllHeFa()
    {
        // 创建2个投票人
        Voter vr1 = new Voter("v1");
        Voter vr2 = new Voter("v2");

        // 设定2个投票人的权重
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(vr1, 1.0);
        weightedVoters.put(vr2, 1.0);

        // 设定投票类型
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType voteType = new VoteType(types);

        // 创建候选对象：候选人
        Person p1 = new Person("candidate1", 19);
        Person p2 = new Person("candidate2", 20);
        Person p3 = new Person("candidate3", 21);
        ArrayList<Person> candidates = new ArrayList<>();
        candidates.add(p1);
        candidates.add(p2);
        candidates.add(p3);
        // 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
        VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
        VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
        VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
        Set<VoteItem<Person>> voteItems1 = new HashSet<>();
        voteItems1.add(vi11);
        voteItems1.add(vi12);
        voteItems1.add(vi13);

        VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
        VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
        VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
        Set<VoteItem<Person>> voteItems2 = new HashSet<>();
        voteItems2.add(vi21);
        voteItems2.add(vi22);
        voteItems2.add(vi23);

        // 创建2个投票人vr1、vr2的选票
        Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
        Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));

        // 创建投票活动
        Election poll = new Election();
        // 设定投票基本信息：名称、日期、投票类型、选出的数量
        String name = "代表选举";
        GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
        int quantity = 2;
        poll.setInfo(name, date, voteType, quantity);

        // 增加投票人及其权重
        poll.addVoters(weightedVoters);
        poll.addCandidates(candidates);
        // 增加三个投票人的选票
        poll.addVote(rv1, vr1);
        poll.addVote(rv2, vr2);
        CountProportionVisitor<Person> countProportionVisitor = new CountProportionVisitor<>();
        poll.accept(countProportionVisitor);
        Double res = countProportionVisitor.getData();

        assertEquals(1.0,res);
    }
    @Test
    void acceptTest_BuFenHeFa()
    {
        // 创建2个投票人
        Voter vr1 = new Voter("v1");
        Voter vr2 = new Voter("v2");
        Voter vr3 = new Voter("v3");

        // 设定2个投票人的权重
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(vr1, 1.0);
        weightedVoters.put(vr2, 1.0);
        weightedVoters.put(vr3, 1.0);

        // 设定投票类型
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType voteType = new VoteType(types);

        // 创建候选对象：候选人
        Person p1 = new Person("candidate1", 19);
        Person p2 = new Person("candidate2", 20);
        Person p3 = new Person("candidate3", 21);
        ArrayList<Person> candidates = new ArrayList<>();
        candidates.add(p1);
        candidates.add(p2);
        candidates.add(p3);
        // 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
        VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
        VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
        VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
        Set<VoteItem<Person>> voteItems1 = new HashSet<>();
        voteItems1.add(vi11);
        voteItems1.add(vi12);
        voteItems1.add(vi13);

        VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
        VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
        VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
        Set<VoteItem<Person>> voteItems2 = new HashSet<>();
        voteItems2.add(vi21);
        voteItems2.add(vi22);
        voteItems2.add(vi23);

        VoteItem<Person> vi31 = new VoteItem<>(p1, "Oppose");
        VoteItem<Person> vi32 = new VoteItem<>(p2, "Waive");
        Set<VoteItem<Person>> voteItems3 = new HashSet<>();
        voteItems3.add(vi31);
        voteItems3.add(vi32);

        // 创建2个投票人vr1、vr2的选票
        Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
        Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
        Vote<Person> rv3 = new Vote<Person>(voteItems3, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
        // 创建投票活动
        Election poll = new Election();
        // 设定投票基本信息：名称、日期、投票类型、选出的数量
        String name = "代表选举";
        GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
        int quantity = 2;
        poll.setInfo(name, date, voteType, quantity);

        // 增加投票人及其权重
        poll.addVoters(weightedVoters);
        poll.addCandidates(candidates);
        // 增加三个投票人的选票
        poll.addVote(rv1, vr1);
        poll.addVote(rv2, vr2);
        poll.addVote(rv3, vr3);
        CountProportionVisitor<Person> countProportionVisitor = new CountProportionVisitor<>();
        poll.accept(countProportionVisitor);
        Double res = countProportionVisitor.getData();
        assertEquals(2.0/3.0,res);
    }
}
