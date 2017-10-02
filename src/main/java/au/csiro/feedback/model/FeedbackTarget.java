package au.csiro.feedback.model;

// Generated 24/10/2015 11:50:16 AM by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * FeedbackTarget generated by hbm2java
 */
@Entity
@Table(name = "feedback_target", catalog = "userfeedback", uniqueConstraints = @UniqueConstraint(columnNames = "target_identifier"))
public class FeedbackTarget implements java.io.Serializable {

	private Integer targetId;
	private TargetType targetType;
	private String targetName;
	private String targetIdentifier;
	private String otherIdentifier;
	private Set<FeedbackcollectionTarget> feedbackcollectionTargets = new HashSet<FeedbackcollectionTarget>(0);
	private Set<TargetContext> targetContexts = new HashSet<TargetContext>(0);

	public FeedbackTarget() {
	}

	public FeedbackTarget(String targetIdentifier) {
		this.targetIdentifier = targetIdentifier;
	}

	public FeedbackTarget(TargetType targetType, String targetName, String targetIdentifier, String otherIdentifier,
			Set<FeedbackcollectionTarget> feedbackcollectionTargets, Set<TargetContext> targetContexts) {
		this.targetType = targetType;
		this.targetName = targetName;
		this.targetIdentifier = targetIdentifier;
		this.otherIdentifier = otherIdentifier;
		this.feedbackcollectionTargets = feedbackcollectionTargets;
		this.targetContexts = targetContexts;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "target_id", unique = true, nullable = false)
	public Integer getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	@Cascade({CascadeType.ALL})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_type")
	public TargetType getTargetType() {
		return this.targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	@Column(name = "target_name", length = 100)
	public String getTargetName() {
		return this.targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	@Column(name = "target_identifier", unique = true, nullable = false, length = 200)
	public String getTargetIdentifier() {
		return this.targetIdentifier;
	}

	public void setTargetIdentifier(String targetIdentifier) {
		this.targetIdentifier = targetIdentifier;
	}

	@Column(name = "other_identifier", length = 200)
	public String getOtherIdentifier() {
		return this.otherIdentifier;
	}

	public void setOtherIdentifier(String otherIdentifier) {
		this.otherIdentifier = otherIdentifier;
	}

	@Cascade({CascadeType.ALL})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "feedbackTarget")
	public Set<FeedbackcollectionTarget> getFeedbackcollectionTargets() {
		return this.feedbackcollectionTargets;
	}

	public void setFeedbackcollectionTargets(Set<FeedbackcollectionTarget> feedbackcollectionTargets) {
		this.feedbackcollectionTargets = feedbackcollectionTargets;
	}

	@Cascade({CascadeType.ALL})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "feedbackTarget")
	public Set<TargetContext> getTargetContexts() {
		return this.targetContexts;
	}

	public void setTargetContexts(Set<TargetContext> targetContexts) {
		this.targetContexts = targetContexts;
	}

}
