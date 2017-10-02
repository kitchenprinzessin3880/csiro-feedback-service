package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class TargetContext.
 * @see au.csiro.feedback.model.TargetContext
 * @author Hibernate Tools
 */
@Stateless
public class TargetContextHome {

	private static final Log log = LogFactory.getLog(TargetContextHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TargetContext transientInstance) {
		log.debug("persisting TargetContext instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TargetContext persistentInstance) {
		log.debug("removing TargetContext instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TargetContext merge(TargetContext detachedInstance) {
		log.debug("merging TargetContext instance");
		try {
			TargetContext result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TargetContext findById(Integer id) {
		log.debug("getting TargetContext instance with id: " + id);
		try {
			TargetContext instance = entityManager.find(TargetContext.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
