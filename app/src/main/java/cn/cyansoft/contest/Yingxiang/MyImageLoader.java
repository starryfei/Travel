package cn.cyansoft.contest.Yingxiang;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import cn.cyansoft.contest.Constants;


/**
 * 
 * 功能描述: 图片加载
 * @author 欧阳海冰(OuyangHaibing)
 * @date 2014-3-28 下午4:58:44
 */
public class MyImageLoader {

	private volatile static MyImageLoader mInstance;
	
	public static MyImageLoader getInstance(Context context){
		if(mInstance == null){
			synchronized (MyImageLoader.class) {
				if(mInstance == null){
					mInstance = new MyImageLoader(context);
				}
			}
		}
		return mInstance;
	}
	
	private MyImageLoader(Context context){
		//Returns application cache directory. 
		//Cache directory will be created on SD card ("/Android/data/[app_package_name]/cache") 
		//if card is mounted and app has appropriate permission. Else - Android defines cache directory on device's file system
		File diskCache = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
			.threadPoolSize(Constants.IMAGE_LOADER_THREAD_COUNT)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			//当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
			.denyCacheImageMultipleSizesInMemory()
			.discCache(new UnlimitedDiscCache(StorageUtils.getCacheDirectory(context)))
			.discCacheSize(50 * 1024 * 1024)
			.discCacheFileCount(200) //缓存的文件数量  
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			.imageDownloader(new BaseImageDownloader(context, Constants.LOAD_IMAGE_CONNECT_TIMEOUT, Constants.LOAD_IMAGE_READ_TIMEOUT))
			.memoryCache(new WeakMemoryCache())
			.memoryCacheSize(50 * 1024 * 1024)
			.build();
		ImageLoader.getInstance().init(config);
		
	}
	
	/**
	 * 
	 * 功能描述: 下载图片
	 * @param @param url
	 * @param @param options
	 * @param @param listener  
	 * @return void    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午4:59:19
	 */

	public static void loadImage(String url,DisplayImageOptions options,ImageLoadingListener listener){
		ImageLoader.getInstance().loadImage(url, options, listener);
	}
	
	/**
	 * 
	 * 功能描述: 加载图片并在ImageView中显示
	 * @param @param url
	 * @param @param imageview  
	 * @return void    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午4:59:33
	 */
	public static void displayImage(String url,ImageView imageview){
		ImageLoader.getInstance().displayImage(url, imageview);
	}
	
	/**
	 * 
	 * 功能描述: 加载图片并在ImageView中显示
	 * @param @param url
	 * @param @param imageview
	 * @param @param options  
	 * @return void    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午4:59:47
	 */
	public static void displayImage(String url,ImageView imageview,DisplayImageOptions options){
		ImageLoader.getInstance().displayImage(url, imageview, options);
	}
	
	/**
	 * 
	 * 功能描述: 加载图片并在ImageView中显示 
	 * @param @param url
	 * @param @param imageview
	 * @param @param listener  
	 * @return void    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午4:59:59
	 */
	public static void displayImage(String url,ImageView imageview,ImageLoadingListener listener){
		ImageLoader.getInstance().displayImage(url, imageview, listener);
	}
	
	/**
	 * 
	 * 功能描述: 加载图片并在ImageView中显示
	 * @param @param url
	 * @param @param imageview
	 * @param @param options
	 * @param @param listener  
	 * @return void    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:00:10
	 */
	public static void displayImage(String url,ImageView imageview,DisplayImageOptions options,ImageLoadingListener listener){
		ImageLoader.getInstance().displayImage(url, imageview, options, listener);
	}
	
	public static void clearCacheInMemory(){
		ImageLoader.getInstance().clearMemoryCache();
	}
}
