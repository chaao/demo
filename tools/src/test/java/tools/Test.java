package tools;

import java.util.Map;

/**
 * @author chao.li
 * @date 2017年3月9日	
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User user = new User();
		user.setName("jack");
		System.out.println(JsonUtils.toJson(user));
	}

}
class User{
	private String name;
	private int age;
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
	
}
