package edu.gatech.bobsbuilders.socialsaver;

import android.content.Context;

import java.io.File;

class FileCache {

	private File cacheDir;
	
	public FileCache() {
	
	}
	public FileCache(Context context) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"ParseListViewImgTxt");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
            //noinspection ResultOfMethodCallIgnored
            cacheDir.mkdirs();
	}

	public File getFile(String url) {
		String filename = String.valueOf(url.hashCode());
		// String filename = URLEncoder.encode(url);
		@SuppressWarnings("UnnecessaryLocalVariable") File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
            //noinspection ResultOfMethodCallIgnored
            f.delete();
	}

}