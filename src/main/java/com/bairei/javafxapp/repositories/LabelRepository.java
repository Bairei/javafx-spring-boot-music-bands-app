package com.bairei.javafxapp.repositories;

import com.bairei.javafxapp.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
