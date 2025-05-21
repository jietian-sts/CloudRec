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
package com.alipay.application.service.rule.utils;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DirectoryWalkerUtil {
    
    public static List<Path> findDeepestDirectories(Path startPath) throws IOException {
        List<Path> deepestDirs = new ArrayList<>();
        Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                boolean hasSubDir = false;
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                    for (Path entry : stream) {
                        if (Files.isDirectory(entry)) {
                            hasSubDir = true;
                            break;
                        }
                    }
                }
                if (!hasSubDir) {
                    deepestDirs.add(dir);
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return deepestDirs;
    }
}
