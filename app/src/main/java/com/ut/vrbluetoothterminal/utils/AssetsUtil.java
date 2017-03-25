package com.ut.vrbluetoothterminal.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.ut.vrbluetoothterminal.utils.UIUtils.getResources;

/**
 * Created by djf on 2017/3/25.
 */

public class AssetsUtil {
    public  static byte[] getFromAssets(String fileName) {
        try {
            InputStream input = getResources().getAssets().open(fileName);
            BufferedInputStream bis=new BufferedInputStream(input);
            if (bis != null) {
                byte[] bs = new byte[512];
                while(bis.available() > 512) {
                    bis.read(bs);
                    ByteBuffer src = ByteBuffer.wrap(bs);
                    Arrays.fill(bs, (byte)0);
                }
                // 处理不足512的剩余部分
                int remain = bis.available();
                byte[] last = new byte[remain];
                bis.read(last);
                bis.close();
                input.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
