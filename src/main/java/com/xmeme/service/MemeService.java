package com.xmeme.service;

import java.util.List;

import com.xmeme.dto.Meme;

public interface MemeService {

	List<Meme> getMemeBySearch(String meme);
	
}
