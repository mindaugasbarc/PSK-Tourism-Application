package com.tourism.psk;

import com.tourism.psk.model.*;
import com.tourism.psk.repository.TripRepository;
import com.tourism.psk.repository.WorkerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class PskApplication implements CommandLineRunner {

	private final WorkerRepository workerRepository;
	private final TripRepository tripRepository;

	public PskApplication(WorkerRepository workerRepository, TripRepository tripRepository) {
		this.workerRepository = workerRepository;
		this.tripRepository = tripRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Worker jonas = new Worker("Jonas", "Jonaitis");
		workerRepository.save(jonas);
		tripRepository.save(
				new Trip(
					new Duration(LocalDate.now(), LocalDate.ofYearDay(2019, 28)),
					Arrays.asList(jonas),
					new TravelInfo(
							"Vilnius",
							"Paris",
							Arrays.asList(
									new Car("Vilnius", "Warsaw"),
									new Plane("Warsaw", "Paris"))),
					Arrays.asList(
							new HotelWithPeople(
								new Hotel(
									new Duration(LocalDate.now(), LocalDate.ofYearDay(2019, 28)),
								"Le france st 48"),
								Arrays.asList(jonas)))));
	}
}
