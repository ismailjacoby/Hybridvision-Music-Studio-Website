package be.technobel.makerhub_backend.dal.repositories;

import be.technobel.makerhub_backend.dal.models.entities.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    @Query("SELECT w FROM WishlistEntity w JOIN w.user u WHERE u.username = :username")
    Optional<WishlistEntity> findByUsername(String username);
    Optional<WishlistEntity> findByUserId(Long userId);
}
