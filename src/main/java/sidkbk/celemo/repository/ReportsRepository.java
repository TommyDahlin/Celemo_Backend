package sidkbk.celemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sidkbk.celemo.models.Reports;

public interface ReportsRepository extends MongoRepository<Reports, String> {
}
