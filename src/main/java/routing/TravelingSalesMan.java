package routing;

import com.google.ortools.constraintsolver.*;

/**
 * Created by manu.sharma on 5/30/20
 * Link : https://developers.google.com/optimization/routing/tsp
 */

public class TravelingSalesMan {
    static {
        System.loadLibrary("jniortools");
    }
    static class DataModel {
        public final long[][] distanceMatrix = {
                {0, 2451, 713, 1018, 1631, 1374, 2408, 213, 2571, 875, 1420, 2145, 1972},
                {2451, 0, 1745, 1524, 831, 1240, 959, 2596, 403, 1589, 1374, 357, 579},
                {713, 1745, 0, 355, 920, 803, 1737, 851, 1858, 262, 940, 1453, 1260},
                {1018, 1524, 355, 0, 700, 862, 1395, 1123, 1584, 466, 1056, 1280, 987},
                {1631, 831, 920, 700, 0, 663, 1021, 1769, 949, 796, 879, 586, 371},
                {1374, 1240, 803, 862, 663, 0, 1681, 1551, 1765, 547, 225, 887, 999},
                {2408, 959, 1737, 1395, 1021, 1681, 0, 2493, 678, 1724, 1891, 1114, 701},
                {213, 2596, 851, 1123, 1769, 1551, 2493, 0, 2699, 1038, 1605, 2300, 2099},
                {2571, 403, 1858, 1584, 949, 1765, 678, 2699, 0, 1744, 1645, 653, 600},
                {875, 1589, 262, 466, 796, 547, 1724, 1038, 1744, 0, 679, 1272, 1162},
                {1420, 1374, 940, 1056, 879, 225, 1891, 1605, 1645, 679, 0, 1017, 1200},
                {2145, 357, 1453, 1280, 586, 887, 1114, 2300, 653, 1272, 1017, 0, 504},
                {1972, 579, 1260, 987, 371, 999, 701, 2099, 600, 1162, 1200, 504, 0},
        };
        public final int vehicleNumber = 1;
        public final int depot = 0;
    }

    public static void main(String[] args) {
        DataModel dataModel = new DataModel();
        RoutingIndexManager manager = new RoutingIndexManager(dataModel.distanceMatrix.length, dataModel.vehicleNumber, dataModel.depot);
        RoutingModel routingModel = new RoutingModel(manager);

        int transitCallbackIndex = routingModel.registerTransitCallback((long fromIndex, long toIndex)->{
           int fromNode = manager.indexToNode(fromIndex);
           int toNode = manager.indexToNode(toIndex);
           return dataModel.distanceMatrix[fromNode][toNode];
        });

        routingModel.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters()
                .toBuilder()
                .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                .build();

        Assignment solution = routingModel.solveWithParameters(searchParameters);
        printSolution(routingModel, manager, solution);
    }

    private static void printSolution(RoutingModel routingModel, RoutingIndexManager routingIndexManager, Assignment solution){
        System.out.println("Objective : "+solution.objectiveValue()+" miles");
        System.out.println("Route : ");
        long routeDistance = 0;
        String route = "";
        long index = routingModel.start(0);
        while (!routingModel.isEnd(index)){
            route += routingIndexManager.indexToNode(index)+"->";
            long previousIndex = index;
            index = solution.value(routingModel.nextVar(index));
            routeDistance += routingModel.getArcCostForVehicle(previousIndex, index, 0);
        }
        route += routingIndexManager.indexToNode(routingModel.end(0));
        System.out.println(route);
        System.out.println("Route distance : "+routeDistance);
    }
}
