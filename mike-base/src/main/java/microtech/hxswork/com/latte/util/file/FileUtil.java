package microtech.hxswork.com.latte.util.file;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.sax.RootElement;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.Locale;

import microtech.hxswork.com.latte.init.Latte;
import retrofit2.http.FieldMap;

/**
 * Created by microtech on 2017/11/14.封装的文件出来方法
 */

public final class FileUtil {
    private static final  String TIME_FORMAT=
                                        "_yyyyMMdd_HHmmss";//时间命名文件
    private static final String SDCARD_DIR=
                                        Environment.getExternalStorageDirectory().getPath();

    //默认本地上传图片目录
    public static final String UPLOAD_PHOTO_DIR=
                                        Environment.getExternalStorageDirectory().getPath()+"/a_upload_photos/";

    //网页缓存地址
    public static final String WEB_CACHE_DIR=
                                        Environment.getExternalStorageDirectory().getPath()+"/app_web_cache/";

    //系统相机目录
    public static final String CAMERA_PHOTO_DIR=
                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+"/Camera/";

    private static String getTimeFormatName(String timeFormatHeader){
        final Date date = new Date(System.currentTimeMillis());
        //必须加载单引号
        final SimpleDateFormat dateFormat = new SimpleDateFormat("'"+timeFormatHeader+"'"+TIME_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }
/*
*
*@return 返回时间格式话后的文件名
* */
    public static String getFileNameByTime(String timeFormHeader,String extension){
        return getTimeFormatName(timeFormHeader)+"."+extension;
    }
    private static File createDir(String sdcarDirName){
        //拼接成SD卡完整的Dir
        final String dir = SDCARD_DIR+"/"+sdcarDirName+"/";
        final File fileDir = new File(dir);
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        return fileDir;
    }
    public static File createFile(String sdcardDirName,String fileName){
        return new File(createDir(sdcardDirName),fileName);
    }
    private static File createFileByTime(String sdcardDirName,String timeFormatHeader,String extension){
        final String filename=  getFileNameByTime(timeFormatHeader,extension);
        return createFile(sdcardDirName,filename);
    }

    //获取文件的MIME
    public  static String getMimeType(String filePath){
        final String extension = getExtension(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
    //获取文件的后缀名
    public static String getExtension(String filePath) {
        String suffix="";
        final File file = new File(filePath);
        final String name=  file.getName();
        final  int idx = name.lastIndexOf('.');
        if(idx > 0){
            suffix = name.substring(idx+1);
        }
        return suffix;
    }


    /*
    * 保存Bitmap到SD卡中
    * */
    public static File saveBitmap(Bitmap mBitmap,String dir,int compress){
        final String sdStatus = Environment.getExternalStorageState();
        //检测sd卡是否可用
        if(!sdStatus.equals(Environment.MEDIA_MOUNTED)){
            return  null;
        }
        FileOutputStream fos = null;
        BufferedOutputStream bos= null;
        File fileName = createFileByTime(dir,"DOWN_LOAD","jpg");
        try {
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            mBitmap.compress(Bitmap.CompressFormat.JPEG,compress,bos);//把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
            if(bos != null) {
                bos.flush();
                }
             if(bos !=null){
                 bos.close();
                }
             //关闭流
                if(fos!=null){
                    fos.flush();
                }
             if(fos!=null){
                 fos.close();
                }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            refreshDCIM();//刷新系统相册 显示新的相片出来
        return fileName;
    }

    public static File writeToDisk(InputStream is,String dir ,String name){
        final File file=  FileUtil.createFile(dir,name);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos= null;

        try{
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte data[] = new byte[1024*4];
            int count;
            while ((count= bis.read(data))!=-1)
            {
                bos.write(data,0,count);
            }
            bos.flush();
            fos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                //关闭流
                if(bis!=null){
                    bis.close();
                }
                if(fos!=null){
                    fos.close();
                }
                if(bos!=null){
                    bos.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    public static File writeToDisk(InputStream is,String dir ,String prefix ,String extension){
        final File file=  FileUtil.createFileByTime(dir,prefix,extension);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos= null;

        try{
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte data[] = new byte[1024*4];
            int count;
            while ((count= bis.read(data))!=-1)
            {
                bos.write(data,0,count);
            }
            bos.flush();
            fos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                //关闭流
                if(bis!=null){
                    bis.close();
                }
                if(fos!=null){
                    fos.close();
                }
                if(bos!=null){
                    bos.close();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
/*
* 通知系统刷新系统相册  ，使照片展现出来
* */
private static void refreshDCIM(){
    if(Build.VERSION.SDK_INT>=19){
        MediaScannerConnection.scanFile(Latte.getApplicationContext(),
                    new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()},
                    null,null);
    }else {
        //烧苗整个SD卡来更新系统图库，当文件很多用户
        Latte.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+Environment.getExternalStorageDirectory())));
    }
}

public static String getRawFile(int id){
    final InputStream is= Latte.getApplicationContext().getResources().openRawResource(id);
    final BufferedInputStream bis= new BufferedInputStream(is);
    final InputStreamReader isr =new InputStreamReader(bis);
    final BufferedReader br= new BufferedReader(isr);
    final StringBuilder stringBuilder = new StringBuilder();
    String str;
    try {
        while ((str = br.readLine())!=null){
        stringBuilder.append(str);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        try {
            br.close();
            isr.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    return stringBuilder.toString();
}

public static void setIconFont(String path, TextView textView){
    final Typeface typeface = Typeface.createFromAsset(Latte.getApplicationContext().getAssets(),path);
    textView.setTypeface(typeface);
}
/*
* 读取assets目录下的文件，并返回字符串
* */
public static String getAssetsFile(String name){
    InputStream is=  null;
    BufferedInputStream bis = null;
    InputStreamReader isr= null;
    BufferedReader br= null;
    StringBuilder stingBuilder = null;
    final AssetManager assetManager = Latte.getApplicationContext().getAssets();

    try {
        is = assetManager.open(name);
        bis = new BufferedInputStream(is);
        isr = new InputStreamReader(bis);
        br = new BufferedReader(isr);
        String str;
        while ((str=br.readLine())!=null){
            stingBuilder.append(str);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
            try {
                if(br!=null) {
                    br.close();
                }
                if(isr!=null){
                    isr.close();
                }
                if(bis!=null){
                    bis.close();
                }
                if(is!=null){
                    is.close();
                }
                assetManager.close();
            } catch (IOException e) {
                e.printStackTrace();
        }
    }
    if(stingBuilder!=null){
        return stingBuilder.toString();
    }else {
        return null;
    }
}

public static String getRealFilePath(final Context context,final Uri uri){
    if(null == uri)
        return null;
    final String scheme = uri.getScheme();
    String data = null;
    if(scheme == null){
        data = uri.getPath();
    }else if (ContentResolver.SCHEME_FILE.equals(scheme)){
        data = uri.getPath();
    }else if(ContentResolver.SCHEME_CONTENT.equals(scheme)){
        final Cursor cursor = context.getContentResolver().query(uri,new String[]{MediaStore.Images.ImageColumns.DATA},null,null,null);
        if(null!=cursor){
            if(cursor.moveToFirst()){
                final int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                if(index > -1){
                    data = cursor.getString(index);
                }
            }
            cursor.close();
        }
    }
    return data;
}

}
