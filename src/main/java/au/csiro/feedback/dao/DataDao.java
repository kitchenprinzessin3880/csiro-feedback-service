package au.csiro.feedback.dao;

import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import au.csiro.feedback.model.FeedbackItem;

public interface DataDao {
	
	public ResponseEntity<? extends Object> addFeedback(MultipartHttpServletRequest files, 
			String feedbacktype, String feedbackuser, String doi, String message, 
			String domain, String owner, String title, boolean isPublic)throws Exception;
	public 	Map<String,Map<String, Map<String, Object>>> getFeedback(String doi) throws Exception;
}
