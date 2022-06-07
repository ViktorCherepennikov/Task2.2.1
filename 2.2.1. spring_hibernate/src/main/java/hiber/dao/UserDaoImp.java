package hiber.dao;

import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;
   public User getUser(String model, int series) {
      String HQL="from User u where u.car.model = :model and u.car.series = :series";
      try {
         Session session = sessionFactory.getCurrentSession();
         Query query = session.createQuery(HQL);
         query.setParameter("model",model);
         query.setParameter("series",series);
         return ((User) query.getSingleResult());
      } catch (HibernateException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

}
