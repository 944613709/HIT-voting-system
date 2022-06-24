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
	 * ����������,����ʵ��ͶƱ���Զ��ͶƱ��Ƿ������
	 * 	 * ��ѡ��candidate1
	 * 	 * ͶƱ��vr1,ѡƱ����candidate1
	 * 	 * ͶƱ��vr1,ѡƱ����candidate1
	 */
	@Test
	void test() {
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Proposal p1 = new Proposal("candidate1", date);
		ArrayList<Proposal> candidates = new ArrayList<>();
		candidates.add(p1);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
		VoteItem<Proposal> vi11 = new VoteItem<>(p1, "Support");
		Set<VoteItem<Proposal>> voteItems1 = new HashSet<>();
		voteItems1.add(vi11);

		VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Support");
		Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		// ����ͶƱ��vr1��vr1��ѡƱ
		RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date,vr1);
		RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date,vr1);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		BusinessVoting poll = new BusinessVoting();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
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
