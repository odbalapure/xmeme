package com.xmemetest.memes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xmeme.XMemeApplication;
import com.xmeme.dto.Meme;
import com.xmeme.entity.MemeEntity;
import com.xmeme.repository.MemeRepository;

@SpringBootTest(classes = { XMemeApplication.class })
public class MemeRepositoryTest {

	@Autowired
	MemeRepository memeRepository;

	@BeforeEach
	public void setup() throws Exception {
		List<MemeEntity> memeList = Arrays.asList(
				new MemeEntity("0010", "Gomu Gomu", "PM", "www.google.com"),
				new MemeEntity("0011", "Hari", "Covid", "www.google.com"),
				new MemeEntity("0012", "Satyam", "Modi", "www.google.com")
			);
		memeRepository.saveAll(memeList);
	}

	@AfterEach
	public void tearDown() throws Exception {
		memeRepository.deleteAll();
	}

	@Test
	void findAllMemeTest() {
		List<MemeEntity> memeEntityList = memeRepository.findAll();
		List<Meme> memeList = new ArrayList<>();
		
		ModelMapper modelMapper = new ModelMapper();
		
		memeEntityList.forEach(memeEntity -> {
			memeList.add(modelMapper.map(memeEntity, Meme.class));
		});

		assertEquals(3, memeList.size());
	}
	
	@Test
	void findMeme() {
		Optional<MemeEntity> meme = memeRepository.findMemeById("0012");		
		assertTrue(meme.isPresent());
	}
	
	@Test
	void findMeme_NotFound() {
		Optional<MemeEntity> meme = memeRepository.findMemeById("009");
		assertFalse(meme.isPresent());
	}
	
}
