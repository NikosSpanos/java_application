package gr.codehub.toDoAppWithLogin.repository;

import gr.codehub.toDoAppWithLogin.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
