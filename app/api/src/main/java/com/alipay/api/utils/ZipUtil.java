/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.api.utils;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 *@title ZipUtil
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/20 11:38
 */
@Slf4j
public class ZipUtil {

    private ZipUtil() {
    }

    public static void downloadFiles(HttpServletResponse response, String path, String zipName) {
        try {
            File rootDir = new File(path);

            // 检查目录有效性
            if (!rootDir.exists() || !rootDir.isDirectory()) {
                sendError(response, HttpStatus.NOT_FOUND, "目录不存在: " + path);
                return;
            }

            // 设置响应头
            response.setContentType("application/zip");
            String encodedFileName = URLEncoder.encode(zipName + ".zip", StandardCharsets.UTF_8)
                    .replace("+", "%20");
            response.setHeader("Content-Disposition",
                    "attachment; filename*=UTF-8''" + encodedFileName);

            // 递归写入 ZIP
            writeZipStream(response, rootDir);

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private static void writeZipStream(HttpServletResponse response, File rootDir) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            // 递归添加目录和文件
            addDirToZip(rootDir, rootDir, zipOut);
            zipOut.finish();
        }
    }

    /**
     * 递归添加目录和文件到 ZIP
     *
     * @param rootDir    根目录（用于计算相对路径）
     * @param currentDir 当前处理的目录
     * @param zipOut     ZIP 输出流
     */
    private static void addDirToZip(File rootDir, File currentDir, ZipOutputStream zipOut) {
        File[] files = currentDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                // 递归处理子目录
                addDirToZip(rootDir, file, zipOut);
            } else {
                // 计算相对路径（关键步骤）
                String relativePath = rootDir.toPath().relativize(file.toPath()).toString();
                // 替换路径分隔符为 ZIP 标准格式（兼容 Windows/Linux）
                relativePath = relativePath.replace(File.separatorChar, '/');

                addFileToZip(file, relativePath, zipOut);
            }
        }
    }

    /**
     * 添加单个文件到 ZIP
     */
    private static void addFileToZip(File file, String entryName, ZipOutputStream zipOut) {
        if (!file.canRead()) {
            log.warn("File not readable, skip: {}", file.getAbsolutePath());
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry entry = new ZipEntry(entryName);
            zipOut.putNextEntry(entry);

            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                zipOut.write(buffer, 0, length);
            }
            zipOut.closeEntry();
        } catch (IOException e) {
            log.error("Error adding file to zip: {}", entryName, e);
        }
    }


    private static void sendError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        log.error("Download failed :{}", message);
        if (!response.isCommitted()) {
            response.sendError(status.value(), message);
        }
    }

    private static void handleException(HttpServletResponse response, Exception e) {
        log.error("Download failed", e);
        if (!response.isCommitted()) {
            try {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
            } catch (IOException ex) {
                log.error("response err", e);
            }
        }
    }
}
