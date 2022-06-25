package poll;

import auxiliary.Person;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.CountProportionVisitor;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitorTest {
    //���Բ��ԣ�
    //����accept����,������Visitorģʽ
//	 1.ȫ���Ϸ�
//	  2.���ֺϷ�
    /**
     * ��������11����ADT ��������� Visitor ���ģʽ��
     * ����Ϸ�ѡƱ������ѡƱ����ռ����
     * ����accept����
     *1.ȫ���Ϸ�
     * 	 * 	  ѡ������ǰ2�ĺ�ѡ��
     * 	 * 	  ��ѡ��candidate1��candidate2��candidate3
     * 	 * 	  ͶƱ��vr1����candidate1-support����candidate2-oppose����candidate3-support
     * 	 * 	  ͶƱ��vr2����candidate1-Oppose����candidate2-Waive����candidate3-Waive
     * 	 ����2/2
     * 2.���ֺϷ�
     * 	  ѡ������ǰ2�ĺ�ѡ��
     * 	  ��ѡ��candidate1��candidate2��candidate3
     * 	  ͶƱ��vr1����candidate1-support����candidate2-oppose����candidate3-support
     * 	  ͶƱ��vr2����candidate1-Oppose����candidate2-Waive����candidate3-Waive
     * 	 ͶƱ��vr3����candidate1-Oppose����candidate2-Waive
     * 	 2�źϷ���3��ѡƱ
     * 	 ����2/3
     */
    @Test
    void acceptTest_AllHeFa()
    {
        // ����2��ͶƱ��
        Voter vr1 = new Voter("v1");
        Voter vr2 = new Voter("v2");

        // �趨2��ͶƱ�˵�Ȩ��
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(vr1, 1.0);
        weightedVoters.put(vr2, 1.0);

        // �趨ͶƱ����
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType voteType = new VoteType(types);

        // ������ѡ���󣺺�ѡ��
        Person p1 = new Person("candidate1", 19);
        Person p2 = new Person("candidate2", 20);
        Person p3 = new Person("candidate3", 21);
        ArrayList<Person> candidates = new ArrayList<>();
        candidates.add(p1);
        candidates.add(p2);
        candidates.add(p3);
        // ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
        VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
        VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
        VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
        Set<VoteItem<Person>> voteItems1 = new HashSet<>();
        voteItems1.add(vi11);
        voteItems1.add(vi12);
        voteItems1.add(vi13);

        VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
        VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
        VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
        Set<VoteItem<Person>> voteItems2 = new HashSet<>();
        voteItems2.add(vi21);
        voteItems2.add(vi22);
        voteItems2.add(vi23);

        // ����2��ͶƱ��vr1��vr2��ѡƱ
        Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
        Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));

        // ����ͶƱ�
        Election poll = new Election();
        // �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
        String name = "����ѡ��";
        GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
        int quantity = 2;
        poll.setInfo(name, date, voteType, quantity);

        // ����ͶƱ�˼���Ȩ��
        poll.addVoters(weightedVoters);
        poll.addCandidates(candidates);
        // ��������ͶƱ�˵�ѡƱ
        poll.addVote(rv1, vr1);
        poll.addVote(rv2, vr2);
        CountProportionVisitor<Person> countProportionVisitor = new CountProportionVisitor<>();
        poll.accept(countProportionVisitor);
        Double res = countProportionVisitor.getData();

        assertEquals(1.0,res);
    }
    @Test
    void acceptTest_BuFenHeFa()
    {
        // ����2��ͶƱ��
        Voter vr1 = new Voter("v1");
        Voter vr2 = new Voter("v2");
        Voter vr3 = new Voter("v3");

        // �趨2��ͶƱ�˵�Ȩ��
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(vr1, 1.0);
        weightedVoters.put(vr2, 1.0);
        weightedVoters.put(vr3, 1.0);

        // �趨ͶƱ����
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType voteType = new VoteType(types);

        // ������ѡ���󣺺�ѡ��
        Person p1 = new Person("candidate1", 19);
        Person p2 = new Person("candidate2", 20);
        Person p3 = new Person("candidate3", 21);
        ArrayList<Person> candidates = new ArrayList<>();
        candidates.add(p1);
        candidates.add(p2);
        candidates.add(p3);
        // ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
        VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
        VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
        VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
        Set<VoteItem<Person>> voteItems1 = new HashSet<>();
        voteItems1.add(vi11);
        voteItems1.add(vi12);
        voteItems1.add(vi13);

        VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
        VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
        VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
        Set<VoteItem<Person>> voteItems2 = new HashSet<>();
        voteItems2.add(vi21);
        voteItems2.add(vi22);
        voteItems2.add(vi23);

        VoteItem<Person> vi31 = new VoteItem<>(p1, "Oppose");
        VoteItem<Person> vi32 = new VoteItem<>(p2, "Waive");
        Set<VoteItem<Person>> voteItems3 = new HashSet<>();
        voteItems3.add(vi31);
        voteItems3.add(vi32);

        // ����2��ͶƱ��vr1��vr2��ѡƱ
        Vote<Person> rv1 = new Vote<Person>(voteItems1, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
        Vote<Person> rv2 = new Vote<Person>(voteItems2, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
        Vote<Person> rv3 = new Vote<Person>(voteItems3, new GregorianCalendar(2019, 6, 14, 16, 15, 30));
//		System.out.println("rv1 = " + rv1);
//		System.out.println("rv2 = " + rv2);
        // ����ͶƱ�
        Election poll = new Election();
        // �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
        String name = "����ѡ��";
        GregorianCalendar date = new GregorianCalendar(2019, 6, 14, 16, 15, 30);
        int quantity = 2;
        poll.setInfo(name, date, voteType, quantity);

        // ����ͶƱ�˼���Ȩ��
        poll.addVoters(weightedVoters);
        poll.addCandidates(candidates);
        // ��������ͶƱ�˵�ѡƱ
        poll.addVote(rv1, vr1);
        poll.addVote(rv2, vr2);
        poll.addVote(rv3, vr3);
        CountProportionVisitor<Person> countProportionVisitor = new CountProportionVisitor<>();
        poll.accept(countProportionVisitor);
        Double res = countProportionVisitor.getData();
        assertEquals(2.0/3.0,res);
    }
}
