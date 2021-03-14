package com.example.spring_rest.dto;


import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.spring_rest.domain.Item;
import com.example.spring_rest.domain.Item.ItemBuilder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDTO {

	@Setter
	@Getter
	public static class Create {
		
		@NotEmpty
		private String name;
		
		@NotNull
		private Integer price;
		
		@NotEmpty
		private String imgPath;
		
		public Item toEntity() {
			
			return Item.builder()
					.name(name)
					.price(price)
					.imgPath(imgPath)
					.build();
		}
	}
	
	
	@Setter
	@Getter
	public static class Update {
		
		private String name;
		private Integer price;
		private String imgPath;
		
		public Item apply(Item item) {
			
			return item.update(name,price,imgPath);
		}
	}
	
	
	@Getter
	public static class Response {
		private Integer id;
		private String name;
		private Integer price;
		private String imgPath;
		
		@Builder
		private Response(Integer id, String name, Integer price, String imgPath) {
			this.id = id;
			this.name = name;
			this.price = price;
			this.imgPath = imgPath;
		}
		
		public static Response of(Item item) {
			return Response.builder()
					.id(item.getId())
					.name(item.getName())
					.price(item.getPrice())
					.imgPath(item.getImgPath())
					.build();
		}
	}
	
    @Getter
    public static class ResponseOne {

        private Response item;

        public ResponseOne(Response item) {
            this.item = item;
        }
    }

    @Getter
    public static class ResponseList {

        private List<Response> items;

        public ResponseList(List<Response> items) {
            this.items = items;
        }
    }
}
