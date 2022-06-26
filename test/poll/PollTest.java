package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.CountProportionVisitor;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

class PollTest {

	// test strategy
	//总体假定条件（如无提示修改就默认）以下
	//  候选人candidate1，candidate2，candidate3
	//	投票人vr1，对candidate1-support，对candidate2-oppose，对candidate3-support
	//	投票人vr2，对candidate1-Oppose，对candidate2-Waive，对candidate3-Waive
	//
//	测试poll.create
//	  以GeneralPollImpl为实现类
//
//	  测试setInfo
//	  测试设定之后的name, date, voteType, quantity
//
//	 addVoter的测试
//	  1.权重都相等
//	  2.权重不都相等
//	  	1.候选人vr1，vr2，权重都为1
//	  	2.候选人vr1，vr2，权重为1，权重为2
//
//	 addCandidate的测试
//	  分类：
//	  1.单个候选人，候选人candidate1
//	  2.多个候选人，候选人candidate1，candidate2，candidate3
//
//    checkVote测试
//	  四种共用不合法选票的测试
//	  选票不合法情况:
//	  一张选票中没有包含本次投票活动中的所有候选人
//	  一张选票中包含了不在本次投票活动中的候选人
//	  一张选票中出现了本次投票不允许的选项值
//	  一张选票中有对同一个候选对象的多次投票
//
//	 addVote测试
//	  以2个投票人为例
//	  1.只有单个投票（后续报错无法计票）。
//	  2.多个投票
//
//	  测试statistics方法
//	  分为正常情况
//	  异常情况1：还有人没有投票无法计票
//	  异常情况2：四种不合法投票（在checkVoteTest以测试）
//
//	 测试selection
//	  测试策略对比selection得到的result
//	  以Election为实现类代表测试
//
//	 测试result
//	  测试策略对比result得到的String形式
//
//	 * 测试accept方法,访问者Visitor模式
//	 *1.全部合法
//	  2.部分合法
//	 */
//	 getVotersVoteFrequencies测试
//	  1.投票次数为1
//	  2.投票次数>1(=2)
//	  3.投票次数<1(=0)



	/**
	 * 测试poll.create
	 * 以Election为实现类
	 */
	@Test
	void createTest() {
		Poll<Person> poll = Poll.create();
		GeneralPollImpl<Person> election = new GeneralPollImpl<Person>();
		assertEquals(election, poll);
		;
	}

	/**
	 * 测试setInfo
	 * 测试设定之后的name, date, voteType, quantity
	 */
	@Test
	void setInfoTest() {
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

		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		assertEquals(name, poll.getName());
		assertEquals(date, poll.getDate());
		assertEquals(voteType, poll.getVoteType());
		assertEquals(quantity, poll.getQuantity());
	}

	/**
	 *addVoter的测试
	 * 1.权重都相等
	 * 2.权重不都相等
	 * 	1.候选人vr1，vr2，权重都为1
	 * 	2.候选人vr1，vr2，权重为1，权重为2
	 */
	@Test
	void addVotersTest_1() {
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
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		assertEquals(weightedVoters, poll.getVoters());
	}
	@Test
	void addVotersTest_2() {
		// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 2.0);

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
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		assertEquals(weightedVoters, poll.getVoters());
	}
	/**
	 *addCandidate的测试
	 * 分类：
	 * 1.单个候选人，候选人candidate1
	 * 2.多个候选人，候选人candidate1，candidate2，candidate3
	 */
	@Test
	void addCandidatesTest_1() {
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
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);

		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");

		Set<VoteItem<Person>> voteItems1 = new HashSet<>();
		voteItems1.add(vi11);

		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");

		Set<VoteItem<Person>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);
		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		assertEquals(candidates, poll.getCandidates());

	}
	@Test
	void addCandidatesTest_2() {
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
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		assertEquals(candidates, poll.getCandidates());

	}

	/**
	 * 是statisticTest的检测第二种异常情况：
	 * (也是checkVote的测试）
	 * 四种共用不合法选票的测试
	 * * 选票不合法情况
	 * * ? 一张选票中没有包含本次投票活动中的所有候选人
	 * * ? 一张选票中包含了不在本次投票活动中的候选人
	 * * ? 一张选票中出现了本次投票不允许的选项值
	 * * ? 一张选票中有对同一个候选对象的多次投票
	 * 具体测试样例:
	 * 候选人candidate1.candidate2
	 * 投票人vr1,选票里有candidate1
	 * 投票人vr2,选票里有candidate1,candidate3
	 * 投票人vr3,选票里有candidate1,candidate2,但是出现了本次投票不允许的选项值”like"
	 * 投票人vr4,选票里有candidate1,candidate1
	 */
	@Test
	void checkVote_statistics_BuHeFaTest() {
		// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");
		Voter vr4 = new Voter("v4");

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

		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv3 = new Vote<Person>(voteItems3, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv4 = new Vote<Person>(voteItems4, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
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
		GeneralPollImpl election =  poll;
		Map<Vote<Person>, Boolean> voteIsLegal = election.getVoteIsLegal();
		for (Map.Entry<Vote<Person>, Boolean> voteBooleanEntry : voteIsLegal.entrySet()) {
			Boolean isLegal = voteBooleanEntry.getValue();
			assertEquals(false, isLegal);
		}
	}

	/**
	 * addVote测试
	 * 以2个投票人为例
	 * 1.只有单个投票（后续报错无法计票）
	 * 投票人vr1，对candidate1-support，对candidate2-oppose，对candidate3-support。
	 * 2.多个投票
	 * 	投票人vr1，对candidate1-support，对candidate2-oppose，对candidate3-support。投票人vr2，对candidate1-Oppose，对candidate2-Waive，对candidate3-Waive
	 */
	@Test
	void addVoteTest_DanGe() {
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


		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
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
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		assertEquals(votes, poll.getVotes());
	}
	@Test
	void addVoteTest_DuoGe() {
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
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
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
		assertEquals(votes, poll.getVotes());
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
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
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
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
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
	 *测试selection
	 * 测试策略对比selection得到的result
	 * 以Election为实现类代表测试
	 */
	@Test
	void selectionTest() {
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
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
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
	 *测试result
	 * 测试策略对比result得到的String形式
	 */
	@Test
	void resultTest() {
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
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
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
		assertEquals("候选对象:candidate1 排名:1\n" +
				"候选对象:candidate3 排名:2\n", poll.result());
	}

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
	/**
	 * getVotersVoteFrequencies测试
	 * 1.投票次数为1
	 * 2.投票次数>1(=2)
	 */
	@Test
	void getVotersVoteFrequenciesTest()
	{
		/**
		 * 	 * 选择排名前2的候选人
		 * 	 * 候选人candidate1，candidate2，candidate3
		 * 	 * 投票人vr1，对candidate1-support，对candidate2-oppose，对candidate3-support
		 * 	 * 投票人vr2，对candidate1-Oppose，对candidate2-Waive，对candidate3-Waive
		 * 	 * 投票人vr2，对candidate1-Oppose，对candidate2-Waive，对candidate3-Waive
		*/
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

		VoteItem<Person> vi31 = new VoteItem<>(p1, "Oppose");
		VoteItem<Person> vi32 = new VoteItem<>(p2, "Waive");
		VoteItem<Person> vi33 = new VoteItem<>(p3, "Waive");
		Set<VoteItem<Person>> voteItems3 = new HashSet<>();
		voteItems3.add(vi31);
		voteItems3.add(vi32);
		voteItems3.add(vi33);

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
		poll.addVote(rv3, vr2);
//		System.out.println("poll = " + poll.getVotes());
		// 按规则计票
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		assertEquals(1,poll.getVotersVoteFrequencies().get(vr1));
		assertEquals(2,poll.getVotersVoteFrequencies().get(vr2));
	}
}



