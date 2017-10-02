package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:20 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class SupplementaryFiles.
 * @see au.csiro.feedback.model.SupplementaryFiles
 * @author Hibernate Tools
 */
@Stateless
public class SupplementaryFilesHome {

	private static final Log log = LogFactory.getLog(SupplementaryFilesHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SupplementaryFiles transientInstance) {
		log.debug("persisting SupplementaryFiles instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SupplementaryFiles persistentInstance) {
		log.debug("removing SupplementaryFiles instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SupplementaryFiles merge(SupplementaryFiles detachedInstance) {
		log.debug("merging SupplementaryFiles instance");
		try {
			SupplementaryFiles result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SupplementaryFiles findById(Integer id) {
		log.debug("getting SupplementaryFiles instance with id: " + id);
		try {
			SupplementaryFiles instance = entityManager.find(SupplementaryFiles.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
