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

import com.example.spring_rest.dto.ItemDTO;
import com.example.spring_rest.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.spring_rest.utils.ApiDocumentUtils.getDocumentRequest;
import static com.example.spring_rest.utils.ApiDocumentUtils.getDocumentResponse;

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

}
