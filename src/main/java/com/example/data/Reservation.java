package com.example.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;


/**
 * R2dbc example.
 *
 * This is the used for persisting the data.
 *
 * @Data
 * @AllArgsConstructor
 * @NoArgsConstructor
 * class Reservation {
 *
 *     @Id
 *     private Integer id;
 *
 *     private String name;
 * }
 * Mongo example.
 *
 * @Document
 * @Data
 * @AllArgsConstructor
 * @NoArgsConstructor
 * class Reservation {
 *
 *     @Id
 *     private String id;
 *
 *     private String name;
 * }
 *
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
class Reservation {

    @Id
    private Integer id;  //change back to string to use MongoDB

    private String name;
}