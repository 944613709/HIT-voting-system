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
//		optionsVoteType.put("ϲ��",2);
//		optionsVoteType.put("��ϲ��",0);
//		optionsVoteType.put("����ν",1);
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
	 * ���ֹ��ò��Ϸ�ѡƱ�Ĳ���
	 * * ѡƱ���Ϸ����
	 * * ? һ��ѡƱ��û�а�������ͶƱ��е����к�ѡ��
	 * * ? һ��ѡƱ�а����˲��ڱ���ͶƱ��еĺ�ѡ��
	 * * ? һ��ѡƱ�г����˱���ͶƱ�������ѡ��ֵ
	 * * ? һ��ѡƱ���ж�ͬһ����ѡ����Ķ��ͶƱ
	 *
	 * ��ѡ��candidate1.candidate2
	 * ͶƱ��vr1,ѡƱ����candidate1
	 * ͶƱ��vr2,ѡƱ����candidate1,candidate3
	 * ͶƱ��vr3,ѡƱ����candidate1,candidate2,���ǳ����˱���ͶƱ�������ѡ��ֵ��like"
	 * ͶƱ��vr4,ѡƱ����candidate1,candidate1
	 * ͶƱ��vr5,ѡƱ����candidate1,candidate2�����Ƕ���֧��Ʊ��֧��Ʊ���������������
	 */
	@Test
	void BuHeFaTest() {
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
		Poll<Person> poll = Poll.create();
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
		Election election = (Election) poll;
		Map<Vote<Person>, Boolean> voteIsLegal = election.getVoteIsLegal();
		for (Map.Entry<Vote<Person>, Boolean> voteBooleanEntry : voteIsLegal.entrySet()) {
			Boolean isLegal = voteBooleanEntry.getValue();
			assertEquals(false,isLegal);
		}

	}

}
