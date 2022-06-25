package vote;

import java.util.*;

//immutable
public class Vote<C> {

	// 缺省为“匿名”投票，即不需要管理投票人的信息

	// 一个投票人对所有候选对象的投票项集合
	private Set<VoteItem<C>> voteItems = new HashSet<>();
	// 投票时间
	private Calendar date = Calendar.getInstance();

	// Rep Invariants
	// voteItems.size()>=1
	// Abstract Function
	// AF(voteItem,date),一个投票人对所有候选对象的投票项集合,且记录了投票时间
	// Safety from Rep Exposure
	// 所有的数据域都是私有的
	// 用Collections.unmodifiableSet()转化为不可变类型返回给外部
	// 使用深拷贝(Calendar) date.clone();
	// 构造函数传入参数时候防御性拷贝

	/**
	 * 检查不变量
	 */
	private void checkRep() {
		assert voteItems.size()>=1;
	}

	/**
	 * 创建一个选票对象
	 * 
	 *
	 * @param Set<VoteItem<C>> voteItems	所有候选对象的投票项
	 * @param date    投票时间
	 */
	public Vote(Set<VoteItem<C>> voteItems,Calendar date) {
		this.voteItems = new HashSet<>(voteItems);
		this.date= date;
		checkRep();
	}

	/**
	 * 无参数构造函数
	 * （用于后续的设计模式的构造函数）
	 */
	public Vote() {
	}

	/**
	 * 查询该选票中包含的所有投票项
	 * 自己编写函数
	 * @return 所有投票项
	 */
	public Set<VoteItem<C>> getVoteItems() {
		return Collections.unmodifiableSet(voteItems);
	}

	/**
	 * 一个特定候选人是否包含本选票中
	 * 自己编写函数
	 * @param candidate 待查询的候选人
	 * @return 若包含该候选人的投票项，则返回true，否则false
	 */
	public boolean candidateIncluded(C candidate) {
		for (VoteItem<C> voteItem : voteItems) {
			if(voteItem.getCandidate().equals(candidate))
				return true;
		}
		return false;
	}

	/**
	 * 查询投票日期
	 * @return  投票日期
	 */
	public Calendar getDate()
	{
		return (Calendar) date.clone();
	}

	@Override
	public String toString() {
		return "Vote{" +
				"voteItems=" + voteItems +
				", date=" + date +
				'}';
	}

}
