package com.example.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


/**
 *
 * Since reactive processing of data does not take place on the same thread,
 * Reactor provides a context that will be available to the
 * threads processing the data.
 * The subscriber context is used in the infastructure code by Spring.
 * e.g Transaction propagation , etc
 *
 * Flux<Reservation> saveReservationFluxWithContext = Flux
 *                 .just("Madhura", "Josh", "Olga", "Marcin", "Ria", "Stephane", "Violetta", "Dr. Syer")
 *                 .map(name -> new Reservation(null, name))
 *                 .flatMap(this.reservationRepository::save)
 *                 .doOnEach(signal -> signal.getContext().get("a1"))
 *                 .subscriberContext(Context.of("a", "b", "a1", "b1"));
 *
 *   This is an ALTERNATIVE LOW LEVEL approach to reactively
 *   querying the postgres database.
 *   preferred approach would be to use the SPRING DATA ORM
 *
 *   private final DatabaseClient databaseClient;
 *   this.databaseClient
 *                 .select()
 *                 .from("reservation").as(Reservation.class)
 *                 .fetch()
 *                 .all()
 *                 .subscribe(log::info);
 *
 *   Commands used to install
 *   docker run --name postgres -e POSTGRES_PASSWORD=12345 -p 5432:5432 -d --rm postgres
 *   docker run -d --rm -v /home/pasberry/mongo/data/db:/data/db -p 27017:27017  --name spring-mongo mongo:latest
 */

@Log4j2
@Component
@RequiredArgsConstructor
class SampleDataInitializer {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready(){

        Flux<Reservation> saveReservationFlux =
                this.reservationService.saveAll("Madura", "Josh", "Olga", "Marcin",
                        "Ria", "Stephane", "Violetta", "Dr Syer");

        this.reservationRepository
                .deleteAll()
                .thenMany(saveReservationFlux)
                .thenMany(this.reservationRepository.findAll())
                .subscribe(log::info);

    }
}
