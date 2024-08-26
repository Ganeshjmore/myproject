package com.ubs.dashboard.service.dto;

import com.ubs.dashboard.domain.enumeration.RatingLevel;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ubs.dashboard.domain.Rating} entity.
 */
public class RatingDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer score;

    @NotNull
    private RatingLevel level;

    @NotNull
    private Instant ratedAt;

    private ResourceDTO resource;

    private ObjectiveDTO objective;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public RatingLevel getLevel() {
        return level;
    }

    public void setLevel(RatingLevel level) {
        this.level = level;
    }

    public Instant getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Instant ratedAt) {
        this.ratedAt = ratedAt;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    public ObjectiveDTO getObjective() {
        return objective;
    }

    public void setObjective(ObjectiveDTO objective) {
        this.objective = objective;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RatingDTO)) {
            return false;
        }

        RatingDTO ratingDTO = (RatingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ratingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RatingDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", level='" + getLevel() + "'" +
            ", ratedAt='" + getRatedAt() + "'" +
            ", resource=" + getResource() +
            ", objective=" + getObjective() +
            "}";
    }
}
