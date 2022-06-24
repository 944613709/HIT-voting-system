package pattern;

import auxiliary.Dish;
import vote.Vote;

import java.util.*;

public class DinnerOrderSelectionStrategy<Dish> implements SelectionStrategy<Dish> {
    /**
     * Ӧ�� 3��ѡ������ǰ?�Ĳˣ�����Ϊ�ж���˵÷���ȶ��޷���Ȼ�ų�ǰ
     * ?�����������Щ��ȷ�ɽ���ǰ?���Ĳ�֮�⣬�������÷���ȵĲ�����
     * ��ѡȡһ���֣�����?���ˡ�
     * @param statistics    ��Ʊ���
     * @param k ѡ����������
     * @param voteIsLegal   �Ƿ�Ϸ�
     * @return
     */
    @Override
    public Map<Dish, Double> selection(Map<Dish, Double> statistics, int k, Map<Vote<Dish>, Boolean> voteIsLegal) {
        //statistics��value->֧��Ʊ������
        HashMap<Dish, Double> result = new HashMap<>();
        //һ�����
        List<Map.Entry<Dish, Double>> res = new ArrayList();
        //res�������Ű�֧��Ʊ�������е�
        for(Map.Entry<Dish, Double> entry : statistics.entrySet()) {
            res.add(entry);
        }
        Collections.sort(res, new Comparator<Map.Entry<Dish, Double>>(){
            public int compare(Map.Entry<Dish, Double> e1, Map.Entry<Dish, Double> e2) {
                return e2.getValue().intValue()- e1.getValue().intValue();
            }
        });
        //��ǰk�����ȼ���
        Double Number=1.0;//����
        for (Map.Entry<Dish, Double> entry : res) {
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
        return result;
    }
}
