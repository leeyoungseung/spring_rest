package com.example.spring_rest.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.spring_rest.dto.ItemDTO;
import com.example.spring_rest.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.spring_rest.utils.ApiDocumentUtils.getDocumentRequest;
import static com.example.spring_rest.utils.ApiDocumentUtils.getDocumentResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {
		ItemController.class
})
@AutoConfigureRestDocs//(outputDir = "build/snippets")
public class ItemDocumentationTests {
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@MockBean
	protected ItemService itemService;
	
	@Test
	public void findAll() throws Exception {
		
		// given 
		List<ItemDTO.Response> resList = Arrays.asList(
				ItemDTO.Response.builder()
					.id(1)
					.name("lee001")
					.price(1000)
					.imgPath("/uploads/lee001")
					.build(),
				ItemDTO.Response.builder()
					.id(2)
					.name("lee002")
					.price(2000)
					.imgPath("/uploads/lee002")
					.build(),
				ItemDTO.Response.builder()
					.id(3)
					.name("lee002")
					.price(3000)
					.imgPath("/uploads/lee002")
					.build()
				);
		
		given(itemService.findAll())
		.willReturn(resList);
		
		// when 
		ResultActions result = this.mockMvc.perform(
				get("/api/items")
				.accept(MediaType.APPLICATION_JSON)
				);
		
		//then
		result.andExpect(status().isOk())
		.andDo(document("items-find-all",
				getDocumentRequest(),
				getDocumentResponse(),
				responseFields(beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("items[].id").type(JsonFieldType.NUMBER).description("아이디"),
                        fieldWithPath("items[].name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("items[].price").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("items[].imgPath").type(JsonFieldType.STRING).description("이미지경로")
					)
				));
		
	}
	
	@Test
	public void findById() throws Exception {
		
		//given 
		given(itemService.findById(1))
			.willReturn(ItemDTO.Response.builder()
					.id(1)
					.name("lee001")
					.price(1000)
					.imgPath("/uploads/lee001")
					.build()
				);
		
		//when
		ResultActions result = this.mockMvc.perform(
				get("/api/items/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)
			);
		
		// then 
		result.andExpect(status().isOk())
			.andDo(document("items-findOne",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    pathParameters(
                            parameterWithName("id").description("아이디")
                    ),
    				responseFields(beneathPath("data").withSubsectionId("data"),
                            fieldWithPath("item.id").type(JsonFieldType.NUMBER).description("아이디"),
                            fieldWithPath("item.name").type(JsonFieldType.STRING).description("이름"),
                            fieldWithPath("item.price").type(JsonFieldType.NUMBER).description("가격"),
                            fieldWithPath("item.imgPath").type(JsonFieldType.STRING).description("이미지경로")
    					)
                    
			));         
			
	}
	
	@Test
	public void create() throws Exception {
		// given
		ItemDTO.Response response =  ItemDTO.Response.builder()
			.id(1)
			.name("lee001")
			.price((Integer)1000)
			.imgPath("/uploads/lee001")
			.build();
		
		given(itemService.createItem(any(ItemDTO.Create.class)))
			.willReturn(response);
		
		
		// when 
		ItemDTO.Create create = new ItemDTO.Create();
		create.setName("lee001");
		create.setPrice((Integer)1000);
		create.setImgPath("/uploads/lee001");
		
		System.out.println(create.toEntity());
		
		ResultActions result = this.mockMvc.perform(
				post("/api/items")
					.content(objectMapper.writeValueAsString(create))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
			);
		
		// then
		result.andExpect(status().isOk())
		.andDo(document("items-create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("imgPath").type(JsonFieldType.STRING).description("이미지경로")

                ),
				responseFields(beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("item.id").type(JsonFieldType.NUMBER).description("아이디"),
                        fieldWithPath("item.name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("item.price").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("item.imgPath").type(JsonFieldType.STRING).description("이미지경로")
					
                )
		));  
		
	}
	
	
	@Test
	public void update() throws Exception {
		// given
		ItemDTO.Response response =  ItemDTO.Response.builder()
			.id(1)
			.name("lee001")
			.price(1000)
			.imgPath("/uploads/lee001")
			.build();
		
		given(itemService.updateItem(eq(1), any(ItemDTO.Update.class)))
		.willReturn(response);
		
		// when 
		ItemDTO.Update update = new ItemDTO.Update();
		update.setName("lee001");
		update.setPrice(1000);
		update.setImgPath("/uploads/lee001");
		
		
		ResultActions result = this.mockMvc.perform(
				put("/api/items/{id}", 1)
					.content(objectMapper.writeValueAsString(update))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
			);
		
		// then
		result.andExpect(status().isOk())
		.andDo(document("items-update",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("아이디")
                ),
                requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("imgPath").type(JsonFieldType.STRING).description("이미지경로")

                ),
				responseFields(beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("item.id").type(JsonFieldType.NUMBER).description("아이디"),
                        fieldWithPath("item.name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("item.price").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("item.imgPath").type(JsonFieldType.STRING).description("이미지경로")
					
                )
		));  		
		
	}
	
	@Test
	public void deleteOne() throws Exception {
		// given
		doNothing()
			.when(itemService)
			.deleteItem(1);
		
		// when 
		ResultActions result = this.mockMvc.perform(
				delete("/api/items/{id}", 1)
					.accept(MediaType.APPLICATION_JSON)
			);
		
		// then 
		result.andExpect(status().isOk())
			.andDo(document("items-delete",
	                getDocumentRequest(),
	                getDocumentResponse(),
                    pathParameters(
                            parameterWithName("id").description("아이디")
                    )
			));
	}
	

}
