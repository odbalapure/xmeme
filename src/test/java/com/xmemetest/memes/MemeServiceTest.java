package com.xmemetest.memes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.xmeme.XMemeApplication;
import com.xmeme.dto.Meme;
import com.xmeme.repositoryservice.MemeRepositoryService;
import com.xmeme.service.MemeServiceImpl;

@SpringBootTest(classes = { XMemeApplication.class })
public class MemeServiceTest {

	@InjectMocks
	private MemeServiceImpl memeService;
	
	@Mock
	private MemeRepositoryService memeRepositoryService;
	
	@Test
	void getAllMemesTest() {
		Mockito.when(memeRepositoryService.findAllMemes()).thenReturn(Arrays.asList(
					new Meme("0010", "Gomu ", "PM", "www.google.com"),
					new Meme("0011", "Hari", "Covid", "www.google.com"),
					new Meme("0012", "Satyam", "Modi", "www.google.com")
				));
		
		List<Meme> memeList = memeService.getAllMemes();
		
		assertEquals("PM", memeList.get(0).getCaption());
		assertEquals("Covid", memeList.get(1).getCaption());
		assertEquals("Modi", memeList.get(2).getCaption());
	}
	
	@Test
    void getMemeTest() {
		Mockito.when(memeRepositoryService.findMeme("001")).thenReturn(
					new Meme("001", "Om", "PM", "www.google.com")
				);
		Meme meme = memeService.getMeme("001");
		
		assertEquals("Om", meme.getOwner());
		assertEquals("PM", meme.getCaption());	
	}
	
	@Test
	void getMemeNotFoundTest() {
		Mockito.when(memeRepositoryService.findMeme("001")).thenReturn(new Meme());
		
		Meme meme = memeService.getMeme("001");
		
		assertNull(meme.getMemeId());
		assertNull(meme.getOwner());	
	}

}
