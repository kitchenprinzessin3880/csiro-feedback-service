package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class FeedbackCollection.
 * @see au.csiro.feedback.model.FeedbackCollection
 * @author Hibernate Tools
 */
@Stateless
public class FeedbackCollectionHome {

	private static final Log log = LogFactory.getLog(FeedbackCollectionHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(FeedbackCollection transientInstance) {
		log.debug("persisting FeedbackCollection instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(FeedbackCollection persistentInstance) {
		log.debug("removing FeedbackCollection instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public FeedbackCollection merge(FeedbackCollection detachedInstance) {
		log.debug("merging FeedbackCollection instance");
		try {
			FeedbackCollection result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FeedbackCollection findById(Integer id) {
		log.debug("getting FeedbackCollection instance with id: " + id);
		try {
			FeedbackCollection instance = entityManager.find(FeedbackCollection.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
