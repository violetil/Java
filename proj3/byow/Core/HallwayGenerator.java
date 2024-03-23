package byow.Core;

import java.util.*;

public class HallwayGenerator {
    private ArrayList<Hallway> hallLists;
    private int hallNumbers;

    public HallwayGenerator(ArrayList<Hallway> hs, int n) {
        hallLists = hs;
        hallNumbers = n;
    }

    public void connectRooms(ArrayList<Room> rooms) {
        Map<Room, List<Room>> graph = new HashMap<>();
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

        // Generate the hall lists.
        if (connectedComponents.size() < 1) return;
        Room r1 = connectedComponents.get(0).get(0);
        for (List<Room> l : connectedComponents) {
            Room r2 = l.get(0);
            if (r1 != r2) generateHall(r1.getCenter(), r2.getCenter());
        }
    }

    private void generateHall(Position s, Position e) {
        hallLists.add(new Hallway(s, e));
        hallNumbers += 1;
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

    private boolean isOverlapping(Room r1, Room r2) {
        int xDis = Math.abs(r1.getCenter().x - r2.getCenter().x);
        int yDis = Math.abs(r1.getCenter().y - r2.getCenter().y);
        if (xDis < r1.getWidth()/2 + r2.getWidth()/2 + 1 || yDis < r1.getHeight()/2 + r2.getHeight()/2 + 1) {
            return true;
        }
        return false;
    }
}
