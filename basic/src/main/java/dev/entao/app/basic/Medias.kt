package dev.entao.app.basic

import android.media.MediaMetadataRetriever
import java.io.File


fun videoMillSeconds(file: File): Long {
    val m = MediaMetadataRetriever()
    m.setDataSource(file.absolutePath)
    m.closeAuto {
        val ms = m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) ?: return 0L
        return ms.toLongOrNull() ?: 0L
    }
}
