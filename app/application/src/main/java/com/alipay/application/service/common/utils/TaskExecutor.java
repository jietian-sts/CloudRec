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
package com.alipay.application.service.common.utils;

import java.util.concurrent.*;

public class TaskExecutor {

    private static final ExecutorService executorService = new ThreadPoolExecutor(
            8,
            8,
            1L,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(1000),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * Executes a given task in a new thread with a timeout and returns the result.
     *
     * @param task    The task to be executed, returning a result.
     * @param timeout The maximum time to wait for the task to complete, in milliseconds.
     * @param <V>     The result type of method call.
     * @return The result of the task if completed within the timeout.
     * @throws TimeoutException     If the task takes longer than the specified timeout.
     * @throws InterruptedException If the thread is interrupted while waiting.
     * @throws ExecutionException   If the computation threw an exception.
     */
    public static <V> V executeWithTimeout(Callable<V> task, long timeout)
            throws TimeoutException, InterruptedException, ExecutionException {

        Future<V> future = executorService.submit(task);

        try {
            // Wait for the task to complete with the specified timeout and return the result.
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // Cancel the task if it exceeds the timeout.
            future.cancel(true);
            throw new TimeoutException("Task execution exceeded timeout of " + timeout + " milliseconds");
        }catch (RejectedExecutionException e){
            future.cancel(true);
            throw new RejectedExecutionException("Task execution was rejected");
        }
    }

    /**
     * Executes a given task
     *
     * @param task The task to be executed, returning a result.
     * @param <V>  The result type of method call.
     * @return The result of the task if completed within the timeout.
     * @throws InterruptedException If the thread is interrupted while waiting.
     * @throws ExecutionException   If the computation threw an exception.
     */
    public static <V> V execute(Callable<V> task)
            throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<V> future = executor.submit(task);
        return future.get();
    }


    public static void execute(Runnable runnable)
            throws InterruptedException, ExecutionException {
        Future<?> future = executorService.submit(runnable);
        // Wait for the task to complete with the specified timeout and return the result.
        future.get();
    }

//    public static void main(String[] args) {
//        Callable<String> task = () -> {
//            try {
//                // Simulate a long-running task
//                Thread.sleep(4000);
//                return "Task completed successfully!";
//            } catch (InterruptedException e) {
//                throw new RuntimeException("Task was interrupted", e);
//            }
//        };
//
//        try {
//            // Execute the task with a timeout of 3 seconds and get the result
//            String result = executeWithTimeout(task, 3000);
//            System.out.println("Result: " + result);
//        } catch (TimeoutException | InterruptedException | ExecutionException e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//    }
}