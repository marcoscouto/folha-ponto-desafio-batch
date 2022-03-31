package com.springbatch.folhaponto.writer;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.PathResource;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class FolhaPontoWriterConfig {

    @Bean("classifierCompositeItemWriter")
    public ClassifierCompositeItemWriter<FolhaPonto> classifierCompositeItemWriter(
            @Qualifier("folhaPontoFlatFileWriter") ItemWriter<? super FolhaPonto> folhaPontoFlatFileWriter,
            @Qualifier("funcionarioSemPontoFlatFileWriter") ItemWriter<? super FolhaPonto> funcionarioSemPontoFlatFileWriter
    ) {
        return new ClassifierCompositeItemWriterBuilder<FolhaPonto>()
                .classifier(classifier(folhaPontoFlatFileWriter, funcionarioSemPontoFlatFileWriter))
                .build();
    }

    private Classifier<FolhaPonto, ItemWriter<? super FolhaPonto>> classifier(
            ItemWriter<? super FolhaPonto> folhaPontoFlatFileWriter,
            ItemWriter<? super FolhaPonto> funcionarioSemPontoFlatFileWriter
    ) {
        return folhaPonto ->
                folhaPonto.getNome() != null ? folhaPontoFlatFileWriter : funcionarioSemPontoFlatFileWriter;
    }

}
