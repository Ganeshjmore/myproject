package com.ubs.dashboard.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ubs.dashboard.domain.GitlabContributionMatrix} entity.
 */
public class GitlabContributionMatrixDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer totalCommits;

    @NotNull
    private Integer mergeRequests;

    @NotNull
    private Integer issuesClosed;

    @NotNull
    private Integer codeReviews;

    @NotNull
    private Instant lastUpdated;

    private ResourceDTO resource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalCommits() {
        return totalCommits;
    }

    public void setTotalCommits(Integer totalCommits) {
        this.totalCommits = totalCommits;
    }

    public Integer getMergeRequests() {
        return mergeRequests;
    }

    public void setMergeRequests(Integer mergeRequests) {
        this.mergeRequests = mergeRequests;
    }

    public Integer getIssuesClosed() {
        return issuesClosed;
    }

    public void setIssuesClosed(Integer issuesClosed) {
        this.issuesClosed = issuesClosed;
    }

    public Integer getCodeReviews() {
        return codeReviews;
    }

    public void setCodeReviews(Integer codeReviews) {
        this.codeReviews = codeReviews;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GitlabContributionMatrixDTO)) {
            return false;
        }

        GitlabContributionMatrixDTO gitlabContributionMatrixDTO = (GitlabContributionMatrixDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gitlabContributionMatrixDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GitlabContributionMatrixDTO{" +
            "id=" + getId() +
            ", totalCommits=" + getTotalCommits() +
            ", mergeRequests=" + getMergeRequests() +
            ", issuesClosed=" + getIssuesClosed() +
            ", codeReviews=" + getCodeReviews() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", resource=" + getResource() +
            "}";
    }
}
