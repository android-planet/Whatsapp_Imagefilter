package com.example.whatsapp_imagefilter.filepicker.utils;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.whatsapp_imagefilter.filepicker.FilePickerConst;
import com.example.whatsapp_imagefilter.filepicker.cursors.DocScannerTask;
import com.example.whatsapp_imagefilter.filepicker.cursors.loadercallbacks.FileMapResultCallback;
import com.example.whatsapp_imagefilter.filepicker.cursors.loadercallbacks.FileResultCallback;
import com.example.whatsapp_imagefilter.filepicker.cursors.loadercallbacks.PhotoDirLoaderCallbacks;
import com.example.whatsapp_imagefilter.filepicker.models.Document;
import com.example.whatsapp_imagefilter.filepicker.models.FileType;
import com.example.whatsapp_imagefilter.filepicker.models.PhotoDirectory;

import java.util.Comparator;
import java.util.List;


public class MediaStoreHelper {

  public static void getPhotoDirs(FragmentActivity activity, Bundle args, FileResultCallback<PhotoDirectory> resultCallback) {
    if(activity.getSupportLoaderManager().getLoader(FilePickerConst.MEDIA_TYPE_IMAGE)!=null)
      activity.getSupportLoaderManager().restartLoader(FilePickerConst.MEDIA_TYPE_IMAGE, args, new PhotoDirLoaderCallbacks(activity, resultCallback));
    else
      activity.getSupportLoaderManager().initLoader(FilePickerConst.MEDIA_TYPE_IMAGE, args, new PhotoDirLoaderCallbacks(activity, resultCallback));
  }

  public static void getVideoDirs(FragmentActivity activity, Bundle args, FileResultCallback<PhotoDirectory> resultCallback) {
    if(activity.getSupportLoaderManager().getLoader(FilePickerConst.MEDIA_TYPE_VIDEO)!=null)
      activity.getSupportLoaderManager().restartLoader(FilePickerConst.MEDIA_TYPE_VIDEO, args, new PhotoDirLoaderCallbacks(activity, resultCallback));
    else
      activity.getSupportLoaderManager().initLoader(FilePickerConst.MEDIA_TYPE_VIDEO, args, new PhotoDirLoaderCallbacks(activity, resultCallback));
  }

  public static void getDocs(FragmentActivity activity,
                             List<FileType> fileTypes,
                             Comparator<Document> comparator,
                             FileMapResultCallback fileResultCallback)
  {
    new DocScannerTask(activity, fileTypes, comparator, fileResultCallback).execute();
  }
}