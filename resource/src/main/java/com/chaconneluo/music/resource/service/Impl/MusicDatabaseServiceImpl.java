package com.chaconneluo.music.resource.service.Impl;

import com.chaconneluo.music.resource.service.MusicDatabaseService;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class MusicDatabaseServiceImpl implements MusicDatabaseService {

    private final MongoClient mongoClient;

}
