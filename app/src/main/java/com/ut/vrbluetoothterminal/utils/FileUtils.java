package com.ut.vrbluetoothterminal.utils;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by djf on 2017/3/25.
 */

public class FileUtils {

    /**
     * 创建文件
     *
     * @param filepath 文件
     * @return 是否创建成功，成功则返回true
     */
    public static boolean createFile(Context context, String filepath) {
        Boolean bool = false;

        File file = new File(filepath);
        try {
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is " + file.getAbsolutePath());
                //创建文件成功后，写入内容到文件里

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * B方法追加文件：使用FileWriter
     */
    public static void appendMethodB(Context context, String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            if (!new File(fileName).exists()) {
                createFile(context, fileName);
            }
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
