package poll;

import java.util.*;

import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import pattern.Visitor;
import vote.VoteItem;
import vote.VoteType;
import vote.Vote;

public class GeneralPollImpl<C> implements Poll<C> {

	// 投票活动的名称
	protected String name;
	// 投票活动发起的时间
	protected Calendar date;
	// 候选对象集合
	protected List<C> candidates;
	// 投票人集合，key为投票人，value为其在本次投票中所占权重
	protected Map<Voter, Double> voters;
	// 拟选出的候选对象数量
	protected int quantity;
	// 本次投票拟采用的投票类型（合法选项及各自对应的分数）
	protected VoteType voteType;
	// 所有选票集合
	protected Set<Vote<C>> votes;
	// 计票结果，key为候选对象，value为其得分
	protected Map<C, Double> statistics;
	// 遴选结果，key为候选对象，value为其排序位次
	protected Map<C, Double> results;
	//每个voter对应的vote，应该每个voter投且仅投1次
	protected Map<Voter,Integer> votersVoteFrequencies;
	//记录每个Vote是否合法或者不合法
	protected Map<Vote<C>,Boolean> voteIsLegal;
	// Rep Invariants
	//name不能为“”
	//date不能为null
	//quantity>0;

	//需要选出的数量quantity<=candidates.size() =statistics.size()

	//votes的VoteItem的投票候选人，要刚好覆盖到了所有的candidate候选人
	//votes的VoteItem的投票候选人，不能有其他候选人
	//votes的VoteItem的value投票选项，不能包含VoteType.options的Keys集合之外的
	//candidates.size()=statistics.size()

	//statistics与results的key要刚好覆盖到了所有的candidate候选人
	//statistics与results的key不能有其他候选人
	//candidates.size()=statistics.size()

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
	 * 将candidates和voters和statistics和result初始化空间
	 */
	public GeneralPollImpl() {
		candidates = new ArrayList<>();
		voters = new HashMap<>();
		statistics = new HashMap<>();
		results = new HashMap<>();
		votersVoteFrequencies = new HashMap<>();
		voteIsLegal = new HashMap<>();
		votes = new HashSet<>();
	}


	@Override
	public void setInfo(String name, Calendar date, VoteType type, int quantity) {
		// 可修改
		this.name=name;
		this.date=date;
		this.voteType=type;
		this.quantity=quantity;
		checkRep();
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
	 * 	 * 接收一个投票人对全体候选对象的投票
	 * 	  首先检查该选票的合法性并标记
	 * 	 * 并且统计每个voter提交投票次数
	 *
	 * 选票不合法情况
	 * ? 一张选票中没有包含本次投票活动中的所有候选人
	 * ? 一张选票中包含了不在本次投票活动中的候选人
	 * ? 一张选票中出现了本次投票不允许的选项值
	 * ? 一张选票中有对同一个候选对象的多次投票
	 * ? （仅应用 2）一张选票中对所有候选对象的支持票数量大于?。
	 * @param vote 一个投票人对全体候选对象的投票记录集合
	 */
	@Override
	public void addVote(Vote<C> vote,Voter voter)  {
		// 此处应首先检查该选票的合法性并标记
		checkVote(vote,voter);
		//合法或者不合法都会加入
		votersVoteFrequencies.put(voter,votersVoteFrequencies.getOrDefault(voter,0)+1);
		votes.add(vote);
	}

	/**
	 * 在addVote之前检查该选票合法性并标记
	 * @param vote 投票
	 * @param voter 投票人
	 */
	public void checkVote(Vote<C> vote,Voter voter)
	{
		Set<VoteItem<C>> voteItems = vote.getVoteItems();
		for (VoteItem<C> voteItem : voteItems) {
			if(!candidates.contains(voteItem.getCandidate()))
			{
				voteIsLegal.put(vote,false);//包含了不在本次投票活动的候选人
				return;
			}

			if(!voteType.checkLegality(voteItem.getVoteValue()))
			{
				voteIsLegal.put(vote,false);//一张选票中出现了本次投票不允许的选项值
				return;
			}

			for(VoteItem<C> voteItem2 : voteItems)
			{
				if(voteItem2!=voteItem && voteItem2.getCandidate().equals(voteItem.getCandidate()))
				{
					voteIsLegal.put(vote,false);;//一张选票中有对同一个候选对象的多次投票
					return;
				}
			}
		}
		for (C candidate : candidates) {
			if(!vote.candidateIncluded(candidate))
			{
				voteIsLegal.put(vote,false);//一张选票中没有包含本次投票活动中的所有候选人
				return;
			}

		}
		//若都无异常
		voteIsLegal.put(vote,true);//鉴定为合法

//		for (Map.Entry<Vote<C>, Boolean> entry : voteIsLegal.entrySet()) {
//            System.out.println("entry.getValue() = " + entry.getValue());
//        }
	}

	//在进行计票之前，还需要检查以下内容，具体在 Poll 的 statistics()方
	//法中实现：
	//? 若尚有投票人还未投票，则不能开始计票；
	//? 若一个投票人提交了多次选票，则它们均为非法，计票时不计算在内。
	/**
	 * 按规则计票
	 *
	 * @param ss 所采取的计票规则策略类
	 */
	@Override
	public void statistics(StatisticsStrategy<C> ss) throws CanNotVoteException {
		//在进行计票之前，还需要检查以下内容，具体在 Poll 的 statistics()方法中实现：

		// 若尚有投票人还未投票，则不能开始计票；
		if(votersVoteFrequencies.keySet().size()!=voters.size())
			throw new CanNotVoteException();

		// 若一个投票人提交了多次选票，则它们均为非法，计票时这个投票人的不计算在内。
//		for (Integer value : votersVoteFrequencies.values()) {
//			if(value!=1)
//				throw new CanNotVoteException();
//		}
		//让子类继承之后从此处按规则计票
		statistics = ss.statistics(votes, voteType, votersVoteFrequencies, voteIsLegal);

	}

	@Override
	public void selection(SelectionStrategy<C> ss) {
		results = ss.selection(statistics,quantity);
	}

	@Override
	public String result() {
		return results.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public String getName() {
		return name;
	}

	public Calendar getDate() {
		return date;
	}

	public List<C> getCandidates() {
		return candidates;
	}

	public Map<Voter, Double> getVoters() {
		return voters;
	}

	public int getQuantity() {
		return quantity;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public Map<C, Double> getResults() {
		return results;
	}

	public Map<Voter, Integer> getVotersVoteFrequencies() {
		return votersVoteFrequencies;
	}

	@Override
	public String toString() {
		return "GeneralPollImpl{" +
				"name='" + name + '\'' +
				", date=" + date +
				", candidates=" + candidates +
				", voters=" + voters +
				", quantity=" + quantity +
				", voteType=" + voteType +
				", votes=" + votes +
				", statistics=" + statistics +
				", results=" + results +
				", votersVoteFrequencies=" + votersVoteFrequencies +
				", voteIsLegal=" + voteIsLegal +
				'}';
	}

	public Set<Vote<C>> getVotes() {
		return votes;
	}

	public Map<C, Double> getStatistics() {
		return statistics;
	}

	public Map<Vote<C>, Boolean> getVoteIsLegal() {
		return voteIsLegal;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GeneralPollImpl<?> that = (GeneralPollImpl<?>) o;
		return quantity == that.quantity && Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(candidates, that.candidates) && Objects.equals(voters, that.voters) && Objects.equals(voteType, that.voteType) && Objects.equals(votes, that.votes) && Objects.equals(statistics, that.statistics) && Objects.equals(results, that.results) && Objects.equals(votersVoteFrequencies, that.votersVoteFrequencies) && Objects.equals(voteIsLegal, that.voteIsLegal);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, date, candidates, voters, quantity, voteType, votes, statistics, results, votersVoteFrequencies, voteIsLegal);
	}
}
