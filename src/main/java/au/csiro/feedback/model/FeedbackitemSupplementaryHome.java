package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class FeedbackitemSupplementary.
 * @see au.csiro.feedback.model.FeedbackitemSupplementary
 * @author Hibernate Tools
 */
@Stateless
public class FeedbackitemSupplementaryHome {

	private static final Log log = LogFactory.getLog(FeedbackitemSupplementaryHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(FeedbackitemSupplementary transientInstance) {
		log.debug("persisting FeedbackitemSupplementary instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(FeedbackitemSupplementary persistentInstance) {
		log.debug("removing FeedbackitemSupplementary instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public FeedbackitemSupplementary merge(FeedbackitemSupplementary detachedInstance) {
		log.debug("merging FeedbackitemSupplementary instance");
		try {
			FeedbackitemSupplementary result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public FeedbackitemSupplementary findById(Integer id) {
		log.debug("getting FeedbackitemSupplementary instance with id: " + id);
		try {
			FeedbackitemSupplementary instance = entityManager.find(FeedbackitemSupplementary.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
