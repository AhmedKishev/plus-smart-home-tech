package ru.yandex.practicum.analyzer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.processors.HubEventProcessor;
import ru.yandex.practicum.analyzer.processors.SnapshotProcessor;

import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnalyzerRunner implements CommandLineRunner {
    HubEventProcessor hubEventProcessor;
    SnapshotProcessor snapshotEventProcessor;
    ExecutorService executorService;

    @Override
    public void run(String... args) throws Exception {
        executorService.submit(hubEventProcessor);
        executorService.submit(snapshotEventProcessor);
    }
}