package app;

import java.util.*;

import auxiliary.Person;
import auxiliary.Voter;
import pattern.ElectionStatisticsStrategy;
import poll.CanNotVoteException;
import poll.Election;
import poll.Poll;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

public class ElectionApp {

	public static void main(String[] args) {

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
		Person p1 = new Person("ABC", 19);
		Person p2 = new Person("DEF", 20);
		Person p3 = new Person("GHI", 21);

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
		voteItems2.add(vi11);
		voteItems2.add(vi12);
		voteItems2.add(vi13);

		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(voteItems1,new GregorianCalendar(2019, 6, 14, 16, 15,30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2,new GregorianCalendar(2019, 6, 14, 16, 15,30));

		// 创建投票活动
		Poll<Person> poll = new Election();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name,date,voteType,quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);

		// 增加三个投票人的选票
		poll.addVote(rv1,vr1);
		poll.addVote(rv2,vr2);

		// 按规则计票
		ElectionStatisticsStrategy<Object> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		// 按规则遴选
		poll.selection(/* TODO */);
		// 输出遴选结果
		System.out.println(poll.result());
	}

}
