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


/*
 *@title GitHubSync
 *@description
 *@author jietian
 *@version 1.0
 *@create 2025/3/18 16:01
 */

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import java.nio.file.Path;

@Slf4j
public class GitHubSyncUtil {
    public static void cloneRepository(String repoUrl, Path localPath) {
        try (Git ignored = Git.cloneRepository().setURI(repoUrl).setDirectory(localPath.toFile()).call()) {
            log.info("Repository cloned successfully");
        } catch (InvalidRemoteException e) {
            throw new RuntimeException("Invalid warehouse address", e);
        } catch (TransportException e) {
            throw new RuntimeException("Network transmission error", e);
        } catch (GitAPIException e) {
            throw new RuntimeException("Git operation failed", e);
        }
    }
}
