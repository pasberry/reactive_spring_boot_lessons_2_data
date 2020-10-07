package com.example.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TransactionalOperator transactionalOperator;

    /**
     * Creates a FLux that will persist the records in the reservation table with
     * an explicit transaction. Transaction management can be applied to databases
     * like MySql, Postgres and even NoSql types like MongoDB. Please refer to
     * the Spring Data docs for more info.
     *
     *
     * @param names - array of names to persist
     * @return Flux of Reservation
     *
     *
     * Example class with implicit transactions
     *
     * @RequiredArgsConstructor
     * @Service
     * @Transactional
     * class ReservationService {
     *
     *     private final ReservationRepository reservationRepository;
     *     private final TransactionalOperator transactionalOperator;
     *
     *     Flux<Reservation> saveAll (String ... names){
     *
     *         Flux<Reservation> saveReservationFlux = Flux
     *                 .fromArray(names)
     *                 .map(name -> new Reservation(null, name))
     *                 .flatMap(this.reservationRepository::save)
     *                 .doOnNext(this::assertReservation);
     *
     *         return saveReservationFlux;
     *     }
     *
     *     private void assertReservation (final Reservation reservation){
     *         Assert.isTrue(isReservationValid(reservation), () -> "The Reservation is not valid");
     *     }
     *
     *     private boolean isReservationValid(Reservation reservation) {
     *         return reservation.getName() != null &&
     *                 Character.isUpperCase(reservation.getName().charAt(0));
     *     }
     *
     *     On the main run SpringBoot application class the following will be
     *     added to the class
     * @EnableTransactionManagement
     */
    Flux<Reservation> saveAll (String ... names){

        Flux<Reservation> saveReservationFlux = Flux
                .fromArray(names)
                .map(name -> new Reservation(null, name))
                .flatMap(this.reservationRepository::save)
                .doOnNext(this::assertReservation);

        return this.transactionalOperator.transactional(saveReservationFlux);
    }

    private void assertReservation (final Reservation reservation){
        Assert.isTrue(isReservationValid(reservation), () -> "The Reservation is not valid");
    }

    private boolean isReservationValid(Reservation reservation) {
        return reservation.getName() != null &&
                Character.isUpperCase(reservation.getName().charAt(0));
    }
}