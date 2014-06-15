package laboratory.shop.items;

public interface ItemInterface {

	public Category getCateogry();
	public void setCategory(Category category);
	public float getPrice();
	public void setPrice(float price);
	public String getDescription();
	public void setDescription(String description);
	public void setAmount(int amount);
	public int getAmount();
}
