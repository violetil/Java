package byow.Core;

import java.util.*;

public class HallwayGenerator {
    private ArrayList<Hallway> hallLists;
    private int hallNumbers;
    private Random random;

    public HallwayGenerator() {
        hallLists = new ArrayList<>();
        hallNumbers = 0;
    }

    /** Call this function to connect all rooms by create hallway. */
    public void connectAllRooms(Random r, ArrayList<Room> rooms) {
        random = r;
        List<List<Room>> connectedComponents = findConnectedComponents(rooms);
        // Connect all rooms.
        while (connectedComponents.size() > 1) {
            int index1 = RandomUtils.uniform(random, connectedComponents.size());
            int index2;
            do {
                index2 = RandomUtils.uniform(random, connectedComponents.size());
            } while (index1 == index2);

            Room r1 = connectedComponents.get(index1).get(0);
            Room r2 = connectedComponents.get(index2).get(0);
            connectRooms(r1, r2);
            connectedComponents = findConnectedComponents(rooms);
        }
    }

    private List<List<Room>> findConnectedComponents(List<Room> rooms) {
        Map<Room, List<Room>> graph = new HashMap<>();
        buildGraph(rooms, graph);
        // Construct the map.
        for (Room room1 : rooms) {
            for (Room room2 : rooms) {
                if (room1 != room2 && isOverlapping(room1, room2)) {
                    graph.computeIfAbsent(room1, k -> new ArrayList<>()).add(room2);
                    graph.computeIfAbsent(room2, k -> new ArrayList<>()).add(room1);
                }
            }
        }

        // Find all connecting component.
        Set<Room> visited = new HashSet<>();
        List<List<Room>> connectedComponents = new ArrayList<>();
        for (Room room : rooms) {
            if (!visited.contains(room)) {
                List<Room> component = new ArrayList<>();
                dfs(room, graph, visited, component);
                connectedComponents.add(component);
            }
        }

        return connectedComponents;
    }

    private void buildGraph(List<Room> rooms, Map<Room, List<Room>> graph) {
        for (Room room1 : rooms) {
            for (Room room2 : rooms) {
                if (room1 != room2 && isOverlapping(room1, room2)) {
                    graph.computeIfAbsent(room1, k -> new ArrayList<>()).add(room2);
                    graph.computeIfAbsent(room2, k -> new ArrayList<>()).add(room1);
                }
            }
        }
    }

    private void dfs(Room room, Map<Room, List<Room>> graph, Set<Room> visited, List<Room> component) {
        visited.add(room);
        component.add(room);
        for (Room next : graph.getOrDefault(room, new ArrayList<>())) {
            if (!visited.contains(next)) {
                dfs(next, graph, visited, component);
            }
        }
    }

    /** Connect rooms by create a hallway between them. */
    private void connectRooms(Room r1, Room r2) {
        hallLists.add(new Hallway(r1.getCenter(), r2.getCenter()));
        hallNumbers += 1;
    }

    private boolean isOverlapping(Room r1, Room r2) {
        int xDis = Math.abs(r1.getCenter().x - r2.getCenter().x);
        int yDis = Math.abs(r1.getCenter().y - r2.getCenter().y);
        if (xDis < r1.getWidth()/2 + r2.getWidth()/2 - 1 && yDis < r1.getHeight()/2 + r2.getHeight()/2 - 1) {
            return true;
        } else {
            for (Hallway h : hallLists) {
                if ((r1.getCenter().equals(h.getStart()) && r2.getCenter().equals(h.getEnd())) ||
                        (r1.getCenter().equals(h.getEnd()) && r2.getCenter().equals(h.getStart()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Hallway> getHallLists() {
        return hallLists;
    }
}
