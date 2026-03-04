package com.fluxocaixacontrol.fluxo_caixa_control.batch;

import com.fluxocaixacontrol.fluxo_caixa_control.domain.model.SaldoConsolidado;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;

public class ConsolidadoBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JobLauncher jobLauncher;
    private final ConsolidadoReader reader;
    private final ConsolidadoProcessor processor;
    private final ConsolidadoWriter writer;

    @Bean
    public Job consolidarSaldoDiarioJob() {
        return new JobBuilder("consolidarSaldoDiarioJob", jobRepository)
                .start(consolidarSaldoStep())
                .build();
    }

    @Bean
    public Step consolidarSaldoStep() {
        return new StepBuilder("consolidarSaldoStep", jobRepository)
                .<LocalDate, SaldoConsolidado>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retryLimit(3)
                .retry(Exception.class)
                .build();
    }

    // Executa todo dia às 00:05 para processar o dia anterior
    @Scheduled(cron = "0 5 0 * * *")
    public void executarJobDiario() {
        executarParaData(LocalDate.now().minusDays(1));
    }

    public void executarParaData(LocalDate data) {
        var params = new JobParametersBuilder()
                .addString("data", data.toString())
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(consolidarSaldoDiarioJob(), params);
    }
}
