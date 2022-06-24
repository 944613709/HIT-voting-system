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

class PollTest {
	
	// test strategy
	// TODO
	@Test
	void createTest() {
		fail("Not yet implemented");
	}
	@Test
	void setInfoTest() {
		//TODO
//		Poll<Object> poll = Poll.create();
//		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
//
//		HashMap<String,Integer> optionsVoteType = new HashMap<>();
//		optionsVoteType.put("喜欢",2);
//		optionsVoteType.put("不喜欢",0);
//		optionsVoteType.put("无所谓",1);
//		VoteType voteType = new VoteType(optionsVoteType);
//		poll.setInfo("ElectionPoll",date,voteType,1);
	}
	@Test
	void addCandidatesTest() {
		fail("Not yet implemented");
	}@Test
	void addVotersTest() {
		fail("Not yet implemented");
	}@Test
	void addVoteTest() {
		fail("Not yet implemented");
	}@Test
	void statisticsTest() {
		fail("Not yet implemented");
	}@Test
	void selectionTest() {
		fail("Not yet implemented");
	}@Test
	void resultTest() {
		fail("Not yet implemented");
	}

	/**
	 * 四种共用不合法选票的测试
	 * * 选票不合法情况
	 * * ? 一张选票中没有包含本次投票活动中的所有候选人
	 * * ? 一张选票中包含了不在本次投票活动中的候选人
	 * * ? 一张选票中出现了本次投票不允许的选项值
	 * * ? 一张选票中有对同一个候选对象的多次投票
	 *
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
		Poll<Person> poll = Poll.create();
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
		Election election = (Election) poll;
		Map<Vote<Person>, Boolean> voteIsLegal = election.getVoteIsLegal();
		for (Map.Entry<Vote<Person>, Boolean> voteBooleanEntry : voteIsLegal.entrySet()) {
			Boolean isLegal = voteBooleanEntry.getValue();
			assertEquals(false,isLegal);
		}

	}

}
