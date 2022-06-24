package vote;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

class VoteTest {

	// test strategy
//	  Vote���캯��
//	  1.��������
//	  2.������������voteItem.size()<1
//			 ����getVoteItems
//	  1.voteItemsֻ��һ����ѡ��
//	  2.voteItems�������ѡ��
//		  	����candidateIncluded
//	  1.����ѯ��ѡ����ѡƱ��
//	  2.����ѯ��ѡ�˲���ѡƱ��
//			����getDate


	/**
	 * Vote���캯��
	 * ����
	 * 1.��������
	 * 2.������������voteItem.size()<1
	 */
	@Test
	void VoteTest() {
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "֧��");
		voteItems.add(voteItem);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertEquals(voteItems,personVote.getVoteItems());
		assertEquals(date,personVote.getDate());

		//��Ϊ�����м��֮��assert false����ע��
//		Set<VoteItem<Person>> voteItems2 = new HashSet<>();
//		Person person2 = new Person("candidate2", 15);
//		VoteItem<Person> voteItem2 = new VoteItem<>(person2, "֧��");
//		Calendar date2 =new GregorianCalendar(2019, 6, 14, 16, 15,30);
//		Vote<Person> personVote2 = new Vote<>(voteItems2, date2);
	}

	/**
	 * ����getVoteItems
	 * 1.voteItemsֻ��һ����ѡ��
	 * 2.voteItems�������ѡ��
	 */
	@Test
	void getVoteItemsTest_Single()
	{
		//���Զ��
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "֧��");
		voteItems.add(voteItem);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertEquals(voteItems,personVote.getVoteItems());
	}
	@Test
	void getVoteItemsTest_Multi() {
		//���Զ��
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		Person person2 = new Person("candidate2", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "֧��");
		VoteItem<Person> voteItem2 = new VoteItem<>(person2, "֧��");
		voteItems.add(voteItem);
		voteItems.add(voteItem2);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertEquals(voteItems,personVote.getVoteItems());
	}

	/**
	 * ����candidateIncluded
	 * 1.����ѯ��ѡ����ѡƱ��
	 * 2.����ѯ��ѡ�˲���ѡƱ��
	 */
	@Test
	void candidateIncludedTest() {
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		Person person2 = new Person("candidate2", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "֧��");
		voteItems.add(voteItem);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertTrue(personVote.candidateIncluded(person1));//1.����ѯ��ѡ����ѡƱ��
		assertFalse(personVote.candidateIncluded(person2));//2.����ѯ��ѡ�˲���ѡƱ��
	}

	/**
	 * ����getDate
	 */
	@Test
	void getDateTest() {
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "֧��");
		voteItems.add(voteItem);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertEquals(date,personVote.getDate());
	}
}
