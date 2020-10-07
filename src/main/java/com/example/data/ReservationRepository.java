package com.example.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * This is an example Reactive Spring Data repository example.
 *
 * This example worked with both the Mongo DB reactive library
 * and the r2dbc library and Postgres.
 *
 * interface ReservationRepository extends ReactiveCrudRepository<Reservation, String>
 *
 * The above statement was the interface signature for the Mongo Database since the key
 * was a unique uuid generated by the system.
 *
 * interface ReservationRepository extends ReactiveCrudRepository<Reservation, Integer>
 * The above statement was the interface statement for the Postgres example.
 *
 * As of the time of this application examples development, Reactive Spring Data
 * can not handle the following query types for SQL databases. All was fine with NoSQL
 * databases.
 *
 * Flux<Reservation> findByName(String name);
 *
 * The previouse would have to be rewritten with the @Query annotation
 *
 * @Query("select * from reservation where name = :name")
 * Flux<Reservation> findByName(String name);
 */

@Repository
interface ReservationRepository extends ReactiveCrudRepository<Reservation, Integer> {

   // Flux<Reservation> findByName(String name);
}