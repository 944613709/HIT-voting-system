package app;

import auxiliary.Proposal;
import auxiliary.Voter;
import org.junit.Test;
import pattern.BusinessSelectionStrategy;
import pattern.BusinessVotingStatisticsStrategy;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import poll.BusinessVoting;
import poll.CanNotVoteException;
import poll.Election;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class BusinessVotingApp {
    /**
     * ����ɹ�->���������ѡ����ѡ��
     * * 1����ѡ����
     * * ��ѡ����candidate1
     * * ͶƱ��vr1��Ȩ��Ϊ3����candidate1-support
     * * ͶƱ��vr2��Ȩ��Ϊ1����candidate1-Oppose
     * * ͶƱ��vr3��Ȩ��Ϊ1����candidate1-Oppose
     * ��Ʊ�õ�	candidate1->3
     * �ܹ��Ϸ�ѡƱ����3
     * ������(3*(2/3))
     * ��ѡ�õ�candidate1>1
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        //Ҫ��ʵ��ͶƱ��Vote���붼��RealNameVote
        Calendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
        // ����2��ͶƱ��
        Voter vr1 = new Voter("v1");
        Voter vr2 = new Voter("v2");
        Voter vr3 = new Voter("v3");

        // �趨2��ͶƱ�˵�Ȩ��
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(vr1, 3.0);
        weightedVoters.put(vr2, 1.0);
        weightedVoters.put(vr3, 1.0);

        // �趨ͶƱ����
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType voteType = new VoteType(types);

        // ������ѡ���󣺺�ѡ��
        Proposal p1 = new Proposal("candidate1", date);

        ArrayList<Proposal> candidates = new ArrayList<>();
        candidates.add(p1);

        // ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
        VoteItem<Proposal> vi11 = new VoteItem<>(p1, "Support");
        Set<VoteItem<Proposal>> voteItems1 = new HashSet<>();
        voteItems1.add(vi11);

        VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Oppose");
        Set<VoteItem<Proposal>> voteItems2 = new HashSet<>();
        voteItems2.add(vi21);

        VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Oppose");
        Set<VoteItem<Proposal>> voteItems3 = new HashSet<>();
        voteItems3.add(vi31);

        // ����2��ͶƱ��vr1��vr2��ѡƱ
        RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(voteItems1, date, vr1);
        RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(voteItems2, date, vr2);
        RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(voteItems3, date, vr3);

        // ����ͶƱ�

        BusinessVoting poll = new BusinessVoting();
        // �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
        String name = "��ҵ���";
        int quantity = 1;
        poll.setInfo(name, date, voteType, quantity);

        // ����ͶƱ�˼���Ȩ��
        poll.addVoters(weightedVoters);
        poll.addCandidates(candidates);
        // ��������ͶƱ�˵�ѡƱ
        poll.addVote(rv1, vr1);
        poll.addVote(rv2, vr2);
        poll.addVote(rv3, vr3);
        //		System.out.println("poll = " + poll.getVotes());
        // �������Ʊ
        BusinessVotingStatisticsStrategy<Proposal> businessVotingStatisticsStrategy = new BusinessVotingStatisticsStrategy<>();
        try {
            poll.statistics(businessVotingStatisticsStrategy);
        } catch (CanNotVoteException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
//			System.out.println("poll = " + poll.getStatistics());
        // ��������ѡ
        BusinessSelectionStrategy<Proposal> businessSelectionStrategy = new BusinessSelectionStrategy<>();
        poll.selection(businessSelectionStrategy);
        // �����ѡ���
        System.out.println("����ͶƱ����"+poll.getClass());
        System.out.println("����ͶƱ������Ϣ" +poll);
        System.out.println("��Ȩͳ�ƻ��֧��Ʊ������,�����Ʊ���");
        System.out.println(poll.getStatistics());
        System.out.println("���֧��Ʊ�����Ϸ�ѡƱ��2/3������ʾ���ͨ��,�����ѡ���");
        System.out.println(poll.result());

    }
}
