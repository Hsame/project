package com.yedam.app.free;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Freeboardcomment {
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
			result += "nono " + nono + " [id=" + id + ", comment=" + comment + "]";
		}
		return result;
	}

	
}
