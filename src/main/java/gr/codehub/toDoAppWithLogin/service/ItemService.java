package gr.codehub.toDoAppWithLogin.service;

import gr.codehub.toDoAppWithLogin.base.AbstractLogEntity;
import gr.codehub.toDoAppWithLogin.exception.EmptyItemDescriptionException;
import gr.codehub.toDoAppWithLogin.model.Item;
import gr.codehub.toDoAppWithLogin.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService extends AbstractLogEntity {

    private final ItemRepository itemRepository;

    public List<Item> findAllItems() {
        logger.info("Attempting to find all items.");
        return itemRepository.findAll();
    }

    public Item addItem(String description) {
        logger.info("Attempting to add item.");
        Item savedItem = new Item();
//        if (description.isEmpty()) {
//            throw new EmptyItemDescriptionException("Item's description cannot be empty");
//        }
//        Item savedItem = itemRepository.save(Item.builder().description(description).date_created(new Date()).build());
//        logger.info("Item added.");
        return savedItem;
    }

    public void deleteItem(long id) {
        logger.info("Attempting to delete item.");
        itemRepository.deleteById(id);
        logger.info("Item deleted.");
    }
}
