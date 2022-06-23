package pattern;

import auxiliary.Person;

import java.util.*;

public class ElectionSelectionStrategy<Person> implements SelectionStrategy<Person> {
    /**
     * 应用 2：选择排名前?(quantity)的候选人，
     * 若有多个候选人的支持票数量相等而无法自然排出前?名，则仅有那些明确可进入前?名的人当选；
     * @param statistics
     * @return
     */
    @Override
    public Map<Person, Double> selection(Map<Person, Double> statistics,int k) {
        //statistics的value->支持票总总数
        HashMap<Person, Double> result = new HashMap<>();
        //一般情况
        List<Map.Entry<Person, Double>> res = new ArrayList();
        //res里面存放着按支持票总数排列的
        for(Map.Entry<Person, Double> entry : statistics.entrySet()) {
            res.add(entry);
        }
        Collections.sort(res, new Comparator<Map.Entry<Person, Double>>(){
            public int compare(Map.Entry<Person, Double> e1, Map.Entry<Person, Double> e2) {
                    return e1.getValue().intValue()- e2.getValue().intValue();
            }
        });
        Double Number=1.0;//排名
        for (Map.Entry<Person, Double> entry : res) {
            result.put(entry.getKey(), Number);

            Number=Number+1;
        }

    }
}
