package xyz.kfdykme.mobistudi.tool;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * @author kf
 * @date 2017/6/30
 * 用户注册或登录时查找文件中是否存在该用户的信息
 */

public class FileHelper {

    public static String DEFAULT_ROOT_DIR = "/mobistudi/";

    public static String rootDIr = DEFAULT_ROOT_DIR;

    public static String NOT_FIND = "not find";

    /**
     * 重新创建文件
     */
    public static void createFile(@NonNull String reDir,@NonNull String content,@NonNull String name ,@NonNull String fileType) throws IOException {
        File root = Environment.getExternalStorageDirectory();

        File dir = new File(root.getCanonicalPath()+rootDIr+reDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File targetFile = new File(dir.getCanonicalPath()+"/"+name+"."+fileType);
        if(targetFile.exists()){
            targetFile.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(targetFile,"rw");
        raf.write(content.getBytes());
        raf.close();
        Log.i("FileHelper","creat/edit file "+name+" successfully");
    }

    /**
     *  暂时只用于读取json
     */
    public static String readFile(@NonNull String reDir,@NonNull String name,@NonNull String fileType) throws IOException {
        File root = Environment.getExternalStorageDirectory();

        File dir = new File(root.getCanonicalPath()+rootDIr+reDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File targetFile = new File(dir.getCanonicalPath()+"/"+name+"."+fileType);

        String content = "";
        if(targetFile.isFile() && targetFile.exists()){

            InputStreamReader read = null;

            read = new InputStreamReader(new FileInputStream(targetFile),"utf8");

            BufferedReader bufferedReader = new BufferedReader(read);
            String line = null;

            while((line = bufferedReader.readLine()) != null) {
                content+=line;
            }
        }else{
            content = NOT_FIND;
        }
        return content;
    }
}
