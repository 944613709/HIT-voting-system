package poll;

public class CanNotVoteException extends Exception{
    public CanNotVoteException() {
        super("Ŀǰ�޷�����ͶƱ");
    }

    public CanNotVoteException(String message) {
        super(message);
    }
}
