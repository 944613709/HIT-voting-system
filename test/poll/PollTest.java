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
	//����ٶ�������������ʾ�޸ľ�Ĭ�ϣ�����
	//  ��ѡ��candidate1��candidate2��candidate3
	//	ͶƱ��vr1����candidate1-support����candidate2-oppose����candidate3-support
	//	ͶƱ��vr2����candidate1-Oppose����candidate2-Waive����candidate3-Waive
	//
//	����poll.create
//	  ��GeneralPollImplΪʵ����
//
//	  ����setInfo
//	  �����趨֮���name, date, voteType, quantity
//
//	 addVoter�Ĳ���
//	  1.Ȩ�ض����
//	  2.Ȩ�ز������
//	  	1.��ѡ��vr1��vr2��Ȩ�ض�Ϊ1
//	  	2.��ѡ��vr1��vr2��Ȩ��Ϊ1��Ȩ��Ϊ2
//
//	 addCandidate�Ĳ���
//	  ���ࣺ
//	  1.������ѡ�ˣ���ѡ��candidate1
//	  2.�����ѡ�ˣ���ѡ��candidate1��candidate2��candidate3
//
//    checkVote����
//	  ���ֹ��ò��Ϸ�ѡƱ�Ĳ���
//	  ѡƱ���Ϸ����:
//	  һ��ѡƱ��û�а�������ͶƱ��е����к�ѡ��
//	  һ��ѡƱ�а����˲��ڱ���ͶƱ��еĺ�ѡ��
//	  һ��ѡƱ�г����˱���ͶƱ�������ѡ��ֵ
//	  һ��ѡƱ���ж�ͬһ����ѡ����Ķ��ͶƱ
//
//	 addVote����
//	  ��2��ͶƱ��Ϊ��
//	  1.ֻ�е���ͶƱ�����������޷���Ʊ����
//	  2.���ͶƱ
//
//	  ����statistics����
//	  ��Ϊ�������
//	  �쳣���1��������û��ͶƱ�޷���Ʊ
//	  �쳣���2�����ֲ��Ϸ�ͶƱ����checkVoteTest�Բ��ԣ�
//
//	 ����selection
//	  ���Բ��ԶԱ�selection�õ���result
//	  ��ElectionΪʵ����������
//
//	 ����result
//	  ���Բ��ԶԱ�result�õ���String��ʽ
//
//	 * ����accept����,������Visitorģʽ
//	 *1.ȫ���Ϸ�
//	  2.���ֺϷ�
//	 */
//	 getVotersVoteFrequencies����
//	  1.ͶƱ����Ϊ1
//	  2.ͶƱ����>1(=2)
//	  3.ͶƱ����<1(=0)



	/**
	 * ����poll.create
	 * ��ElectionΪʵ����
	 */
	@Test
	void createTest() {
		Poll<Person> poll = Poll.create();
		GeneralPollImpl<Person> election = new GeneralPollImpl<Person>();
		assertEquals(election, poll);
		;
	}

	/**
	 * ����setInfo
	 * �����趨֮���name, date, voteType, quantity
	 */
	@Test
	void setInfoTest() {
// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�

		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		assertEquals(name, poll.getName());
		assertEquals(date, poll.getDate());
		assertEquals(voteType, poll.getVoteType());
		assertEquals(quantity, poll.getQuantity());
	}

	/**
	 *addVoter�Ĳ���
	 * 1.Ȩ�ض����
	 * 2.Ȩ�ز������
	 * 	1.��ѡ��vr1��vr2��Ȩ�ض�Ϊ1
	 * 	2.��ѡ��vr1��vr2��Ȩ��Ϊ1��Ȩ��Ϊ2
	 */
	@Test
	void addVotersTest_1() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		assertEquals(weightedVoters, poll.getVoters());
	}
	@Test
	void addVotersTest_2() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 2.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		assertEquals(weightedVoters, poll.getVoters());
	}
	/**
	 *addCandidate�Ĳ���
	 * ���ࣺ
	 * 1.������ѡ�ˣ���ѡ��candidate1
	 * 2.�����ѡ�ˣ���ѡ��candidate1��candidate2��candidate3
	 */
	@Test
	void addCandidatesTest_1() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);

		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");

		Set<VoteItem<Person>> voteItems1 = new HashSet<>();
		voteItems1.add(vi11);

		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");

		Set<VoteItem<Person>> voteItems2 = new HashSet<>();
		voteItems2.add(vi21);

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		assertEquals(candidates, poll.getCandidates());

	}
	@Test
	void addCandidatesTest_2() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		assertEquals(candidates, poll.getCandidates());

	}

	/**
	 * ��statisticTest�ļ��ڶ����쳣�����
	 * (Ҳ��checkVote�Ĳ��ԣ�
	 * ���ֹ��ò��Ϸ�ѡƱ�Ĳ���
	 * * ѡƱ���Ϸ����
	 * * ? һ��ѡƱ��û�а�������ͶƱ��е����к�ѡ��
	 * * ? һ��ѡƱ�а����˲��ڱ���ͶƱ��еĺ�ѡ��
	 * * ? һ��ѡƱ�г����˱���ͶƱ�������ѡ��ֵ
	 * * ? һ��ѡƱ���ж�ͬһ����ѡ����Ķ��ͶƱ
	 * �����������:
	 * ��ѡ��candidate1.candidate2
	 * ͶƱ��vr1,ѡƱ����candidate1
	 * ͶƱ��vr2,ѡƱ����candidate1,candidate3
	 * ͶƱ��vr3,ѡƱ����candidate1,candidate2,���ǳ����˱���ͶƱ�������ѡ��ֵ��like"
	 * ͶƱ��vr4,ѡƱ����candidate1,candidate1
	 */
	@Test
	void checkVote_statistics_BuHeFaTest() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");
		Voter vr3 = new Voter("v3");
		Voter vr4 = new Voter("v4");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);
		weightedVoters.put(vr3, 1.0);
		weightedVoters.put(vr4, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 20);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv3 = new Vote<Person>(voteItems3, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv4 = new Vote<Person>(voteItems4, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 1;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
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
	 * addVote����
	 * ��2��ͶƱ��Ϊ��
	 * 1.ֻ�е���ͶƱ�����������޷���Ʊ��
	 * ͶƱ��vr1����candidate1-support����candidate2-oppose����candidate3-support��
	 * 2.���ͶƱ
	 * 	ͶƱ��vr1����candidate1-support����candidate2-oppose����candidate3-support��ͶƱ��vr2����candidate1-Oppose����candidate2-Waive����candidate3-Waive
	 */
	@Test
	void addVoteTest_DanGe() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
		VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
		Set<VoteItem<Person>> voteItems1 = new HashSet<>();
		voteItems1.add(vi11);
		voteItems1.add(vi12);
		voteItems1.add(vi13);


		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		assertEquals(votes, poll.getVotes());
	}
	@Test
	void addVoteTest_DuoGe() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		votes.add(rv2);
		assertEquals(votes, poll.getVotes());
	}

	/**
	 * ����statistics����
	 * ��Ϊ�������
	 * �쳣���1��������û��ͶƱ�޷���Ʊ
	 * �쳣���2�����ֲ��Ϸ�ͶƱ����checkVoteTest�Բ��ԣ�
	 */
	@Test
	void statisticsTest_ZhenChang() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		votes.add(rv2);
		// �������Ʊ
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
	 * ��һ���쳣���
	 * ������ûͶƱ�޷���Ʊ
	 */
	@Test
	void statisticsTest_YiChang1() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
//		poll.addVote(rv2, vr2);
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
//		votes.add(rv2);
		// �������Ʊ
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			assertEquals("Ŀǰ�޷�����ͶƱ", e.getMessage());
		}

	}


	/**
	 *����selection
	 * ���Բ��ԶԱ�selection�õ���result
	 * ��ElectionΪʵ����������
	 */
	@Test
	void selectionTest() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		votes.add(rv2);
		// �������Ʊ
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		// ��������ѡ
		ElectionSelectionStrategy<Person> electionSelectionStrategy = new ElectionSelectionStrategy<>();
		poll.selection(electionSelectionStrategy);
		HashMap<Person, Double> result = new HashMap<>();
		result.put(p1, 1.0);
		result.put(p3, 2.0);
		assertEquals(result, poll.getResults());
	}

	/**
	 *����result
	 * ���Բ��ԶԱ�result�õ���String��ʽ
	 */
	@Test
	void resultTest() {
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		GeneralPollImpl<Person> poll= new GeneralPollImpl<Person>();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		HashSet<Vote<Person>> votes = new HashSet<>();
		votes.add(rv1);
		votes.add(rv2);
		// �������Ʊ
		ElectionStatisticsStrategy<Person> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		// ��������ѡ
		ElectionSelectionStrategy<Person> electionSelectionStrategy = new ElectionSelectionStrategy<>();
		poll.selection(electionSelectionStrategy);
		assertEquals("��ѡ����:candidate1 ����:1\n" +
				"��ѡ����:candidate3 ����:2\n", poll.result());
	}

	/**
	 * ��������11����ADT ��������� Visitor ���ģʽ��
	 * ����Ϸ�ѡƱ������ѡƱ����ռ����
	 * ����accept����
	 *1.ȫ���Ϸ�
	 * 	 * 	  ѡ������ǰ2�ĺ�ѡ��
	 * 	 * 	  ��ѡ��candidate1��candidate2��candidate3
	 * 	 * 	  ͶƱ��vr1����candidate1-support����candidate2-oppose����candidate3-support
	 * 	 * 	  ͶƱ��vr2����candidate1-Oppose����candidate2-Waive����candidate3-Waive
	 * 	 ����2/2
	 * 2.���ֺϷ�
	 * 	  ѡ������ǰ2�ĺ�ѡ��
	 * 	  ��ѡ��candidate1��candidate2��candidate3
	 * 	  ͶƱ��vr1����candidate1-support����candidate2-oppose����candidate3-support
	 * 	  ͶƱ��vr2����candidate1-Oppose����candidate2-Waive����candidate3-Waive
	 * 	 ͶƱ��vr3����candidate1-Oppose����candidate2-Waive
	 * 	 2�źϷ���3��ѡƱ
	 * 	 ����2/3
	 */
	@Test
	void acceptTest_AllHeFa()
	{
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		Election poll = new Election();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
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
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv3 = new Vote<Person>(voteItems3, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		Election poll = new Election();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr3);
		CountProportionVisitor<Person> countProportionVisitor = new CountProportionVisitor<>();
		poll.accept(countProportionVisitor);
		Double res = countProportionVisitor.getData();
		assertEquals(2.0/3.0,res);
	}
	/**
	 * getVotersVoteFrequencies����
	 * 1.ͶƱ����Ϊ1
	 * 2.ͶƱ����>1(=2)
	 */
	@Test
	void getVotersVoteFrequenciesTest()
	{
		/**
		 * 	 * ѡ������ǰ2�ĺ�ѡ��
		 * 	 * ��ѡ��candidate1��candidate2��candidate3
		 * 	 * ͶƱ��vr1����candidate1-support����candidate2-oppose����candidate3-support
		 * 	 * ͶƱ��vr2����candidate1-Oppose����candidate2-Waive����candidate3-Waive
		 * 	 * ͶƱ��vr2����candidate1-Oppose����candidate2-Waive����candidate3-Waive
		*/
		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType voteType = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("candidate1", 19);
		Person p2 = new Person("candidate2", 20);
		Person p3 = new Person("candidate3", 21);
		ArrayList<Person> candidates = new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
		Vote<Person> rv3 = new Vote<Person>(voteItems3, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
		// ����ͶƱ�
		Election poll = new Election();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name, date, voteType, quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		poll.addCandidates(candidates);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1, vr1);
		poll.addVote(rv2, vr2);
		poll.addVote(rv3, vr2);
//		System.out.println("poll = " + poll.getVotes());
		// �������Ʊ
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



