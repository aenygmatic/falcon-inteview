package io.falcon.interview.virtualboard.services.domain.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityCrudRepository extends PagingAndSortingRepository<PostEntity, String> {

}
