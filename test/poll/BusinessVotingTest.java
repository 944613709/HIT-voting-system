package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import auxiliary.Proposal;
import auxiliary.Proposal;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.BusinessSelectionStrategy;
import pattern.BusinessVotingStatisticsStrategy;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

class BusinessVotingTest {

	// test strategy
	// TODO

	/**
	 * 针对实名投票：
	 * 对于实名投票测试多次投票后非法的情况
	 * 	 * 候选人candidate1
	 * 	 * 投票人vr1,选票里有candidate1
	 * 	 * 投票人vr1,选票里有candidate1
	 */
	@Test
	void RealName_FeiFaTest() {
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		// 创建2个投票人
		Voter vr1 = new Voter("v1");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);

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

		VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Support");
		Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		// 创建投票人vr1、vr1的选票
		RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date,vr1);
		RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date,vr1);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// 创建投票活动
		BusinessVoting poll = new BusinessVoting();
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name = "代表选举";
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// 增加三个投票人的选票
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr1);
		BusinessVotingStatisticsStrategy<Proposal> businessVotingStatisticsStrategy = new BusinessVotingStatisticsStrategy<>();
		try {
			poll.statistics(businessVotingStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}

		Map<Vote<Proposal>, Boolean> voteIsLegal = poll.getVoteIsLegal();
		for (Map.Entry<Vote<Proposal>, Boolean> voteBooleanEntry : voteIsLegal.entrySet()) {
			Boolean isLegal = voteBooleanEntry.getValue();
			Vote<Proposal> vote = voteBooleanEntry.getKey();
			assert vote instanceof RealNameVote;
			RealNameVote<Proposal> realNameVote = (RealNameVote<Proposal>) vote;
			Voter voter = realNameVote.getVoter();
			if (voter.equals(vr1))
				assertEquals(false,isLegal);
		}
	}

	/**
	 * 测试statistics
	 * 1.权重不全相同
	 * 2.权重都相同
	 */
	@Test
	void statisticsTest_BuQuanXiangTong() {
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
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		shouleBe.put(p1,3.0);
		assertEquals(shouleBe,poll.getStatistics());


	}
	@Test
	void statisticsTest_XiangTong() {
		//要求实名投票的Vote必须都是RealNameVote
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
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
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		shouleBe.put(p1,1.0);
		assertEquals(shouleBe,poll.getStatistics());


	}

	/**
	 * selection测试
	 * 1.超过2/3,通过
	 *  		1个候选对象
	 * 		候选人candidate1
	 * 	   投票人vr1，权重为3，对candidate1-support
	 * 	   投票人vr2，权重为1，对candidate1-Oppose
	 * 	   投票人vr3，权重为1，对candidate1-Oppose
	 * 	   计票得到	candidate1->3
	 * 	   总共合法选票数量3
	 * 	   均大于(3*(2/3))
	 * 	   遴选得到candidate1>1
	 * 2.刚好等于2/3,未通过
	 *  		1个候选对象
	 * 		候选人candidate1
	 * 	   投票人vr1，权重为2，对candidate1-support
	 * 	   投票人vr2，权重为1，对candidate1-Oppose
	 * 	   投票人vr3，权重为1，对candidate1-Oppose
	 * 	   计票得到	candidate1->2
	 * 	   总共合法选票数量3
	 * 	   2=(3*(2/3))
	 * 	   遴选得到 空
	 * 3.小于2/3,未通过
	 *  		1个候选对象
	 * 		候选人candidate1
	 * 	   投票人vr1，权重为3，对candidate1-Oppose
	 * 	   投票人vr2，权重为1，对candidate1-Oppose
	 * 	   投票人vr3，权重为1，对candidate1-Oppose
	 * 	   计票得到	candidate1->0
	 * 	   总共合法选票数量2
	 * 	   均大于(2*(2/3))
	 * 	   遴选得到 空
	 */
	@Test
	void selectionTest_Bigger() {
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
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		shouleBe.put(p1,1.0);
		assertEquals(shouleBe,poll.getResults());
	}
	@Test
	void selectionTest_Equals() {
		//要求实名投票的Vote必须都是RealNameVote
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 2.0);
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
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		assertEquals(shouleBe,poll.getResults());
	}
	@Test
	void selectionTest_Smaller() {
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
		VoteItem<Proposal> vi11 = new VoteItem<>(p1, "Oppose");
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
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		assertEquals(shouleBe,poll.getResults());
	}
}
