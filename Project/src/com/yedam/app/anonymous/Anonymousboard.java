package com.yedam.app.anonymous;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Anonymousboard{
	private int no;
	private String title;
	private String content;
	private String id;
	private String regdate;
	
	@Override
	public String toString() {	
		return String.format("%5d", no) + "            " + String.format("%-20s", title) +  String.format("%-15s", id) + "    " + regdate;
	}
}
