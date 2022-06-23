package pattern;

import auxiliary.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElectionSelectionStrategy<Person> implements SelectionStrategy<Person> {
    /**
     * 应用 2：选择排名前?(quantity)的候选人，若有多个候选人的支持票数量相等而无
     * 法自然排出前?名，则仅有那些明确可进入前?名的人当选；
     * @param statistics
     * @return
     */
    @Override
    public Map<Person, Double> selection(Map<Person, Double> statistics,int k) {
        //statistics的value->支持票总总数
        HashMap<Person, Double> result = new HashMap<>();
        //一般情况
        ArrayList<Person> candidates = new ArrayList<>();
        
    }
}
