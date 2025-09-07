package com.example.smartsearch.service;

import com.example.smartsearch.dto.MovieDto;
import com.example.smartsearch.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final VectorStore vectorStore;

    public void initVectorStore() {
        List<MovieDto> movieDtos = movieRepository.findAllMovie();

        if (movieDtos == null || movieDtos.isEmpty()) return;

        TokenTextSplitter splitter = new TokenTextSplitter();

        for (MovieDto movie : movieDtos) {
            Map<String, Object> metadata = new HashMap<>();
            if (movie.id() != null) metadata.put("id", movie.id());
            if (movie.name() != null) metadata.put("name", movie.name());
            if (movie.mainLeads() != null) metadata.put("mainLeads", movie.mainLeads());
            if (movie.description() != null) metadata.put("description", movie.description());

            String content = String.join(" ",
                    safe(movie.name()),
                    safe(movie.mainLeads()),
                    safe(movie.description())
            );
            Document document = new Document(content, metadata);

            List<Document> docs = splitter.apply(List.of(document));
            vectorStore.add(docs);
        }
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    public List<MovieDto> searchSimilarMovies(String query) {
        List<Document> similarDocuments = vectorStore.similaritySearch(query);

        return similarDocuments.stream()
                .map(doc -> {
                    Map<String, Object> metadata = doc.getMetadata();
                    return new MovieDto(
                            ((Number) metadata.get("id")).longValue(),
                            (String) metadata.get("name"),
                            (String) metadata.get("mainLeads"),
                            (String) metadata.get("description")
                    );
                })
                .collect(Collectors.toList());
    }

    public List<MovieDto> searchSimilarMoviesWithRequest(String query, int topK) {
        List<Document> similarDocuments = vectorStore.similaritySearch(query);

        return similarDocuments.stream()
                .limit(topK)
                .map(doc -> {
                    Map<String, Object> metadata = doc.getMetadata();
                    return new MovieDto(
                            ((Number) metadata.get("id")).longValue(),
                            (String) metadata.get("name"),
                            (String) metadata.get("mainLeads"),
                            (String) metadata.get("description")
                    );
                })
                .collect(Collectors.toList());
    }
}
