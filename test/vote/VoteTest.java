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
	// TODO

	/**
	 * Vote初始化
	 * 测试
	 */
	@Test
	void VoteTest() {
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "支持");
		voteItems.add(voteItem);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertEquals(voteItems,personVote.getVoteItems());
		assertEquals(date,personVote.getDate());
	}

	/**
	 * 测试getVoteItems
	 * 1.voteItems只有一个候选人
	 * 2.voteItems含多个候选人
	 */
	@Test
	void getVoteItemsTest_Single()
	{
		//测试多个
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "支持");
		voteItems.add(voteItem);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertEquals(voteItems,personVote.getVoteItems());
	}
	@Test
	void getVoteItemsTest_Multi() {
		//测试多个
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		Person person2 = new Person("candidate2", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "支持");
		VoteItem<Person> voteItem2 = new VoteItem<>(person2, "支持");
		voteItems.add(voteItem);
		voteItems.add(voteItem2);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertEquals(voteItems,personVote.getVoteItems());
	}

	/**
	 * 测试candidateIncluded
	 * 1.待查询候选人在选票中
	 * 2.待查询候选人不在选票中
	 */
	@Test
	void candidateIncludedTest() {
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		Person person2 = new Person("candidate2", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "支持");
		voteItems.add(voteItem);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertTrue(personVote.candidateIncluded(person1));//1.待查询候选人在选票中
		assertFalse(personVote.candidateIncluded(person2));//2.待查询候选人不在选票中
	}

	/**
	 * 测试getDate
	 */
	@Test
	void getDateTest() {
		Set<VoteItem<Person>> voteItems = new HashSet<>();
		Person person1 = new Person("candidate1", 15);
		VoteItem<Person> voteItem = new VoteItem<>(person1, "支持");
		voteItems.add(voteItem);
		Calendar date =new GregorianCalendar(2019, 6, 14, 16, 15,30);
		Vote<Person> personVote = new Vote<>(voteItems, date);
		assertEquals(date,personVote.getDate());
	}
}
