package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class TargetType.
 * @see au.csiro.feedback.model.TargetType
 * @author Hibernate Tools
 */
@Stateless
public class TargetTypeHome {

	private static final Log log = LogFactory.getLog(TargetTypeHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TargetType transientInstance) {
		log.debug("persisting TargetType instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TargetType persistentInstance) {
		log.debug("removing TargetType instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TargetType merge(TargetType detachedInstance) {
		log.debug("merging TargetType instance");
		try {
			TargetType result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TargetType findById(Integer id) {
		log.debug("getting TargetType instance with id: " + id);
		try {
			TargetType instance = entityManager.find(TargetType.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
