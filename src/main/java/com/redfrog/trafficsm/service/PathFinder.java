package com.redfrog.trafficsm.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redfrog.trafficsm.annotation.Debug;
import com.redfrog.trafficsm.annotation.Main;
import com.redfrog.trafficsm.annotation.MainImp;
import com.redfrog.trafficsm.annotation.Messy;
import com.redfrog.trafficsm.annotation.NotWorking;
import com.redfrog.trafficsm.annotation.PerformancePotential;
import com.redfrog.trafficsm.annotation.ToUseEntry;
import com.redfrog.trafficsm.annotation.Todo;
import com.redfrog.trafficsm.annotation.UseWithoutSpring;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayDirLinkerComponent;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwayPoint;
import com.redfrog.trafficsm.model.roadway.fundamental.RoadwaySegment;
import com.redfrog.trafficsm.model.roadway.fundamental.pseudo.RoadwaySegmentPseudoBegin;
import com.redfrog.trafficsm.model.roadway.main.RoadwayGenericLocationPointOnRoadway;
import com.redfrog.trafficsm.model.roadway.main.RoadwayGenericLocationSegment;
import com.redfrog.trafficsm.model.roadway.main.RoadwayNormalSegment;
import com.redfrog.trafficsm.model.roadway.main.RoadwaySolidRoad;
import com.redfrog.trafficsm.service.exception.PointIsNotNearAnyRoadwayException;
import com.redfrog.trafficsm.session.WindowSession;
import com.redfrog.trafficsm.shape.Point;
import com.redfrog.trafficsm.util.MathUtil;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PathFinder {

  //___________________________________________________________

  //___________________________________________________________________________________________________

  @UseWithoutSpring
  private final WindowSession windowSession_corr;

  private final MapBuilder mapBuilder_corr;

  @UseWithoutSpring
  public PathFinder(WindowSession windowSession_corr) {
    this.windowSession_corr = windowSession_corr;
    this.mapBuilder_corr    = windowSession_corr.mapBuilder;
  }

  //___

  @Autowired
  public PathFinder(MapBuilder mapBuilder) {
    this.windowSession_corr = null;
    this.mapBuilder_corr    = mapBuilder;

  }

  //_____________

  //_____________

  @PerformancePotential
  //_____________________________

  public Pair<RoadwayNormalSegment, Point> locatePositionInRoadway(double x, double y) throws PointIsNotNearAnyRoadwayException {
    final Point point_P = new Point(x, y);
    return locatePositionInRoadway(point_P);
  }

  @Main
  public Pair<RoadwayNormalSegment, Point> locatePositionInRoadway(@NonNull final Point point_P) throws PointIsNotNearAnyRoadwayException {
    //__________________________________________________________________________
    //____________________
    //_________________________________________________________________
    //_______________________________________
    //________________

    //______________________________________

    HashMap<RoadwayNormalSegment, Point> mpp_roadwaySegment_WithinMarginOfError = new HashMap<>();
    ImmutablePair<RoadwayNormalSegment, Point> result_ClosestOne = null;
    Double distance_Shortest = null;
    for (RoadwaySolidRoad roadway_curr : mapBuilder_corr.getMapFile().getGpRoadwaySolidRoad()) {
      List<RoadwayDirLinkerComponent> arr_roadwayDirLinkerComponent_curr = roadway_curr.getArrRoadwayDirLinkerComponent();
      int i = 0;
      for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_curr : arr_roadwayDirLinkerComponent_curr) {
        i++;
        RoadwaySegment roadwaySegment_curr_cast = roadwayDirLinkerComponent_curr.getRoadwaySegment();
        if (i == 1) {
          //____________
          if (!(roadwaySegment_curr_cast instanceof RoadwaySegmentPseudoBegin)) { throw new Error("Should be RoadwaySegmentPseudoBegin"); }
          continue;
        }
        RoadwayNormalSegment roadwaySegment_curr = (RoadwayNormalSegment) roadwaySegment_curr_cast;
        Point point_M = MathUtil.findPointMOnLineClosestToGivenPointP(roadwaySegment_curr.getRoadwayPointSp().getPointLocation(), roadwaySegment_curr.getRoadwayPointNp().getPointLocation(), point_P);
        if (point_M != null) {
          double distance = point_M.distance(point_P);
          if (distance <= MapBuilder.marginOfError_UserFuzzyLocate) {
            mpp_roadwaySegment_WithinMarginOfError.put(roadwaySegment_curr, point_M); //_
            if (distance_Shortest == null || distance < distance_Shortest) {
              result_ClosestOne = new ImmutablePair<RoadwayNormalSegment, Point>(roadwaySegment_curr, point_M);
              distance_Shortest = distance;
            }
          }
        }
      }
    }
    if (mpp_roadwaySegment_WithinMarginOfError.size() == 0) {
      throw new PointIsNotNearAnyRoadwayException("Not found, maybe your point is not close enough to a Roadway"); //____
    }
    else if (mpp_roadwaySegment_WithinMarginOfError.size() > 1) {
      @Debug byte debug;
      mpp_roadwaySegment_WithinMarginOfError.forEach((t, u) -> {
        log.warn(point_P);
        log.warn(u);
        log.warn(t);
        //__________________________________________________________________________________________________________________________________________________________________________________
      });
      //___________________________________________________
      //___________________________________________________________________________________________________
      return result_ClosestOne;
    }
    else {
      return result_ClosestOne;
    }

  }

  public enum FindPathAlgorithm {
    Permutation, //__________________________
    @Todo
    @Deprecated
    NearestSlope, //_____________________
    @NotWorking
    @Deprecated //________________________
    SecondTimeFindNearAfterFound, //______________________________________________________
    ClosestPoint, //_____________________
  }

  @ToUseEntry
  public Triple<LinkedList<RoadwayDirLinkerComponent>, Pair<RoadwayNormalSegment, Point>, Pair<RoadwayNormalSegment, Point>> findPath(@NonNull Point point_SelfNearPath, @NonNull Point point_TargetNearPath) throws PointIsNotNearAnyRoadwayException {
    return findPath(point_SelfNearPath, point_TargetNearPath, true, FindPathAlgorithm.ClosestPoint); //
  }

  public Triple<LinkedList<RoadwayDirLinkerComponent>, Pair<RoadwayNormalSegment, Point>, Pair<RoadwayNormalSegment, Point>> findPath(@NonNull Point point_SelfNearPath, @NonNull Point point_TargetNearPath, boolean mode_ShortCircuit, FindPathAlgorithm findPathAlgorithm) throws PointIsNotNearAnyRoadwayException {
    Triple<LinkedList<LinkedList<RoadwayDirLinkerComponent>>, Pair<RoadwayNormalSegment, Point>, Pair<RoadwayNormalSegment, Point>> result_findPathAllPossible = findPathAllPossible(point_SelfNearPath, point_TargetNearPath, mode_ShortCircuit, findPathAlgorithm);
    return new ImmutableTriple<>(
                                 filterPathShortest(result_findPathAllPossible.getLeft()),
                                 result_findPathAllPossible.getMiddle(),
                                 result_findPathAllPossible.getRight());
  }

  public Triple<LinkedList<LinkedList<RoadwayDirLinkerComponent>>, Pair<RoadwayNormalSegment, Point>, Pair<RoadwayNormalSegment, Point>> findPathAllPossible(@NonNull Point point_SelfNearPath, @NonNull Point point_TargetNearPath, boolean mode_ShortCircuit, FindPathAlgorithm findPathAlgorithm) throws PointIsNotNearAnyRoadwayException {

    //________________
    Pair<RoadwayNormalSegment, Point> result_Self = locatePositionInRoadway(point_SelfNearPath);
    Pair<RoadwayNormalSegment, Point> result_Target = locatePositionInRoadway(point_TargetNearPath);
    log.debug(result_Self.getRight());
    log.debug(result_Target.getRight());

    RoadwayNormalSegment roadwaySegment_Self = result_Self.getLeft();
    RoadwayNormalSegment roadwaySegment_Target = result_Target.getLeft();

    Point point_SelfOnPath = result_Self.getRight();
    Point point_TargetOnPath = result_Target.getRight();

    return new ImmutableTriple<>(
                                 findPathAllPossibleGivenAccurateSegment(roadwaySegment_Self, point_SelfOnPath, roadwaySegment_Target, point_TargetOnPath, mode_ShortCircuit, findPathAlgorithm),
                                 result_Self,
                                 result_Target);
  }

  @Main
  @MainImp
  public LinkedList<LinkedList<RoadwayDirLinkerComponent>> findPathAllPossibleGivenAccurateSegment(@NonNull RoadwaySegment roadwaySegment_Self, @NonNull Point point_SelfOnPath,
                                                                                                   @NonNull RoadwaySegment roadwaySegment_Target, @NonNull Point point_TargetOnPath, boolean mode_ShortCircuit, FindPathAlgorithm findPathAlgorithm) {
    //_________________________________________________________________
    //________________________________________________________________________________________________________________________________________________________________

    //________________________________________________________________________________________________
    //____________________________________________________
    //___________________________________________________________________________________________________
    //__________________________________________________________________________________________________________
    //__________________________________________________________________________________________________________
    //________________________________________________________________________________________________________________
    //________________________________________________________________________________________________________________

    //________________________________________________________
    //_____________________________________________________________________________________
    //___________________________________________________
    //_________________________________________________
    //
    //___________________________________________________________________________________________________________________________________________________________
    //___________________________________________________________________________________________________________________________________________________________
    //___________________________________________________________________________________________________________________________________________________________
    //___________________________________________________________________________________________________________________________________________________________

    //______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //______________________________
    //
    //_________
    //___________
    //_______
    //_______________________________________________
    //_________
    //____________________________________________________________________________
    //_______
    //____________________________________________________________________________________________________
    //
    //_____________________________________________________________________________________________________
    //
    //_________________________________________________________________________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________________________________________________________________________
    //
    //_______________________________________________________________________________________________________________________________________________________________________________________
    //_______________________________________________________________________________________________________________________________________________________________________________________
    //_______________________________________________________________________________________________________________________________________________________________________________________
    //_______________________________________________________________________________________________________________________________________________________________________________________
    //_
    //________________________________________________________________________
    //____
    //_________________________________________
    //____
    //_______________________________________________________________________________________________________
    //_
    //______________________________________________________________________________________________________
    //__________________________________________________________________________________________________________
    //
    //________________________________________________________________________________________________
    //____________________________________________________
    //___________________________________________________________________________________________________
    //________________________________________________________________________________________________________________________________________
    //________________________________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________________
    //______________________________________________________________________________________________________________________________________________

    //____________________
    //___________________________
    //_____________________________________________________________________________________________________________
    //_______________________________________________________________________________________
    //____________________________________________________
    //____________________________________________________________________________________________________________
    //__________________________________________
    //__________
    //_____________________
    //________________________________

    //_____

    //________________________________________________________________________________________________
    //____________________________________________________________________________________________________
    //________
    //_____________________________________________________________________________
    //_________________________________________________________________________________

    RoadwayPoint point_Self_A = roadwaySegment_Self.getRoadwayPointSp();
    RoadwayPoint point_Self_B = roadwaySegment_Self.getRoadwayPointNp();
    RoadwayPoint point_Target_A = roadwaySegment_Target.getRoadwayPointSp();
    RoadwayPoint point_Target_B = roadwaySegment_Target.getRoadwayPointNp();

    //_____

    //___________________________________________________________
    //_______________________________________________________________

    //______________________________________________________________
    //__________________________________________________________________________

    RoadwayGenericLocationPointOnRoadway genericLocationPoint_Self = new RoadwayGenericLocationPointOnRoadway(point_SelfOnPath);
    RoadwayGenericLocationPointOnRoadway genericLocationPoint_Target = new RoadwayGenericLocationPointOnRoadway(point_TargetOnPath);

    //___________________________________________________________________
    //___________________________________________________________________________________________________________________________________________________________________________________________
    //___________________________________________________________________________________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________________________________________________________________________________________
    //_________________________________________________________________________________________________________________________________________________________________________________________________
    //_________________
    //______________
    RoadwayDirLinkerComponent roadwayDirLinkerComponent_btPStoGS = new RoadwayDirLinkerComponent(RoadwaySegment.roadwaySegmentPseudoBegin, genericLocationPoint_Self);
    RoadwayDirLinkerComponent roadwayDirLinkerComponent_btGStoSA = new RoadwayDirLinkerComponent(new RoadwayGenericLocationSegment(genericLocationPoint_Self, point_Self_A), point_Self_A);
    RoadwayDirLinkerComponent roadwayDirLinkerComponent_btGStoSB = new RoadwayDirLinkerComponent(new RoadwayGenericLocationSegment(genericLocationPoint_Self, point_Self_B), point_Self_B);
    RoadwayDirLinkerComponent roadwayDirLinkerComponent_btTAtoGT = new RoadwayDirLinkerComponent(new RoadwayGenericLocationSegment(point_Target_A, genericLocationPoint_Target), genericLocationPoint_Target);
    RoadwayDirLinkerComponent roadwayDirLinkerComponent_btTBtoGT = new RoadwayDirLinkerComponent(new RoadwayGenericLocationSegment(point_Target_B, genericLocationPoint_Target), genericLocationPoint_Target);

    //_____

    final LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath_SATA = new LinkedList<>();
    final LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath_SATB = new LinkedList<>();
    final LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath_SBTA = new LinkedList<>();
    final LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath_SBTB = new LinkedList<>();

    //__________________________________________________________________________
    if (findPathAlgorithm == FindPathAlgorithm.Permutation) {
      //_____________________________________
      find_Path_permutation_recursive(point_Self_A, point_Target_A, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SATA, mode_ShortCircuit, null);
      find_Path_permutation_recursive(point_Self_A, point_Target_B, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SATB, mode_ShortCircuit, null);
      find_Path_permutation_recursive(point_Self_B, point_Target_A, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SBTA, mode_ShortCircuit, null);
      find_Path_permutation_recursive(point_Self_B, point_Target_B, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SBTB, mode_ShortCircuit, null);
    }
    //___________________________________________________________________
    //______________________________________
    //____________________________________________________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________________________________________
    //____________________________________________________________________________________________________________________________________________________
    //_____
    //___________________________________________________________________________________
    //______________________________________
    //____________________________________________________________________________________________________________________________________________________________
    //______________________________________________________
    //_____________________________________________________________________________________________________________________________________________________
    //___________________________________________________________________________
    //_________________________________________________________________________________________________________
    //______________________________________________________________________________________________
    //_________________________________________________________________________________________________________________________________________
    //___________________
    //_________________________________________________________________________
    //__________________________________________________________________________________________________________________________________________________________________________
    //_______
    //___________________________________________________________
    //_______________________
    //_______
    //____________
    //______________________________________________________________
    //_______
    //____________________________________________________________________
    //_____
    else if (findPathAlgorithm == FindPathAlgorithm.ClosestPoint) {
      find_Path_ClosestPoint_recursive(point_Self_A, point_Target_A, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SATA, 1);
      find_Path_ClosestPoint_recursive(point_Self_A, point_Target_B, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SATB, 1);
      find_Path_ClosestPoint_recursive(point_Self_B, point_Target_A, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SBTA, 1);
      find_Path_ClosestPoint_recursive(point_Self_B, point_Target_B, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SBTB, 1);

      if (arr_pathArr_PossiblePath_SATA.isEmpty()
          && arr_pathArr_PossiblePath_SATB.isEmpty()
          && arr_pathArr_PossiblePath_SBTA.isEmpty()
          && arr_pathArr_PossiblePath_SBTB.isEmpty()) {
        //_________________________________________________________________________________________________
        find_Path_permutation_recursive(point_Self_A, point_Target_A, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SATA, true, null);
        find_Path_permutation_recursive(point_Self_A, point_Target_B, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SATB, true, null);
        find_Path_permutation_recursive(point_Self_B, point_Target_A, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SBTA, true, null);
        find_Path_permutation_recursive(point_Self_B, point_Target_B, new LinkedList<RoadwayDirLinkerComponent>(), arr_pathArr_PossiblePath_SBTB, true, null);
      }

    }
    else {
      throw new Error();
    }

    //_____________________________________________________________________________________________

    //_____________________________

    //_______________________________________________________________________________________

    for (LinkedList<RoadwayDirLinkerComponent> pathArr_curr : arr_pathArr_PossiblePath_SATA) {
      pathArr_curr.addFirst(roadwayDirLinkerComponent_btGStoSA);
      pathArr_curr.addLast(roadwayDirLinkerComponent_btTAtoGT);

      pathArr_curr.addFirst(roadwayDirLinkerComponent_btPStoGS);
    }
    for (LinkedList<RoadwayDirLinkerComponent> pathArr_curr : arr_pathArr_PossiblePath_SATB) {
      pathArr_curr.addFirst(roadwayDirLinkerComponent_btGStoSA);
      pathArr_curr.addLast(roadwayDirLinkerComponent_btTBtoGT);

      pathArr_curr.addFirst(roadwayDirLinkerComponent_btPStoGS);
    }
    for (LinkedList<RoadwayDirLinkerComponent> pathArr_curr : arr_pathArr_PossiblePath_SBTA) {
      pathArr_curr.addFirst(roadwayDirLinkerComponent_btGStoSB);
      pathArr_curr.addLast(roadwayDirLinkerComponent_btTAtoGT);

      pathArr_curr.addFirst(roadwayDirLinkerComponent_btPStoGS);
    }
    for (LinkedList<RoadwayDirLinkerComponent> pathArr_curr : arr_pathArr_PossiblePath_SBTB) {
      pathArr_curr.addFirst(roadwayDirLinkerComponent_btGStoSB);
      pathArr_curr.addLast(roadwayDirLinkerComponent_btTBtoGT);

      pathArr_curr.addFirst(roadwayDirLinkerComponent_btPStoGS);
    }

    //________________________________________________________________________________________________________________________________________________
    final LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath = new LinkedList<>();
    arr_pathArr_PossiblePath.addAll(arr_pathArr_PossiblePath_SATA);
    arr_pathArr_PossiblePath.addAll(arr_pathArr_PossiblePath_SATB);
    arr_pathArr_PossiblePath.addAll(arr_pathArr_PossiblePath_SBTA);
    arr_pathArr_PossiblePath.addAll(arr_pathArr_PossiblePath_SBTB);

    return arr_pathArr_PossiblePath;
  }

  //______

  @Main
  private void find_Path_ClosestPoint_recursive(RoadwayPoint roadwayPoint_ongoing, RoadwayPoint roadwayPoint_end, LinkedList<RoadwayDirLinkerComponent> pathArr_Planning_buildOn_prev, LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath, final long sn_recursion) {
    //_____________________________________________________________

    Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_rowRoadwayDirLinkerComponent_nearby_ongoing = roadwayPoint_ongoing.getMppRoadwayDirLinkerComponent();

    Point point_EE = roadwayPoint_end.getPointLocation();

    RoadwayDirLinkerComponent rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = null;
    Double distance_closest = null;
    for (Entry<RoadwayPoint, RoadwayDirLinkerComponent> pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr : mpp_rowRoadwayDirLinkerComponent_nearby_ongoing.entrySet()) {
      RoadwayPoint roadwayPoint_ongoing_nearby = pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getKey();
      RoadwayDirLinkerComponent rowRoadwayDirLinkerComponent_nearby_ongoing_curr = pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getValue();
      if (roadwayPoint_ongoing_nearby == roadwayPoint_end) {
        pathArr_Planning_buildOn_prev.add(rowRoadwayDirLinkerComponent_nearby_ongoing_curr);
        arr_pathArr_PossiblePath.add(pathArr_Planning_buildOn_prev);
        return;
      }
      else {
        RoadwaySegment roadwaySegment_ongoing_nearby = rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getRoadwaySegment();

        boolean visisted = false;
        for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_visited_curr : pathArr_Planning_buildOn_prev) {
          if (roadwayDirLinkerComponent_visited_curr.getRoadwaySegment() == roadwaySegment_ongoing_nearby) {
            visisted = true;
            break;
          }
        }

        if (visisted) {
          //___________________________________
          continue;
        }
        else {
          if (mpp_rowRoadwayDirLinkerComponent_nearby_ongoing.size() == 1) {
            rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = rowRoadwayDirLinkerComponent_nearby_ongoing_curr;
          }
          else {
            Point point_BB = roadwayPoint_ongoing_nearby.getPointLocation();

            double distance_curr = point_BB.distance(point_EE);
            //____________________
            if (distance_closest == null || distance_curr < distance_closest) {
              rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = rowRoadwayDirLinkerComponent_nearby_ongoing_curr;
              distance_closest                                         = distance_curr;
            }
          }
        }
      }
    }

    //________________________
    if (rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest == null) {
      //_________________________
    }
    else {
      pathArr_Planning_buildOn_prev.add(rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest);
      //________________________________________________________________________________________________________________________________
      find_Path_ClosestPoint_recursive(rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest.getRoadwayPoint(), roadwayPoint_end, pathArr_Planning_buildOn_prev, arr_pathArr_PossiblePath, sn_recursion + 1);
    }

  }

  //_____

  //_______________________________________________________________________________
  //
  //_____________________________________________________________________________________________________________
  //
  //_______________________________________________________________
  //
  //_______________________________________________
  //
  //_______________________________________________________________________________________________________
  //______________________________________

  @Main
  @MainImp
  private boolean find_Path_permutation_recursive(RoadwayPoint roadwayPoint_ongoing, final RoadwayPoint roadwayPoint_end,
                                                  LinkedList<RoadwayDirLinkerComponent> pathArr_Planning_buildOn_prev,
                                                  final LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath,
                                                  final boolean mode_ShortCircuit,
                                                  @Deprecated final HashSet<RoadwayPoint> gp_ScopeOfSearch) {

    Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_rowRoadwayDirLinkerComponent_nearby_ongoing = roadwayPoint_ongoing.getMppRoadwayDirLinkerComponent();

    for (Entry<RoadwayPoint, RoadwayDirLinkerComponent> pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr : mpp_rowRoadwayDirLinkerComponent_nearby_ongoing.entrySet()) {
      RoadwayPoint roadwayPoint_ongoing_nearby = pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getKey();
      //_________________________________________________
      if (gp_ScopeOfSearch != null && !gp_ScopeOfSearch.contains(roadwayPoint_ongoing_nearby)) { continue; }

      //__________________________________________________________________________________________________

      RoadwayDirLinkerComponent rowRoadwayDirLinkerComponent_nearby_ongoing_curr = pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getValue();
      RoadwaySegment roadwaySegment_ongoing_nearby_cast = rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getRoadwaySegment();
      if (roadwaySegment_ongoing_nearby_cast instanceof RoadwaySegmentPseudoBegin) {
        //_________________________________________________________
        //_________________________________________________________________________________________________________________________________
        continue;
      }
      RoadwayNormalSegment roadwaySegment_ongoing_nearby = (RoadwayNormalSegment) roadwaySegment_ongoing_nearby_cast;

      //______________________________________________________________________________________________
      if (roadwayPoint_ongoing_nearby != rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getRoadwayPoint()) { throw new Error("Must match"); }

      boolean visisted = false;
      for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_visited_curr : pathArr_Planning_buildOn_prev) {
        //________________________________________________________________________________________________________________________________________________________
        if (roadwayDirLinkerComponent_visited_curr.getRoadwaySegment() == roadwaySegment_ongoing_nearby) {
          visisted = true;
          break;
        }
      }

      if (visisted) {
        //___________________________________
        continue;
      }
      else {
        //__________________________
        //_______________________________________________________________________________________________________
        //____________________________________________________________________________

        //_______________

        LinkedList<RoadwayDirLinkerComponent> pathArr_Planning_buildOn_curr = new LinkedList<>(pathArr_Planning_buildOn_prev);
        pathArr_Planning_buildOn_curr.add(rowRoadwayDirLinkerComponent_nearby_ongoing_curr);

        if (roadwayPoint_ongoing_nearby == roadwayPoint_end) {
          //__________________________________________________________
          System.out.println(">> find_Path_permutation_recursive()" + " :: " + pathArr_Planning_buildOn_curr);
          arr_pathArr_PossiblePath.add(pathArr_Planning_buildOn_curr);

          if (!mode_ShortCircuit) {
            break;
            //______
            //____________________________________________________________________
            //_________________________________________________________________
            //______________________________________

            //__________________________________________
          }
          else {
            @Messy byte messy; //______________________________________________
            return true;
          }

        }
        else {
          boolean foundFirstOne = find_Path_permutation_recursive(roadwayPoint_ongoing_nearby, roadwayPoint_end, pathArr_Planning_buildOn_curr, arr_pathArr_PossiblePath, mode_ShortCircuit, gp_ScopeOfSearch);
          if (mode_ShortCircuit && foundFirstOne) { return true; }
        }

      }

    }
    return false;

  }

  //_____

  @Deprecated //______________________________
  private void find_Path_NearestSlope_recursive(RoadwayPoint roadwayPoint_ongoing, RoadwayPoint roadwayPoint_end, LinkedList<RoadwayDirLinkerComponent> pathArr_Planning_buildOn_prev, LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath, final long sn_recursion) {
    if (sn_recursion > 1E3) { throw new Error("Inf Loop?"); }

    Map<RoadwayPoint, RoadwayDirLinkerComponent> mpp_rowRoadwayDirLinkerComponent_nearby_ongoing = roadwayPoint_ongoing.getMppRoadwayDirLinkerComponent();

    Point point_AA = roadwayPoint_ongoing.getPointLocation();
    Point point_EE = roadwayPoint_end.getPointLocation();

    Double m_AE_yn = MathUtil.calculateSlopeAndIntersect(point_AA, point_EE).getLeft();

    Double gap_nearest = null;
    Double slope_closest = null;
    RoadwayDirLinkerComponent rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = null;
    for (Entry<RoadwayPoint, RoadwayDirLinkerComponent> pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr : mpp_rowRoadwayDirLinkerComponent_nearby_ongoing.entrySet()) {
      RoadwayPoint roadwayPoint_ongoing_nearby = pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getKey();
      RoadwayDirLinkerComponent rowRoadwayDirLinkerComponent_nearby_ongoing_curr = pair_rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getValue();
      if (roadwayPoint_ongoing_nearby == roadwayPoint_end) {
        pathArr_Planning_buildOn_prev.add(rowRoadwayDirLinkerComponent_nearby_ongoing_curr);
        arr_pathArr_PossiblePath.add(pathArr_Planning_buildOn_prev);
        return;
      }
      else {
        if (mpp_rowRoadwayDirLinkerComponent_nearby_ongoing.size() == 1) {

        }
        //_______________
        RoadwaySegment roadwaySegment_ongoing_nearby_cast = rowRoadwayDirLinkerComponent_nearby_ongoing_curr.getRoadwaySegment();

        Point point_BB = roadwayPoint_ongoing_nearby.getPointLocation();

        Double m_AB_yn = MathUtil.calculateSlopeAndIntersect(point_AA, point_BB).getLeft();

        if (m_AB_yn == m_AE_yn) {
          rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = rowRoadwayDirLinkerComponent_nearby_ongoing_curr;
          break;
        }
        else {
          if (m_AE_yn == null) {
            if (slope_closest == null) {
              rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = rowRoadwayDirLinkerComponent_nearby_ongoing_curr;
              slope_closest                                            = m_AB_yn;
            }
            else {
              if (m_AB_yn > slope_closest) {
                rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = rowRoadwayDirLinkerComponent_nearby_ongoing_curr;
                slope_closest                                            = m_AB_yn;
              }
            }
          }
          else {
            @Messy byte messy; //__________
            if (m_AB_yn == null) { m_AB_yn = 1E8; }
            double gap = Math.abs(m_AB_yn - m_AE_yn);
            if (gap_nearest == null) {
              rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = rowRoadwayDirLinkerComponent_nearby_ongoing_curr;
              gap_nearest                                              = gap;
            }
            else {
              if (gap < gap_nearest) {
                rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest = rowRoadwayDirLinkerComponent_nearby_ongoing_curr;
                gap_nearest                                              = gap;
              }
            }
          }
        }
      }
    }

    pathArr_Planning_buildOn_prev.add(rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest);
    if (rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest.getRoadwayPoint() == roadwayPoint_end) { throw new Error(); }
    find_Path_NearestSlope_recursive(rowRoadwayDirLinkerComponent_nearby_ongoing_curr_closest.getRoadwayPoint(), roadwayPoint_end, pathArr_Planning_buildOn_prev, arr_pathArr_PossiblePath, sn_recursion + 1);
  }

  //__________

  public LinkedList<RoadwayDirLinkerComponent> filterPathShortest(LinkedList<LinkedList<RoadwayDirLinkerComponent>> arr_pathArr_PossiblePath) {
    double length_min = -1;
    LinkedList<RoadwayDirLinkerComponent> pathArr_min = null;
    for (LinkedList<RoadwayDirLinkerComponent> pathArr_curr : arr_pathArr_PossiblePath) {
      double length_curr = 0;
      for (RoadwayDirLinkerComponent roadwayDirLinkerComponent_curr : pathArr_curr) {
        RoadwaySegment roadwaySegment_curr = roadwayDirLinkerComponent_curr.getRoadwaySegment();
        if (roadwaySegment_curr instanceof RoadwaySegmentPseudoBegin) { continue; }
        length_curr += roadwaySegment_curr.length(); //
      }
      if (length_curr < length_min || length_min == -1) {
        length_min  = length_curr;
        pathArr_min = pathArr_curr;
      }
    }
    return pathArr_min;

  }

}
