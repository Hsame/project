package com.yedam.app.noticeboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Noticeboardcomment {
	String id;
	String comment;
	int no;
	int nono;
	
	
	@Override
	public String toString() {
		String result = "";
		if (id == null) {
			System.out.print("");
		} else {
			result += nono +". " + String.format("%-10s", id) +"   "+ comment;
		}
		return result;
	}

	
}
	
