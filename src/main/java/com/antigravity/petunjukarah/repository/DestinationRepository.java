package com.antigravity.petunjukarah.repository;

import com.antigravity.petunjukarah.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByCategoryContainingIgnoreCase(String category);
    List<Destination> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String category, String location);
}
