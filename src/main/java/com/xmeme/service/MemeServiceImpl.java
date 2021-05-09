package com.xmeme.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmeme.dto.Meme;
import com.xmeme.repositoryservice.MemeRepositoryService;

@Service
public class MemeServiceImpl implements MemeService {

	@Autowired
	private MemeRepositoryService memeRepositoryService;
	
	@Override
	public List<Meme> getAllMemes() {
		return memeRepositoryService.findAllMemes();
	}
	
	@Override
	public Meme getMeme(String memeId) {
		Meme meme = memeRepositoryService.findMeme(memeId);
	
		return meme;
	}
	 
	@Override
	public List<Meme> getMemeBySearch(String searchFor) {
		List<List<Meme>> listOfListMeme = new ArrayList<>();
		
		if (!searchFor.isEmpty()) {
			listOfListMeme.add(memeRepositoryService.getMemesByOwner(searchFor));
			listOfListMeme.add(memeRepositoryService.getMemesByCaption(searchFor));

			Set<String> memeSet = new HashSet<>();
			List<Meme> memeList = new ArrayList<>();
			for (List<Meme> setToList : listOfListMeme) {
				for (Meme meme : setToList) {
					if (!memeSet.contains(meme.getMemeId())) {
						memeList.add(meme);
						memeSet.add(meme.getMemeId());
					}
				}
			}

			return memeList;
		}

		return new ArrayList<Meme>();
	}
	
	@Override
	public void postMeme(Meme meme) {
		memeRepositoryService.postMeme(meme);
	}
	
	@Override
	public void editMeme(Meme meme) {
		memeRepositoryService.editMeme(meme);
	}
	
	@Override
	public void deleteMeme(String memeId) {
		memeRepositoryService.deleteMeme(memeId);
	}
	
	@Override
	public void deleteAllMemes() {
		memeRepositoryService.deleteAllMemes();
	}

}
