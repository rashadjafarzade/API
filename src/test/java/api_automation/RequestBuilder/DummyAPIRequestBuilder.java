package api_automation.RequestBuilder;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class DummyAPIRequestBuilder {

	@JsonPropertyOrder({"name","salary", "age"})
	private String name;
	private int age;
	private double salary;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}



	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}




	

}