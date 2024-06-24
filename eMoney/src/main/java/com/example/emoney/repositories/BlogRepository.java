package com.example.emoney.repositories;

import com.example.emoney.models.Blog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog, ObjectId> {
}
