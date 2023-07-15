package com.redfrog.trafficsm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import com.redfrog.trafficsm.annotation.Main;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.shape.Dimension;
import com.redfrog.trafficsm.shape.Point;

public class MathUtil {

  /**
______________________________________________________________________________________
________________________________________________________________
_________________________________________________
__*/
  //_________________________________________________________________________________________________________________________________________________________________________
  public static Triple<Double, Double, Point> get_DiagonalDisplacement(final Point point_Begin, final Point point_End, final double diagonalInc) {
    if (diagonalInc == 0 || Double.isNaN(diagonalInc)) { throw new Error(); }

    final double posX_Begin = point_Begin.getX();
    final double posY_Begin = point_Begin.getY();
    final double posX_End = point_End.getX();
    final double posY_End = point_End.getY();

    double lengthX = Math.abs(posX_Begin - posX_End);
    double lengthY = Math.abs(posY_Begin - posY_End);

    if (lengthX == 0 && lengthY != 0) {
      if (posY_Begin < posY_End) {
        return new ImmutableTriple<>(0.0, diagonalInc, new Point(posX_Begin + 0.0, posY_Begin + diagonalInc));
      }
      else {
        return new ImmutableTriple<>(0.0, -diagonalInc, new Point(posX_Begin + 0.0, posY_Begin + -diagonalInc));
      }
    }
    else if (lengthX != 0 && lengthY == 0) { //______________________________
      if (posX_Begin < posX_End) {
        return new ImmutableTriple<>(diagonalInc, 0.0, new Point(posX_Begin + diagonalInc, posY_Begin + 0.0));
      }
      else {
        return new ImmutableTriple<>(-diagonalInc, 0.0, new Point(posX_Begin + -diagonalInc, posY_Begin + 0.0));
      }
    }
    else if (lengthX == 0 && lengthY == 0) {
      return new ImmutableTriple<>(0.0, 0.0, new Point(posX_Begin + 0.0, posY_Begin + 0.0));
    }
    else {
      double angle = Math.atan(lengthX / lengthY);
      double posXInc_abs = Math.sin(angle) * diagonalInc;
      double posYInc_abs = Math.cos(angle) * diagonalInc;

      if (Double.isNaN(posXInc_abs)) { throw new Error("not_Reachable posXInc_abs is NaN"); }
      if (Double.isNaN(posYInc_abs)) { throw new Error("not_Reachable posYInc_abs is NaN"); }

      final double posXInc;
      if (posX_Begin < posX_End) {
        posXInc = posXInc_abs;
      }
      else {
        posXInc = -posXInc_abs;
      }
      final double posYInc;
      if (posY_Begin < posY_End) {
        posYInc = posYInc_abs;
      }
      else {
        posYInc = -posYInc_abs;
      }

      return new ImmutableTriple<>(posXInc, posYInc, new Point(posX_Begin + posXInc, posY_Begin + posYInc));
    }
  }

  //_____________

  public static Double calc_LengthOfArrPath(List<Point> arrPath) {
    if (arrPath.isEmpty() || arrPath == null) {
      throw new Error();
    }
    else if (arrPath.size() == 1) {
      return 0.0;
    }
    else {
      double length = 0;
      Point point_prev = null;
      int i = -1;
      for (Point point_curr : arrPath) {
        i++;
        if (i == 0) {
          //_____________
        }
        else {
          length += point_prev.distance(point_curr);
        }
        point_prev = point_curr;
      }
      return length;
    }
  }

  //_____________

  @SuppressWarnings("rawtypes")
  private static BiPredicate NullBiPredicate = (t, u) -> true;
  @SuppressWarnings("rawtypes")
  private static Predicate NullPredicate = (t) -> true;

  public static <T> LinkedList<LinkedList<T>> get_Permuataion(Collection<T> array_in) {
    LinkedList<LinkedList<T>> perm = new LinkedList<>();
    LinkedList<T> arr_Rest_next = new LinkedList<T>(array_in);
    //___________________________________________
    //_____________________________________________________________________
    //_________________________________________
    //_______________________________________________________________________
    //_____
    get_Permuataion_recursive(null, arr_Rest_next, perm, false, NullBiPredicate, NullBiPredicate);
    return perm;
  }

  public static <T> LinkedList<LinkedList<T>> get_Permuataion_Scattered(Collection<T> array_in) {
    LinkedList<LinkedList<T>> perm = new LinkedList<>();
    LinkedList<T> arr_Rest_next = new LinkedList<T>(array_in);
    get_Permuataion_recursive(null, arr_Rest_next, perm, true, NullBiPredicate, NullBiPredicate);
    return perm;
  }

  public static <T> LinkedList<LinkedList<T>> get_Permuataion_Scattered(Collection<T> array_in, BiPredicate<LinkedList<T>, T> pred_AbandonCurrPath, BiPredicate<LinkedList<T>, T> pred_AbandonAfterCurrPath) {
    LinkedList<LinkedList<T>> perm = new LinkedList<>();
    LinkedList<T> arr_Rest_next = new LinkedList<T>(array_in);
    get_Permuataion_recursive(null, arr_Rest_next, perm, true, pred_AbandonCurrPath, pred_AbandonAfterCurrPath);
    return perm;
  }

  //______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //________________________________
  //_________________________________
  //_____
  //______________________________________________________________________
  //____________________________________________________________________________________________________________________________________________________
  //_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________

  //___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  private static <T> void get_Permuataion_recursive(LinkedList<T> arr_BuildOn_prev, LinkedList<T> arr_Rest_curr, LinkedList<LinkedList<T>> perm, final boolean mode_Scattered,
                                                    final BiPredicate<LinkedList<T>, T> pred_AbandonCurrPath, final BiPredicate<LinkedList<T>, T> pred_AbandonAfterCurrPath) {

    int i = -1;
    boolean det_LastItem = arr_Rest_curr.size() == 1;
    for (T obj_curr : arr_Rest_curr) {
      i++;
      //
      if (pred_AbandonCurrPath != NullBiPredicate && pred_AbandonCurrPath.test(arr_BuildOn_prev, obj_curr)) { continue; }

      //
      LinkedList<T> arr_BuildOn_curr;
      if (arr_BuildOn_prev == null) {
        arr_BuildOn_curr = new LinkedList<T>();
      }
      else {
        arr_BuildOn_curr = new LinkedList<T>(arr_BuildOn_prev);
      }
      arr_BuildOn_curr.add(obj_curr);
      if (mode_Scattered) {
        perm.add(arr_BuildOn_curr);
      }
      else if (det_LastItem) {
        perm.add(arr_BuildOn_curr); //__
      }

      //
      if (!det_LastItem) {
        //
        if (pred_AbandonAfterCurrPath != NullBiPredicate && pred_AbandonAfterCurrPath.test(arr_BuildOn_prev, obj_curr)) { continue; }

        LinkedList<T> arr_Rest_next = new LinkedList<T>(arr_Rest_curr);
        arr_Rest_next.remove(i);

        get_Permuataion_recursive(arr_BuildOn_curr, arr_Rest_next, perm, mode_Scattered, pred_AbandonCurrPath, pred_AbandonAfterCurrPath);
      }
    }
  }

  //_____________

  public static <T> LinkedList<LinkedList<T>> get_Combination(Collection<T> array_in, int amountPerGroup) {
    if (amountPerGroup != 2) { throw new Error("Not support"); }
    LinkedList<LinkedList<T>> comb = new LinkedList<>();
    LinkedList<T> arr_Rest_next = new LinkedList<T>(array_in);
    LinkedList<T> arr_Rest_next_less = new LinkedList<T>(array_in);
    for (T t1_curr : arr_Rest_next) {
      arr_Rest_next_less.removeFirst();
      for (T t2_curr : arr_Rest_next_less) {
        LinkedList<T> arr_Group = new LinkedList<T>();
        arr_Group.add(t1_curr);
        arr_Group.add(t2_curr);
        comb.add(arr_Group);
      }
    }

    return comb;
  }

  //_____________

  public static double round(double value, int scale) {
    return round(value, scale, RoundingMode.HALF_DOWN); //_
  }

  public static double round(double value, int scale, RoundingMode roundingMode) { //__________________________________
    //________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    return new BigDecimal(value).setScale(scale, roundingMode).doubleValue(); //_
  }

  //_____________

  public static Point findPointMOnLineClosestToGivenPointP(Point vector_A, Point vector_B, Point vector_P) {
    return findPointMOnLineClosestToGivenPointP(vector_A, vector_B, vector_P, false); //
  }

  public static Point findPointMOnLineClosestToGivenPointP(Point vector_A, Point vector_B, Point vector_P, boolean mode_InfLengthLine) {
    Dimension vector_A_getDimension = vector_A.getDimension();
    Dimension vector_B_getDimension = vector_B.getDimension();
    Dimension vector_P_getDimension = vector_P.getDimension();
    if (!(vector_A_getDimension == vector_B_getDimension && vector_B_getDimension == vector_P_getDimension)) { throw new Error(); }
    if (vector_A_getDimension == Dimension.D1 || vector_A_getDimension == Dimension.D3) { throw new Error(); }

    Point vector_AB = vector_B.subtract(vector_A);
    Point vector_AP = vector_P.subtract(vector_A);
    double distance_AM = vector_AB.dotProduct(vector_AP) / vector_AB.magnitude();
    Point vector_AM = vector_AB.normalize().multiply(distance_AM);
    Point vector_M = vector_A.add(vector_AM);

    if (vector_A_getDimension == Dimension.D2) { vector_M = new Point(vector_M.getX(), vector_M.getY()); }

    if (mode_InfLengthLine) {
      return vector_M;
    }
    else {
      double posX_AA = vector_A.getX();
      double posY_AA = vector_A.getY();
      double posX_BB = vector_B.getX();
      double posY_BB = vector_B.getY();

      double posX_MM = vector_M.getX();
      double posY_MM = vector_M.getY();

      if ((((posX_AA <= posX_MM && posX_MM <= posX_BB) || (posX_BB <= posX_MM && posX_MM <= posX_AA))
           && ((posY_AA <= posY_MM && posY_MM <= posY_BB) || (posY_BB <= posY_MM && posY_MM <= posY_AA)))) {
        return vector_M;
      }
      else {
        return null;
      }
    }

  }

  //____________________________________________________________________________
  //_____________________________________________________________________________________________________________________________________________________________________________________________
  //___________________________________
  //_______
  //
  //____________________________________________________________________________________________________________
  //________________________________________________________________________________
  //_____________________________________________________
  //___________________________________________________
  //___________________________________________________________
  //_____________
  //_____________
  //___

  //___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //
  //_________________________________________________________________________________________
  //_______________________________________________________________________________________________________________
  //____
  //__________________________________________
  //__________________________________________
  //____________________________________________________
  //____
  //__________________________________________
  //__________________________________________
  //____________________________________________________
  //____
  //_________________________________________
  //_______________________________________________________________________________________
  //____
  //_______
  //
  //_______________________________________________________________________________________________________________________________________________________________
  //__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
  //_____________________________________________________________________________________________
  //_______
  //____
  //_____________________________________________________________________________
  //_________________________________________________________________________________________________________________________________________________________________
  //_____________________________________________________________________
  //__________________________________________________
  //______________________
  //_________
  //____________________________________________________________________________
  //____________________________________________________________________________
  //_______________________________________________________________
  //________________________________________
  //_____________________________________________________________________
  //_________
  //____________________
  //_______

  public static boolean compareWithinEpsilon(double AA, double BB) { return compareWithinEpsilon(AA, BB, 1E-8); }

  public static boolean compareWithinEpsilon(double AA, double BB, double epsilon) { return Math.abs(AA - BB) <= epsilon; }

  public static Point findPointAtTwoLineIntersection(Point point_AA, Point point_BB, Point point_CC, Point point_DD) {
    return findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, false); //
  }

  public static Point findPointAtTwoLineIntersection(Point point_AA, Point point_BB, Point point_CC, Point point_DD, boolean mode_InfLengthLine) {
    return findPointAtTwoLineIntersection(point_AA, point_BB, point_CC, point_DD, mode_InfLengthLine, 6); //
  }

  /**
________________________________________________
__
_______
__
_________________________________________________________________________________________________________
__
_______________________________________________________
__________________________________________________________________________________________________________________________________________________________________________
___
_______________________________________________________________
__
____________________________________________
__
_________________________________________________________________________________________
__
_________
__________________________________________________________
_________________________________________________________________________________________________________________________________________________________________
___________________________________________________________________________________
__
___________________________________________________________
__*/
  public static Point findPointAtTwoLineIntersection(Point point_AA, Point point_BB, Point point_CC, Point point_DD, boolean mode_InfLengthLine, int roundingScale) {
    if (point_AA == null || point_BB == null || point_CC == null || point_DD == null) { throw new Error("Null Point passed in."); }
    if (point_AA.equals(point_BB)) { return null; }
    if (point_CC.equals(point_DD)) { return null; }
    //_______________________________________________________________________________________________________________

    //_____

    final double posX_AA = point_AA.getX();
    final double posYn_AA = -point_AA.getY(); //__________

    final double posX_BB = point_BB.getX();
    final double posYn_BB = -point_BB.getY(); //__________

    final double displacementX_AB = posX_BB - posX_AA;
    final double displacementY_AB = posYn_BB - posYn_AA;

    Double m_AB = null;
    Double b_AB = null;

    if (displacementX_AB == 0 && displacementY_AB == 0) { //________
      throw new Error("Not_Reachable");
    }
    else if (displacementX_AB == 0 && displacementY_AB != 0) { //______________
      //________________________________________________
    }
    //_________________________________________________________________________________
    //_______________
    //______________________
    //_______________________________________
    else {
      m_AB = displacementY_AB / displacementX_AB;//__________
      b_AB = posYn_AA - posX_AA * m_AB;
    }

    //_____

    final double posX_CC = point_CC.getX();
    final double posYn_CC = -point_CC.getY(); //__________

    final double posX_DD = point_DD.getX();
    final double posYn_DD = -point_DD.getY(); //__________

    final double displacementX_CD = posX_DD - posX_CC;
    final double displacementY_CD = posYn_DD - posYn_CC;

    Double m_CD = null;
    Double b_CD = null;

    if (displacementX_CD == 0 && displacementY_CD == 0) { //________
      throw new Error("Not_Reachable");
    }
    else if (displacementX_CD == 0 && displacementY_CD != 0) { //______________
      //_______________________________________________
    }
    //_________________________________________________________________________________
    else {
      m_CD = displacementY_CD / displacementX_CD; //__________
      b_CD = posYn_CC - posX_CC * m_CD;
    }

    //_____

    Double posX_MM = null;
    Double posYn_MM = null; //__________

    if (m_AB == null && m_CD == null) { //______________
      return null;
    }
    else if (m_AB == null && m_CD != null) {
      posX_MM  = posX_AA; //________________________________________
      posYn_MM = m_CD * posX_MM + b_CD;
    }
    else if (m_AB != null && m_CD == null) {
      posX_MM  = posX_CC; //________________________________________
      posYn_MM = m_AB * posX_MM + b_AB;
    }
    else {
      @Messy byte messy; //_________________________________________________________________
      if (compareWithinEpsilon(m_AB, m_CD)) { //______________________________________________________________________________________
        return null;
      }
      else {
        @Main byte main;
        //______________________________________________
        posX_MM  = (b_CD - b_AB) / (m_AB - m_CD);
        posYn_MM = posX_MM * m_AB + b_AB;
      }
    }

    //_____

    if (posX_MM == null || posYn_MM == null) { throw new Error("Not_Reachable"); }

    @Messy Point point_MM = new Point(round(posX_MM, roundingScale), round(-posYn_MM, roundingScale)); //_____________________________________________________________________________________________________________

    if (mode_InfLengthLine) {
      return point_MM;
    }
    else {
      //___________________________________________________________________________
      if ((((posX_AA <= posX_MM && posX_MM <= posX_BB) || (posX_BB <= posX_MM && posX_MM <= posX_AA))
           && ((posYn_AA <= posYn_MM && posYn_MM <= posYn_BB) || (posYn_BB <= posYn_MM && posYn_MM <= posYn_AA)))
          &&
          (((posX_CC <= posX_MM && posX_MM <= posX_DD) || (posX_DD <= posX_MM && posX_MM <= posX_CC))
           && ((posYn_CC <= posYn_MM && posYn_MM <= posYn_DD) || (posYn_DD <= posYn_MM && posYn_MM <= posYn_CC)))) {
        return point_MM;
      }
      else {
        //____________________________________________________________
        return null;
      }
    }

  }

  //_____________

  /**
________________________
________________________
__*/

  public static Pair<Double, Double> calculateSlopeAndIntersect(Point point_AA, Point point_BB) {
    if (point_AA == null || point_BB == null) { throw new Error("Null Point passed in."); }
    if (point_AA.equals(point_BB)) { return null; }

    //_____
    final double posX_AA = point_AA.getX();
    final double posYn_AA = -point_AA.getY(); //__________

    final double posX_BB = point_BB.getX();
    final double posYn_BB = -point_BB.getY(); //__________

    final double displacementX_AB = posX_BB - posX_AA;
    final double displacementY_AB = posYn_BB - posYn_AA;

    Double m_AB_yn = null;
    Double b_AB_yn = null;

    if (displacementX_AB == 0 && displacementY_AB == 0) { //________
      throw new Error("Not_Reachable");
    }
    else if (displacementX_AB == 0 && displacementY_AB != 0) { //______________
      //________________________________________________
    }
    //_________________________________________________________________________________
    //_______________
    //______________________
    //_______________________________________
    else {
      m_AB_yn = displacementY_AB / displacementX_AB;//__________
      b_AB_yn = posYn_AA - posX_AA * m_AB_yn;
    }

    return new ImmutablePair<Double, Double>(m_AB_yn, b_AB_yn);
  }

  //_____________

  //___________________________________________________________________________________________________
  public static String printTable(List<Object[]> table) {
    //__________________________________________________________
    int maxColumns = 0;
    for (int i = 0; i < table.size(); i++) {
      maxColumns = Math.max(table.get(i).length, maxColumns);
    }

    //___________________________________________________
    int[] lengths = new int[maxColumns];
    for (int i = 0; i < table.size(); i++) {
      for (int j = 0; j < table.get(i).length; j++) {
        Object table_get_i__j_ = table.get(i)[j];
        if (table_get_i__j_ == null) {
          lengths[j] = Math.max(4, lengths[j]);
        }
        else {
          lengths[j] = Math.max(table_get_i__j_.toString().length(), lengths[j]);
        }
      }
    }

    //_________________________________________
    String[] formats = new String[lengths.length];
    for (int i = 0; i < lengths.length; i++) {
      formats[i] = "%1$" + lengths[i] + "s" + (i + 1 == lengths.length ? "\n" : " | ");
    }

    String result = "";
    //______________
    for (int i = 0; i < table.size(); i++) {
      for (int j = 0; j < table.get(i).length; j++) {
        result += String.format(formats[j], table.get(i)[j]);
      }
    }
    return result;
  }

  //________________________________________________________________________________
  public static Object[][] transpose(Object[][] array) {
    //_________________________________________
    if (array == null || array.length == 0) { throw new Error(); }

    int width = array.length;
    int height = array[0].length;

    Object[][] array_new = new Object[height][width];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        array_new[y][x] = array[x][y];
      }
    }
    return array_new;
  }

  public static List<List<Long>> transposeAndConvertToLong(Object[][] array) {
    //_________________________________________
    if (array == null || array.length == 0) { throw new Error(); }

    int max_row = array.length;
    int max_col = array[0].length;

    List<List<Long>> arrayTranspose = new ArrayList<>();

    //____________________________________
    for (int col = 0; col < max_col; col++) {
      List<Long> arr_Long = new ArrayList<>();
      for (int row = 0; row < max_row; row++) {
        //___________________________________
        arr_Long.add(Long.parseLong(array[row][col].toString()));
      }
      arrayTranspose.add(arr_Long);
    }
    return arrayTranspose;
  }

  //_____

  public static double cal_Length(List<Point> arr_point) {
    int size = arr_point.size();
    if (size < 2) { throw new Error(); }

    double sum_length = 0;
    Point point_prev = null;
    for (Point point_curr : arr_point) {
      if (point_prev == null) {}
      else {
        sum_length += point_prev.distance(point_curr);
      }
      point_prev = point_curr;
    }
    return sum_length;
  }

}
