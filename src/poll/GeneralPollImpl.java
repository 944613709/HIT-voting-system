package poll;

import java.util.*;

import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.VoteItem;
import vote.VoteType;
import vote.Vote;

public class GeneralPollImpl<C> implements Poll<C> {

	// ͶƱ�������
	private String name;
	// ͶƱ������ʱ��
	private Calendar date;
	// ��ѡ���󼯺�
	private List<C> candidates;
	// ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪ���ڱ���ͶƱ����ռȨ��
	private Map<Voter, Double> voters;
	// ��ѡ���ĺ�ѡ�����������
	private int quantity;
	// ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�ѡ����Զ�Ӧ�ķ�����
	private VoteType voteType;
	// ����ѡƱ����
	protected Set<Vote> votes;
	// ��Ʊ�����keyΪ��ѡ����valueΪ��÷�
	protected Map<C, Double> statistics;
	// ��ѡ�����keyΪ��ѡ����valueΪ������λ��
	private Map<C, Double> results;
	//ÿ��voter��Ӧ��vote��Ӧ��ÿ��voterͶ�ҽ�Ͷ1��
	private Map<Voter,Integer> votersVoteFrequencies;
	// Rep Invariants
	//name����Ϊ����
	//date����Ϊnull
	//quantity>0;
	//��candidates.size()>=1;
	//��Ҫѡ��������quantity>=candidates.size() =statistics.size() =results.size()

	//votes��VoteItem��ͶƱ��ѡ�ˣ�Ҫ�պø��ǵ������е�candidate��ѡ��
	//votes��VoteItem��ͶƱ��ѡ�ˣ�������������ѡ��
	//votes��VoteItem��valueͶƱѡ����ܰ���VoteType.options��Keys����֮���
	//candidates.size()=statistics.size()=results.size()

	//statistics��results��keyҪ�պø��ǵ������е�candidate��ѡ��
	//statistics��results��key������������ѡ��
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
	 * ���캯��
	 * ��candidates��voters��statistics��result��ʼ���ռ�
	 */
	public GeneralPollImpl() {
		candidates = new ArrayList<>();
		voters = new HashMap<>();
		statistics = new HashMap<>();
		results = new HashMap<>();
	}


	@Override
	public void setInfo(String name, Calendar date, VoteType type, int quantity) {
		// ���޸�
		this.name=name;
		this.date=date;
		this.voteType=type;
		this.quantity=quantity;
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
	 * ����һ��ͶƱ�˶�ȫ���ѡ�����ͶƱ
	 * ����ͳ��ÿ��voter�ύͶƱ����
	 * �쳣���
	 * ? һ��ѡƱ��û�а�������ͶƱ��е����к�ѡ��
	 * ? һ��ѡƱ�а����˲��ڱ���ͶƱ��еĺ�ѡ��
	 * ? һ��ѡƱ�г����˱���ͶƱ�������ѡ��ֵ
	 * ? һ��ѡƱ���ж�ͬһ����ѡ����Ķ��ͶƱ
	 * ? ����Ӧ�� 2��һ��ѡƱ�ж����к�ѡ�����֧��Ʊ��������?��
	 * @param vote һ��ͶƱ�˶�ȫ���ѡ�����ͶƱ��¼����
	 */
	@Override
	public void addVote(Vote<C> vote,Voter voter) throws NoEnoughCandidateException, InvalidCadidatesException, InvalidVoteException, RepeatCandidateException {
		// �˴�Ӧ���ȼ���ѡƱ�ĺϷ��Բ����
		Set<VoteItem<C>> voteItems = vote.getVoteItems();
		for (VoteItem<C> voteItem : voteItems) {
			if(!candidates.contains(voteItem))
				throw new InvalidCadidatesException();
			if(!voteType.checkLegality(voteItem.getVoteValue()))
				throw new InvalidVoteException();//һ��ѡƱ�г����˱���ͶƱ�������ѡ��ֵ
			for(VoteItem<C> voteItem2 : voteItems)
			{
				if(voteItem2!=voteItem && voteItem2.getCandidate().equals(voteItem.getCandidate()))
					throw new RepeatCandidateException();//һ��ѡƱ���ж�ͬһ����ѡ����Ķ��ͶƱ
			}
		}

		for (C candidate : candidates) {
			if(!vote.candidateIncluded(candidate))
				throw new NoEnoughCandidateException();//һ��ѡƱ��û�а�������ͶƱ��е����к�ѡ��
		}
		//�������쳣
		votes.add(vote);
		votersVoteFrequencies.put(voter,votersVoteFrequencies.getOrDefault(voter,0)+1);
	}

	//�ڽ��м�Ʊ֮ǰ������Ҫ����������ݣ������� Poll �� statistics()��
	//����ʵ�֣�
	//? ������ͶƱ�˻�δͶƱ�����ܿ�ʼ��Ʊ��
	//? ��һ��ͶƱ���ύ�˶��ѡƱ�������Ǿ�Ϊ�Ƿ�����Ʊʱ���������ڡ�
	/**
	 * �������Ʊ
	 *
	 * @param ����ȡ�ļ�Ʊ���������
	 */
	@Override
	public void statistics(StatisticsStrategy ss) {
		////�ڽ��м�Ʊ֮ǰ������Ҫ����������ݣ������� Poll �� statistics()��
		//	//����ʵ�֣�
		//	//? ������ͶƱ�˻�δͶƱ�����ܿ�ʼ��Ʊ��
		//	//? ��һ��ͶƱ���ύ�˶��ѡƱ�������Ǿ�Ϊ�Ƿ�����Ʊʱ���������ڡ�
		// �˴�Ӧ���ȼ�鵱ǰ��ӵ�е�ѡƱ�ĺϷ���
		//TODO
		// TODO�˴�Ӧ���ȼ�鵱ǰ��ӵ�е�ѡƱ�ĺϷ���

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
