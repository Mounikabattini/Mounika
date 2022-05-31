package com.book.donation.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;

public  class SystemUtility {

    private static SystemUtility systemUtility;

    public static SystemUtility getInstance() {
        if (systemUtility == null)
            systemUtility = new SystemUtility();

        return systemUtility;
    }


    /**
     * hide the virtual keyboard from the current screen
     **/
    public static void hideVirtualKeyboard(Activity _activity) {

        try {
            if (_activity != null && _activity.getWindow() != null) {
                View view = _activity.getWindow().getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) _activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (view != null) {
                    assert imm != null;
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else
                    _activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTempMediaDirectory(Context context) {
        String state = Environment.getExternalStorageState();
        File dir = null;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            dir = context.getExternalCacheDir();
        } else {
            dir = context.getCacheDir();
        }

        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        if (dir.exists() && dir.isDirectory()) {
            return dir.getAbsolutePath();
        }
        return null;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        if (validateFilePath(contentUri.getPath())) {
            return contentUri.getPath();
        }
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            if (cursor == null)
                return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static boolean validateFilePath(String path) {
        return Utils.validateString(path) && validateFile(new File(path));
    }

    private static boolean validateFile(File file) {
        return file.exists();
    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e){e.printStackTrace();}finally {
                assert cursor != null;
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}
