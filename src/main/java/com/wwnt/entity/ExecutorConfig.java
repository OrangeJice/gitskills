package com.wwnt.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by dev3 on 21/12/2017.
 */
//@Configuration
//@EnableAsync
public class ExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);
    /**
     * 核心线程数，默认为1
     */
    private int corePoolSize = 5;
    /**
     * 最大线程数，默认为Integer.MAX_VALUE  2的31次方-1
     */
    private int maxPoolSize = 50;
    /**
     * 队列最大长度，一般需要设置值>=notifyScheduledMainExecutor.maxNum；默认为Integer.MAX_VALUE
     */
    private int queueCapacity = 200;
    /**
     * 线程池维护线程所允许的空闲时间，默认为60s
     */
    //private int keepAliveSeconds = 30;

    /**
     * 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
     * AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
     * CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
     * DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行
     * DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行
     */

    @Bean
    public Executor displayTracksAsyncAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //executor.setAllowCoreThreadTimeOut(true);//核心线程超时关闭
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("TrackExecutor-");
        //executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        logger.info("------displayTracks线程池初始化成功");
        return executor;
    }
}
