package mt.taxm.app;

/**
 * Using this to substitute SocketTimeoutException.
 *
 */
public class DisconnectException extends Exception{

    private static final long serialVersionUID = 1L;

    public DisconnectException() {
        super();
    }

    public DisconnectException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DisconnectException(String detailMessage) {
        super(detailMessage);
    }

    public DisconnectException(Throwable throwable) {
        super(throwable);
    }
    
    

}
