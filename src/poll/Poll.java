package poll;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.Vote;
import vote.VoteType;

public interface Poll<C> {

	/**
	 * 创建投票活动，但尚未设定任何属性，也未有任何投票数据
	 * 
	 * @return 一个Poll<C>对象
	 */
	public static <C> Poll<C> create() {
		// TODO
		return null;
	}

	/**
	 * 设定投票活动的基本属性
	 * 
	 * @param name     投票活动的名称
	 * @param date     投票创建日期
	 * @param type     投票类型，包含各投票选项以及相应的分数
	 * @param quantity 拟选出的数量
	 */
	public void setInfo(String name, Calendar date, VoteType type, int quantity);

	/**
	 * 设定候选人
	 * 
	 * @param candidates 候选人清单
	 */
	public void addCandidates(List<C> candidates);

	/**
	 * 设定投票人及其权重
	 * 
	 * @param voters Key为投票人，Value为投票人的权重
	 */
	public void addVoters(Map<Voter, Double> voters);

	/**
	 * 接收一个投票人对全体候选对象的投票
	 * 
	 * @param vote 一个投票人对全体候选对象的投票记录集合
	 */
	public void addVote(Vote<C> vote) throws NoEnoughCandidateException, InvalidCadidatesException, InvalidVoteException, RepeatCandidateException;

	/**
	 * 按规则计票
	 *
	 * @param 所采取的计票规则策略类
	 */
	public void statistics(StatisticsStrategy ss) throws CanNotVoteException;

	/**
	 * 按规则遴选
	 * 
	 * @param ss 所采取的遴选规则策略类
	 */
	public void selection(SelectionStrategy ss);

	/**
	 * 输出遴选结果
	 * 
	 * @return 一个表示遴选结果的字符串，每行包含：候选对象ID、排序
	 */
	public String result();
}
