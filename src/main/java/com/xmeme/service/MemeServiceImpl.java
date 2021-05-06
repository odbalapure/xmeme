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

	List<List<Meme>> listOfListMeme = new ArrayList<>();

	@Override
	public List<Meme> getMemeBySearch(String searchFor) {

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
			
			System.out.println(memeList);
			return memeList;
		}

		return new ArrayList<Meme>();
	}

}
