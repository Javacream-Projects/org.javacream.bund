package org.javacream.training.bund.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

@Repository
@ApplicationScope
public interface SimpleRepository extends JpaRepository<SimpleData, Long>{
}
