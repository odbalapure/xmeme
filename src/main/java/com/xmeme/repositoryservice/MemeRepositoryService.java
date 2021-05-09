package com.xmeme.repositoryservice;

import java.util.List;

import com.xmeme.dto.Meme;

public interface MemeRepositoryService {

	List<Meme> findAllMemes();

	Meme findMeme(String memeId);

	void postMeme(Meme meme);

	void editMeme(Meme meme);

	void deleteMeme(String memeId);

	void deleteAllMemes();
	
	List<Meme> getMemesByOwner(String searchFor);
	
	List<Meme> getMemesByCaption(String searchFor);

}
