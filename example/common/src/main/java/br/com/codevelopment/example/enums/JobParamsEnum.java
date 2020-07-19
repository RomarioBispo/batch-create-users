package br.com.codevelopment.example.enums;

public enum JobParamsEnum {
	
	DATE_INCREMENTER("DATE_INCREMENTER"),
	SEMESTER("SEMESTER"),
	YEAR("YEAR");
	
	private String name;
	
	private JobParamsEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
}
