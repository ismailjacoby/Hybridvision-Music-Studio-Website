package be.technobel.makerhub_backend.dal.repositories;

import be.technobel.makerhub_backend.dal.models.entities.ProductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends JpaRepository<ProductionEntity,Long> {
}
