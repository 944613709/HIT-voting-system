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
	//name不能为“”
	//date不能为null
	//quantity>0;
	//且candidates.size()>=1;
	//需要选出的数量quantity>=candidates.size() =statistics.size() =results.size()

	//votes的VoteItem的投票候选人，要刚好覆盖到了所有的candidate候选人
	//votes的VoteItem的投票候选人，不能有其他候选人
	//votes的VoteItem的value投票选项，不能包含VoteType.options的Keys集合之外的
	//candidates.size()=statistics.size()=results.size()

	//statistics与results的key要刚好覆盖到了所有的candidate候选人
	//statistics与results的key不能有其他候选人
	//candidates.size()=statistics.size()=results.size()

	// Abstract Function
	// TODO
	// Safety from Rep Exposure
	// TODO

	private void checkRep() {
		assert !name.equals("");
		assert date!=null;
		assert quantity>0;
//		assert quantity>=candidates.size();
//		assert candidates.size()==statistics.size();
//		assert candidates.size()==results.size();
	}

	/**
	 * 构造函数
	 */
	public GeneralPollImpl() {
		candidates = new ArrayList<>();
		voters = new HashMap<>();
		statistics = new HashMap<>();
		results = new HashMap<>();
	}


	@Override
	public void setInfo(String name, Calendar date, VoteType type, int quantity) {
		// 可修改
		this.name=name;
		this.date=date;
		this.voteType=type;
		this.quantity=quantity;
	}

	/**
	 * 设定投票人及其权重
	 * @param voters Key为投票人，Value为投票人的权重
	 */
	@Override
	public void addVoters(Map<Voter, Double> voters) {
		this.voters=new HashMap<>(voters);
	}
	/**
	 * 设定候选人
	 *
	 * @param candidates 候选人清单
	 */
	@Override
	public void addCandidates(List<C> candidates) {
		this.candidates=new ArrayList<>(candidates);
	}


	/**
	 * 接收一个投票人对全体候选对象的投票
	 * 异常情况
	 * ? 一张选票中没有包含本次投票活动中的所有候选人
	 * ? 一张选票中包含了不在本次投票活动中的候选人
	 * ? 一张选票中出现了本次投票不允许的选项值
	 * ? 一张选票中有对同一个候选对象的多次投票
	 * ? （仅应用 2）一张选票中对所有候选对象的支持票数量大于?。
	 * @param vote 一个投票人对全体候选对象的投票记录集合
	 */
	@Override
	public void addVote(Vote<C> vote) {
		// 此处应首先检查该选票的合法性并标记，为此需扩展或修改rep
		
		for (C candidate : candidates) {
			if(vote.candidateIncluded(candidate))
				votes.add()
		}
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
