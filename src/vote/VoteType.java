package vote;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//immutable
public class VoteType {

	// keyΪѡ������valueΪѡ������Ӧ�ķ���
	private Map<String, Integer> options = new HashMap<>();

	// Rep Invariants
	// ѡ����key����Ϊ""�����в�������ֿո�
	// options.size()ѡ�������Ҫ>=2
	// Abstract Function
	// AF(String,Integer)->ѡ������keyΪѡ������valueΪѡ������Ӧ�ķ���
	// Safety from Rep Exposure
	// ���е���������˽�е�
	// ��Collections.unmodifiableMap()ת��Ϊ���ɱ����ͷ��ظ��ⲿ

	/**
	 * String����Ϊ""������Ϊnull
	 * @return
	 */
	private void checkRep() {
		for (Map.Entry<String, Integer> stringIntegerEntry : options.entrySet()) {
			String keyStr = stringIntegerEntry.getKey();
			assert !keyStr.equals("");
			assert !keyStr.contains(" ");
			assert options.size()>=2;
		}
	}

	/**
	 * ����һ��ͶƱ���Ͷ���
	 * 
	 * ����������Ƹ÷��������õĲ���
	 */
	public VoteType(Map<String,Integer> options) {
		this.options=new HashMap<>(options);
		checkRep();
	}

	/**
	 * ���������ض��﷨������ַ���������һ��ͶƱ���Ͷ���
	 *
	 * @param regex ��ѭ�ض��﷨�ġ�����ͶƱ������Ϣ���ַ�����������12�ٿ��ǣ�
	 */
	//	���ַ�
	//	�����﷨�������£�
	//			��ϲ����(2)|����ϲ����(0)|������ν��(1)
	//	���У���˫���������������ֲ�����һ��ͶƱѡ����Ȳ����� 5�����в�
	//	������ֿո���������������������ͶƱѡ���Ӧ�ķ�������������������0
	//	�����������ܴ�С��������������Ҫʹ�á�+��������������Ҫʹ�á�-������ͬ
	//	��ͶƱѡ��֮���á�|��������
	//	Ҳ������������ʽ��
	//			��֧�֡�|�����ԡ�|����Ȩ��
	//	�������������ȣ�������û�з��������������������ͶƱѡ���Ȩ����һ����
	public VoteType(String regex) throws IllegalArgumentException{
		// split�Ĳ�����һ��������ʽ����|����Ҫת��
		String[] inputOptions = regex.split("\\|");
		//�õ� ��ϲ����(2)
		//���ߵõ� ��֧�֡�
		if(inputOptions.length < 2) {
			throw new IllegalArgumentException("�Ƿ�����:ѡ����������");
		}
		Pattern regexWithNum = Pattern.compile("\"(\\S+)\"\\(([-]?\\d+)\\)");
		Pattern regexWithoutNum = Pattern.compile("\"(\\S+)\"");
		int unMatchFlag=0;//��ƥ�����
		//�ȳ���ƥ��������ģʽ
		for (String option : inputOptions) {
			Matcher m = regexWithNum.matcher(option);
			if(!m.matches())
			{
				unMatchFlag++;//�������ǰ��ƥ�䣬��ƥ�����+1
				break;
			}
			if (m.group(1).length() > 5)
				throw new IllegalArgumentException("ѡ�������ȳ���5");
			options.put(m.group(1), Integer.valueOf(m.group(2)));
		}
		if(unMatchFlag==0)
		{
			checkRep();
			return;
		}
		//ƥ�������ֵĵ�Ȩ�ظ�ʽ
		for (String option : inputOptions) {
			Matcher m = regexWithoutNum.matcher(option);
			if(!m.matches())
			{
				unMatchFlag++;//�������ǰ��ƥ�䣬��ƥ�����+1
				break;
			}
			if (m.group(1).length() > 5)
				throw new IllegalArgumentException("ѡ�������ȳ���5");
			options.put(m.group(1), 1);
		}
		if(unMatchFlag==2)//������ƥ��
			throw new IllegalArgumentException("�����������ƥ��");
		checkRep();
	}

	/**
	 * �ж�һ��ͶƱѡ���Ƿ�Ϸ�������Poll�ж�ѡƱ�ĺϷ��Լ�飩
	 * 
	 * ���磬ͶƱ�˸�����ͶƱѡ���ǡ�Strongly reject������options�в��������ѡ�������ǷǷ���
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱѡ��
	 * @return �Ϸ���true������false
	 */
	public boolean checkLegality(String option) {
		for (Map.Entry<String, Integer> stringIntegerEntry : options.entrySet())
		{
			if(option.equals(stringIntegerEntry.getKey()))
				return true;
		}
		return false;
	}

	/**
	 * ����һ��ͶƱѡ��õ����Ӧ�ķ���
	 * 
	 * ���磬ͶƱ�˸�����ͶƱѡ���ǡ�֧�֡�����ѯ�õ����Ӧ�ķ�����1
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱ��ȡֵ
	 * @return ��ѡ������Ӧ�ķ���
	 */
	//Ҫ�������ڸ�ѡ��
	public int getScoreByOption(String option) {
		return options.get(option);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VoteType voteType = (VoteType) o;
		return Objects.equals(options, voteType.options);
	}

	@Override
	public int hashCode() {
		return Objects.hash(options);
	}

	/**
	 * �������,����options
	 * @return	options���ɱ�����
	 */
	public Map<String, Integer> getOptions() {
		return Collections.unmodifiableMap(options);
	}

	@Override
	public String toString() {
		return "VoteType{" +
				"options=" + options +
				'}';
	}
}
