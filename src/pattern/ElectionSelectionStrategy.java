package pattern;

import auxiliary.Person;
import vote.Vote;

import java.awt.font.FontRenderContext;
import java.util.*;

public class ElectionSelectionStrategy<Person> implements SelectionStrategy<Person> {
    /**
     * Ӧ�� 2��ѡ������ǰ?(quantity)�ĺ�ѡ�ˣ�
     * ���ж����ѡ�˵�֧��Ʊ������ȶ��޷���Ȼ�ų�ǰ?�����������Щ��ȷ�ɽ���ǰ?�����˵�ѡ��
     * @param statistics
     * @return
     */
    @Override
    public Map<Person, Double> selection(Map<Person, Double> statistics, int k, Map<Vote<Person>, Boolean> voteIsLegal) {
        //statistics��value->֧��Ʊ������
        HashMap<Person, Double> result = new HashMap<>();
        //һ�����
        List<Map.Entry<Person, Double>> res = new ArrayList();
        //res�������Ű�֧��Ʊ�������е�
        for(Map.Entry<Person, Double> entry : statistics.entrySet()) {
            res.add(entry);
        }
        Collections.sort(res, new Comparator<Map.Entry<Person, Double>>(){
            public int compare(Map.Entry<Person, Double> e1, Map.Entry<Person, Double> e2) {
                    return e2.getValue().intValue()- e1.getValue().intValue();
            }
        });
//        System.out.println("res = " + res);
        //��ǰk�����ȼ���
        Double Number=1.0;//����
        for (Map.Entry<Person, Double> entry : res) {
            if(Number<=k)
            {
                result.put(entry.getKey(), Number);
                Number=Number+1;
            }
            else if(Number>k)//�������ǵ�k��Ҫ����ʱ��
            {
                break;
            }
        }
//        System.out.println("res = " + res);
        //����Ƿ����ж����ѡ�˵�֧��Ʊ������ȶ��޷���Ȼ�ų�ǰ?�����������Щ��ȷ�ɽ���ǰ?�����˵�ѡ��
        ArrayList<Person> peopleWillDelete = new ArrayList<>();//��Ҫɾ��������
        if (k<res.size() && Objects.equals(res.get(k - 1).getValue(), res.get(k).getValue()))//index=k-1�����k�����������ͺ�����ͬ����Ҫɾ��
        {
            peopleWillDelete.add(res.get(k-1).getKey());
            int i=1;
            while(k-1-i>0 && res.get(k-1-i).getValue()==res.get(k-1).getKey())//��ǰ��
            {
            peopleWillDelete.add(res.get(k-1-i).getKey());
            i++;
            }
        }
//        System.out.println("peopleWillDelete = " + peopleWillDelete);
//        System.out.println("result = " + result);
        for (Person person : peopleWillDelete) {
            result.remove(person);
        }
//        System.out.println("result = " + result);
        return result;
    }

}
