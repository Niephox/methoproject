package com.example.niephox.methophotos.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.niephox.methophotos.Entities.Album;
import com.example.niephox.methophotos.Entities.Image;

import java.util.ArrayList;
import java.util.Calendar;

/*
 * Created by greycr0w on 4/28/2018.
 */

public class AlbumRepository {
    private ArrayList<Image> selectedImages;
    private Image currentImage;
    private Album album;
    private Context context; //context is used for selectionImageGallery
    private Activity genActivity; //its important bcs this is controller not an Activity
    private boolean booleanFolder;
    public  static com.example.niephox.methophotos.Interfaces.iAsyncCallback iAsyncCallback;
    private FirebaseService service = new FirebaseService();
	private final static int REQUEST_PICTURES = 1;

    public AlbumRepository() {
    	album = new Album();
	}

    //you can create an AlbumCreateController by giving the EditText album name, that the user
    //enters and the constructor will throw a pop up screen to the user for selecting pictures saved
    //in device storage.

	//you can create an new album with just the album entity so you dont need a constructor for shit like desc and album names
	//you only call create Album with the album you created if its an autogenerated album

	//this is a constuctor that is used when the user wants to manually create an album by giving the album name and the descri of
	//the x album
    public AlbumRepository(String albumName, String description, Context context) {
		album = new Album();
        album.setName(albumName); //we only set the album name and the album description. When the user has selected his desired pictures then the creation date of the x album is set with the images selected.
        album.setDescription(description);
        this.context = context;
		openSelectionImageGallery();
    }

	//TODO: create an album with the autosorted images
    public AlbumRepository(Album autoGeneratedAlbum) {
    	album = new Album();
    	album = autoGeneratedAlbum;
	}


    //get created album
    public Album getAlbum () {
        return this.album;
    }

    /*
        TODO: create album should be more generic? or should we create a syncOnlineAlbums because we open the selection
        TODO: gallery activity, although we just get the created albums that are saved in the firebase with their storageLocationUrl references
        TODO: that gets the albums all the thumbnails of those albums
     */
    public void createAlbumFromSelection(Album albumToCreate, Context context) {
		this.context = context;
    	album = albumToCreate;
        openSelectionImageGallery();
    }

    private void openSelectionImageGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //allows any image file type. Change * to specific extension to limit it
        intent.setType("image/*");
        //EXTRA_ALLOW_MULTIPLE is an intent property that allows multiple selection of image/* images
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        genActivity = (Activity) context;
        genActivity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICTURES);

    }
    public void onActivityResult ( int requestCode, int resultCode, Intent data) {
        selectedImages = new ArrayList<>();
        currentImage = new Image();
        if (requestCode == REQUEST_PICTURES) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) { //data.getClipData is null
                //count before loop so you dont reset count everiteme
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        currentImage.setImageURI(data.getClipData().getItemAt(i).getUri().toString());
                        selectedImages.add(currentImage);
                        currentImage = new Image();
                    } //else if there is only one image selected, do this:
                }else if (data.getData() != null) {
                    Uri singleImagePath = data.getData();
                    currentImage.setImageURI(singleImagePath.toString());
                    selectedImages.add(currentImage);
                }
            }

            if (selectedImages != null) {
                album.setImages(selectedImages);
                album.setDate(Calendar.getInstance().getTime());
                //getCreatedAlbum(album);
            }
     }
     for (Image img : selectedImages)
     	Log.w(" TAG ALEXANDER IMAGEs " , img.getImageURI());
        createAlbum(album);

    }

	//Gets all the photos from Device Storage and creates a LocalAlbum
	public Album getLocalAlbum(Context context) {
		ArrayList<Image> tempImageArray = new ArrayList<>();
		int pos = 0;
		Uri uri;
		Cursor cursor;
		int columnIndexData, columnIndexFolderName;

		String absolutePathOfImage = null;
		uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
		cursor = context.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

		columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
		columnIndexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
		while (cursor.moveToNext()) {
			absolutePathOfImage = cursor.getString(columnIndexData);
			Log.e("Column", absolutePathOfImage);
			Log.e("Folder", cursor.getString(columnIndexFolderName));

			if (booleanFolder) {

				ArrayList<String> imagesPath = new ArrayList<>();
				imagesPath.addAll(tempImageArray.get(pos).getImagesPath());
				imagesPath.add(absolutePathOfImage);
				tempImageArray.get(pos).setImagesPath(imagesPath);

			} else {
				ArrayList<String> imagesPath = new ArrayList<>();
				imagesPath.add(absolutePathOfImage);

				Image tempImage = new Image();

				tempImage.setImageURI(absolutePathOfImage);
				//obj_model.setStr_folder(cursor.getString(column_index_folder_name));
				tempImage.setImagesPath(imagesPath);
				tempImageArray.add(tempImage);

			}
		}
		for (int i = 0; i < tempImageArray.size(); i++) {
			//Log.e("FOLDER", al_images.get(i).getStr_folder());
			for (int j = 0; j < tempImageArray.get(i).getImagesPath().size(); j++) {
				//Log.e("FILE", tempImageArray.get(i).getImagesPath().get(j));
			}
		}
		for(Image img : tempImageArray) {
			Log.w("ALEXANDER ",img.getImageURI());
		}
		Album localAlbum = new Album("Local Album", Calendar.getInstance().getTime(), "This is the autogenerated Album that contains all device Images", tempImageArray);

		return localAlbum;
	}   // iAsyncCallback.RetrieveData(1

	public void transferImage(Image imgToTransfer, Album fromAlbum, Album toAlbum) {
    	service.queryTransferImage(imgToTransfer, fromAlbum, toAlbum);
	}

	public void createAlbum(Album albumToCreate) {
        service.queryAlbumCreate(albumToCreate);
    }

    public void deleteAlbum (Album albumToDelete) {
       service.queryAlbumDelete(albumToDelete);
    }


    }


