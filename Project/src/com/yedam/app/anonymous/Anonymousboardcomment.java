package com.yedam.app.anonymous;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Anonymousboardcomment {
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
			result += nono +". " + String.format("%-10s", "*****") +"   "+ comment;
		}
		return result;
	}

	
}
