package com.ms.email.models.repositorys;

import com.ms.email.models.entitys.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
}
