package mt.taxm.webapp.service.imp;

import java.util.List;

import org.appfuse.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import mt.taxm.webapp.service.LoginManager;

public class LoginManagerImp implements LoginManager {
	@Autowired
	private UserDao userdao; 
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SaltSource saltSource;
	
	public LoginManagerImp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> validate(String userid, String password) {
		String pwd_db = userdao.getUserPassword(userid);
		
		// TODO Auto-generated method stub
		return null;
	}

}
