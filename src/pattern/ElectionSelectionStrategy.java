package pattern;

import auxiliary.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElectionSelectionStrategy<Person> implements SelectionStrategy<Person> {
    /**
     * Ӧ�� 2��ѡ������ǰ?(quantity)�ĺ�ѡ�ˣ����ж����ѡ�˵�֧��Ʊ������ȶ���
     * ����Ȼ�ų�ǰ?�����������Щ��ȷ�ɽ���ǰ?�����˵�ѡ��
     * @param statistics
     * @return
     */
    @Override
    public Map<Person, Double> selection(Map<Person, Double> statistics,int k) {
        //statistics��value->֧��Ʊ������
        HashMap<Person, Double> result = new HashMap<>();
        //һ�����
        ArrayList<Person> candidates = new ArrayList<>();
        
    }
}
