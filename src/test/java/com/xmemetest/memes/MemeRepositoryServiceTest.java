package com.xmemetest.memes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xmeme.XMemeApplication;
import com.xmeme.dto.Meme;
import com.xmeme.entity.MemeEntity;
import com.xmeme.repository.MemeRepository;
import com.xmeme.repositoryservice.MemeRepositoryService;

@SpringBootTest(classes = { XMemeApplication.class })
public class MemeRepositoryServiceTest {

	@Autowired
	private MemeRepository memeRepository;
	
	@Autowired
	private MemeRepositoryService memeRepositoryService;

	@BeforeEach
	void setup() throws Exception {
		List<MemeEntity> memeEntityList = Arrays.asList(
				new MemeEntity("0010", "Gomu Gomu", "PM", "www.google.com"),
				new MemeEntity("0011", "Hari", "Covid", "www.google.com"),
				new MemeEntity("0012", "Satyam", "Modi", "www.google.com")
			);
		memeRepository.saveAll(memeEntityList);
	}

	@AfterEach
	void tearDown() throws Exception {
		memeRepository.deleteAll();
	}

	@Test
	void findAllMemeTest() {
		List<Meme> memeList = memeRepositoryService.findAllMemes();
		assertEquals(3, memeList.size());
	}
	
	@Test
	void findMemeTest() {
		Meme meme = memeRepositoryService.findMeme("0012");		
		assertNotNull(meme);
	}
	
	@Test
	void findMemeNotFoundTest() {
		Meme meme = memeRepositoryService.findMeme("009");
		assertNull(meme.getMemeId());
		assertNull(meme.getOwner());
		assertNull(meme.getCaption());
		assertNull(meme.getUrl());
	}
	
}
