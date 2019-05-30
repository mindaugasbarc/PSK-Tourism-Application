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
import java.util.*;

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

		User user = new User("Admin", "admin@example.com", UserRole.DEFAULT);
		// admin : password
		UserLogin userLogin = new UserLogin("admin", "5f4dcc3b5aa765d61d8327deb882cf99");
		user.setUserLogin(userLogin);
		userLogin.setUser(user);
		userRepository.save(user);

		List<OfficeRoom> office1rooms = new ArrayList<>();
		office1rooms.add(new OfficeRoom("Apartment room 1-1"));
		office1rooms.add(new OfficeRoom("Apartment room 1-2"));
		Office office1 = new Office("Vilnius Office", "Didlaukio g. 69, Vilnius", office1rooms);
		officeRepository.save(office1);

		List<OfficeRoom> office2rooms = new ArrayList<>();
		office2rooms.add(new OfficeRoom("Fancy apartment room"));
		Office office2 = new Office("Paris Office", "Champ de Mars, 5 Avenue Anatole, Paris", office2rooms);
		officeRepository.save(office2);

		List<OfficeRoom> office3rooms = new ArrayList<>();
		office3rooms.add(new OfficeRoom("Office apartment room 1-1"));
		office3rooms.add(new OfficeRoom("Office apartment room 1-2"));
		office3rooms.add(new OfficeRoom("Fancy apartment room 2-1"));
		Office office3 = new Office("Copenhagen Office", "Charlotte Ammundsens Pl. 3, København", office3rooms);
		officeRepository.save(office3);

		Office office4 = new Office("Kaunas Office", "Karaliaus Mindaugo pr. 50, Kaunas", new ArrayList<>());
		officeRepository.save(office4);
	}
}
