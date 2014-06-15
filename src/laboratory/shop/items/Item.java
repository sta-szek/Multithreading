package laboratory.shop.items;

public final class Item implements ItemInterface {

	private Category category;
	private float price;
	private String description;
	private int amount;

	public Item(Category category, float price, String description, int amount) {
		super();
		this.category = category;
		this.price = price;
		this.description = description;
	}

	public Category getCateogry() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return category + " " + description + " " + price;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}



}
