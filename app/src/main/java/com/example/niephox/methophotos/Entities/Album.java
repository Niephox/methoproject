package com.example.niephox.methophotos.Entities;

import android.os.Build;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.example.niephox.methophotos.Controllers.FirebaseControllers.FirebaseService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Niephox on 3/30/2018.
 */

public class Album implements Comparable {
	private String name;
	private Date date;
	private String description;
	private ArrayList<Image> images = new ArrayList<>();
	private Image thumbnail;

	public Album() {

	}

	public Album(String albumName, String description) {
		this.name = albumName;
		this.description = description;
	}

	private Album(Parcel in) {
		this.name = in.readString();
	}

	public Album(String name, Date date, String description, ArrayList<Image> images) {
		this.name = name;
		this.date = date;
		this.description = description;
		this.images = images;
		if (this.images != null) {
			this.thumbnail = this.images.get(0);
		}
	}


	public String getName() {

		return name;
	}

	//TODO: CHANGE FUNCTIONALLITY TO SUPPORT POSSIBLE DELETION OF IMAGE THAT EXISTS AS THUMBNAIL
	public void setThumbnail(Image image) {
		this.thumbnail = image;
	} // maybe get int for the intex of the specific album images array list

	public Image getThumbnail() {
		return this.thumbnail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void addImage(Image image) {
		images.add(image);
	}

	public void removeImage(Image image) {
		for (Image img : images) {
			if (img.getImageURI().equals(image.getImageURI()))
				images.remove(img);
		}
	}

	public void setImages(ArrayList<Image> images) {

		this.images = images == null ? new ArrayList<Image>() : images;
		genThumbnail();
//		if (images != null)
//			this.thumbnail = this.images.get(0);
//		else {
//			this.thumbnail.
//		}

	}

	private void genThumbnail() {
		if(!images.isEmpty())
			this.thumbnail = this.images.get(0);
		else {
			FirebaseService fbService = new FirebaseService();
			fbService.queryAlbumDelete(name);

		}
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public int compareTo(@NonNull Object o) {
		Date albumDate = (Date) this.images.get(0).getInfoMap().get("Date");
		LocalDate localAlbumDate = albumDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Date comperatorDate = (Date) ((Album)o).getImages().get(0).getInfoMap().get("Date");
		LocalDate comparatorLocalDate = comperatorDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return comparatorLocalDate.compareTo(localAlbumDate);
	}
}



