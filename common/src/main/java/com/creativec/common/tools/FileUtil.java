package com.creativec.common.tools;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class FileUtil {
    private static Logger logger = Logger.getLogger(FileUtil.class.getName());

    /**
     * 加载文件内容
     * @param path
     * @return
     */
    public static String loadFile(String path){
        try {
            InputStream inputStream = new ClassPathResource(path).getInputStream();
            StringBuilder out = new StringBuilder();
            byte[] b = new byte[4096];
            for (int n; (n = inputStream.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
            return out.toString();
        } catch (IOException e) {
            logger.info(e.getMessage());
            return null;
        }
    }
}
