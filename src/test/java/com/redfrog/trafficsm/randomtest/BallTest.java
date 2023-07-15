package com.redfrog.trafficsm.randomtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.redfrog.trafficsm.test.model.Ball;
import com.redfrog.trafficsm.test.repository.BallRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BallTest {

  @Autowired
  private BallRepository ballRepository;

  @Test
  public void demo_01() {
    System.out.println(">--<");

    Ball ball = new Ball("Alpha", 1984.0);
    //_________________________________________________
    Ball ball_MG = ballRepository.save(ball);
    Long id = ball_MG.getIdSql();
    System.out.println(id);

    Ball ball_FN = ballRepository.findById(id).get();
    System.out.println(ball_FN);
    System.out.println(ball_MG == ball_FN);

  }

}
