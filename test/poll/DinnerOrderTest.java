package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Dish;
import auxiliary.Dish;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.BusinessVotingStatisticsStrategy;
import pattern.DinnerOrderSelectionStrategy;
import pattern.DinnerOrderStatisticsStrategy;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

class DinnerOrderTest {

	// test strategy
//	 * 针对实名投票：
//	 * 对于实名投票测试多次投票后非法的情况
//	 * statistics测试
//	 * 1.权重相同
//	 * 2.权重不相同
//	 * selection遴选测试
//	 * 1.都可以明确进入前k名
//	 * 2.有部分不可以明确进入


	/**
	 * 针对实名投票：
	 * 对于实名投票测试多次投票后非法的情况
	 * 候选对象candidate1，candidate2，candidate3
	 * Like=2,Unlike=0,Indifferent=1
	 * 投票人vr1，对candidate1-Like，对candidate2-Like，对candidate3-Unlike
	 * 投票人vr1，对candidate1-Unlike，对candidate2-Unlike，对candidate3-Indifferent
	 * 投票人vr2，对candidate1-Unlike，对candidate2-Unlike，对candidate3-Indifferent
	 */
	@Test
	void RealName_FeiFaTest() {
		// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// 创建候选对象：候选人
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
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

		VoteItem<Dish> vi31 = new VoteItem<>(p1, "Unlike");
		VoteItem<Dish> vi32 = new VoteItem<>(p2, "Unlike");
		VoteItem<Dish> vi33 = new VoteItem<>(p3, "Indifferent");
		Set<VoteItem<Dish>> voteItems3 = new HashSet<>();
		voteItems3.add(vi31);
		voteItems3.add(vi32);
		voteItems3.add(vi33);

		// 创建2个投票人vr1、vr2的选票
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
		RealNameVote<Dish> rv3 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		DinnerOrder poll = new DinnerOrder();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "聚餐点菜";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr1);
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
		Map<Vote<Dish>, Boolean> voteIsLegal = poll.getVoteIsLegal();
		for (Map.Entry<Vote<Dish>, Boolean> voteBooleanEntry : voteIsLegal.entrySet()) {
			Boolean isLegal = voteBooleanEntry.getValue();
			Vote<Dish> vote = voteBooleanEntry.getKey();
			assert vote instanceof RealNameVote;
			RealNameVote<Dish> realNameVote = (RealNameVote<Dish>) vote;
			Voter voter = realNameVote.getVoter();
			if (voter.equals(vr1))
				assertEquals(false, isLegal);
		}
	}

	/**
	 * statistics测试
	 * 1.权重相同
	 * * 选择排名前2的候选对象
	 * * 候选对象candidate1，candidate2，candidate3
	 * * Like=2,Unlike=0,Indifferent=1
	 * * 投票人vr1，权重为1,对candidate1-Like，对candidate2-Like，对candidate3-Unlike
	 * * 投票人vr2，权重为1,对candidate1-Unlike，对candidate2-Unlike，对candidate3-Indifferent
	 * 2.权重不相同
	 * * 选择排名前2的候选对象
	 * * 候选对象candidate1，candidate2，candidate3
	 * * Like=2,Unlike=0,Indifferent=1
	 * * 投票人vr1，权重为3,对candidate1-Like，对candidate2-Like，对candidate3-Unlike
	 * * 投票人vr2，权重为1,对candidate1-Unlike，对candidate2-Unlike，对candidate3-Indifferent
	 */
	@Test
	void statisticsTest_XiangTong() {
// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// 创建候选对象：候选人
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
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

		// 创建2个投票人vr1、vr2的选票
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		DinnerOrder poll = new DinnerOrder();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "聚餐点菜";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
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
		HashMap<Dish, Double> shouldBe = new HashMap<>();
		shouldBe.put(p1, 2.0);
		shouldBe.put(p2, 2.0);
		shouldBe.put(p3, 1.0);
		assertEquals(shouldBe, poll.statistics);
	}

	@Test
	void statisticsTest_BuXiangTong() {
// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// 创建候选对象：候选人
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
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

		// 创建2个投票人vr1、vr2的选票
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		DinnerOrder poll = new DinnerOrder();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "聚餐点菜";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
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
		HashMap<Dish, Double> shouldBe = new HashMap<>();
		shouldBe.put(p1, 6.0);
		shouldBe.put(p2, 6.0);
		shouldBe.put(p3, 1.0);
		assertEquals(shouldBe, poll.statistics);

	}

	/**
	 * selection遴选测试
	 * 选择排名前k的菜，若因为有多道菜得分相等而无法自然排出前
	 * k名，则除了那些明确可进入前k名的菜之外，在其他得分相等的菜中随
	 * 机选取一部分，凑足k个菜
	 *
	 * 1.都可以明确进入前k名
	 * * 选择排名前2的候选对象
	 * * 候选对象candidate1，candidate2，candidate3
	 * * Like=2,Unlike=0,Indifferent=1
	 * * 投票人vr1，权重为3,对candidate1-Like，对candidate2-Like，对candidate3-Unlike
	 * * 投票人vr2，权重为1,对candidate1-Unlike，对candidate2-Unlike，对candidate3-Indifferent
	 * * 计票得到	candidate1->3*2*1=6
	 * * 计票得到	candidate2->3*2*1=6
	 * * 计票得到	candidate3->1*1*1=1
	 * * 遴选得到candidate2>1
	 * * 遴选得到candidate1>2
	 *
	 * 2.有部分不可以明确进入
	 * 选择排名前2的候选对象
	 * 	 候选对象candidate1，candidate2，candidate3
	 * 	 	Like=2,Unlike=0,Indifferent=1
	 * 	  投票人vr1，权重为3,对candidate1-Like，对candidate2-Like，对candidate3-like
	 * 	  投票人vr2，权重为1,对candidate1-Unlike，对candidate2-Unlike，对candidate3-Unlike
	 * 	  计票得到	candidate1->3*2*1=6
	 * 	  计票得到	candidate2->3*2*1=6
	 * 	  计票得到	candidate3->6
	 * 	  随机遴选结果！
	 * 	  应该得到candidate1,candidate2,candidate3其中两个
	 */
	@Test
	void selectionTest_MingQue() {        // 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// 创建候选对象：候选人
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
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

		// 创建2个投票人vr1、vr2的选票
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		DinnerOrder poll = new DinnerOrder();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "聚餐点菜";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
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
		HashMap<Dish, Double> shouldBe = new HashMap<>();
		shouldBe.put(p1,2.0);
		shouldBe.put(p2,1.0);
		assertEquals(shouldBe,poll.results);
	}
	@Test
	void selectionTest_BuMingQue()
	{
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// 创建候选对象：候选人
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
		VoteItem<Dish> vi11 = new VoteItem<>(p1, "Like");
		VoteItem<Dish> vi12 = new VoteItem<>(p2, "Like");
		VoteItem<Dish> vi13 = new VoteItem<>(p3, "like");
		Set<VoteItem<Dish>> voteItems1 = new HashSet<>();
		voteItems1.add(vi11);
		voteItems1.add(vi12);
		voteItems1.add(vi13);

		VoteItem<Dish> vi21 = new VoteItem<>(p1, "Unlike");
		VoteItem<Dish> vi22 = new VoteItem<>(p2, "Unlike");
		VoteItem<Dish> vi23 = new VoteItem<>(p3, "Unlike");
		Set<VoteItem<Dish>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);
		voteItems2.add(vi22);
		voteItems2.add(vi23);

		// 创建2个投票人vr1、vr2的选票
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		DinnerOrder poll = new DinnerOrder();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "聚餐点菜";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
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
		HashMap<Dish, Double> shouldBe = new HashMap<>();
		shouldBe.put(p1,2.0);
		shouldBe.put(p2,1.0);
		assertEquals(shouldBe,poll.results);
	}
	
}

