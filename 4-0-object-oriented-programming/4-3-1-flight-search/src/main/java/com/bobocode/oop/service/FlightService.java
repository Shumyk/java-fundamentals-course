package com.bobocode.oop.service;

import com.bobocode.oop.data.Flights;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link FlightService} provides an API that allows to manage flight numbers
 * <p>
 */
@RequiredArgsConstructor
public class FlightService {

    private final Flights flights;

    /**
     * Adds a new flight number
     *
     * @param flightNumber a flight number to add
     * @return {@code true} if a flight number was added, {@code false} otherwise
     */
    public boolean registerFlight(String flightNumber) {
        return flights.register(flightNumber);
    }

    /**
     * Returns all flight numbers that contains a provided key.
     *
     * @param query a search query
     * @return a list of found flight numbers
     */
    public List<String> searchFlights(String query) {
        return flights.findAll()
                .stream()
                .filter(f -> f.toUpperCase().contains(query.toUpperCase()))
                .collect(Collectors.toList());
    }
}
