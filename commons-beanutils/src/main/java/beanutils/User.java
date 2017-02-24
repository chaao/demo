package beanutils;

/**
 * @author lichao
 * @date 2015年10月23日
 */
public class User {
	private long id;
	private String name;
	private Double price;
	private int age;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (price != null)
			builder.append("price=").append(price).append(", ");
		builder.append("age=").append(age).append("]");
		return builder.toString();
	}
}
