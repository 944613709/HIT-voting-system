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
	 * ���VoteType(Map<String,Integer> options)
	 * 1.������ͶƱѡ��
	 * 2.����ͶƱѡ��
//	 * 2.1Key��������
//	 * 2.2Key����������
	 */
	@Test
	public void VoteTypeTest_NotRegex()
	{
		Map<String, Integer> options = new HashMap<>();
		VoteType voteType = new VoteType(options);
		assertEquals(options,voteType.getOptions());

		Map<String, Integer> options2 = new HashMap<>();
		options2.put("ϲ��",2);
		options2.put("��ϲ��",0);
		options2.put("����ν",1);
		VoteType voteType2 = new VoteType(options2);
		assertEquals(options2,voteType2.getOptions());

//		Map<String, Integer> options3 = new HashMap<>();
//		options3.put("�ر��ر��ϲ��",2);
//		options3.put(" ",0);
//		options3.put("�� �� ν",1);
//		VoteType voteType3 = new VoteType(options3);
//		assertFalse(false,voteType3.getOptions());
	}
	/**
	 * ���VoteType(String regex)
	 * ���Դ����ֵ�
	 * ���Բ������ֵ�
	 */
	@Test
	public void VoteTypeTest_regex()
	{
		//"ϲ��"(2)|"��ϲ��"(0)|"����ν"(1)
		Map<String, Integer> options = new HashMap<>();
		options.put("ϲ��",2);
		options.put("��ϲ��",0);
		options.put("����ν",1);
		VoteType voteType = new VoteType("\"ϲ��\"(2)|\"��ϲ��\"(0)|\"����ν\"(1)");
		assertEquals(options,voteType.getOptions());

		//"֧��"|"����"|"��Ȩ"
		//û�з�����Ĭ��Ϊ1
		Map<String, Integer> options2 = new HashMap<>();
		options2.put("֧��",1);
		options2.put("����",1);
		options2.put("��Ȩ",1);
		VoteType voteType2 = new VoteType("\"֧��\"|\"����\"|\"��Ȩ\"");
		assertEquals(options2,voteType2.getOptions());
	}

	/**
	 * ����checkLegality
	 * 1.ͶƱѡ��Ϊ��
	 * 2.ͶƱѡ�Ϊ��
	 * 2.1.���ڸ�ͶƱѡ��
	 * 2.2.������ͶƱѡ��
	 */

	@Test
	public void checkLegalityTest()
	{
		HashMap<String, Integer> options = new HashMap<>();
		VoteType voteType = new VoteType(options);
		assertEquals(false,voteType.checkLegality("ϲ��"));

		Map<String, Integer> options2 = new HashMap<>();
		options2.put("ϲ��",2);
		options2.put("��ϲ��",0);
		options2.put("����ν",1);
		VoteType voteType2 = new VoteType(options2);
		assertEquals(true,voteType2.checkLegality("ϲ��"));
		assertEquals(false,voteType2.checkLegality("˿����ϲ��"));
	}

	/**
	 * ����getScoreByOption
	 * ��ǰ��������ָ������ڸ�ѡ��
	 */
	@Test
	public void getScoreByOptionTest()
	{
		Map<String, Integer> options2 = new HashMap<>();
		options2.put("ϲ��",2);
		options2.put("��ϲ��",0);
		options2.put("����ν",1);
		VoteType voteType2 = new VoteType(options2);
		assertEquals(2,voteType2.getScoreByOption("ϲ��"));
		assertEquals(0,voteType2.getScoreByOption("��ϲ��"));
		assertEquals(1,voteType2.getScoreByOption("����ν"));
	}

}
