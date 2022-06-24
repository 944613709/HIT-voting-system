package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

class ElectionTest {

	// test strategy
	//针对Election特殊情况测试
	// TODO

	/**
	 * 总体正常测试情况1
	 * 选择排名前?的候选人
	 * 候选人candidate1，candidate2，candidate3
	 * 投票人vr1，对candidate1-support，对candidate2-oppose，对candidate3-support
	 * 投票人vr2，对candidate1-Oppose，对candidate2-Waive，对candidate3-Waive
	 */
	@Test
	void test1() {
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
//		System.out.println("poll = " + poll.getVotes());
		// 按规则计票
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}

		// 按规则遴选
		ElectionSelectionStrategy<Person> electionSelectionStrategy = new ElectionSelectionStrategy<>();
		poll.selection(electionSelectionStrategy);
		// 输出遴选结果
		System.out.println(poll.result());


	}

	/**
	 * 总体异常测试情况2
	 * 当若有多个候选人的支持票数量相等而无法自然排出前?名
	 * k=2，应该result只有candidate1
	 * 投票人给candidate1 支持票2
	 * candidate2 支持票1
	 * candidate3 支持票1
	 */
	@Test
	void test2() {
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
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

		VoteItem<Person> vi21 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi22 = new VoteItem<>(p2, "Support");
		VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
		Set<VoteItem<Person>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);
		voteItems2.add(vi22);
		voteItems2.add(vi23);

		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(voteItems1, date);
		Vote<Person> rv2 = new Vote<Person>(voteItems2, date);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		Election poll = new Election();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
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
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
//		System.out.println("poll.getStatistics = " + poll.getStatistics());
		// 按规则遴选
		ElectionSelectionStrategy<Person> electionSelectionStrategy = new ElectionSelectionStrategy<>();
		poll.selection(electionSelectionStrategy);
		// 输出遴选结果
		System.out.println(poll.result());
	}

	/**
	 * Election要求k<=m
	 * 测试k（quantity）>m(candidates.size)的报错情况
	 */
	@Test
	void kBiggerThanmTest() {
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
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		Election poll = new Election();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 5;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
//		poll.addCandidates(candidates);
		//将提示assert报错
	}

	/**
	 * 测试statistics方法
	 * 分为正常情况
	 * 异常情况1：还有人没有投票无法计票
	 * 异常情况2：四种不合法投票（在checkVoteTest以测试）
	 */
	@Test
	void statisticsTest_ZhenChang() {
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
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		votes.add(rv2);
		// 按规则计票
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		HashMap<Person, Double> statistics = new HashMap<>();
		statistics.put(p1, 1.0);
		statistics.put(p3, 1.0);
		assertEquals(statistics, poll.getStatistics());
	}

	/**
	 * 第一种异常情况
	 * 还有人没投票无法计票
	 */
	@Test
	void statisticsTest_YiChang1() {
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

//		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
//		VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
//		VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
//		Set<VoteItem<Person>> voteItems2 = new HashSet<>();
//		voteItems2.add(vi21);
//		voteItems2.add(vi22);
//		voteItems2.add(vi23);

		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
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
//		poll.addVote(rv2, vr2);
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
//		votes.add(rv2);
		// 按规则计票
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			assertEquals("目前无法进行投票", e.getMessage());
		}

	}

	/**
	 * 不合法选票的测试(包括额外的测试)
	 * * 选票不合法情况
	 * 针对Election的额外不合法情况：支持票数量超过最大数量
	 * <p>
	 * 候选人candidate1.candidate2
	 * 投票人vr1,选票里有candidate1
	 * 投票人vr2,选票里有candidate1,candidate3
	 * 投票人vr3,选票里有candidate1,candidate2,但是出现了本次投票不允许的选项值”like"
	 * 投票人vr4,选票里有candidate1,candidate1
	 * 投票人vr5,选票里有candidate1,candidate2，但是都是支持票，支持票数量超过最大数量
	 */
	@Test
	void BuHeFaTest() {
		// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");
		Voter vr4 = new Voter("v4");
		Voter vr5 = new Voter("v5");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);
		weightedVoters.put(vr3, 1.0);
		weightedVoters.put(vr4, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// 创建候选对象：候选人
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 20);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
		Set<VoteItem<Person>> voteItems1 = new HashSet<>();
		voteItems1.add(vi11);

		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
		VoteItem<Person> vi22 = new VoteItem<>(p3, "Waive");
		Set<VoteItem<Person>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);
		voteItems2.add(vi22);

		VoteItem<Person> vi31 = new VoteItem<>(p1, "Oppose");
		VoteItem<Person> vi32 = new VoteItem<>(p2, "like");
		Set<VoteItem<Person>> voteItems3 = new HashSet<>();
		voteItems3.add(vi31);
		voteItems3.add(vi32);

		VoteItem<Person> vi41 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi42 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Person>> voteItems4 = new HashSet<>();
		voteItems4.add(vi41);
		voteItems4.add(vi42);

		VoteItem<Person> vi51 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi52 = new VoteItem<>(p1, "Support");
		Set<VoteItem<Person>> voteItems5 = new HashSet<>();
		voteItems5.add(vi51);
		voteItems5.add(vi52);

		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv3 = new Vote<Person>(voteItems3, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv4 = new Vote<Person>(voteItems4, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv5 = new Vote<Person>(voteItems5, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		Election poll = new Election();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr3);
		poll.addVote(rv4, vr4);
		poll.addVote(rv5, vr5);
		Map<Vote<Person>, Boolean> voteIsLegal = poll.getVoteIsLegal();
		for (Map.Entry<Vote<Person>, Boolean> voteBooleanEntry : voteIsLegal.entrySet()) {
			Boolean isLegal = voteBooleanEntry.getValue();
			assertEquals(false, isLegal);
		}

	}

	/**
	 * 测试selection
	 * 测试策略对比selection得到的result
	 * 1，测试一般情况
	 * * 选择排名前?的候选人
	 * * 候选人candidate1，candidate2，candidate3
	 * * 投票人vr1，对candidate1-support，对candidate2-oppose，对candidate3-support
	 * * 投票人vr2，对candidate1-Oppose，对candidate2-Waive，对candidate3-Waive
	 */
	@Test
	void selectionTest_1() {
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
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		Poll<Person> originPoll = Poll.create();
		Election poll = (Election) originPoll;
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
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		votes.add(rv2);
		// 按规则计票
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		// 按规则遴选
		ElectionSelectionStrategy<Person> electionSelectionStrategy = new ElectionSelectionStrategy<>();
		poll.selection(electionSelectionStrategy);
		HashMap<Person, Double> result = new HashMap<>();
		result.put(p1, 1.0);
		result.put(p3, 2.0);
		assertEquals(result, poll.getResults());
	}

	/**
	 * 测试selection
	 * 当若有多个候选人的支持票数量相等而无法自然排出前?名
	 * k=2，应该result只有candidate1
	 * 投票人给candidate1 支持票2
	 * candidate2 支持票1
	 * candidate3 支持票1
	 */
	@Test
	void selectionTest_2() {
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
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

		VoteItem<Person> vi21 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi22 = new VoteItem<>(p2, "Support");
		VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
		Set<VoteItem<Person>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);
		voteItems2.add(vi22);
		voteItems2.add(vi23);

		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(voteItems1, date);
		Vote<Person> rv2 = new Vote<Person>(voteItems2, date);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		Election poll = new Election();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		votes.add(rv2);
		// 按规则计票
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		// 按规则遴选
		ElectionSelectionStrategy<Person> electionSelectionStrategy = new ElectionSelectionStrategy<>();
		poll.selection(electionSelectionStrategy);
		HashMap<Person, Double> result = new HashMap<>();
		result.put(p1, 1.0);
		assertEquals(result, poll.getResults());
	}
}