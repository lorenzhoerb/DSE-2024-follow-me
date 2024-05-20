package fm.service.event;

import fm.api.common.LogLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByTimestampDesc();
    List<Event> findByLogLevelOrderByTimestampDesc(LogLevel logLevel);

}
