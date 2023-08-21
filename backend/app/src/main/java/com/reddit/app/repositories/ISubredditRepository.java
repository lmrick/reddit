package com.reddit.app.repositories;

import com.reddit.app.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubredditRepository extends JpaRepository<Subreddit, Long> {

}
