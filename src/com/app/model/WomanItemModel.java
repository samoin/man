package com.app.model;

public class WomanItemModel {

	private int img;//װŮ��ͼƬ
	private String name; //װŮ������
	private int vote = 0;//װŮ�ɱ�ͶƱ��
	private int rank = 0; //����
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getImg() {
		return img;
	}
	public void setImg(int img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	
	
}
