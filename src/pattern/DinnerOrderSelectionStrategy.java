package pattern;

import auxiliary.Dish;
import vote.Vote;

import java.util.*;

public class DinnerOrderSelectionStrategy<Dish> implements SelectionStrategy<Dish> {
    /**
     * 应用 3：选择排名前?的菜，若因为有多道菜得分相等而无法自然排出前
     * ?名，则除了那些明确可进入前?名的菜之外，在其他得分相等的菜中随
     * 机选取一部分，凑足?个菜。
     * @param statistics    计票结果
     * @param k 选出来多少人
     * @param voteIsLegal   是否合法
     * @return
     */
    @Override
    public Map<Dish, Double> selection(Map<Dish, Double> statistics, int k, Map<Vote<Dish>, Boolean> voteIsLegal) {
        //statistics的value->支持票总总数
        HashMap<Dish, Double> result = new HashMap<>();
        //一般情况
        List<Map.Entry<Dish, Double>> res = new ArrayList();
        //res里面存放着按支持票总数排列的
        for(Map.Entry<Dish, Double> entry : statistics.entrySet()) {
            res.add(entry);
        }
        Collections.sort(res, new Comparator<Map.Entry<Dish, Double>>(){
            public int compare(Map.Entry<Dish, Double> e1, Map.Entry<Dish, Double> e2) {
                return e2.getValue().intValue()- e1.getValue().intValue();
            }
        });
        //将前k个都先加入
        Double Number=1.0;//排名
        for (Map.Entry<Dish, Double> entry : res) {
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
        return result;
    }
}
