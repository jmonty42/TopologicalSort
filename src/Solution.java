import java.util.*;

public class Solution {

    public static void main(String[] args) {
        int[][] edgeList = {{1,2},{2,4},{1,3}};
        System.out.println(findOrdering(edgeList));
        int[][] edgeList2 = {};
        System.out.println(findOrdering(edgeList2));
        int[][] edgeList3 = {{1,2},{1,3},{2,4},{2,5}};
        System.out.println(findOrdering(edgeList3));
        int[][] edgeList4 = {{1,2},{1,3},{2,4},{3,4},{4,7},{4,8},{2,5},{3,6}};
        System.out.println(findOrdering(edgeList4));
    }

    public static String findOrdering(int[][] edgeList) {
        // node -> nodes that it depends on
        Map<Integer, Set<Integer>> dependencies = new HashMap<>();
        // Nodes with no incoming edges (they don't depend on other nodes)
        Set<Integer> independentNodes = new HashSet<>();
        for (int[] pair : edgeList) {
            //pair[1] depends on pair[0], pair[0] must come before pair[1] in the final ordering
            if (pair.length != 2) {
                throw new RuntimeException("Edge list input is malformed, pairing does not contain exactly 2 elements: " + Arrays.toString(pair));
            }
            if (!dependencies.containsKey(pair[1])) {
                dependencies.put(pair[1], new HashSet<>());
            }
            dependencies.get(pair[1]).add(pair[0]);
            // if pair[1] was previously independent, it isn't anymore
            independentNodes.remove(pair[1]);
            if (!dependencies.containsKey(pair[0])) {
                dependencies.put(pair[0], new HashSet<>());
                // if pair[0] wasn't in the dependency map before, it is independent so far
                independentNodes.add(pair[0]);
            }
        }

        //System.out.println("dependencies: " + dependencies);
        //System.out.println("independentNodes: " + independentNodes);

        List<Integer> orderedList = new ArrayList<>();

        // independentNodes contains all nodes with either no dependencies or dependencies that are all in the ordered
        // list
        while (!independentNodes.isEmpty()) {
            // pull an independent node out of the set
            Iterator<Integer> setIterator = independentNodes.iterator();
            Integer nextNodeInList = setIterator.next();
            dependencies.remove(nextNodeInList);
            //System.out.println("Processing independent node: " + nextNodeInList);
            // add that node to the ordered list
            orderedList.add(nextNodeInList);
            setIterator.remove();
            //System.out.println("orderedList: " + orderedList);
            //System.out.println("independentNodes: " + independentNodes);
            // Iterator is needed since we'll be modifying the map as we iterate
            Iterator<Map.Entry<Integer, Set<Integer>>> mapIterator = dependencies.entrySet().iterator();
            while(mapIterator.hasNext()) {
                Map.Entry<Integer, Set<Integer>> mapEntry = mapIterator.next();
                if (mapEntry.getValue().contains(nextNodeInList)) {
                    //System.out.println("Removing " + nextNodeInList + " from the dependency set of " + mapEntry.getKey());
                    // if a node is dependent on the node that was just added to the ordered list, remove that node from
                    // the list of dependencies (as it has been satisfied by being placed on the list)
                    mapEntry.getValue().remove(nextNodeInList);
                    //System.out.println("dependencies: " + dependencies);
                    if (mapEntry.getValue().isEmpty()) {
                        //System.out.println(mapEntry.getKey() + " has an empty dependency set now, removing from the map.");
                        // if the dependency list for the node is now empty, add it to the set and remove it from the
                        // map
                        independentNodes.add(mapEntry.getKey());
                        mapIterator.remove();
                        //System.out.println("independentNodes: " + independentNodes);
                        //System.out.println("dependencies: " + dependencies);
                    }
                }
            }
        }

        if (!dependencies.isEmpty()) {
            throw new RuntimeException("The graph represented by the given edge list is cyclic.");
        }

        return orderedList.toString();
    }
}
