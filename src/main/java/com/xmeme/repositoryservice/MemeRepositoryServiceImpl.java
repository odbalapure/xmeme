package com.xmeme.repositoryservice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.xmeme.dto.Meme;
import com.xmeme.entity.MemeEntity;
import com.xmeme.repository.MemeRepository;

@Repository
public class MemeRepositoryServiceImpl implements MemeRepositoryService {

	@Autowired
	private MemeRepository memeRepository;

	@Autowired
	private MongoTemplate mongoTempalte;

	@Override
	public List<Meme> findAllMemes() {
		List<MemeEntity> memeEntityList = memeRepository.findAll();
		List<Meme> memeList = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();
		memeEntityList.forEach(memeEntity -> {
			memeList.add(modelMapper.map(memeEntity, Meme.class));
		});

		return memeList;
	}

	@Override
	public Meme findMeme(String memeId) {
		Optional<MemeEntity> optionalMemeEntity = memeRepository.findMemeById(memeId);
		ModelMapper modelMapper = new ModelMapper();

		if (optionalMemeEntity.isPresent()) {
			MemeEntity memeEntity = optionalMemeEntity.get();
			return modelMapper.map(memeEntity, Meme.class);
		}

		return new Meme();
	}

	@Override
	public void postMeme(Meme meme) {
		ModelMapper modelMapper = new ModelMapper();
		
		List<MemeEntity> memeEntityList = memeRepository.findAll();
		Integer id = 1;
		if (memeEntityList.size() == 0) {
			id = 1;
		} else {
			id = Integer.parseInt(memeEntityList.get(memeEntityList.size() - 1).getMemeId());
			id += 1;
		}
		
		meme.setMemeId(String.valueOf(id));
		memeRepository.save(modelMapper.map(meme, MemeEntity.class));
	}

	@Override
	public void editMeme(Meme meme) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memeId").is(meme.getMemeId()));

		Update update = new Update();
		update.set("owner", meme.getOwner());
		update.set("caption", meme.getCaption());
		update.set("url", meme.getUrl());

		mongoTempalte.upsert(query, update, MemeEntity.class);
	}

	@Override
	public void deleteMeme(String memeId) {
		memeRepository.deleteById(memeId);
	}

	@Override
	public void deleteAllMemes() {
		memeRepository.deleteAll();
	}

	@Override
	public List<Meme> getMemesByOwner(String searchFor) {
		ModelMapper modelMapper = new ModelMapper();

		Set<String> memeSet = new HashSet<>();
		List<Meme> memeList = new ArrayList<>();

		Optional<List<MemeEntity>> optionalExactOwnerList = memeRepository.findMemeByExactOwner(searchFor);
		if (optionalExactOwnerList.isPresent()) {
			List<MemeEntity> memeEntityList = optionalExactOwnerList.get();

			memeEntityList.forEach(memeEntity -> {
				if (!memeSet.contains(memeEntity.getMemeId())) {
					memeList.add(modelMapper.map(memeEntity, Meme.class));
					memeSet.add(memeEntity.getMemeId());
				}
			});
		}

		Optional<List<MemeEntity>> optionalOwnerList = memeRepository.findMemeByExactOwner(searchFor);
		if (optionalOwnerList.isPresent()) {
			List<MemeEntity> memeEntityList = optionalOwnerList.get();

			memeEntityList.forEach(memeEntity -> {
				if (!memeSet.contains(memeEntity.getMemeId())) {
					memeList.add(modelMapper.map(memeEntity, Meme.class));
					memeSet.add(memeEntity.getMemeId());
				}
			});
		}

		return memeList;
	}

	@Override
	public List<Meme> getMemesByCaption(String searchFor) {
		ModelMapper modelMapper = new ModelMapper();
		List<Meme> memeList = new ArrayList<>();

		Optional<List<MemeEntity>> memeByCaption = memeRepository.findMemeByCaption(searchFor);
		if (memeByCaption.isPresent()) {
			List<MemeEntity> memeEntityList = memeByCaption.get();

			memeEntityList.forEach(memeEntity -> {
				memeList.add(modelMapper.map(memeEntity, Meme.class));
			});
		}

		return memeList;

	}

}
