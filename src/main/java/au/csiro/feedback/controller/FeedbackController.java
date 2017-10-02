package au.csiro.feedback.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import au.csiro.feedback.model.FeedbackItem;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
	static final Logger log = Logger.getLogger(FeedbackController.class);
	
	@Value("#{myProps['SUPPLEMENTARY_FILES_PATH']}")
	private String SUPPLEMENTARY_FILES_PATH;
	
	@Autowired
	ServletContext servletContext;

	@Autowired
	au.csiro.feedback.services.DataServices dataServices;

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.HEAD })
	public ResponseEntity<?> getRoot() {
		return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST, RequestMethod.HEAD })
	public ResponseEntity addFeedback(HttpServletRequest request) {
		ResponseEntity<? extends Object> response = null;

		Map<String, String> parameterMap = request.getParameterMap();
		Iterator<?> it = parameterMap.entrySet().iterator();
		String feedbacktype = "";
		String feedbackuserEml = "";
		String targetdoi = "";
		String message = "";
		String domainUrl = "";
		String owner = "";
		String dataTitle = "";
		boolean isPublic = true;

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String[] value = (String[]) pair.getValue();

			if (pair.getKey().equals("type")) {
				feedbacktype = value[0];
			} else if (pair.getKey().equals("email")) {
				feedbackuserEml = value[0];
			} else if (pair.getKey().equals("doi")) {
				targetdoi = value[0];
			} else if (pair.getKey().equals("message")) {
				message = value[0];
			} else if (pair.getKey().equals("url")) {
				domainUrl = value[0];
			} else if (pair.getKey().equals("owner")) {
				owner = value[0];
			} else if (pair.getKey().equals("title")) {
				dataTitle = value[0];
			} else if (pair.getKey().equals("ispublic")) {
				if (value[0].equals("0")) {
					isPublic = false;
				}
			}
		}
		log.debug("Feedback :" + feedbacktype + "--" + feedbackuserEml + "--" + targetdoi + "--" + message + "--"
				+ domainUrl + "--" + owner + "--" + dataTitle + "--" + isPublic);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile multipartFile = null;
		if (multipartRequest.getMultiFileMap().size() > 0) {
			Iterator<String> iterator = multipartRequest.getFileNames();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				// create multipartFile array if you upload multiple files
				multipartFile = (CommonsMultipartFile) multipartRequest.getFile(key);
				log.debug(multipartFile.getOriginalFilename() + "-- " + multipartFile.getSize() + "-- "
						+ multipartFile.getContentType());
			}
		}

		try {
			response = dataServices.addFeedback(multipartRequest, feedbacktype, feedbackuserEml, targetdoi, message,
					domainUrl, owner, dataTitle, isPublic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/list/**", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retriveFeedback(HttpServletRequest request) {
		String doi = request.getParameter("doi");
		// @RequestMapping(value = "/user/by-email/{email:.+}")
		Map<String,Map<String, Map<String, Object>>> items = null;
		ResponseEntity<? extends Object> response = null;
		try {
			items = dataServices.getFeedback(doi);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (items != null) {
			return response = new ResponseEntity<>(items, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Feedback not found for the specified DOI!", new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
