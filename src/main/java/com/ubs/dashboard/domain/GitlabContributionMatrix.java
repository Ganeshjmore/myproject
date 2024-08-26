package com.ubs.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GitlabContributionMatrix.
 */
@Entity
@Table(name = "gitlab_contribution_matrix")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GitlabContributionMatrix implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "total_commits", nullable = false)
    private Integer totalCommits;

    @NotNull
    @Column(name = "merge_requests", nullable = false)
    private Integer mergeRequests;

    @NotNull
    @Column(name = "issues_closed", nullable = false)
    private Integer issuesClosed;

    @NotNull
    @Column(name = "code_reviews", nullable = false)
    private Integer codeReviews;

    @NotNull
    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "objectives", "productionReleases", "kudosPoints", "gitlabContributions", "manager" },
        allowSetters = true
    )
    private Resource resource;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GitlabContributionMatrix id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalCommits() {
        return this.totalCommits;
    }

    public GitlabContributionMatrix totalCommits(Integer totalCommits) {
        this.setTotalCommits(totalCommits);
        return this;
    }

    public void setTotalCommits(Integer totalCommits) {
        this.totalCommits = totalCommits;
    }

    public Integer getMergeRequests() {
        return this.mergeRequests;
    }

    public GitlabContributionMatrix mergeRequests(Integer mergeRequests) {
        this.setMergeRequests(mergeRequests);
        return this;
    }

    public void setMergeRequests(Integer mergeRequests) {
        this.mergeRequests = mergeRequests;
    }

    public Integer getIssuesClosed() {
        return this.issuesClosed;
    }

    public GitlabContributionMatrix issuesClosed(Integer issuesClosed) {
        this.setIssuesClosed(issuesClosed);
        return this;
    }

    public void setIssuesClosed(Integer issuesClosed) {
        this.issuesClosed = issuesClosed;
    }

    public Integer getCodeReviews() {
        return this.codeReviews;
    }

    public GitlabContributionMatrix codeReviews(Integer codeReviews) {
        this.setCodeReviews(codeReviews);
        return this;
    }

    public void setCodeReviews(Integer codeReviews) {
        this.codeReviews = codeReviews;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public GitlabContributionMatrix lastUpdated(Instant lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public GitlabContributionMatrix resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GitlabContributionMatrix)) {
            return false;
        }
        return id != null && id.equals(((GitlabContributionMatrix) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GitlabContributionMatrix{" +
            "id=" + getId() +
            ", totalCommits=" + getTotalCommits() +
            ", mergeRequests=" + getMergeRequests() +
            ", issuesClosed=" + getIssuesClosed() +
            ", codeReviews=" + getCodeReviews() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
