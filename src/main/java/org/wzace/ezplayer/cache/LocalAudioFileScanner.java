package org.wzace.ezplayer.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @description: Local Audio File Scanner
 * @author: wangzhe
 * @date: 2024/12/1 9:34
 * @Version: 1.0
 */
public class LocalAudioFileScanner {

    private static final Logger log = LoggerFactory.getLogger(LocalAudioFileScanner.class);

    // 定义支持的文件扩展名
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList("mp3", "wav", "aac", "m4a", "aiff", "ogg");

    public static List<LocalAudioFile> doScan(String directoryPath) {
        Objects.requireNonNull(directoryPath);

        List<LocalAudioFile> fileList = new ArrayList<>();
        File rootDirectory = new File(directoryPath);

        // 检查指定路径是否是一个有效目录
        if (rootDirectory.isDirectory()) {
            // 使用栈来存储待处理的目录
            Stack<File> stack = new Stack<>();
            stack.push(rootDirectory);

            while (!stack.isEmpty()) {
                File currentDirectory = stack.pop();
                File[] files = currentDirectory.listFiles(); // 获取当前目录下的所有文件和目录

                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            String fileExtension = getFileExtension(file.getName());
                            if (SUPPORTED_EXTENSIONS.contains(fileExtension)) {
                                fileList.add(new LocalAudioFile(removeFileExtension(file.getName()), fileExtension, file.getAbsolutePath()));
                            }
                        }

                        // 如果是目录，将其推入栈中以便后续处理
                        if (file.isDirectory()) {
                            stack.push(file);
                        }
                    }
                }
            }
        } else {
            log.error("Scanning folder exception! [{}] is not a valid directory.", directoryPath);
        }

        return fileList;
    }

    // 获取文件扩展名的方法
    private static String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex >= 0 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex + 1); // 返回扩展名（不包括点）
        }
        return ""; // 如果没有扩展名，则返回空字符串
    }

    // 去除文件扩展名的方法
    private static String removeFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return fileName; // 如果没有扩展名，返回原文件名
        }
        return fileName.substring(0, lastIndexOfDot); // 返回去掉扩展名的文件名
    }

}
