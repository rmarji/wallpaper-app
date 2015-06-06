package com.solodroid.materialwallpaper;

import java.io.Serializable;

public class Constant implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SERVER_IMAGE_UPFOLDER_CATEGORY = "http://www.dimasword.com/material_wallpaper/upload/category/";
	public static final String SERVER_IMAGE_UPFOLDER_THUMB = "http://www.dimasword.com/material_wallpaper/upload/thumbs/";
	public static final String SERVER_IMAGE_DETAILS = "http://www.dimasword.com/material_wallpaper/upload/";
	public static final String LATEST_URL = "http://www.dimasword.com/material_wallpaper/api.php?latest=50";
	public static final String CATEGORY_URL = "http://www.dimasword.com/material_wallpaper/api.php";
	public static final String CATEGORY_ITEM_URL = "http://www.dimasword.com/material_wallpaper/api.php?cat_id=";

    public static final int NUM_OF_COLUMNS = 2;
    public static final int GRID_PADDING = 5;

	public static final String LATEST_ARRAY_NAME = "MaterialWallpaper";
	public static final String LATEST_IMAGE_CATEGORY_NAME = "category_name";
	public static final String LATEST_IMAGE_URL = "image";

	public static final String CATEGORY_ARRAY_NAME = "MaterialWallpaper";
	public static final String CATEGORY_NAME = "category_name";
	public static final String CATEGORY_CID = "cid";
	public static final String CATEGORY_IMAGE_URL = "category_image";

	public static final String CATEGORY_ITEM_ARRAY = "MaterialWallpaper";
	public static final String CATEGORY_ITEM_CATNAME = "cat_name";
	public static final String CATEGORY_ITEM_IMAGEURL = "images";
	public static final String CATEGORY_ITEM_CATID = "cid";

	public static String CATEGORY_ITEM_CATIDD;
	public static String CATEGORY_TITLE;
	public static String CATEGORY_ID;

}
