package com.reddit.app.repositories;

import com.reddit.app.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVoteRepository extends JpaRepository<Vote, Long> {

}
