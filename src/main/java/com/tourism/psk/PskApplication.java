package com.tourism.psk;

import com.tourism.psk.constants.DocumentStatus;
import com.tourism.psk.constants.DocumentType;
import com.tourism.psk.constants.UserRole;
import com.tourism.psk.model.*;
import com.tourism.psk.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class PskApplication implements CommandLineRunner {

	private final WorkerRepository workerRepository;
	private final TripRepository tripRepository;
	private final TripRepository tripResponseRepository;
	private final OfficeRepository officeRepository;
	private final OfficeRoomRepository officeRoomRepository;
	private final UserRepository userRepository;
	private final OccupationRepository occupationRepository;
	private final GroupTripRepository groupTripRepository;

	@Value("${date-format}")
	private String dateFormat;

	public PskApplication(WorkerRepository workerRepository,
						  TripRepository tripRepository,
						  TripRepository tripResponseRepository,
						  OfficeRepository officeRepository,
						  OfficeRoomRepository officeRoomRepository,
						  UserRepository userRepository,
						  OccupationRepository occupationRepository, GroupTripRepository groupTripRepository) {
		this.workerRepository = workerRepository;
		this.tripRepository = tripRepository;
		this.tripResponseRepository = tripResponseRepository;
		this.officeRepository = officeRepository;
		this.officeRoomRepository = officeRoomRepository;
		this.userRepository = userRepository;
		this.occupationRepository = occupationRepository;
		this.groupTripRepository = groupTripRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Worker jonas = new Worker("Jonas", "Jonaitis");
		workerRepository.save(jonas);
		Trip trip = new Trip(Arrays.asList(new Document(DocumentStatus.PENDING, DocumentType.TICKET, "plane tickets from Vilnius to Copenhagen")),
				Arrays.asList(new Accomodation("51st street, Copenhagen", "DevBridge assignment")),
				new TripInfo(new Office("Vilnius Office", "Kalvariju st. 21", new ArrayList<>()),
						new Office("Vilnius Office", "Copenhagen st. 52", new ArrayList<>()), LocalDate.now(), LocalDate.now()));
		tripResponseRepository.save(trip);




		OfficeRoom room1 = officeRoomRepository.save(new OfficeRoom("Office room 1"));
		Office office = officeRepository.save(new Office("Kaunas office", "Savanoriu pr. 20", new ArrayList<>()));
		office.addHouseRoom(room1);
		office.addHouseRoom(new OfficeRoom("Office room 2"));
		officeRepository.save(office);

		User user = new User("Testas Testas", "testmail@test.com", UserRole.USER);
		UserLogin userLogin = new UserLogin("testusername", "testpassword");
		user.setUserLogin(userLogin);
		userLogin.setUser(user);
		userRepository.save(user);

		DateFormat format = new SimpleDateFormat(dateFormat);
		Occupation occupation = new Occupation(format.parse("2019-01-01"), format.parse("2019-01-07"));
		occupation.setUser(user);
		occupationRepository.save(occupation);

		Set<Trip> trips = new HashSet<>();
		trips.add(trip);
		groupTripRepository.save(new GroupTrip("test trip", "the best trip", trips, office, office, Arrays.asList(new Comment(user, "test", "test", null))));
	}
}
