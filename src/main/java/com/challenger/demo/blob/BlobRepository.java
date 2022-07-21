package com.challenger.demo.blob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlobRepository extends JpaRepository<StreamingFileRecord, Long> {
}
