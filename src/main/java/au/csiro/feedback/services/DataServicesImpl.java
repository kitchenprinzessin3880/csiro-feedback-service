package au.csiro.feedback.services;

import java.util.Map;
import java.util.Set;

import javax.xml.validation.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import au.csiro.feedback.dao.DataDao;
import au.csiro.feedback.model.FeedbackItem;

public class DataServicesImpl implements DataServices {

	@Autowired
	DataDao dataDao;

	static Schema schema = null;

	@Override
	public ResponseEntity<? extends Object> addFeedback(MultipartHttpServletRequest files, String feedbacktype,
			String feedbackuser, String doi, String message, String domain, String owner, String title, boolean isPublic)
			throws Exception {
		// TODO Auto-generated method stub
		return dataDao.addFeedback(files, feedbacktype, feedbackuser, doi, message, domain, owner, title,isPublic);
	}

	@Override
	public 	Map<String,Map<String, Map<String, Object>>> getFeedback(String doi) throws Exception {
		// TODO Auto-generated method stub
		return dataDao.getFeedback(doi);
	}

}
