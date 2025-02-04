package com.familring.questionservice.repository;

import com.familring.questionservice.domain.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // 내림차순
    Slice<Question> findAllByIdLessThanEqualOrderByIdDesc(Long questionId, Pageable pageable);

    // 오름차순
    Slice<Question> findAllByIdLessThanEqualOrderByIdAsc(Long questionId, Pageable pageable);

}
