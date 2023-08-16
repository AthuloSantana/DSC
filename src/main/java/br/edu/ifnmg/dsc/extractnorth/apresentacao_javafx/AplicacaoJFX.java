package br.edu.ifnmg.dsc.extractnorth.apresentacao_javafx;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import br.edu.ifnmg.dsc.extractnorth.apresentacao_javafx.AplicacaoSpring;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class AplicacaoJFX extends Application {
    private ConfigurableApplicationContext contextoSpring;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.contextoSpring = new SpringApplicationBuilder()
                .sources(AplicacaoSpring.class)
                .run(args);
    }

    @Override
    public void stop() {
        this.contextoSpring.close();
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}
