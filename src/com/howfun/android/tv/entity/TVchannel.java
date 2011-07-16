package com.howfun.android.tv.entity;

public class TVchannel {

	private int mChannelId;
	private String mChannelName = "";

	public TVchannel(int id, String channelName) {
		mChannelId = id;
		mChannelName = channelName;
	}

	public int getChannelId() {
		return mChannelId;
	}

	public void setChannelId(int id) {
		this.mChannelId = id;
	}

	public String getChannelName() {
		return mChannelName;
	}

	public void setChannelName(String channelName) {
		this.mChannelName = channelName;
	}

}
