package com.example.spring_rest.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.example.spring_rest.dto.ApiResponseDTO;
import com.example.spring_rest.dto.ItemDTO;
import com.example.spring_rest.dto.ItemDTO.ResponseOne;

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
    public ApiResponseDTO<ItemDTO.ResponseList> getItems() {
    	log.info("ItemController getItems");
    	List<ItemDTO.Response> data = itemService.findAll();
        log.info("getItems : "+ data);
        
        return ApiResponseDTO.createOK(new ItemDTO.ResponseList(data));
    }
    

    @GetMapping(path = "/items/{id}")
    public ApiResponseDTO<ItemDTO.ResponseOne> getOneItem(@PathVariable("id") Integer id) {
    	log.info("ItemController getOneItem");
    	return ApiResponseDTO.createOK(new ItemDTO.ResponseOne(itemService.findById(id)));
    }
    
    
    @PostMapping("/items")
    public ApiResponseDTO<ItemDTO.ResponseOne> createItem(@RequestBody @Valid final ItemDTO.Create create) {
    	log.info("ItemController createItem");

    	return ApiResponseDTO.createOK(new ItemDTO.ResponseOne(itemService.createItem(create)));
    }
    
    
    @PutMapping("/items/{id}")
    public ApiResponseDTO<ItemDTO.ResponseOne> updateItem(@PathVariable("id") Integer id,
    		@RequestBody final ItemDTO.Update update
    		) {
    	log.info("ItemController updateItem");

    	return ApiResponseDTO.createOK(new ItemDTO.ResponseOne(itemService.updateItem(id, update)));
    }
    
    
    @DeleteMapping("/items/{id}")
    public ApiResponseDTO deleteItem(@PathVariable("id") Integer id) {
    	log.info("ItemControlsler deleteItem");
    	itemService.deleteItem(id);
    	
    	return ApiResponseDTO.DEFAULT_OK;
    }
}
