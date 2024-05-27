package fm.service.event;

import fm.api.common.LogLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findAllByOrderByTimestampDesc();
    List<Event> findByLogLevelOrderByTimestampDesc(LogLevel logLevel);
}
