package com.droidninja.imageeditengine;

import static com.droidninja.imageeditengine.ImageEditor.EXTRA_IMAGE_PATH;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.droidninja.imageeditengine.utils.FragmentUtil;

public class ImageEditActivity extends BaseImageEditActivity
    implements PhotoEditorFragment.OnFragmentInteractionListener{
  private Rect cropRect;

  //private View touchView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_edit);

    String imagePath = getIntent().getStringExtra(EXTRA_IMAGE_PATH);
    if (imagePath != null) {
      FragmentUtil.addFragment(this, R.id.fragment_container,
          PhotoEditorFragment.newInstance(imagePath));
    }
  }

  @Override public void onCropClicked(Bitmap bitmap) {
  //  FragmentUtil.replaceFragment(this, R.id.fragment_container, CropFragment.newInstance(bitmap, cropRect));
  //  CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);

  }

  @Override public void onDoneClicked(String imagePath) {

    Intent intent = new Intent();
    intent.putExtra(ImageEditor.EXTRA_EDITED_PATH, imagePath);
    setResult(Activity.RESULT_OK, intent);
    finish();
  }

  //crop frargent
  /* @Override public void onImageCropped(Bitmap bitmap, Rect cropRect) {
    this.cropRect = cropRect;
    PhotoEditorFragment photoEditorFragment = (PhotoEditorFragment)
            FragmentUtil.getFragmentByTag(this,
                    PhotoEditorFragment.class.getSimpleName());
    if (photoEditorFragment != null) {
      photoEditorFragment.setImageWithRect(cropRect);
      photoEditorFragment.reset();
      FragmentUtil.removeFragment(this,
          (BaseFragment) FragmentUtil.getFragmentByTag(this,  CropFragment.class.getSimpleName()));
    }
  }


 @Override public void onCancelCrop() {
    FragmentUtil.removeFragment(this,
        (BaseFragment) FragmentUtil.getFragmentByTag(this, CropFragment.class.getSimpleName()));
  }*/

  @Override public void onBackPressed() {
    super.onBackPressed();
  }

 /* @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d("Result___:", "" + requestCode + "--" + resultCode);
  }*/

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
      fragment.onActivityResult(requestCode, resultCode, data);
    }
  }
}
