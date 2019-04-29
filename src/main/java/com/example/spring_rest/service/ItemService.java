package com.example.spring_rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_rest.domain.Item;
import com.example.spring_rest.repository.ItemRepository;

/**
 * 비지니스 로직이 들어가는 서비스 클래스
 * @author leeyoungseung
 *
 */
@Service
public class ItemService {
	@Autowired
	ItemRepository itemRepository;
	
	public List<Item> findAll() {
		System.err.println("ItemService findAll");
		List<Item> list = itemRepository.findAll();
		for(Item item : list) {
			System.out.println(item.toString());
		}
		
		return list;
	}

	public Item findOne(int id) {
		System.err.println("ItemService findOne");
		Item item = itemRepository.getOne(id);
		System.out.println(item.toString());
		return item;
	}

	
	public Item createItem(Item item) {
		System.err.println("ItemService createItem");
		System.out.println(item.toString());
		Item res = itemRepository.save(item);
		System.out.println(res.toString());
		return res; 
	}

	public void updateItem(long id, Item item) {
		System.err.println("ItemService createItem");
		System.out.println(item.toString());
		
		itemRepository.findById((int)id)
		.map(items -> {
			items.setName(item.getName());
			items.setPrice(item.getPrice());
			items.setImgPath(item.getImgPath());
			return itemRepository.save(items);
		}).orElseGet(()->{
			item.setId((int)id);
			return itemRepository.save(item);
		});
	}

	public void deleteItem(int id) {
		System.err.println("ItemService deleteItem");
		itemRepository.deleteById(id);
	}
	
}