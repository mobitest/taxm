package mt.taxm.webapp.service.imp;

import javax.annotation.Resource;

import org.appfuse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import mt.taxm.webapp.dao.UserInfoDao;
import mt.taxm.webapp.service.LoginManager;

public class LoginManagerImp implements LoginManager {
	private UserInfoDao userdao; 
	
	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager; // specific for Spring Security
	
	public LoginManagerImp() {
		super();
	}

	@Autowired
	public LoginManagerImp(UserInfoDao userdao) {
		super();
		this.userdao = userdao;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User auth(String username, String password) {
		User user;
		
		if(login(username,password)) {
			user = userdao.getUserByName(username);
		}else{
			user = new User(username);
			user.setId(0L);
		}
		
		return user;
	}
	
	/*调用认证服务*/
	private boolean login(String username, String password) {
	    try {
	        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        if (authenticate.isAuthenticated()) {
	            SecurityContextHolder.getContext().setAuthentication(authenticate);             
	            return true;
	        }
	    }
	    catch (AuthenticationException e) { 
	    	e.printStackTrace();
	    }
	    return false;
	}

}
