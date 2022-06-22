package poll;

public class CanNotVoteException extends Exception{
    public CanNotVoteException() {
        super("目前无法进行投票");
    }

    public CanNotVoteException(String message) {
        super(message);
    }
}
