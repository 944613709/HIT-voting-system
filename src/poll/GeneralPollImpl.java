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

	// ͶƱ�������
	protected String name;
	// ͶƱ������ʱ��
	protected Calendar date;
	// ��ѡ���󼯺�
	protected List<C> candidates;
	// ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪ���ڱ���ͶƱ����ռȨ��
	protected Map<Voter, Double> voters;
	// ��ѡ���ĺ�ѡ��������
	protected int quantity;
	// ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�ѡ����Զ�Ӧ�ķ�����
	protected VoteType voteType;
	// ����ѡƱ����
	protected Set<Vote<C>> votes;
	// ��Ʊ�����keyΪ��ѡ����valueΪ��÷�
	protected Map<C, Double> statistics;
	// ��ѡ�����keyΪ��ѡ����valueΪ������λ��
	protected Map<C, Double> results;
	//ÿ��voter��Ӧ��vote��Ӧ��ÿ��voterͶ�ҽ�Ͷ1��
	protected Map<Voter,Integer> votersVoteFrequencies;
	//��¼ÿ��Vote�Ƿ�Ϸ����߲��Ϸ�
	protected Map<Vote<C>,Boolean> voteIsLegal;
	// Rep Invariants
	//name����Ϊ����
	//date����Ϊnull
	//quantity>0;

	//��Ҫѡ��������quantity<=candidates.size() =statistics.size()

	//votes��VoteItem��ͶƱ��ѡ�ˣ�Ҫ�պø��ǵ������е�candidate��ѡ��
	//votes��VoteItem��ͶƱ��ѡ�ˣ�������������ѡ��
	//votes��VoteItem��valueͶƱѡ����ܰ���VoteType.options��Keys����֮���
	//candidates.size()=statistics.size()

	//statistics��results��keyҪ�պø��ǵ������е�candidate��ѡ��
	//statistics��results��key������������ѡ��
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
	 * ���캯��
	 * ��candidates��voters��statistics��result��ʼ���ռ�
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
		// ���޸�
		this.name=name;
		this.date=date;
		this.voteType=type;
		this.quantity=quantity;
		checkRep();
	}

	/**
	 * �趨ͶƱ�˼���Ȩ��
	 * @param voters KeyΪͶƱ�ˣ�ValueΪͶƱ�˵�Ȩ��
	 */
	@Override
	public void addVoters(Map<Voter, Double> voters) {
		this.voters=new HashMap<>(voters);
	}
	/**
	 * �趨��ѡ��
	 *
	 * @param candidates ��ѡ���嵥
	 */
	@Override
	public void addCandidates(List<C> candidates) {
		this.candidates=new ArrayList<>(candidates);
	}


	/**
	 * 	 * ����һ��ͶƱ�˶�ȫ���ѡ�����ͶƱ
	 * 	  ���ȼ���ѡƱ�ĺϷ��Բ����
	 * 	 * ����ͳ��ÿ��voter�ύͶƱ����
	 *
	 * ѡƱ���Ϸ����
	 * ? һ��ѡƱ��û�а�������ͶƱ��е����к�ѡ��
	 * ? һ��ѡƱ�а����˲��ڱ���ͶƱ��еĺ�ѡ��
	 * ? һ��ѡƱ�г����˱���ͶƱ�������ѡ��ֵ
	 * ? һ��ѡƱ���ж�ͬһ����ѡ����Ķ��ͶƱ
	 * ? ����Ӧ�� 2��һ��ѡƱ�ж����к�ѡ�����֧��Ʊ��������?��
	 * @param vote һ��ͶƱ�˶�ȫ���ѡ�����ͶƱ��¼����
	 */
	@Override
	public void addVote(Vote<C> vote,Voter voter)  {
		// �˴�Ӧ���ȼ���ѡƱ�ĺϷ��Բ����
		checkVote(vote,voter);
		//�Ϸ����߲��Ϸ��������
		votersVoteFrequencies.put(voter,votersVoteFrequencies.getOrDefault(voter,0)+1);
		votes.add(vote);
	}

	/**
	 * ��addVote֮ǰ����ѡƱ�Ϸ��Բ����
	 * @param vote ͶƱ
	 * @param voter ͶƱ��
	 */
	public void checkVote(Vote<C> vote,Voter voter)
	{
		Set<VoteItem<C>> voteItems = vote.getVoteItems();
		for (VoteItem<C> voteItem : voteItems) {
			if(!candidates.contains(voteItem.getCandidate()))
			{
				voteIsLegal.put(vote,false);//�����˲��ڱ���ͶƱ��ĺ�ѡ��
				return;
			}

			if(!voteType.checkLegality(voteItem.getVoteValue()))
			{
				voteIsLegal.put(vote,false);//һ��ѡƱ�г����˱���ͶƱ�������ѡ��ֵ
				return;
			}

			for(VoteItem<C> voteItem2 : voteItems)
			{
				if(voteItem2!=voteItem && voteItem2.getCandidate().equals(voteItem.getCandidate()))
				{
					voteIsLegal.put(vote,false);;//һ��ѡƱ���ж�ͬһ����ѡ����Ķ��ͶƱ
					return;
				}
			}
		}
		for (C candidate : candidates) {
			if(!vote.candidateIncluded(candidate))
			{
				voteIsLegal.put(vote,false);//һ��ѡƱ��û�а�������ͶƱ��е����к�ѡ��
				return;
			}

		}
		//�������쳣
		voteIsLegal.put(vote,true);//����Ϊ�Ϸ�

//		for (Map.Entry<Vote<C>, Boolean> entry : voteIsLegal.entrySet()) {
//            System.out.println("entry.getValue() = " + entry.getValue());
//        }
	}

	//�ڽ��м�Ʊ֮ǰ������Ҫ����������ݣ������� Poll �� statistics()��
	//����ʵ�֣�
	//? ������ͶƱ�˻�δͶƱ�����ܿ�ʼ��Ʊ��
	//? ��һ��ͶƱ���ύ�˶��ѡƱ�������Ǿ�Ϊ�Ƿ�����Ʊʱ���������ڡ�
	/**
	 * �������Ʊ
	 *
	 * @param ss ����ȡ�ļ�Ʊ���������
	 */
	@Override
	public void statistics(StatisticsStrategy<C> ss) throws CanNotVoteException {
		//�ڽ��м�Ʊ֮ǰ������Ҫ����������ݣ������� Poll �� statistics()������ʵ�֣�

		// ������ͶƱ�˻�δͶƱ�����ܿ�ʼ��Ʊ��
		if(votersVoteFrequencies.keySet().size()!=voters.size())
			throw new CanNotVoteException();

		// ��һ��ͶƱ���ύ�˶��ѡƱ�������Ǿ�Ϊ�Ƿ�����Ʊʱ���ͶƱ�˵Ĳ��������ڡ�
//		for (Integer value : votersVoteFrequencies.values()) {
//			if(value!=1)
//				throw new CanNotVoteException();
//		}
		//������̳�֮��Ӵ˴��������Ʊ
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
