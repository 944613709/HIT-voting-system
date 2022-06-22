package poll;

/**
 * 一张选票中有对同一个候选对象的多次投票
 */
public class RepeatCandidateException extends Exception{
    /**
     * 一张选票中有对同一个候选对象的多次投票
     */
    public RepeatCandidateException() {
        super("一张选票中有对同一个候选对象的多次投票");
    }
}
