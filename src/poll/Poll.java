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
	 * ����ͶƱ�������δ�趨�κ����ԣ�Ҳδ���κ�ͶƱ����
	 * 
	 * @return һ��Poll<C>����
	 */
	public static <C> Poll<C> create() {
		// TODO
		return null;
	}

	/**
	 * �趨ͶƱ��Ļ�������
	 * 
	 * @param name     ͶƱ�������
	 * @param date     ͶƱ��������
	 * @param type     ͶƱ���ͣ�������ͶƱѡ���Լ���Ӧ�ķ���
	 * @param quantity ��ѡ��������
	 */
	public void setInfo(String name, Calendar date, VoteType type, int quantity);

	/**
	 * �趨��ѡ��
	 * 
	 * @param candidates ��ѡ���嵥
	 */
	public void addCandidates(List<C> candidates);

	/**
	 * �趨ͶƱ�˼���Ȩ��
	 * 
	 * @param voters KeyΪͶƱ�ˣ�ValueΪͶƱ�˵�Ȩ��
	 */
	public void addVoters(Map<Voter, Double> voters);

	/**
	 * ����һ��ͶƱ�˶�ȫ���ѡ�����ͶƱ
	 * 
	 * @param vote һ��ͶƱ�˶�ȫ���ѡ�����ͶƱ��¼����
	 */
	public void addVote(Vote<C> vote) throws NoEnoughCandidateException, InvalidCadidatesException, InvalidVoteException, RepeatCandidateException;

	/**
	 * �������Ʊ
	 *
	 * @param ����ȡ�ļ�Ʊ���������
	 */
	public void statistics(StatisticsStrategy ss) throws CanNotVoteException;

	/**
	 * ��������ѡ
	 * 
	 * @param ss ����ȡ����ѡ���������
	 */
	public void selection(SelectionStrategy ss);

	/**
	 * �����ѡ���
	 * 
	 * @return һ����ʾ��ѡ������ַ�����ÿ�а�������ѡ����ID������
	 */
	public String result();
}
