package ru.yandex.practicum.analyzer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.processors.HubEventProcessor;
import ru.yandex.practicum.analyzer.processors.SnapshotEventProcessor;

import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
public class AnalyzerRunner implements CommandLineRunner {
    final HubEventProcessor hubEventProcessor;
    final SnapshotEventProcessor snapshotEventProcessor;
    final ExecutorService executorService;

    @Override
    public void run(String... args) throws Exception {
        executorService.submit(hubEventProcessor);
        executorService.submit(snapshotEventProcessor);
    }
}