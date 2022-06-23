package pattern;

import auxiliary.Person;

import java.util.*;

public class ElectionSelectionStrategy<Person> implements SelectionStrategy<Person> {
    /**
     * Ӧ�� 2��ѡ������ǰ?(quantity)�ĺ�ѡ�ˣ�
     * ���ж����ѡ�˵�֧��Ʊ������ȶ��޷���Ȼ�ų�ǰ?�����������Щ��ȷ�ɽ���ǰ?�����˵�ѡ��
     * @param statistics
     * @return
     */
    @Override
    public Map<Person, Double> selection(Map<Person, Double> statistics,int k) {
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
                    return e1.getValue().intValue()- e2.getValue().intValue();
            }
        });
        Double Number=1.0;//����
        for (Map.Entry<Person, Double> entry : res) {
            result.put(entry.getKey(), Number);

            Number=Number+1;
        }

    }
}
