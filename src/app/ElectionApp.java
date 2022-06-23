package app;

import java.util.*;

import auxiliary.Person;
import auxiliary.Voter;
import pattern.ElectionStatisticsStrategy;
import poll.CanNotVoteException;
import poll.Election;
import poll.Poll;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

public class ElectionApp {

	public static void main(String[] args) {

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
		Person p1 = new Person("ABC", 19);
		Person p2 = new Person("DEF", 20);
		Person p3 = new Person("GHI", 21);

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
		voteItems2.add(vi11);
		voteItems2.add(vi12);
		voteItems2.add(vi13);

		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(voteItems1,new GregorianCalendar(2019, 6, 14, 16, 15,30));
		Vote<Person> rv2 = new Vote<Person>(voteItems2,new GregorianCalendar(2019, 6, 14, 16, 15,30));

		// ����ͶƱ�
		Poll<Person> poll = new Election();
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name = "����ѡ��";
		GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
		int quantity = 2;
		poll.setInfo(name,date,voteType,quantity);

		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);

		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1,vr1);
		poll.addVote(rv2,vr2);

		// �������Ʊ
		ElectionStatisticsStrategy<Object> electionStatisticsStrategy = new ElectionStatisticsStrategy<>();
		try {
			poll.statistics(electionStatisticsStrategy);
		} catch (CanNotVoteException e) {
			System.out.println("e.getMessage() = " + e.getMessage());
		}
		// ��������ѡ
		poll.selection(/* TODO */);
		// �����ѡ���
		System.out.println(poll.result());
	}

}
