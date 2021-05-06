package com.xmeme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meme {
	@JsonProperty("memeId")
	private String memeId;

	@JsonProperty("owner")
	private String owner;

	@JsonProperty("caption")
	private String caption;

	@JsonProperty("url")
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
