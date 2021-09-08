package com.example.whatsapp_imagefilter.filepicker.cursors.loadercallbacks;

import com.example.whatsapp_imagefilter.filepicker.models.Document;
import com.example.whatsapp_imagefilter.filepicker.models.FileType;

import java.util.List;
import java.util.Map;


/**
 * Created by gabriel on 10/2/17.
 */

public interface FileMapResultCallback {
    void onResultCallback(Map<FileType, List<Document>> files);
}

