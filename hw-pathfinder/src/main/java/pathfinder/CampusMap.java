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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

import static pathfinder.PathFinder.dijkstraPath;

public class CampusMap implements ModelAPI {
//    private Graph<CampusBuilding, Double> campusGraph;
Graph<CampusBuilding, Double> campusGraph = createGraph("campus_buildings.csv", "campus_paths.csv");
    private List<CampusBuilding> listOfBuildings;

    @Override
    public boolean shortNameExists(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        for (CampusBuilding building : campusGraph.getNodes()) {
            if (building.getShortName().equals(shortName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String longNameForShort(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        for (CampusBuilding building: campusGraph.getNodes()) {
            if (building.getShortName().equals(shortName)) {
                return building.getLongName();
            }
        }
        throw new IllegalArgumentException("This building doesn't exist");
    }

    @Override
    public Map<String, String> buildingNames() {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        Map<String, String> shortToLongNames = new HashMap<>();
        for (CampusBuilding building : campusGraph.getNodes()) {
            shortToLongNames.put(building.getShortName(), building.getLongName());
        }
        return shortToLongNames;
    }

    @Override
    public Path<CampusBuilding> findShortestPath(String startShortName, String endShortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        CampusBuilding startingBuilding = null;
        CampusBuilding endingBuilding = null;
        for(CampusBuilding building : campusGraph.getNodes()) {
            if (building.getShortName().equals(startShortName)) {
                startingBuilding = building;
            } else if (building.getShortName().equals(endShortName)) {
                endingBuilding = building;
            }
        }
        return dijkstraPath(startingBuilding, endingBuilding, campusGraph);
    }

    /**
     * Constructs a graph using the data from the two inputted files
     *
     * @param buildingsFilename one file to be used to build the graph
     * @param pathsFilename one file to be used to build the graph
     * @return graph created using the data from both files
     */
    public static Graph<CampusBuilding, Double> createGraph(String buildingsFilename, String pathsFilename) {
        Set<CampusBuilding> buildings = new HashSet<>(CampusPathsParser.parseCampusBuildings(buildingsFilename));
        Graph<CampusBuilding, Double> campusGraph = new Graph<>();
        Set<CampusPath> paths = new HashSet<>(CampusPathsParser.parseCampusPaths(pathsFilename));
        for (CampusBuilding b : buildings) {
            campusGraph.addNode(b);
        }
        Map<CampusBuilding, HashMap<CampusBuilding, TreeSet<Double>>> coords = new HashMap<>();
        for (CampusPath path : paths) {
            CampusBuilding start = new CampusBuilding("", "", path.getX1(), path.getY1());
            CampusBuilding end = new CampusBuilding("", "", path.getX2(), path.getY2());
            double distance = path.getDistance();
            coords.putIfAbsent(start, new HashMap<>());
            coords.get(start).putIfAbsent(end, new TreeSet<>());
            coords.get(start).get(end).add(distance);
        }
        for (CampusBuilding start : coords.keySet()) {
            boolean foundEnd = false;
            for (CampusBuilding node : campusGraph.getNodes()) {
                if (node.getX() == start.getX() && node.getY() == start.getY()) {
                    foundEnd = true;
                    break;
                }
            }
            if (!foundEnd) {
                campusGraph.addNode(start);
            }
        }
        for (CampusBuilding start : coords.keySet()) {
            HashMap<CampusBuilding, TreeSet<Double>> edges = coords.get(start);
            for (CampusBuilding end : edges.keySet()) {
                double distance = edges.get(end).first();
                for (CampusBuilding n1 : campusGraph.getNodes()) {
                    if (n1.getX() == start.getX() && n1.getY() == start.getY()) {
                        for (CampusBuilding n2 : campusGraph.getNodes()) {
                            if (n2.getX() == end.getX() && n2.getY() == end.getY()) {
                                campusGraph.addEdge(n1, n2, distance);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return campusGraph;
    }

}
