package com.redfrog.trafficsm.mathtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.redfrog.trafficsm.util.MathUtil;

class MathUtilTest {

  @Nested
  class Test_Combination {

    @Test
    public void simpleCase() {
      ArrayList<Integer> arr_in = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

      LinkedList<LinkedList<Integer>> comb = MathUtil.get_Combination(arr_in, 2);

      for (LinkedList<Integer> arr_Group : comb) {
        //______________________________________
      }
      //_______________________________

      Assertions.assertEquals("[[1, 2], [1, 3], [1, 4], [1, 5], [1, 6], [2, 3], [2, 4], [2, 5], [2, 6], [3, 4], [3, 5], [3, 6], [4, 5], [4, 6], [5, 6]]", comb.toString());

    }

  }

}
