package com.antigravity.petunjukarah;

import com.antigravity.petunjukarah.entity.Folder;
import com.antigravity.petunjukarah.entity.Destination;
import com.antigravity.petunjukarah.repository.FolderRepository;
import com.antigravity.petunjukarah.repository.DestinationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetunjukArahApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetunjukArahApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(FolderRepository folderRepository, DestinationRepository destinationRepository,
			com.antigravity.petunjukarah.repository.NoteRepository noteRepository,
			com.antigravity.petunjukarah.repository.TripRepository tripRepository) {
		return args -> {
			if (folderRepository.count() == 0) {
				folderRepository.save(new Folder("Home"));
				folderRepository.save(new Folder("Explore"));
				folderRepository.save(new Folder("Traveling"));
				folderRepository.save(new Folder("Event"));
			}

			if (noteRepository.count() == 0) {
				com.antigravity.petunjukarah.entity.Note n1 = new com.antigravity.petunjukarah.entity.Note();
				n1.setTitle("Rencana Liburan");
				n1.setContent("Membeli tiket dan booking hotel di Bali");
				noteRepository.save(n1);

				com.antigravity.petunjukarah.entity.Note n2 = new com.antigravity.petunjukarah.entity.Note();
				n2.setTitle("Daftar Barang");
				n2.setContent("Bawa jaket tebal untuk ke Bromo");
				noteRepository.save(n2);
			}

			if (tripRepository.count() == 0) {
				tripRepository.save(new com.antigravity.petunjukarah.entity.Trip("Eksplorasi Bali",
						java.time.LocalDate.parse("2025-01-15"), 5, "Bali"));
				tripRepository.save(new com.antigravity.petunjukarah.entity.Trip("Wisata Magelang",
						java.time.LocalDate.parse("2025-02-10"), 2, "Borobudur"));
			}

			seedDestinations(destinationRepository);
		};
	}

	private void seedDestinations(DestinationRepository repo) {
		saveIfNew(repo, createDest("Candi Borobudur", "Wisata", "Magelang", 4.9,
				"https://images.unsplash.com/photo-1544644181-1484b3fdfc62?q=80&w=800",
				"Candi Buddha terbesar di dunia dan Situs Warisan Dunia UNESCO.", "Rp 50.000", -7.6078, 110.2037));
		saveIfNew(repo,
				createDest("Pantai Parangtritis", "Wisata", "Bantul", 4.7,
						"https://images.unsplash.com/photo-1537996194471-e657df975ab4?q=80&w=800",
						"Pantai ikonik di Yogyakarta dengan pemandangan matahari terbenam yang memukau.", "Rp 10.000",
						-8.0245, 110.3323));
		saveIfNew(repo,
				createDest("Malioboro Hotel", "Hotel", "Yogyakarta", 4.5,
						"https://images.unsplash.com/photo-1540541338287-41700207dee6?q=80&w=800",
						"Hotel mewah di pusat kota Yogyakarta, tepat di jantung Malioboro.", "Rp 850.000/malam",
						-7.7926, 110.3658));
		saveIfNew(repo,
				createDest("Bakpia Pathok 25", "Kuliner", "Yogyakarta", 4.8,
						"https://plus.unsplash.com/premium_photo-1673830185613-286866632490?q=80&w=800",
						"Pusat oleh-oleh bakpia legendaris dengan berbagai varian rasa.", "Mulai Rp 35.000", -7.7975,
						110.3585));
		saveIfNew(repo, createDest("Pantai Kuta", "Wisata", "Bali", 4.6,
				"https://images.unsplash.com/photo-1539367628448-4bc5c9d171c8?q=80&w=800",
				"Pantai paling terkenal di Bali bagi peselancar dan pecinta pantai.", "Gratis", -8.7176, 115.1691));
		saveIfNew(repo,
				createDest("Gunung Bromo", "Wisata", "Probolinggo", 4.9,
						"https://images.unsplash.com/photo-1589308078059-be1415e6b219?q=80&w=800",
						"Gunung berapi aktif dengan kawah yang spektakuler dan pasir yang luas.", "Rp 27.500", -7.9425,
						112.9531));
		saveIfNew(repo,
				createDest("The Gaia Hotel", "Hotel", "Bandung", 4.8,
						"https://images.unsplash.com/photo-1566073771259-6a8506099945?q=80&w=800",
						"Hotel kontemporer dengan desain arsitektur yang unik dan pemandangan lembah.",
						"Rp 1.500.000/malam", -6.8335, 107.5947));
		saveIfNew(repo,
				createDest("Tanah Lot", "Wisata", "Bali", 4.8,
						"https://images.unsplash.com/photo-1537996194471-e657df975ab4?q=80&w=800",
						"Pura di atas batu karang besar yang menjorok ke laut.", "Rp 60.000", -8.6212, 115.0868));
		saveIfNew(repo,
				createDest("Raja Ampat", "Wisata", "Papua", 5.0,
						"https://images.unsplash.com/photo-1516641396056-0ce60a85d442?q=80&w=800",
						"Surga bawah laut dengan keanekaragaman hayati tertinggi di dunia.", "Rp 500.000 (PIN)",
						-0.4444, 130.6853));
		saveIfNew(repo,
				createDest("Monas", "Wisata", "Jakarta", 4.5,
						"https://images.unsplash.com/photo-1555899434-94d1368aa7af?q=80&w=800",
						"Monumen Nasional ikon kebanggaan Indonesia di Jakarta.", "Rp 15.000", -6.1754, 106.8272));
		saveIfNew(repo,
				createDest("Sate Khas Senayan", "Kuliner", "Jakarta", 4.6,
						"https://images.unsplash.com/photo-1529692236671-f1f6e9481bfa?q=80&w=800",
						"Restoran legendaris dengan menu sate khas Indonesia yang otentik.", "Rp 75.000/porsi", -6.2241,
						106.8016));
		saveIfNew(repo, createDest("Hotel Indonesia Kempinski", "Hotel", "Jakarta", 4.9,
				"https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?q=80&w=800",
				"Hotel bersejarah yang mewah di Bundaran HI Jakarta.", "Rp 2.800.000/malam", -6.1951, 106.8231));
		saveIfNew(repo, createDest("Farmhouse Lembang", "Wisata", "Bandung", 4.4,
				"https://images.unsplash.com/photo-1506905925346-21bda4d32df4?q=80&w=800",
				"Taman hiburan bernuansa Eropa dengan berbagai spot foto menarik.", "Rp 30.000", -6.8327, 107.6049));
		saveIfNew(repo, createDest("Gudeg Yu Djum", "Kuliner", "Yogyakarta", 4.7,
				"https://images.unsplash.com/photo-1562601579-599dec554e85?q=80&w=800",
				"Gudeg legendaris khas Yogyakarta yang wajib dikunjungi.", "Rp 40.000/paket", -7.7821, 110.3842));
		saveIfNew(repo, createDest("Padma Resort", "Hotel", "Ubud", 4.9,
				"https://images.unsplash.com/photo-1537953391637-96c2ec9bd5d4?q=80&w=800",
				"Resort mewah tersembunyi di tengah hutan tropis Ubud.", "Rp 3.500.000/malam", -8.3637, 115.2778));
		saveIfNew(repo,
				createDest("Warung Babi Guling Ibu Oka", "Kuliner", "Ubud", 4.5,
						"https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?q=80&w=800",
						"Kuliner babi guling paling terkenal di jantung Ubud.", "Rp 50.000/porsi", -8.5069, 115.2625));
		saveIfNew(repo,
				createDest("Museum Angkut", "Wisata", "Batu", 4.7,
						"https://images.unsplash.com/photo-1518709268805-4e9042af9f23?q=80&w=800",
						"Museum transportasi terbesar dan tercanggih di Indonesia.", "Rp 100.000", -7.8791, 112.5255));
		saveIfNew(repo, createDest("Kopi Klotok", "Kuliner", "Yogyakarta", 4.8,
				"https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?q=80&w=800",
				"Warung kopi tradisional dengan nuansa pedesaan yang asri.", "Rp 5.000/kopi", -7.6694, 110.4194));
		saveIfNew(repo,
				createDest("Trans Studio", "Wisata", "Makassar", 4.3,
						"https://images.unsplash.com/photo-1513885535751-8b9238bd345a?q=80&w=800",
						"Taman hiburan indoor terbesar di Indonesia Timur.", "Rp 150.000", -5.1583, 119.3905));
		saveIfNew(repo,
				createDest("Ayam Betutu Khas Gilimanuk", "Kuliner", "Bali", 4.6,
						"https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?q=80&w=800",
						"Spesialis ayam betutu pedas dan gurih khas Bali.", "Rp 65.000/ekor", -8.7467, 115.1833));
		saveIfNew(repo,
				createDest("ATM BNI", "Fasilitas", "Yogyakarta", 4.0,
						"https://images.unsplash.com/photo-1563013544-824ae1b704d3?q=80&w=800",
						"Anjungan Tunai Mandiri untuk berbagai transaksi perbankan.", "-", -7.7956, 110.3695));
		saveIfNew(repo,
				createDest("SPBU Pertamina", "Fasilitas", "Sleman", 4.2,
						"https://images.unsplash.com/photo-1545147986-a9bd0f52bf01?q=80&w=800",
						"Stasiun Pengisian Bahan Umum untuk kebutuhan kendaraan Anda.", "-", -7.7123, 110.3541));
	}

	private Destination createDest(String name, String cat, String loc, Double rat, String img, String desc,
			String price, Double lat, Double lon) {
		Destination d = new Destination(name, cat, loc, rat, img);
		d.setDescription(desc);
		d.setPrice(price);
		d.setLat(lat);
		d.setLon(lon);
		return d;
	}

	private void saveIfNew(DestinationRepository repo, Destination dest) {
		if (repo.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrLocationContainingIgnoreCase(
				dest.getName(), "NONE", "NONE").isEmpty()) {
			repo.save(dest);
		}
	}
}
