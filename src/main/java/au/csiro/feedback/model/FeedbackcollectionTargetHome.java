package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class FeedbackcollectionTarget.
 * @see au.csiro.feedback.model.FeedbackcollectionTarget
 * @author Hibernate Tools
 */
@Stateless
public class FeedbackcollectionTargetHome {

	private static final Log log = LogFactory.getLog(FeedbackcollectionTargetHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(FeedbackcollectionTarget transientInstance) {
		log.debug("persisting FeedbackcollectionTarget instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(FeedbackcollectionTarget persistentInstance) {
		log.debug("removing FeedbackcollectionTarget instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public FeedbackcollectionTarget merge(FeedbackcollectionTarget detachedInstance) {
		log.debug("merging FeedbackcollectionTarget instance");
		try {
			FeedbackcollectionTarget result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FeedbackcollectionTarget findById(Integer id) {
		log.debug("getting FeedbackcollectionTarget instance with id: " + id);
		try {
			FeedbackcollectionTarget instance = entityManager.find(FeedbackcollectionTarget.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
