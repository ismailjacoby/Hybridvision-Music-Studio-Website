package be.technobel.makerhub_backend.dal.repositories;

import be.technobel.makerhub_backend.dal.models.entities.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity,Long> {
}
