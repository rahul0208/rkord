package com.notes.repository;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.notes.entity.Notes;

public interface NotesRepository extends ReactiveCosmosRepository<Notes,Integer> {
}
