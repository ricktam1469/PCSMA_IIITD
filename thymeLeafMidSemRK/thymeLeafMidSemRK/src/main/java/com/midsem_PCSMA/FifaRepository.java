package com.midsem_PCSMA;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface FifaRepository extends MongoRepository<FifaUpdate, String> {
	

		public FifaUpdate findBydate(String date);
		
		Page<FifaUpdate> findTop2Bydate(String date, Pageable pageable);
}
