package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class FeedbackTarget.
 * @see au.csiro.feedback.model.FeedbackTarget
 * @author Hibernate Tools
 */
@Stateless
public class FeedbackTargetHome {

	private static final Log log = LogFactory.getLog(FeedbackTargetHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(FeedbackTarget transientInstance) {
		log.debug("persisting FeedbackTarget instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(FeedbackTarget persistentInstance) {
		log.debug("removing FeedbackTarget instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public FeedbackTarget merge(FeedbackTarget detachedInstance) {
		log.debug("merging FeedbackTarget instance");
		try {
			FeedbackTarget result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FeedbackTarget findById(Integer id) {
		log.debug("getting FeedbackTarget instance with id: " + id);
		try {
			FeedbackTarget instance = entityManager.find(FeedbackTarget.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
