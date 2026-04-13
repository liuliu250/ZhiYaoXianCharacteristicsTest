package xyz.anclain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.anclain.entity.Quests;

@Repository
public interface QuestsRepository extends JpaRepository<Quests, Long> {
}
