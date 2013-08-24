package mt.taxm.app;

/**
 * 用户登录超时异常
 * @author fredzhu
 *
 */
public class SessionException extends Exception {

	private static final long serialVersionUID = 1L;

	public SessionException(String message){
		super(message);
	}
	
}
