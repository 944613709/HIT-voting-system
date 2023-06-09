package pattern;

import auxiliary.Person;
import vote.Vote;

import java.awt.font.FontRenderContext;
import java.util.*;

public class ElectionSelectionStrategy<Person> implements SelectionStrategy<Person> {
    /**
     * 应用 2：选择排名前k(quantity)的候选人，
     * 若有多个候选人的支持票数量相等而无法自然排出前k名，则仅有那些明确可进入前k名的人当选；
     * @param statistics 计票结果
     * @return  返回遴选结果
     */
    @Override
    public Map<Person, Double> selection(Map<Person, Double> statistics, int k, Map<Vote<Person>, Boolean> voteIsLegal) {
        //statistics的value->支持票总总数
        HashMap<Person, Double> result = new HashMap<>();
        //若可以直接前k个
        List<Map.Entry<Person, Double>> res = new ArrayList();
        //res里面存放着按支持票总数排列的
        for(Map.Entry<Person, Double> entry : statistics.entrySet()) {
            res.add(entry);
        }
            Collections.sort(res, new Comparator<Map.Entry<Person, Double>>(){
            public int compare(Map.Entry<Person, Double> e1, Map.Entry<Person, Double> e2) {
                    return e2.getValue().intValue()- e1.getValue().intValue();
            }
        });
        //将前k个都先加入
        Double Number=1.0;//排名
        for (Map.Entry<Person, Double> entry : res) {
            if(Number<=k)
            {
                result.put(entry.getKey(), Number);
                Number=Number+1;
            }
            else if(Number>k)//当这里是第k个要加入时候
            {
                break;
            }
        }
        //检查是否若有多个候选人的支持票数量相等而无法自然排出前k名，则仅有那些明确可进入前k名的人当选；
        ArrayList<Person> peopleWillDelete = new ArrayList<>();//将要删除的名单
        if (k<res.size() && Objects.equals(res.get(k - 1).getValue(), res.get(k).getValue()))//index=k-1代表第k个，如果这个和后面相同则需要删掉
        {
            peopleWillDelete.add(res.get(k-1).getKey());
            int i=1;
            while(k-1-i>0 && Objects.equals(res.get(k - 1 - i).getValue(), res.get(k - 1).getValue()))//若前者
            {
            peopleWillDelete.add(res.get(k-1-i).getKey());
            i++;
            }
        }
        for (Person person : peopleWillDelete) {
            result.remove(person);
        }
        return result;
    }

}
