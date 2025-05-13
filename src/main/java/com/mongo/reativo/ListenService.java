package com.mongo.reativo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ListenService {
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    private Flux<ChangeStreamEvent<Retorno>> changeStream() {
        return reactiveMongoTemplate.changeStream(Retorno.class)
            .watchCollection("origem")
            .filter(where("operationType").in("insert", "update"))
            .filter(where("data").exists(true))
            .listen();
    }

    public void logarRetornoChangeStream() {
        changeStream()
            .doOnNext(event -> {
                Retorno retorno = event.getBody();
                System.out.println("Retorno: " + retorno.getNome());
            })
            .subscribe();
    }

    @PostConstruct
    public void initObservables() {
        logarRetornoChangeStream();
    }

}
