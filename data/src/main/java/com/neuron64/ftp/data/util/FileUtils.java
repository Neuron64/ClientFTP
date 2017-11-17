package com.neuron64.ftp.data.util;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.neuron64.ftp.data.content_provider.DocumentsContract;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.neuron64.ftp.data.content_provider.ExternalStorageProvider.AUTHORITY;

/**
 * Created by Neuron on 22.10.2017.
 */

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static boolean deleteContents(File dir) {
        File[] files = dir.listFiles();
        boolean success = true;
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    success &= deleteContents(file);
                }
                if (!file.delete()) {
                    Log.w(TAG, "Failed to delete " + file);
                    success = false;
                }
            }
        }
        return success;
    }

    public static String buildValidFatFilename(String name) {
        if (TextUtils.isEmpty(name) || ".".equals(name) || "..".equals(name)) {
            return "(invalid)";
        }
        final StringBuilder res = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            final char c = name.charAt(i);
            if (isValidFatFilenameChar(c)) {
                res.append(c);
            } else {
                res.append('_');
            }
        }
        // Even though vfat allows 255 UCS-2 chars, we might eventually write to
        // ext4 through a FUSE layer, so use that limit.
        trimFilename(res, 255);
        return res.toString();
    }

    private static boolean isValidFatFilenameChar(char c) {
        if ((0x00 <= c && c <= 0x1f)) {
            return false;
        }
        switch (c) {
            case '"':
            case '*':
            case '/':
            case ':':
            case '<':
            case '>':
            case '?':
            case '\\':
            case '|':
            case 0x7F:
                return false;
            default:
                return true;
        }
    }

    /**
     * Generates a unique file name under the given parent directory, keeping
     * any extension intact.
     */
    public static File buildUniqueFile(File parent, String displayName)
            throws FileNotFoundException {
        final String name;
        final String ext;
        // Extract requested extension from display name
        final int lastDot = displayName.lastIndexOf('.');
        if (lastDot >= 0) {
            name = displayName.substring(0, lastDot);
            ext = displayName.substring(lastDot + 1);
        } else {
            name = displayName;
            ext = null;
        }
        return buildUniqueFileWithExtension(parent, name, ext);
    }

    public static File buildUniqueFile(File parent, String mimeType, String displayName)
            throws FileNotFoundException {
        final String[] parts = splitFileName(mimeType, displayName);
        return buildUniqueFileWithExtension(parent, parts[0], parts[1]);
    }

    /**
     * Splits file name into base name and extension.
     * If the display name doesn't have an extension that matches the requested MIME type, the
     * extension is regarded as a part of filename and default extension for that MIME type is
     * appended.
     */
    public static String[] splitFileName(String mimeType, String displayName) {
        String name;
        String ext;
        if (DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType)) {
            name = displayName;
            ext = null;
        } else {
            String mimeTypeFromExt;
            // Extract requested extension from display name
            final int lastDot = displayName.lastIndexOf('.');
            if (lastDot >= 0) {
                name = displayName.substring(0, lastDot);
                ext = displayName.substring(lastDot + 1);
                mimeTypeFromExt = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                        ext.toLowerCase());
            } else {
                name = displayName;
                ext = null;
                mimeTypeFromExt = null;
            }
            if (mimeTypeFromExt == null) {
                mimeTypeFromExt = "application/octet-stream";
            }
            final String extFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(
                    mimeType);
            if (Objects.equals(mimeType, mimeTypeFromExt) || Objects.equals(ext, extFromMimeType)) {
                // Extension maps back to requested MIME type; allow it
            } else {
                // No match; insist that create file matches requested MIME
                name = displayName;
                ext = extFromMimeType;
            }
        }
        if (ext == null) {
            ext = "";
        }
        return new String[] { name, ext };
    }


    private static void trimFilename(StringBuilder res, int maxBytes) {
        byte[] raw = res.toString().getBytes(StandardCharsets.UTF_8);
        if (raw.length > maxBytes) {
            maxBytes -= 3;
            while (raw.length > maxBytes) {
                res.deleteCharAt(res.length() / 2);
                raw = res.toString().getBytes(StandardCharsets.UTF_8);
            }
            res.insert(res.length() / 2, "...");
        }
    }

    private static File buildUniqueFileWithExtension(File parent, String name, String ext)
            throws FileNotFoundException {
        File file = buildFile(parent, name, ext);
        // If conflicting file, try adding counter suffix
        int n = 0;
        while (file.exists()) {
            if (n++ >= 32) {
                throw new FileNotFoundException("Failed to create unique file");
            }
            file = buildFile(parent, name + " (" + n + ")", ext);
        }
        return file;
    }

    private static File buildFile(File parent, String name, String ext) {
        if (TextUtils.isEmpty(ext)) {
            return new File(parent, name);
        } else {
            return new File(parent, name + "." + ext);
        }
    }
}
