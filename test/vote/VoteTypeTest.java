package vote;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
class VoteTypeTest {

	// test strategy
	// TODO

	/**
	 * 针对VoteType(Map<String,Integer> options)
	 * 1.不存在投票选项
	 * 2.存在投票选项
//	 * 2.1Key符合条件
//	 * 2.2Key不符合条件
	 */
	@Test
	public void VoteTypeTest_NotRegex()
	{
		Map<String, Integer> options = new HashMap<>();
		VoteType voteType = new VoteType(options);
		assertEquals(options,voteType.getOptions());

		Map<String, Integer> options2 = new HashMap<>();
		options2.put("喜欢",2);
		options2.put("不喜欢",0);
		options2.put("无所谓",1);
		VoteType voteType2 = new VoteType(options2);
		assertEquals(options2,voteType2.getOptions());

//		Map<String, Integer> options3 = new HashMap<>();
//		options3.put("特别特别的喜欢",2);
//		options3.put(" ",0);
//		options3.put("无 所 谓",1);
//		VoteType voteType3 = new VoteType(options3);
//		assertFalse(false,voteType3.getOptions());
	}
	/**
	 * 针对VoteType(String regex)
	 * 测试带数字的
	 * 测试不带数字的
	 */
	@Test
	public void VoteTypeTest_regex()
	{
		//"喜欢"(2)|"不喜欢"(0)|"无所谓"(1)
		Map<String, Integer> options = new HashMap<>();
		options.put("喜欢",2);
		options.put("不喜欢",0);
		options.put("无所谓",1);
		VoteType voteType = new VoteType("\"喜欢\"(2)|\"不喜欢\"(0)|\"无所谓\"(1)");
		assertEquals(options,voteType.getOptions());

		//"支持"|"反对"|"弃权"
		//没有分数都默认为1
		Map<String, Integer> options2 = new HashMap<>();
		options2.put("支持",1);
		options2.put("反对",1);
		options2.put("弃权",1);
		VoteType voteType2 = new VoteType("\"支持\"|\"反对\"|\"弃权\"");
		assertEquals(options2,voteType2.getOptions());
	}

	/**
	 * 测试checkLegality
	 * 1.投票选项为空
	 * 2.投票选项不为空
	 * 2.1.存在该投票选项
	 * 2.2.不存在投票选项
	 */

	@Test
	public void checkLegalityTest()
	{
		HashMap<String, Integer> options = new HashMap<>();
		VoteType voteType = new VoteType(options);
		assertEquals(false,voteType.checkLegality("喜欢"));

		Map<String, Integer> options2 = new HashMap<>();
		options2.put("喜欢",2);
		options2.put("不喜欢",0);
		options2.put("无所谓",1);
		VoteType voteType2 = new VoteType(options2);
		assertEquals(true,voteType2.checkLegality("喜欢"));
		assertEquals(false,voteType2.checkLegality("丝毫不喜欢"));
	}

	/**
	 * 测试getScoreByOption
	 * 由前置条件得指必须存在该选项
	 */
	@Test
	public void getScoreByOptionTest()
	{
		Map<String, Integer> options2 = new HashMap<>();
		options2.put("喜欢",2);
		options2.put("不喜欢",0);
		options2.put("无所谓",1);
		VoteType voteType2 = new VoteType(options2);
		assertEquals(2,voteType2.getScoreByOption("喜欢"));
		assertEquals(0,voteType2.getScoreByOption("不喜欢"));
		assertEquals(1,voteType2.getScoreByOption("无所谓"));
	}

}
