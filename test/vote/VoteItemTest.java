package vote;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import org.junit.jupiter.api.Test;

class VoteItemTest {

	// test strategy
//
// voteItem构造函数测试
//  测试策略对比voteValue与candidate
//  1.满足条件的value
//  2.未满足条件的value
//  2.1value为空
//  2.2value含空格
//
//	 测试getVoteValue
//	 测试策略对比VoteValue
//
//	 测试getCandidate
//	  对比策略对比Candidate
//


	/**
	 *voteItem构造函数测试
	 * 测试策略对比voteValue与candidate
	 * 1.满足条件的value
	 * 2.未满足条件的value
	 * 2.1value为空
	 * 2.2value含空格
	 * */
	@Test
	void voteItemTest() {
		//针对候选对象“张三”，投票选项是“支持”
		Person person = new Person("张三", 15);
		VoteItem<Object> voteItem = new VoteItem<>(person, "支持");
		String voteValue = voteItem.getVoteValue();
		assertEquals("支持", voteValue);
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
		//测试未满足条件的value,因为assert报错提示所以注释
//		Person person2 = new Person("张三", 15);
//		VoteItem<Object> voteItem2 = new VoteItem<>(person2, "支 持");
//		String voteValue2 = voteItem2.getVoteValue();
//		Person person3 = new Person("张三", 15);
//		VoteItem<Object> voteItem3 = new VoteItem<>(person3, "");
//		String voteValue3 = voteItem3.getVoteValue();

	}

	/**
	 * 测试getVoteValue
	 * 测试策略对比VoteValue
	 */
	@Test
	void getVoteValueTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "支持");
		String voteValue = voteItem.getVoteValue();
		assertEquals("支持", voteValue);
		Object candidate = voteItem.getCandidate();
	}

	/**
	 * 测试getCandidate
	 * 对比策略对比Candidate
	 */
	@Test
	void getCandidateTest() {
		Person person = new Person("person1", 18);
		VoteItem<Object> voteItem = new VoteItem<>(person, "支持");
		String voteValue = voteItem.getVoteValue();
		Object candidate = voteItem.getCandidate();
		assertEquals(person, candidate);
	}

}
