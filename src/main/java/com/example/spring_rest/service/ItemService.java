package com.example.spring_rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_rest.domain.Item;
import com.example.spring_rest.dto.ItemDTO;
import com.example.spring_rest.repository.ItemRepository;
import com.example.spring_rest.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * 비지니스 로직이 들어가는 서비스 클래스
 * @author leeyoungseung
 *
 */
@Service
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	public List<ItemDTO.Response> findAll() {

		List<ItemDTO.Response> list = itemRepository.findAll()
			.stream()
			.map(ItemDTO.Response::of)
			.collect(Collectors.toList());

		return list;
	}

	public ItemDTO.Response findById(Integer id) {

		ItemDTO.Response item = itemRepository.findById(id)
				.map(ItemDTO.Response::of)
				.orElseThrow(NotFoundException::new);

		return item;
	}

	
	public ItemDTO.Response createItem(ItemDTO.Create create) {
		Item item = create.toEntity();
		Item res = itemRepository.save(item);

		return ItemDTO.Response.of(res); 
	}

	@Transactional
	public ItemDTO.Response updateItem(Integer id, ItemDTO.Update update) {
		Item updated = itemRepository.findById(id)
		.map(update::apply)
		.orElseThrow(NotFoundException::new);
		
		return ItemDTO.Response.of(updated);
	}

	public void deleteItem(Integer id) {
		Item item = itemRepository.findById(id)
				.orElseThrow(NotFoundException::new);
		
		itemRepository.delete(item);
	}
	
}