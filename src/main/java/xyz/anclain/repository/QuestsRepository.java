package xyz.anclain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.anclain.entity.Quests;

import java.util.List;

@Repository
public interface QuestsRepository extends JpaRepository<Quests, Long> {

    public List<Quests> findByIsShownTrue();

}
