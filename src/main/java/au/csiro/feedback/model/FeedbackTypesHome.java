package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class FeedbackTypes.
 * @see au.csiro.feedback.model.FeedbackTypes
 * @author Hibernate Tools
 */
@Stateless
public class FeedbackTypesHome {

	private static final Log log = LogFactory.getLog(FeedbackTypesHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(FeedbackTypes transientInstance) {
		log.debug("persisting FeedbackTypes instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(FeedbackTypes persistentInstance) {
		log.debug("removing FeedbackTypes instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public FeedbackTypes merge(FeedbackTypes detachedInstance) {
		log.debug("merging FeedbackTypes instance");
		try {
			FeedbackTypes result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FeedbackTypes findById(Integer id) {
		log.debug("getting FeedbackTypes instance with id: " + id);
		try {
			FeedbackTypes instance = entityManager.find(FeedbackTypes.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
