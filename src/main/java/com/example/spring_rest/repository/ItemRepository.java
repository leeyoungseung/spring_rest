package com.example.spring_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_rest.domain.Item;

/**
 * JpaRepository : item 테이블에 해당하는 CRUD기능을 계승해서 사용할 수 있게 해준다
 * @author leeyoungseung
 *
 */
public interface ItemRepository extends JpaRepository<Item, Integer>{

}
