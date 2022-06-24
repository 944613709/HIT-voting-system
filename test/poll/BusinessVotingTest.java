package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import auxiliary.Proposal;
import auxiliary.Proposal;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
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
	 * 总体测试情况,对于实名投票测试多次投票后非法的情况
	 * 	 * 候选人candidate1
	 * 	 * 投票人vr1,选票里有candidate1
	 * 	 * 投票人vr1,选票里有candidate1
	 */
	@Test
	void test() {
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
}
