package au.csiro.feedback.dao;

import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.helpers.DefaultHandler;

import au.csiro.feedback.model.FeedbackCollection;
import au.csiro.feedback.model.FeedbackItem;
import au.csiro.feedback.model.FeedbackTarget;
import au.csiro.feedback.model.FeedbackTypes;
import au.csiro.feedback.model.FeedbackUser;
import au.csiro.feedback.model.FeedbackcollectionTarget;
import au.csiro.feedback.model.FeedbackitemSupplementary;
import au.csiro.feedback.model.Status;
import au.csiro.feedback.model.SupplementaryFiles;
import au.csiro.feedback.model.TargetContext;

public class DataDaoImpl implements DataDao {

	static final Logger log = Logger.getLogger(DataDaoImpl.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	ServletContext servletContext;

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	
	@Value("#{myProps['SUPPLEMENTARY_FILES_PATH']}")
	private String SUPPLEMENTARY_FILES_PATH;

	@Transactional
	@Override
	public ResponseEntity<? extends Object> addFeedback(MultipartHttpServletRequest multiPart, String feedbacktype,
			String feedbackuser, String doi, String message, String domain, String owner, String title, boolean isPublic)
			throws Exception {
		FeedbackUser user = null;
		Timestamp time = getCurrentTimeStamp();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		ResponseEntity<? extends Object> response = null;

		try {
			// user
			List<FeedbackUser> users = getUserById(feedbackuser);
			if (users.isEmpty()) {
				user = new FeedbackUser();
				user.setEmail(feedbackuser);
				user.setDateFirstFeedback(time);
				user.setDateLatestFeedback(time);
				// log.debug("user new.." + user.getEmail());
			} else {
				user = users.get(0);
				// log.debug("user exusts.." + user.getEmail());
				user.setDateLatestFeedback(time);
			}

			List<TargetContext> contextList = getTargetContextBySource(domain);
			TargetContext context = null;
			if (contextList.isEmpty()) {
				context = new TargetContext();
				context.setOwner(owner);
				context.setSource(domain);
				context.setAccessDate(time);
				// log.debug("new context.." + context.getSource());
			} else {
				context = contextList.get(0);
				// log.debug("old context.." + context.getSource());
			}

			// feedback target
			List<FeedbackTarget> targetList = getTargetByDOI(doi);
			FeedbackTarget target = null;
			if (targetList.isEmpty()) {
				target = new FeedbackTarget();
				target.setTargetIdentifier(doi);
				// log.debug("new target.." + target.getTargetIdentifier());
			} else {
				target = targetList.get(0);
				// log.debug("old target.." + target.getTargetIdentifier());
			}
			context.setFeedbackTarget(target);

			// feedback collection
			FeedbackCollection feedbackcol = new FeedbackCollection(1, time);

			// feeback col <> target
			FeedbackcollectionTarget collectionToTarget = new FeedbackcollectionTarget();
			collectionToTarget.setFeedbackCollection(feedbackcol);
			collectionToTarget.setFeedbackTarget(target);

			Set<FeedbackcollectionTarget> colTargetList = new HashSet<FeedbackcollectionTarget>();
			colTargetList.add(collectionToTarget);
			feedbackcol.setFeedbackcollectionTargets(colTargetList);

			String stat = "public";
			if (!isPublic) {
				stat = "private";
			}
			Status status = getStatusType(stat);

			// feedback types
			FeedbackTypes feedbackType = getFeedbackType(feedbacktype);

			// feedback item
			FeedbackItem feedbackItem = new FeedbackItem(user, feedbackType, time, message);
			feedbackItem.setStatus(status);
			feedbackItem.setFeedbackCollection(feedbackcol);

			// files
			Set<FeedbackitemSupplementary> suppList = new HashSet<FeedbackitemSupplementary>();
			CommonsMultipartFile multipartFile = null;
			if (multiPart.getMultiFileMap().size() > 0) {
				Iterator<String> iterator = multiPart.getFileNames();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					multipartFile = (CommonsMultipartFile) multiPart.getFile(key);
					SupplementaryFiles suppFile = new SupplementaryFiles();
					suppFile.setFileName(multipartFile.getOriginalFilename());
					suppFile.setFileExtension(multipartFile.getContentType());
					suppFile.setFile(multipartFile.getBytes());
					suppFile.setFileSize(multipartFile.getSize());
					FeedbackitemSupplementary itemSupplement = new FeedbackitemSupplementary();
					itemSupplement.setSupplementaryFiles(suppFile);
					itemSupplement.setFeedbackItem(feedbackItem);
					suppList.add(itemSupplement);
				}
			}
			feedbackItem.setFeedbackitemSupplementaries(suppList);
			feedbackcol.setFeedbackItem(feedbackItem);
			session.saveOrUpdate(feedbackItem);
			session.saveOrUpdate(context);
			session.saveOrUpdate(feedbackcol);
			
			/*
			FeedbackItem savedItem = (FeedbackItem)session.get(FeedbackItem.class, new Integer(feedbackItem.getFeedbackId()));
			Set<FeedbackitemSupplementary> fs = savedItem.getFeedbackitemSupplementaries();
			for (FeedbackitemSupplementary supp : fs) {
				SupplementaryFiles file = supp.getSupplementaryFiles();
				saveFile(file);
			}*/
			tx.commit();
			response = new ResponseEntity<String>("Records saved!", new HttpHeaders(), HttpStatus.CREATED);
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				throw e;
			}
			response = new ResponseEntity<String>("Database insertion error", new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			session.close();
		}
		return response;
	}

	private void saveFile(SupplementaryFiles file) {
		String message = "";
		//String name = file.getOriginalFilename();
		String name = file.getFileName();
		String id = Integer.toString(file.getFileId());
		//String type = file.getFileExtension();
		try {
			//byte[] bytes = file.getBytes();
			byte[] bytes = file.getFile();

			// Creating the directory to store file
			// String rootPath = System.getProperty("catalina.home");
			String rootPath = servletContext.getRealPath("/files/");
			// File dir = new File(rootPath + File.separator + FILES_FOLDER);
			File dir = new File(rootPath);
			if (!dir.exists())
				dir.mkdirs();

			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + id+"_"+name);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			log.debug("Server File Location=" + serverFile.getAbsolutePath());
		} catch (Exception e) {
			log.debug("Failed to upload " + name + " => " + e.getMessage());
		}
	}

	@Transactional
	@Override
	public Map<String,Map<String, Map<String, Object>>> getFeedback(String doi) throws Exception {
		Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String, Object>>();
		Map<String,Map<String, Map<String, Object>>> finalMap = new HashMap<String,Map<String, Map<String, Object>>>();
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("from FeedbackTarget where targetIdentifier = :code ");
			query.setParameter("code", doi);
			FeedbackTarget feedbackTarget = (FeedbackTarget) query.uniqueResult();
			log.debug("DOI :" + feedbackTarget.getTargetIdentifier());

			Set<FeedbackcollectionTarget> colTargets = feedbackTarget.getFeedbackcollectionTargets();
			for (FeedbackcollectionTarget s : colTargets) {
				FeedbackCollection c = s.getFeedbackCollection();
				Set<FeedbackItem> itemTemp = c.getFeedbackItems();
				for (FeedbackItem ori : itemTemp) {
					Map<String, Object> childMap = new HashMap<String, Object>();

					childMap.put("date", convertTime(ori.getCreatedDate().getTime()));
					childMap.put("details", ori.getDetails());
					childMap.put("type", ori.getFeedbackTypes().getFeedbacktypeCode());

					if (ori.getStatus().getCode().equalsIgnoreCase("private")) {
						// user.setEmail("anonymous");
						childMap.put("user", "anonymous");
					} else {
						// user.setEmail(ori.getFeedbackUser().getEmail());
						childMap.put("user", ori.getFeedbackUser().getEmail());
					}
					String fileName ="";
					//get supplementary files
					Set<FeedbackitemSupplementary> fs = ori.getFeedbackitemSupplementaries();
					List<String> filesList = new ArrayList<String>();
					for (FeedbackitemSupplementary supp : fs) {
						SupplementaryFiles file = supp.getSupplementaryFiles();
						//fileName += file.getFileName()+",";
						saveFile(file);
						filesList.add(SUPPLEMENTARY_FILES_PATH+file.getFileId()+"_"+file.getFileName());
					}
					childMap.put("files", filesList);
					dataMap.put(ori.getFeedbackId().toString(), childMap);
				}
			}
			log.debug("Total feedbacks :" + dataMap.size());

			// String q =
			// "SELECT feedback_id, purpose, details FROM feedback_item fi, feedback_collection fc, "
			// +
			// "feedbackcollection_target fct WHERE fi.feedback_collection_id=fc.collection_id "
			// +
			// "AND fc.collection_id =fct.feedback_collection_id AND fct.target_id =:id";

		} catch (Exception e) {

		} finally {
			session.close();
		}
		finalMap.put("results", dataMap);
		return finalMap;
	}

	private String trim(String str) {
	    if (str.length() > 0 && str.charAt(str.length()-1)==',') {
	      str = str.substring(0, str.length()-1);
	    }
	    return str;
	}
	
	private String convertTime(long time) {
		Date date = new Date(time);
		return sdf.format(date);
	}

	@Transactional
	public List<TargetContext> getTargetContextBySource(String a) throws Exception {
		List<TargetContext> result = null;
		String hql = "from TargetContext where source =:a";
		result = session.createQuery(hql).setParameter("a", a).list();
		return result;
	}

	@Transactional
	public List<FeedbackUser> getUserById(String email) throws Exception {
		List<FeedbackUser> result = null;
		String hql = "from FeedbackUser where email =:eml";
		result = session.createQuery(hql).setParameter("eml", email).list();
		return result;
	}

	@Transactional
	public List<FeedbackTarget> getTargetByDOI(String doi) throws Exception {
		String hql = "from FeedbackTarget where targetIdentifier =:doi";
		List<FeedbackTarget> result = session.createQuery(hql).setParameter("doi", doi).list();
		return result;
	}

	@Transactional
	private Status getStatusType(String typeValue) {
		// sample type: Status code is unique
		String hql = "from Status where code =:type";
		Status result = (Status) session.createQuery(hql).setParameter("type", typeValue).uniqueResult();
		return result;
	}

	@Transactional
	private FeedbackTypes getFeedbackType(String typeValue) {
		// sample type: Status code is unique
		String hql = "from FeedbackTypes where feedbacktypeCode =:type";
		List<FeedbackTypes> result = session.createQuery(hql).setParameter("type", typeValue).list();
		if (result != null) {
			return result.get(0);
		} else {
			return null;
		}
	}

	private java.sql.Timestamp getCurrentTimeStamp() {
		// Calendar calendar = Calendar.getInstance();
		// java.util.Date now = calendar.getTime();
		// java.sql.Timestamp currentTimestamp = new
		// java.sql.Timestamp(now.getTime());
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}

}
