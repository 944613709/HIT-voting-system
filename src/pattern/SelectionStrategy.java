package pattern;

import java.util.Map;

public interface SelectionStrategy<C> {

    Map<C, Double> selection(Map<C, Double> statistics,int quantity);

}
