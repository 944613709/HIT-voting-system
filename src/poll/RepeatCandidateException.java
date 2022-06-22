package poll;

public class RepeatCandidateException extends Exception{
    public RepeatCandidateException() {
        super("一张选票中有对同一个候选对象的多次投票");
    }
}
