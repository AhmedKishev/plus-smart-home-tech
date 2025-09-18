package ru.yandex.practicum.analyzer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

@SpringBootApplication
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Analyzer {

    int arrayBlockingQueueCapacity = 2;
    int threadPoolCorePoolSize = 2;
    int threadPoolMaximumPoolSize = 2;
    long threadPoolKeepAliveTime = 60L;

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