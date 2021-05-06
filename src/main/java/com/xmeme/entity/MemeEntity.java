package com.xmeme.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "memes")
public class MemeEntity {

	@Id
	private ObjectId _id;

	@NotNull
	private String memeId;

	@NotNull
	private String owner;

	@NotNull
	private String caption;

	@NotNull
	private String url;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
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
		return "MemeEntity [_id=" + _id + ", memeId=" + memeId + ", owner=" + owner + ", caption=" + caption + ", url="
				+ url + "]";
	}

}
