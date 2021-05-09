package com.xmeme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "A DTO class to store the meme details")
public class Meme {
	
	@JsonProperty("memeId")
	@ApiModelProperty(notes = "Unique ID of a MEME")
	private String memeId;

	@JsonProperty("owner")
	@ApiModelProperty(notes = "Name of the person who shared the meme")
	private String owner;

	@JsonProperty("caption")
	@ApiModelProperty(notes = "Caption to describe the context of the meme")
	private String caption;

	@JsonProperty("url")
	@ApiModelProperty(notes = "URL to view the meme (image)")
	private String url;

	public Meme(String memeId, String owner, String caption, String url) {
		super();
		this.memeId = memeId;
		this.owner = owner;
		this.caption = caption;
		this.url = url;
	}

	public Meme() {
	}
	
	public String getMemeId() {
		return memeId;
	}

	public void setMemeId(String memeId) {
		this.memeId = memeId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Meme [memeId=" + memeId + ", owner=" + owner + ", caption=" + caption + ", url=" + url + "]";
	}

}
