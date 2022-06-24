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
	 * ���ʵ��ͶƱ��
	 * ����ʵ��ͶƱ���Զ��ͶƱ��Ƿ������
	 * 	 * ��ѡ��candidate1
	 * 	 * ͶƱ��vr1,ѡƱ����candidate1
	 * 	 * ͶƱ��vr1,ѡƱ����candidate1
	 */
	@Test
	void RealName_FeiFaTest() {
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

	/**
	 * ����statistics
	 * 1.Ȩ�ز�ȫ��ͬ
	 * 2.Ȩ�ض���ͬ
	 */
	@Test
	void statisticsTest_BuQuanXiangTong() {
		//Ҫ��ʵ��ͶƱ��Vote���붼��RealNameVote
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);
		weightedVoters.put(vr3, 1.0);

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

		VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems3 = new HashSet<>();
		voteItems3.add(vi31);

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date, vr1);
		RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date, vr2);
		RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(voteItems3, date, vr3);

		// ����ͶƱ�

		BusinessVoting poll = new BusinessVoting();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "��ҵ���";
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr3);
		//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
		BusinessVotingStatisticsStrategy<Proposal> businessVotingStatisticsStrategy = new BusinessVotingStatisticsStrategy<>();
		try {
			poll.statistics(businessVotingStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
//			System.out.println("poll = " + poll.getStatistics());
		// ��������ѡ
		BusinessSelectionStrategy<Proposal> businessSelectionStrategy = new BusinessSelectionStrategy<>();
		poll.selection(businessSelectionStrategy);
		// �����ѡ���
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		shouleBe.put(p1,3.0);
		assertEquals(shouleBe,poll.getStatistics());


	}
	@Test
	void statisticsTest_XiangTong() {
		//Ҫ��ʵ��ͶƱ��Vote���붼��RealNameVote
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);
		weightedVoters.put(vr3, 1.0);

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

		VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems3 = new HashSet<>();
		voteItems3.add(vi31);

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date, vr1);
		RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date, vr2);
		RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(voteItems3, date, vr3);

		// ����ͶƱ�

		BusinessVoting poll = new BusinessVoting();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "��ҵ���";
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr3);
		//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
		BusinessVotingStatisticsStrategy<Proposal> businessVotingStatisticsStrategy = new BusinessVotingStatisticsStrategy<>();
		try {
			poll.statistics(businessVotingStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
//			System.out.println("poll = " + poll.getStatistics());
		// ��������ѡ
		BusinessSelectionStrategy<Proposal> businessSelectionStrategy = new BusinessSelectionStrategy<>();
		poll.selection(businessSelectionStrategy);
		// �����ѡ���
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		shouleBe.put(p1,1.0);
		assertEquals(shouleBe,poll.getStatistics());


	}

	/**
	 * selection����
	 * 1.����2/3,ͨ��
	 *  		1����ѡ����
	 * 		��ѡ��candidate1
	 * 	   ͶƱ��vr1��Ȩ��Ϊ3����candidate1-support
	 * 	   ͶƱ��vr2��Ȩ��Ϊ1����candidate1-Oppose
	 * 	   ͶƱ��vr3��Ȩ��Ϊ1����candidate1-Oppose
	 * 	   ��Ʊ�õ�	candidate1->3
	 * 	   �ܹ��Ϸ�ѡƱ����3
	 * 	   ������(3*(2/3))
	 * 	   ��ѡ�õ�candidate1>1
	 * 2.�պõ���2/3,δͨ��
	 *  		1����ѡ����
	 * 		��ѡ��candidate1
	 * 	   ͶƱ��vr1��Ȩ��Ϊ2����candidate1-support
	 * 	   ͶƱ��vr2��Ȩ��Ϊ1����candidate1-Oppose
	 * 	   ͶƱ��vr3��Ȩ��Ϊ1����candidate1-Oppose
	 * 	   ��Ʊ�õ�	candidate1->2
	 * 	   �ܹ��Ϸ�ѡƱ����3
	 * 	   2=(3*(2/3))
	 * 	   ��ѡ�õ� ��
	 * 3.С��2/3,δͨ��
	 *  		1����ѡ����
	 * 		��ѡ��candidate1
	 * 	   ͶƱ��vr1��Ȩ��Ϊ3����candidate1-Oppose
	 * 	   ͶƱ��vr2��Ȩ��Ϊ1����candidate1-Oppose
	 * 	   ͶƱ��vr3��Ȩ��Ϊ1����candidate1-Oppose
	 * 	   ��Ʊ�õ�	candidate1->0
	 * 	   �ܹ��Ϸ�ѡƱ����2
	 * 	   ������(2*(2/3))
	 * 	   ��ѡ�õ� ��
	 */
	@Test
	void selectionTest_Bigger() {
		//Ҫ��ʵ��ͶƱ��Vote���붼��RealNameVote
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);
		weightedVoters.put(vr3, 1.0);

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

		VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems3 = new HashSet<>();
		voteItems3.add(vi31);

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date, vr1);
		RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date, vr2);
		RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(voteItems3, date, vr3);

		// ����ͶƱ�

		BusinessVoting poll = new BusinessVoting();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "��ҵ���";
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr3);
		//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
		BusinessVotingStatisticsStrategy<Proposal> businessVotingStatisticsStrategy = new BusinessVotingStatisticsStrategy<>();
		try {
			poll.statistics(businessVotingStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
//			System.out.println("poll = " + poll.getStatistics());
		// ��������ѡ
		BusinessSelectionStrategy<Proposal> businessSelectionStrategy = new BusinessSelectionStrategy<>();
		poll.selection(businessSelectionStrategy);
		// �����ѡ���
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		shouleBe.put(p1,1.0);
		assertEquals(shouleBe,poll.getResults());
	}
	@Test
	void selectionTest_Equals() {
		//Ҫ��ʵ��ͶƱ��Vote���붼��RealNameVote
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 2.0);
		weightedVoters.put(vr2, 1.0);
		weightedVoters.put(vr3, 1.0);

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

		VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems3 = new HashSet<>();
		voteItems3.add(vi31);

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date, vr1);
		RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date, vr2);
		RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(voteItems3, date, vr3);

		// ����ͶƱ�

		BusinessVoting poll = new BusinessVoting();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "��ҵ���";
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr3);
		// �������Ʊ
		BusinessVotingStatisticsStrategy<Proposal> businessVotingStatisticsStrategy = new BusinessVotingStatisticsStrategy<>();
		try {
			poll.statistics(businessVotingStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
//			System.out.println("poll = " + poll.getStatistics());
		// ��������ѡ
		BusinessSelectionStrategy<Proposal> businessSelectionStrategy = new BusinessSelectionStrategy<>();
		poll.selection(businessSelectionStrategy);
		// �����ѡ���
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		assertEquals(shouleBe,poll.getResults());
	}
	@Test
	void selectionTest_Smaller() {
		//Ҫ��ʵ��ͶƱ��Vote���붼��RealNameVote
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);
		weightedVoters.put(vr3, 1.0);

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
		VoteItem<Proposal> vi11 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems1 = new HashSet<>();
		voteItems1.add(vi11);

		VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Oppose");
		Set<VoteItem<Proposal>> voteItems3 = new HashSet<>();
		voteItems3.add(vi31);

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date, vr1);
		RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date, vr2);
		RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(voteItems3, date, vr3);

		// ����ͶƱ�

		BusinessVoting poll = new BusinessVoting();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "��ҵ���";
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr3);
		//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
		BusinessVotingStatisticsStrategy<Proposal> businessVotingStatisticsStrategy = new BusinessVotingStatisticsStrategy<>();
		try {
			poll.statistics(businessVotingStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
//			System.out.println("poll = " + poll.getStatistics());
		// ��������ѡ
		BusinessSelectionStrategy<Proposal> businessSelectionStrategy = new BusinessSelectionStrategy<>();
		poll.selection(businessSelectionStrategy);
		// �����ѡ���
		HashMap<Proposal, Double> shouleBe = new HashMap<>();
		assertEquals(shouleBe,poll.getResults());
	}
}
