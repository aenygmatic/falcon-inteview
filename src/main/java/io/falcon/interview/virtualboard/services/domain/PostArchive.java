package io.falcon.interview.virtualboard.services.domain;

import java.util.List;

public interface PostArchive {

    Post save(Post post);

    Post get(String id);

    List<Post> getAll();
}
