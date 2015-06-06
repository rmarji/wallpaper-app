package com.solodroid.materialwallpaper;

public class Item_RecentWallpaper {
	
 	private String CategoryName;
	private String ImageUrl; 
	
	public Item_RecentWallpaper(String lcatename, String limage) {
		// TODO Auto-generated constructor stub
		this.CategoryName=lcatename;
		this.ImageUrl=limage;
	}

	public Item_RecentWallpaper() {
		// TODO Auto-generated constructor stub
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryname) {
		this.CategoryName = categoryname;
	}
	 
	public String getImageurl()
	{
		return ImageUrl;
		
	}
	
	public void setImageurl(String imageurl)
	{
		this.ImageUrl=imageurl;
	}

}
