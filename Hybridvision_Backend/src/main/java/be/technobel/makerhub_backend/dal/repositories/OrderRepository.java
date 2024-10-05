package be.technobel.makerhub_backend.dal.repositories;

import be.technobel.makerhub_backend.dal.models.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    List<OrderEntity> findByUserId(Long userId);
}
