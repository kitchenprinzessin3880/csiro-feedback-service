package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class FeedbackItem.
 * @see au.csiro.feedback.model.FeedbackItem
 * @author Hibernate Tools
 */
@Stateless
public class FeedbackItemHome {

	private static final Log log = LogFactory.getLog(FeedbackItemHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(FeedbackItem transientInstance) {
		log.debug("persisting FeedbackItem instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(FeedbackItem persistentInstance) {
		log.debug("removing FeedbackItem instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public FeedbackItem merge(FeedbackItem detachedInstance) {
		log.debug("merging FeedbackItem instance");
		try {
			FeedbackItem result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FeedbackItem findById(Integer id) {
		log.debug("getting FeedbackItem instance with id: " + id);
		try {
			FeedbackItem instance = entityManager.find(FeedbackItem.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
