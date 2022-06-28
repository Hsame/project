package com.yedam.app.noticeboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Noticeboard {
	private int no;
	private String title;
	private String content;
	private String id;
	private String regdate;
	
	@Override
	public String toString() {
		if (title.length() > 17) {
			title = title.substring(0, 10)+"..";
		}
		return String.format("%5d", no) + String.format("%20s", title) +  String.format("%20s", id) + String.format("%32s", regdate);
	}
}
