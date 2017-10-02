package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:16 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * FeedbackItem generated by hbm2java
 */
@Entity
@Table(name = "feedback_item", catalog = "userfeedback")
public class FeedbackItem implements java.io.Serializable {

	private Integer feedbackId;
	private Status status;
	private FeedbackItem feedbackItem;
	private FeedbackCollection feedbackCollection;
	private FeedbackUser feedbackUser;
	private FeedbackTypes feedbackTypes;
	private Date createdDate;
	private Date lastModified;
	private String title;
	private String details;
	private Boolean isapproved;
	private Integer likeCount;
	private Set<FeedbackCollection> feedbackCollections = new HashSet<FeedbackCollection>(0);
	private Set<FeedbackitemSupplementary> feedbackitemSupplementaries = new HashSet<FeedbackitemSupplementary>(0);
	private Set<FeedbackItem> feedbackItems = new HashSet<FeedbackItem>(0);

	public FeedbackItem() {
	}

	public FeedbackItem(FeedbackUser feedbackUser, FeedbackTypes feedbackTypes, Date createdDate, String details) {
		this.feedbackUser = feedbackUser;
		this.feedbackTypes = feedbackTypes;
		this.createdDate = createdDate;
		this.details = details;
	}

	public FeedbackItem(Status status, FeedbackItem feedbackItem, FeedbackCollection feedbackCollection,
			FeedbackUser feedbackUser, FeedbackTypes feedbackTypes, Date createdDate, Date lastModified, String title,
			String details, Boolean isapproved, Integer likeCount, Set<FeedbackCollection> feedbackCollections,
			Set<FeedbackitemSupplementary> feedbackitemSupplementaries, Set<FeedbackItem> feedbackItems) {
		this.status = status;
		this.feedbackItem = feedbackItem;
		this.feedbackCollection = feedbackCollection;
		this.feedbackUser = feedbackUser;
		this.feedbackTypes = feedbackTypes;
		this.createdDate = createdDate;
		this.lastModified = lastModified;
		this.title = title;
		this.details = details;
		this.isapproved = isapproved;
		this.likeCount = likeCount;
		this.feedbackCollections = feedbackCollections;
		this.feedbackitemSupplementaries = feedbackitemSupplementaries;
		this.feedbackItems = feedbackItems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "feedback_id", unique = true, nullable = false)
	public Integer getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}


	@Cascade({CascadeType.ALL})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status")
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Cascade({CascadeType.ALL})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_feedback")
	public FeedbackItem getFeedbackItem() {
		return this.feedbackItem;
	}

	public void setFeedbackItem(FeedbackItem feedbackItem) {
		this.feedbackItem = feedbackItem;
	}

	@Cascade({CascadeType.ALL})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "feedback_collection_id")
	public FeedbackCollection getFeedbackCollection() {
		return this.feedbackCollection;
	}

	public void setFeedbackCollection(FeedbackCollection feedbackCollection) {
		this.feedbackCollection = feedbackCollection;
	}

	@Cascade({CascadeType.ALL})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user", nullable = false)
	public FeedbackUser getFeedbackUser() {
		return this.feedbackUser;
	}

	public void setFeedbackUser(FeedbackUser feedbackUser) {
		this.feedbackUser = feedbackUser;
	}


	@Cascade({CascadeType.ALL})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "purpose", nullable = false)
	public FeedbackTypes getFeedbackTypes() {
		return this.feedbackTypes;
	}

	public void setFeedbackTypes(FeedbackTypes feedbackTypes) {
		this.feedbackTypes = feedbackTypes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified", length = 19)
	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "details", nullable = false, length = 300)
	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Column(name = "isapproved")
	public Boolean getIsapproved() {
		return this.isapproved;
	}

	public void setIsapproved(Boolean isapproved) {
		this.isapproved = isapproved;
	}

	@Column(name = "like_count")
	public Integer getLikeCount() {
		return this.likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "feedbackItem")
	public Set<FeedbackCollection> getFeedbackCollections() {
		return this.feedbackCollections;
	}

	public void setFeedbackCollections(Set<FeedbackCollection> feedbackCollections) {
		this.feedbackCollections = feedbackCollections;
	}

	@Cascade({CascadeType.ALL})
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "feedbackItem")
	public Set<FeedbackitemSupplementary> getFeedbackitemSupplementaries() {
		return this.feedbackitemSupplementaries;
	}

	public void setFeedbackitemSupplementaries(Set<FeedbackitemSupplementary> feedbackitemSupplementaries) {
		this.feedbackitemSupplementaries = feedbackitemSupplementaries;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "feedbackItem")
	public Set<FeedbackItem> getFeedbackItems() {
		return this.feedbackItems;
	}

	public void setFeedbackItems(Set<FeedbackItem> feedbackItems) {
		this.feedbackItems = feedbackItems;
	}

}