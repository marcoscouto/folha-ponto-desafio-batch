package com.springbatch.folhaponto.writer;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioSemRegistroWriter {

    @Bean("funcionarioSemPontoFlatFileWriter")
    public FlatFileItemWriter<FolhaPonto> funcionarioSemPontoFlatFileWriter() {
        return new FlatFileItemWriterBuilder<FolhaPonto>()
                .name("funcionarioSemPontoFlatFileWriter")
                .resource(new PathResource("./files/funcionario_sem_ponto.txt"))
                .lineAggregator(funcionarioSemPontoLineAggregator())
                .build();
    }

    private LineAggregator<FolhaPonto> funcionarioSemPontoLineAggregator() {
        return folhaPonto -> String.valueOf(folhaPonto.getMatricula());

    }

}
