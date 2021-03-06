/**
 * @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) : https://stackoverflow.com/questions/24994440/no-serializer-found-for-class-org-hibernate-proxy-pojo-javassist-javassist
 */
package com.example.spring_rest.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonFormat(shape=JsonFormat.Shape.ARRAY) : 이게 있음 안되더라....
@Entity
@Table(name = "items")
@DynamicInsert
@DynamicUpdate
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Item implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name ="imgpath")
	private String imgPath;
	
	
	@Builder
	private Item(String name, Integer price, String imgPath) {
		this.name = name;
		this.price = price;
		this.imgPath = imgPath;
	}

	public Item update(String name, Integer price, String imgPath) {
		this.name = name;
		this.price = price;
		this.imgPath = imgPath;
		return this;
	}
	
}
