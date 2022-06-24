package vote;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//immutable
public class VoteType {

	// key为选项名、value为选项名对应的分数
	private Map<String, Integer> options = new HashMap<>();

	// Rep Invariants
	// 选项名key不能为""，其中不允许出现空格
	// options.size()选项个数需要>=2
	// Abstract Function
	// AF(String,Integer)->选项其中key为选项名、value为选项名对应的分数
	// Safety from Rep Exposure
	// 所有的数据域都是私有的
	// 用Collections.unmodifiableMap()转化为不可变类型返回给外部

	/**
	 * String不能为""，不能为null
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
	 * 创建一个投票类型对象
	 * 
	 * 可以自行设计该方法所采用的参数
	 */
	public VoteType(Map<String,Integer> options) {
		this.options=new HashMap<>(options);
		checkRep();
	}

	/**
	 * 根据满足特定语法规则的字符串，创建一个投票类型对象
	 *
	 * @param regex 遵循特定语法的、包含投票类型信息的字符串（待任务12再考虑）
	 */
	//	该字符
	//	串的语法规则如下：
	//			“喜欢”(2)|“不喜欢”(0)|“无所谓”(1)
	//	其中，用双引号括起来的文字部分是一个投票选项，长度不超过 5，其中不
	//	允许出现空格；用括号括起来的数字是投票选项对应的分数，可以是正整数、0
	//	或负整数，不能带小数，正整数不需要使用“+”，但负整数需要使用“-”；不同
	//	的投票选项之间用“|”隔开。
	//	也可以用如下形式：
	//			“支持”|“反对”|“弃权”
	//	与上面的例子相比，区别是没有分数。这种情况表明各个投票选项的权重是一样的
	public VoteType(String regex) throws IllegalArgumentException{
		// split的参数是一个正则表达式，‘|’需要转义
		String[] inputOptions = regex.split("\\|");
		//得到 “喜欢”(2)
		//或者得到 “支持”
		if(inputOptions.length < 2) {
			throw new IllegalArgumentException("非法输入:选项少于两个");
		}
		Pattern regexWithNum = Pattern.compile("\"(\\S+)\"\\(([-]?\\d+)\\)");
		Pattern regexWithoutNum = Pattern.compile("\"(\\S+)\"");
		int unMatchFlag=0;//不匹配次数
		//先尝试匹配有数字模式
		for (String option : inputOptions) {
			Matcher m = regexWithNum.matcher(option);
			if(!m.matches())
			{
				unMatchFlag++;//若这个当前不匹配，不匹配次数+1
				break;
			}
			if (m.group(1).length() > 5)
				throw new IllegalArgumentException("选项名长度超过5");
			options.put(m.group(1), Integer.valueOf(m.group(2)));
		}
		if(unMatchFlag==0)
		{
			checkRep();
			return;
		}
		//匹配无数字的等权重格式
		for (String option : inputOptions) {
			Matcher m = regexWithoutNum.matcher(option);
			if(!m.matches())
			{
				unMatchFlag++;//若这个当前不匹配，不匹配次数+1
				break;
			}
			if (m.group(1).length() > 5)
				throw new IllegalArgumentException("选项名长度超过5");
			options.put(m.group(1), 1);
		}
		if(unMatchFlag==2)//代表都不匹配
			throw new IllegalArgumentException("两种情况都不匹配");
		checkRep();
	}

	/**
	 * 判断一个投票选项是否合法（用于Poll中对选票的合法性检查）
	 * 
	 * 例如，投票人给出的投票选项是“Strongly reject”，但options中不包含这个选项，因此它是非法的
	 * 
	 * 不能改动该方法的参数
	 * 
	 * @param option 一个投票选项
	 * @return 合法则true，否则false
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
	 * 根据一个投票选项，得到其对应的分数
	 * 
	 * 例如，投票人给出的投票选项是“支持”，查询得到其对应的分数是1
	 * 
	 * 不能改动该方法的参数
	 * 
	 * @param option 一个投票项取值
	 * @return 该选项所对应的分数
	 */
	//要求必须存在该选项
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
	 * 自行添加,返回options
	 * @return	options不可变类型
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
