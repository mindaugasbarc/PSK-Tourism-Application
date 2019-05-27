package com.tourism.psk;

import com.tourism.psk.constants.*;
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

	private final TripRepository tripRepository;
	private final TripRepository tripResponseRepository;
	private final OfficeRepository officeRepository;
	private final OfficeRoomRepository officeRoomRepository;
	private final UserRepository userRepository;
	private final UserOccupationRepository userOccupationRepository;
	private final GroupTripRepository groupTripRepository;
	private final OfficeRoomOccupationRepository officeRoomOccupationRepository;

	@Value("${date-format}")
	private String dateFormat;

	public PskApplication(TripRepository tripRepository,
						  TripRepository tripResponseRepository,
						  OfficeRepository officeRepository,
						  OfficeRoomRepository officeRoomRepository,
						  UserRepository userRepository,
						  UserOccupationRepository userOccupationRepository, GroupTripRepository groupTripRepository,
						  OfficeRoomOccupationRepository officeRoomOccupationRepository) {
		this.tripRepository = tripRepository;
		this.tripResponseRepository = tripResponseRepository;
		this.officeRepository = officeRepository;
		this.officeRoomRepository = officeRoomRepository;
		this.userRepository = userRepository;
		this.userOccupationRepository = userOccupationRepository;
		this.groupTripRepository = groupTripRepository;
		this.officeRoomOccupationRepository = officeRoomOccupationRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Trip trip = new Trip(Arrays.asList(new Document(DocumentStatus.PENDING, DocumentType.TICKET, "plane tickets from Vilnius to Copenhagen")),
				Arrays.asList(new Accommodation("51st street, Copenhagen", "DevBridge assignment")));
		tripResponseRepository.save(trip);

		OfficeRoom room1 = officeRoomRepository.save(new OfficeRoom("Office room 1"));
		Office office = officeRepository.save(new Office("Kaunas office", "Savanoriu pr. 20", new ArrayList<>()));
		Office office1 = officeRepository.save(new Office("Vilnius office", "smh st. 21", new ArrayList<>()));
		office.addHouseRoom(room1);
		office.addHouseRoom(new OfficeRoom("Office room 2"));
		officeRepository.save(office);

		User user = new User("Testas Testas", "testmail@test.com", UserRole.DEFAULT);
		UserLogin userLogin = new UserLogin("testusername", "testpassword");
		user.setUserLogin(userLogin);
		userLogin.setUser(user);
		userRepository.save(user);

		DateFormat format = new SimpleDateFormat(dateFormat);
		UserOccupation occupation = new UserOccupation(format.parse("2019-01-01"), format.parse("2019-01-30"));
		occupation.setUser(user);
		userOccupationRepository.save(occupation);
		UserOccupation occupation1 = new UserOccupation(format.parse("2019-01-11"), format.parse("2019-01-16"));
		occupation1.setUser(user);
		userOccupationRepository.save(occupation1);

		Set<Trip> trips = new HashSet<>();
		trips.add(trip);
		groupTripRepository.save(new GroupTrip("test trip", "the best trip", trips, office, office,
				Arrays.asList(new Comment(user, "test", "test", null)), "2018-09-20", "2018-09-22", TripStatus.PENDING, user));

		OfficeRoomOccupation officeRoomOccupation = new OfficeRoomOccupation(format.parse("2019-01-02"), format.parse("2019-01-05"), user, room1);
		officeRoomOccupationRepository.save(officeRoomOccupation);
	}
}
