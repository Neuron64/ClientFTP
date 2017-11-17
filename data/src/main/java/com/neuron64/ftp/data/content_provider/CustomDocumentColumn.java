package com.neuron64.ftp.data.content_provider;

import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;

/**
 * Created by yks-11 on 10/18/17.
 */

@Deprecated
public class CustomDocumentColumn {

    /**
     * Default Column from {@link DocumentsContract.Document(Uri)}
     **/

    public static final String COLUMN_DOCUMENT_ID = "document_id";

    public static final String COLUMN_MIME_TYPE = "mime_type";

    public static final String COLUMN_DISPLAY_NAME = OpenableColumns.DISPLAY_NAME;

    public static final String COLUMN_SUMMARY = "summary";

    public static final String COLUMN_LAST_MODIFIED = "last_modified";

    public static final String COLUMN_ICON = "icon";

    public static final String COLUMN_FLAGS = "flags";

    public static final String COLUMN_SIZE = OpenableColumns.SIZE;

    public static final String MIME_TYPE_DIR = "vnd.android.document/directory";

    /**
     * Custom Column
     **/



}
