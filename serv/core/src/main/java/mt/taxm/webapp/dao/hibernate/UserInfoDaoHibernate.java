package mt.taxm.webapp.dao.hibernate;

import java.util.List;
import mt.taxm.webapp.dao.UserInfoDao;

import org.appfuse.dao.hibernate.UserDaoHibernate;
import org.appfuse.model.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserInfoDaoHibernate extends UserDaoHibernate implements
		UserInfoDao {

	@Override
	public User getUserByName(String username) {
        List<?> users = getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (User) users.get(0);
        }		
	}

}
