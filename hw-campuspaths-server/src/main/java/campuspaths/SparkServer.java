/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.parser.CampusBuilding;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.*;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.

        CampusMap campusMap = new CampusMap();
        Map<Integer, List<Double>> map = new HashMap<>();
        Spark.get("/findPath", new Route() {

            @Override
            public Object handle(Request req, Response rep) throws Exception {
             Gson gson = new Gson();

                String start = req.queryParams("startName");
                String end = req.queryParams("endName");
                Path<CampusBuilding> pathWay = campusMap.findShortestPath(start, end);
                Iterator<Path<CampusBuilding>.Segment> iterator = pathWay.iterator();
                int iterate = 1;
                while(iterator.hasNext()) {
                    Path<CampusBuilding>.Segment segmentOfLine = iterator.next();
                    map.put(iterate, new ArrayList<>());
                    map.get(iterate).add(segmentOfLine.getStart().getX());
                    map.get(iterate).add(segmentOfLine.getStart().getY());
                    map.get(iterate).add(segmentOfLine.getEnd().getX());
                    map.get(iterate).add(segmentOfLine.getEnd().getY());
                    iterate++;
                }
                return gson.toJson(map.values());
            }
        });
    }

}
