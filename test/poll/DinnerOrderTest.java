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
//	 * ���ʵ��ͶƱ��
//	 * ����ʵ��ͶƱ���Զ��ͶƱ��Ƿ������
//	 * statistics����
//	 * 1.Ȩ����ͬ
//	 * 2.Ȩ�ز���ͬ
//	 * selection��ѡ����
//	 * 1.��������ȷ����ǰk��
//	 * 2.�в��ֲ�������ȷ����


	/**
	 * ���ʵ��ͶƱ��
	 * ����ʵ��ͶƱ���Զ��ͶƱ��Ƿ������
	 * ��ѡ����candidate1��candidate2��candidate3
	 * Like=2,Unlike=0,Indifferent=1
	 * ͶƱ��vr1����candidate1-Like����candidate2-Like����candidate3-Unlike
	 * ͶƱ��vr1����candidate1-Unlike����candidate2-Unlike����candidate3-Indifferent
	 * ͶƱ��vr2����candidate1-Unlike����candidate2-Unlike����candidate3-Indifferent
	 */
	@Test
	void RealName_FeiFaTest() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
		RealNameVote<Dish> rv3 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		DinnerOrder poll = new DinnerOrder();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "�۲͵��";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr1);
//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
		DinnerOrderStatisticsStrategy<Dish> dinnerOrderStatisticsStrategy = new DinnerOrderStatisticsStrategy<>();
		try {
			poll.statistics(dinnerOrderStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}

		// ��������ѡ
		DinnerOrderSelectionStrategy<Dish> dinnerOrderSelectionStrategy = new DinnerOrderSelectionStrategy<>();
		poll.selection(dinnerOrderSelectionStrategy);
		// �����ѡ���
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
	 * statistics����
	 * 1.Ȩ����ͬ
	 * * ѡ������ǰ2�ĺ�ѡ����
	 * * ��ѡ����candidate1��candidate2��candidate3
	 * * Like=2,Unlike=0,Indifferent=1
	 * * ͶƱ��vr1��Ȩ��Ϊ1,��candidate1-Like����candidate2-Like����candidate3-Unlike
	 * * ͶƱ��vr2��Ȩ��Ϊ1,��candidate1-Unlike����candidate2-Unlike����candidate3-Indifferent
	 * 2.Ȩ�ز���ͬ
	 * * ѡ������ǰ2�ĺ�ѡ����
	 * * ��ѡ����candidate1��candidate2��candidate3
	 * * Like=2,Unlike=0,Indifferent=1
	 * * ͶƱ��vr1��Ȩ��Ϊ3,��candidate1-Like����candidate2-Like����candidate3-Unlike
	 * * ͶƱ��vr2��Ȩ��Ϊ1,��candidate1-Unlike����candidate2-Unlike����candidate3-Indifferent
	 */
	@Test
	void statisticsTest_XiangTong() {
// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		DinnerOrder poll = new DinnerOrder();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "�۲͵��";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
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
// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		DinnerOrder poll = new DinnerOrder();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "�۲͵��";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
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
	 * selection��ѡ����
	 * ѡ������ǰk�Ĳˣ�����Ϊ�ж���˵÷���ȶ��޷���Ȼ�ų�ǰ
	 * k�����������Щ��ȷ�ɽ���ǰk���Ĳ�֮�⣬�������÷���ȵĲ�����
	 * ��ѡȡһ���֣�����k����
	 *
	 * 1.��������ȷ����ǰk��
	 * * ѡ������ǰ2�ĺ�ѡ����
	 * * ��ѡ����candidate1��candidate2��candidate3
	 * * Like=2,Unlike=0,Indifferent=1
	 * * ͶƱ��vr1��Ȩ��Ϊ3,��candidate1-Like����candidate2-Like����candidate3-Unlike
	 * * ͶƱ��vr2��Ȩ��Ϊ1,��candidate1-Unlike����candidate2-Unlike����candidate3-Indifferent
	 * * ��Ʊ�õ�	candidate1->3*2*1=6
	 * * ��Ʊ�õ�	candidate2->3*2*1=6
	 * * ��Ʊ�õ�	candidate3->1*1*1=1
	 * * ��ѡ�õ�candidate2>1
	 * * ��ѡ�õ�candidate1>2
	 *
	 * 2.�в��ֲ�������ȷ����
	 * ѡ������ǰ2�ĺ�ѡ����
	 * 	 ��ѡ����candidate1��candidate2��candidate3
	 * 	 	Like=2,Unlike=0,Indifferent=1
	 * 	  ͶƱ��vr1��Ȩ��Ϊ3,��candidate1-Like����candidate2-Like����candidate3-like
	 * 	  ͶƱ��vr2��Ȩ��Ϊ1,��candidate1-Unlike����candidate2-Unlike����candidate3-Unlike
	 * 	  ��Ʊ�õ�	candidate1->3*2*1=6
	 * 	  ��Ʊ�õ�	candidate2->3*2*1=6
	 * 	  ��Ʊ�õ�	candidate3->6
	 * 	  �����ѡ�����
	 * 	  Ӧ�õõ�candidate1,candidate2,candidate3��������
	 */
	@Test
	void selectionTest_MingQue() {        // ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		DinnerOrder poll = new DinnerOrder();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "�۲͵��";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
		DinnerOrderStatisticsStrategy<Dish> dinnerOrderStatisticsStrategy = new DinnerOrderStatisticsStrategy<>();
		try {
			poll.statistics(dinnerOrderStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}

		// ��������ѡ
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

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 3.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Like", 2);
		types.put("Unlike", 0);
		types.put("Indifferent", 1);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Dish p1 = new Dish("candidate1", 19);
		Dish p2 = new Dish("candidate2", 20);
		Dish p3 = new Dish("candidate3", 21);
		ArrayList<Dish> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		RealNameVote<Dish> rv1 = new RealNameVote<Dish>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr1);
		RealNameVote<Dish> rv2 = new RealNameVote<Dish>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30), vr2);
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		DinnerOrder poll = new DinnerOrder();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "�۲͵��";
		Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
		DinnerOrderStatisticsStrategy<Dish> dinnerOrderStatisticsStrategy = new DinnerOrderStatisticsStrategy<>();
		try {
			poll.statistics(dinnerOrderStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}

		// ��������ѡ
		DinnerOrderSelectionStrategy<Dish> dinnerOrderSelectionStrategy = new DinnerOrderSelectionStrategy<>();
		poll.selection(dinnerOrderSelectionStrategy);
		HashMap<Dish, Double> shouldBe = new HashMap<>();
		shouldBe.put(p1,2.0);
		shouldBe.put(p2,1.0);
		assertEquals(shouldBe,poll.results);
	}
	
}

