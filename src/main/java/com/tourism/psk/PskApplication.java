package com.tourism.psk;

import com.tourism.psk.constants.DocumentStatus;
import com.tourism.psk.constants.DocumentType;
import com.tourism.psk.model.*;
import com.tourism.psk.model.response.TripResponse;
import com.tourism.psk.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class PskApplication implements CommandLineRunner {

	private final WorkerRepository workerRepository;
	private final TripRepository tripRepository;
	private final TripResponseRepository tripResponseRepository;
	private final OfficeRepository officeRepository;
	private final OfficeRoomRepository officeRoomRepository;

	public PskApplication(WorkerRepository workerRepository,
						  TripRepository tripRepository,
						  TripResponseRepository tripResponseRepository,
						  OfficeRepository officeRepository,
						  OfficeRoomRepository officeRoomRepository) {
		this.workerRepository = workerRepository;
		this.tripRepository = tripRepository;
		this.tripResponseRepository = tripResponseRepository;
		this.officeRepository = officeRepository;
		this.officeRoomRepository = officeRoomRepository;
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

		tripResponseRepository.save(new TripResponse(Arrays.asList(new Document(DocumentStatus.PENDING, DocumentType.TICKET, "plane tickets from Vilnius to Copenhagen")),
				Arrays.asList(new Accomodation("51st street, Copenhagen", "DevBridge assignment")),
				new TripInfo(new Office("Vilnius Office", "Kalvariju st. 21", new ArrayList<>()),
						new Office("Vilnius Office", "Copenhagen st. 52", new ArrayList<>()), LocalDate.now(), LocalDate.now())));


		OfficeRoom room1 = officeRoomRepository.save(new OfficeRoom("Office room 1"));
		Office office = officeRepository.save(new Office("Kaunas office", "Savanoriu pr. 20", new ArrayList<>()));
		office.addHouseRoom(room1);
		office.addHouseRoom(new OfficeRoom("Office room 2"));
		officeRepository.save(office);
	}
}
