package app;

import auxiliary.Dish;
import auxiliary.Dish;
import auxiliary.Proposal;
import auxiliary.Voter;
import pattern.*;
import poll.BusinessVoting;
import poll.CanNotVoteException;
import poll.DinnerOrder;
import poll.Election;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;
public class DinnerOrderApp {
	/**
	 * 选择排名前2的候选对象
	 * 候选对象candidate1，candidate2，candidate3
	 * Like=2,Unlike=0,Indifferent=1
	 * 投票对象vr1，权重为3,对candidate1-Like，对candidate2-Like，对candidate3-Unlike
	 * 投票对象vr2，权重为1,对candidate1-Unlike，对candidate2-Unlike，对candidate3-Indifferent
	 * 计票得到	candidate1->3*2*1=6
	 * 计票得到	candidate2->3*2*1=6
	 * 计票得到	candidate3->1*1*1=1
	 * 遴选得到candidate2>1
	 * 遴选得到candidate1>2
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		// 创建2个投票对象
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票对象的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// 创建候选对象：候选对象
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// 创建投票项，前三个是投票对象vr1对三个候选对象的投票项，后三个是vr2的投票项
		VoteItem<Dish> vi11 = new VoteItem<>(p1, "Like");
		VoteItem<Dish> vi12 = new VoteItem<>(p2, "Like");
		VoteItem<Dish> vi13 = new VoteItem<>(p3, "Unlike");
		Set<VoteItem<Dish>> voteItems1 = new HashSet<>();
		voteItems1.add(vi11);
		voteItems1.add(vi12);
		voteItems1.add(vi13);

		VoteItem<Dish> vi21 = new VoteItem<>(p1, "Unlike");
		VoteItem<Dish> vi22 = new VoteItem<>(p2, "Unlike");
		VoteItem<Dish> vi23 = new VoteItem<>(p3, "Indifferent");
		Set<VoteItem<Dish>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);
		voteItems2.add(vi22);
		voteItems2.add(vi23);

		// 创建2个投票对象vr1、vr2的选票
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30),vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30),vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		DinnerOrder poll = new DinnerOrder();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "聚餐点菜";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票对象及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票对象的选票
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
//		System.out.println("poll = " + poll.getVotes());
		// 按规则计票
		DinnerOrderStatisticsStrategy<Dish> dinnerOrderStatisticsStrategy = new DinnerOrderStatisticsStrategy<>();
		try {
			poll.statistics(dinnerOrderStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}

		// 按规则遴选
		DinnerOrderSelectionStrategy<Dish> dinnerOrderSelectionStrategy = new DinnerOrderSelectionStrategy<>();
		poll.selection(dinnerOrderSelectionStrategy);
		// 输出遴选结果
		System.out.println("本次投票类型"+poll.getClass());
		System.out.println("本次投票具体信息" +poll);
		System.out.println("统计每个菜品的总得分（加权求和）,输出计票结果");
		System.out.println(poll.getStatistics());
		System.out.println("选择排名前k的菜，若因为有多道菜得分相等而无法自然排出前" +
				"k名，则除了那些明确可进入前k名的菜之外，在其他得分相等的菜中随\n" +
				"机选取一部分，凑足k个菜,输出遴选结果");
		System.out.println(poll.result());

	}
}
