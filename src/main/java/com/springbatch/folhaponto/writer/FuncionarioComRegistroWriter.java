package com.springbatch.folhaponto.writer;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FuncionarioComRegistroWriter {

    @Primary
    @Bean("folhaPontoFlatFileWriter")
    public FlatFileItemWriter<FolhaPonto> folhaPontoFlatFileWriter() {
        return new FlatFileItemWriterBuilder<FolhaPonto>()
                .name("folhaPontoFlatFileWriter")
                .resource(new PathResource("./files/folha_ponto.txt"))
                .headerCallback(headerCallback())
                .lineAggregator(folhaPontoLineAggregator())
                .footerCallback(footerCallback())
                .build();
    }

    private LineAggregator<FolhaPonto> folhaPontoLineAggregator() {
        return this::imprimePontos;
    }

    private String imprimePontos(FolhaPonto folhaPonto) {
        StringBuilder writer = new StringBuilder();
        writer.append(String.format("----------------------------------------------------------------------------\n"));
        writer.append(String.format("NOME:%s\n", folhaPonto.getNome()));
        writer.append(String.format("MATRICULA:%s\n", folhaPonto.getMatricula()));
        writer.append(String.format("----------------------------------------------------------------------------\n"));
        writer.append(String.format("%10s%10s%10s%10s%10s", "DATA", "ENTRADA", "SAIDA", "ENTRADA", "SAIDA"));

        for (String dataRegistroPonto : folhaPonto.getRegistrosPontos().keySet()) {
            writer.append(String.format("\n%s", dataRegistroPonto));

            for (String registro : folhaPonto.getRegistrosPontos().get(dataRegistroPonto)) {
                writer.append(String.format("%10s", registro));
            }
        }

        return writer.toString();
    }

    private FlatFileHeaderCallback headerCallback() {
        return writer -> {
            writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t\t DATA: %s\n", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
            writer.append(String.format("MÓDULO: RH \t\t\t\t\t\t\t\t\t HORA: %s\n", new SimpleDateFormat("HH:MM").format(new Date())));
            writer.append(String.format("\t\t\t\t\tFOLHA DE PONTO\n"));
        };
    }

    private FlatFileFooterCallback footerCallback() {
        return writer -> writer.append(String.format("\n\t\t\t\t\t\t\t  Código de Autenticação: %s\n", "fkyew6868fewjfhjjewf"));
    }

}
