import manager from 'app/entities/manager/manager.reducer';
import resource from 'app/entities/resource/resource.reducer';
import objective from 'app/entities/objective/objective.reducer';
import comment from 'app/entities/comment/comment.reducer';
import rating from 'app/entities/rating/rating.reducer';
import productionRelease from 'app/entities/production-release/production-release.reducer';
import kudosPoint from 'app/entities/kudos-point/kudos-point.reducer';
import gitlabContributionMatrix from 'app/entities/gitlab-contribution-matrix/gitlab-contribution-matrix.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  manager,
  resource,
  objective,
  comment,
  rating,
  productionRelease,
  kudosPoint,
  gitlabContributionMatrix,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
