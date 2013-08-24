package mt.taxm.webapp.dao;

import org.appfuse.dao.UserDao;
import org.appfuse.model.User;

/*用户信息查询服务*/
public interface UserInfoDao extends UserDao {
	/*通过用户名，查用户详细信息*/
	public User getUserByName(String username);	
}
