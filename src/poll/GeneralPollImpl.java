package poll;

import java.util.*;

import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.VoteType;
import vote.Vote;

public class GeneralPollImpl<C> implements Poll<C> {

	// 投票活动的名称
	private String name;
	// 投票活动发起的时间
	private Calendar date;
	// 候选对象集合
	private List<C> candidates;
	// 投票人集合，key为投票人，value为其在本次投票中所占权重
	private Map<Voter, Double> voters;
	// 拟选出的候选对象最大数量
	private int quantity;
	// 本次投票拟采用的投票类型（合法选项及各自对应的分数）
	private VoteType voteType;
	// 所有选票集合
	protected Set<Vote> votes;
	// 计票结果，key为候选对象，value为其得分
	protected Map<C, Double> statistics;
	// 遴选结果，key为候选对象，value为其排序位次
	private Map<C, Double> results;

	// Rep Invariants
	//name不能为null且不能为“”
	//date不能为null
	//candidates一组候选人，每个元素代表一个候选人的姓名，姓名由字母构成，首位大写，其内部不含空格
	//candidates.size()=statistics.size()=results.size()
	//且candidates.size()>=1;

	//votes的VoteItem的投票候选人，要刚好覆盖到了所有的candidate候选人
	//votes的VoteItem的投票候选人，不能有其他候选人
	//votes的VoteItem的value投票选项，不能包含VoteType.options的Keys集合之外的

	//statistics与results的key要刚好覆盖到了所有的candidate候选人
	//statistics与results的key不能有其他候选人
	//需要选出的数量quantity<=candidates.size() =statistics.size() =results.size()

	// Abstract Function
	// TODO
	// Safety from Rep Exposure
	// TODO

	private boolean checkRep() {
		// TODO
		return false;
	}

	/**
	 * 构造函数
	 */
	public GeneralPollImpl() {
		// TODO
	}


	@Override
	public void setInfo(String name, Calendar date, VoteType type, int quantity) {
		// 可修改
		this.name=name;
		this.date=date;
		this.voteType=type;
		this.quantity=quantity;
	}

	@Override
	public void addVoters(Map<Voter, Double> voters) {
		this.voters=new HashMap<>(voters);
	}

	@Override
	public void addCandidates(List<C> candidates) {
		this.candidates=new ArrayList<>(candidates);
	}



	@Override
	public void addVote(Vote<C> vote) {
		// 此处应首先检查该选票的合法性并标记，为此需扩展或修改rep
		//TODO
		// TODO此处应首先检查该选票的合法性并标记，为此需扩展或修改rep

		votes.add(vote);

	}

	@Override
	public void statistics(StatisticsStrategy ss) {
		// 此处应首先检查当前所拥有的选票的合法性
		//TODO
		// TODO此处应首先检查当前所拥有的选票的合法性

	}

	@Override
	public void selection(SelectionStrategy ss) {
		// TODO
	}

	@Override
	public String result() {
		// TODO
		return null;
	}
}
