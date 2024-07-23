package com.vss.wardrober.repositories;

import com.vss.wardrober.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
