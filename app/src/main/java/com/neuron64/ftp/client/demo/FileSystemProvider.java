package com.neuron64.ftp.client.demo;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.DocumentsContract;

import com.neuron64.ftp.client.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by yks-11 on 10/3/17.
 */

public class FileSystemProvider extends DocumentsProvider{

    public static final String ID = "com.neuron64.ftp.provider.filesystem";

    private FileAccessor mAccessor = new FileAccessor();

    private final static String[] DEFAULT_DOCUMENT_PROJECTION = new String[] {
            BaseColumns._ID,
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_FLAGS,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
            DocumentsContract.Document.COLUMN_SIZE,
            DocumentsContract.Document.COLUMN_LAST_MODIFIED
    };

    public FileSystemProvider(Context context) {
        super(context);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder, String mimeFilter) throws FileNotFoundException {
        final File parent = new File(parentDocumentId);
        List<File> files = new ArrayList<File>();
        for (File file : parent.listFiles()) {
            // Don't show hidden files/folders
            if (!file.getName().startsWith(".")) {
                // Adds the file's display name, MIME type, size, and so on.
                files.add(file);
            }
        }
        return new ListCursor<File>(files, mAccessor, projection, sortOrder, mimeFilter);
    }

    @Override
    public Collection<? extends Root> getRoots() throws FileNotFoundException {
        File homeDir = Environment.getExternalStorageDirectory();

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            Root root = new Root(this,
                    homeDir.getAbsolutePath(),
                    homeDir.getAbsolutePath(),
                    getContext().getString(R.string.internal_storage),
                    R.drawable.ic_folder,
                    homeDir.getFreeSpace(),
                    DocumentsContract.Root.FLAG_LOCAL_ONLY | DocumentsContract.Root.FLAG_SUPPORTS_CREATE);

            return Collections.singletonList(root);

        } else {
            return Collections.emptyList();
        }
    }

    public static void register(Context context) {
        DocumentsProviderRegistry.get().register(ID, new FileSystemProvider(context));
    }
}
