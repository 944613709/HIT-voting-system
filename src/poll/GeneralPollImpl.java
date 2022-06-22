package poll;

import java.util.*;

import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
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

	// Rep Invariants
	//name����Ϊnull�Ҳ���Ϊ����
	//date����Ϊnull
	//candidatesһ���ѡ�ˣ�ÿ��Ԫ�ش���һ����ѡ�˵���������������ĸ���ɣ���λ��д�����ڲ������ո�
	//candidates.size()=statistics.size()=results.size()
	//��candidates.size()>=1;

	//votes��VoteItem��ͶƱ��ѡ�ˣ�Ҫ�պø��ǵ������е�candidate��ѡ��
	//votes��VoteItem��ͶƱ��ѡ�ˣ�������������ѡ��
	//votes��VoteItem��valueͶƱѡ����ܰ���VoteType.options��Keys����֮���

	//statistics��results��keyҪ�պø��ǵ������е�candidate��ѡ��
	//statistics��results��key������������ѡ��
	//��Ҫѡ��������quantity<=candidates.size() =statistics.size() =results.size()

	// Abstract Function
	// TODO
	// Safety from Rep Exposure
	// TODO

	private boolean checkRep() {
		// TODO
		return false;
	}

	/**
	 * ���캯��
	 */
	public GeneralPollImpl() {
		// TODO
	}


	@Override
	public void setInfo(String name, Calendar date, VoteType type, int quantity) {
		// ���޸�
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
		// �˴�Ӧ���ȼ���ѡƱ�ĺϷ��Բ���ǣ�Ϊ������չ���޸�rep
		//TODO
		// TODO�˴�Ӧ���ȼ���ѡƱ�ĺϷ��Բ���ǣ�Ϊ������չ���޸�rep

		votes.add(vote);

	}

	@Override
	public void statistics(StatisticsStrategy ss) {
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
