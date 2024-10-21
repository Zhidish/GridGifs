import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class CustomGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {

        val memoryCacheSizeBytes = (30 * 1024 * 1024).toLong() // 30 MB
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes))

        val diskCacheSizeBytes = (100 * 1024 * 1024).toLong() // 100 MB
        val diskCacheDirectory = "${context.cacheDir}/glide_cache"
        builder.setDiskCache(DiskLruCacheFactory(diskCacheDirectory, diskCacheSizeBytes))
    }

    override fun isManifestParsingEnabled() = false
}