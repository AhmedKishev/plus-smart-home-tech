package ru.yandex.practicum.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Analyzer {

    private final int arrayBlockingQueueCapacity = 2;
    private final int threadPoolCorePoolSize = 2;
    private final int threadPoolMaximumPoolSize = 2;
    private final long threadPoolKeepAliveTime = 60L;

    public static void main(String[] args) {
        SpringApplication.run(Analyzer.class, args);
    }

    @Bean
    public ExecutorService getExecutorService() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(arrayBlockingQueueCapacity);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadPoolCorePoolSize, threadPoolMaximumPoolSize, threadPoolKeepAliveTime, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }
}