package com.xmemetest.memes;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.xmeme.XMemeApplication;
import com.xmeme.dto.Meme;
import com.xmeme.service.MemeService;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = { XMemeApplication.class })
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MemeControllerTest {
		
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MemeService memeService;
	
	@BeforeEach
	void init() {
		Meme meme = new Meme("0010", "Gomu", "PM", "www.google.com");
		Mockito.when(memeService.getMeme("0010")).thenReturn(meme);
	}

	@Test
    void getAllMemeTest() 
    {
        // given
		List<Meme> memeList = Arrays.asList(
				new Meme("0010", "Gomu", "PM", "www.google.com"),
				new Meme("0011", "Hari", "Covid", "www.google.com"),
				new Meme("0012", "Satyam", "Modi", "www.google.com")
			);
        
        when(memeService.getAllMemes()).thenReturn(memeList);
 
        // when
        List<Meme> memeControllerList = memeService.getAllMemes();
 
        // then
        assertEquals(memeControllerList.size(), 3);
        assertEquals(memeControllerList.get(0).getMemeId(), "0010");
        assertEquals(memeControllerList.get(0).getOwner(), "Gomu");
        assertEquals(memeControllerList.get(0).getCaption(), "PM");
        
    }
	
	@Test
    public void getMemeNotFoundTest() throws Exception {
        mockMvc.perform(get("/xmeme/meme/001")).andExpect(status().isNotFound());
    }
	
	
	@Test
    public void find_bookId_OK() throws Exception {

        mockMvc.perform(get("/xmeme/meme/0010"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memeId", is("0010")))
                .andExpect(jsonPath("$.owner", is("Gomu")))
                .andExpect(jsonPath("$.caption", is("PM")))
                .andExpect(jsonPath("$.url", is("www.google.com")));

        verify(memeService, times(1)).getMeme("0010");

    }
		
}
