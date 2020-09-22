package com.example.spring_rest.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spring_rest.domain.Item;
import com.example.spring_rest.service.ItemService;

// url : http://localhost:8080/api/items
@RestController
@RequestMapping(path = "/api")
public class ItemController {
	
	private static final Logger log = LogManager.getLogger(ItemController.class);
	
    @Autowired
    ItemService itemService;
    
    
    /**
     * @GetMapping 같은 어노테이션 과 @RequestMapping 양쪽 다 사용 가능
     * @return
     */
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    //@GetMapping(path = "/items")
    //@GetMapping("/items")
    public List<Item> getItems() {
    	System.err.println("ItemController getItems");
        List<Item> customers = itemService.findAll();
        return customers;
    }
    
    //@RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    @GetMapping(path = "/items/{id}")
    //@GetMapping("/items/{id}")
    public Item getOneItem(@PathVariable("id") long id) {
    	System.err.println("ItemController getOneItem");
    	Integer select_id = (int)id;
    	return itemService.findOne(select_id);
    }
    
    //@PostMapping(path = "/items")
    @PostMapping("/items")
    public Item createItem(@RequestBody final Item item,
    		final UriComponentsBuilder ucBuilder) {
    	System.err.println("ItemController createItem");
    	Item res = new Item();
    	res = itemService.createItem(item);
    	return res;
    }
    
    //@PutMapping(path = "/items/{id}")
    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable("id") long id,
    		@RequestBody final Item item,
    		final UriComponentsBuilder ucBuilder) {
    	System.err.println("ItemController updateItem");
    	itemService.updateItem(id, item);
    	return itemService.findOne((int)id);
    }
    
    //@DeleteMapping(path = "/items/{id}")
    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable("id") long id) {
    	System.err.println("ItemController deleteItem");
    	itemService.deleteItem((int)id);
    }
}
