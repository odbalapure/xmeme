package com.xmeme.service;

import java.util.List;

import com.xmeme.dto.Meme;

public interface MemeService {

	List<Meme> getMemeBySearch(String meme);

	List<Meme> getAllMemes();

	Meme getMeme(String memeId);

	void postMeme(Meme meme);

	void editMeme(Meme meme);

	void deleteMeme(String memeId);

	void deleteAllMemes();
	
}
